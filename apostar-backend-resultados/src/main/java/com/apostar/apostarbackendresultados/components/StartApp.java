package com.apostar.apostarbackendresultados.components;

import com.apostar.apostarbackendresultados.dto.Loteria;
import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import com.apostar.apostarbackendresultados.services.implementation.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

@Component
public class StartApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartApp.class);

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Autowired
    private EmailServiceImpl emailService;


    @Autowired
    private RestTemplate restTemplate;

    private TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledTask;

    @Override
    public void run(String... args) {
        try {
            Configuration configuration = getConfiguration();
            taskScheduler = new ThreadPoolTaskScheduler();
            if (configuration != null) {
                ((ThreadPoolTaskScheduler) taskScheduler).initialize();
                scheduledTask = taskScheduler.schedule(this::executeTask, new CronTrigger(generateCronExpression(configuration.getTime())));
            } else {
                logger.error("ERROR: No se puede iniciar la tarea programada, no existe la configuración");
                logger.info("INFO: Por favor, cree la configuración necesaria para la tarea programada");
            }
        } catch (Exception e) {
            logger.error("ERROR: Ocurrió un error al iniciar la tarea programada", e);
        }
    }

    public Configuration getConfiguration() throws Exception {
        Configuration configuration = configurationService.getConfiguration(1);
        if (configuration == null) {
            logger.warn("Advertencia: La configuración solicitada no existe");
        }
        return configuration;
    }

    public void executeTask()  {
        Configuration configuration;
        try {
            configuration = configurationService.getConfiguration(1);
            String url = configuration.getEndPoint();
            byte[] imageBytes = decodeBase64(configuration.getBackground());
            BufferedImage image = convertBytesToImage(imageBytes);
            List<Loteria> loterias = obtenerLoterias(url);
            String b64Image = generateImage(image, loterias);
            byte[] imageBytesSend = Base64.getDecoder().decode(b64Image);
            emailService.enviarEmail(configuration.getEmails(), "prueba", "Envio de imagen con los resultados", imageBytesSend);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage convertBytesToImage(byte[] imageBytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        return ImageIO.read(bais);
    }

    public void rescheduleTask() throws InterruptedException {
        Thread.sleep(2000);
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        try {
            Configuration configuration = getConfiguration();
            if (configuration != null) {
                scheduledTask = taskScheduler.schedule(this::executeTask, new CronTrigger(generateCronExpression(configuration.getTime())));
            } else {
                logger.error("ERROR: No se puede iniciar la tarea programada, no existe la configuración");
                logger.info("INFO: Por favor, cree la configuración necesaria para la tarea programada");
            }
        } catch (Exception e) {
            logger.error("ERROR: Ocurrió un error al iniciar la tarea programada", e);
        }
    }

    public String generateImage(BufferedImage image, List<Loteria> loterias) throws IOException {
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(new Font("Arial", Font.BOLD, 24));

        int y1 = 450;
        int y2 = 700;
        int y3 = 700;
        int numLoterias = 1;

        for (Loteria loteria : loterias) {
            String numeroGanador = loteria.getNUMERO_GANADOR();

            if(loteria.getTIPO_LOTERIA_ID() == 1){
                g2d.setColor(Color.BLACK);
                g2d.drawString(loteria.getNOMBRE(), 150, y1);

                numeroGanador = loteria.getNUMERO_GANADOR() == null ? "" : numeroGanador;
                g2d.setColor(Color.RED);
                g2d.drawString(numeroGanador, 450, y1);

                g2d.setColor(Color.BLACK);
                g2d.drawString("SERIE:", 800, y1);

                String serie = loteria.getSERIE() == null ? "" : loteria.getSERIE();
                g2d.setColor(Color.RED);
                g2d.drawString(serie, 900, y1);
                y1 += 40;
            }

            if (numLoterias < 13 && loteria.getTIPO_LOTERIA_ID() != 1 ) {
                g2d.setColor(Color.BLACK);
                g2d.drawString(loteria.getNOMBRE(), 150, y2);

                numeroGanador = numeroGanador == null ? "" : numeroGanador;
                g2d.setColor(Color.RED);
                g2d.drawString(numeroGanador, 470, y2);

                numLoterias++;
                y2 += 40;
            }else {
                g2d.setColor(Color.BLACK);
                g2d.drawString(loteria.getNOMBRE(), 700, y3);

                numeroGanador = numeroGanador == null ? "" : numeroGanador;
                g2d.setColor(Color.RED);
                g2d.drawString(numeroGanador, 1030, y3);
                y3 += 40;
            }

        }

        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Método que genera una expresión cron a partir de una hora.
     * @param time Hora para la expresión cron.
     * @return Expresión cron generada.
     */
    public String generateCronExpression(LocalTime time) {
        int second = time.getSecond();
        int minute = time.getMinute();
        int hour = time.getHour();
        return String.format("%d %d %d * * ?", second, minute, hour);
    }

    public java.util.List<Loteria> obtenerLoterias(String url) {
        ResponseEntity<java.util.List<Loteria>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Loteria>>() {}
        );
        return response.getBody();
    }
}