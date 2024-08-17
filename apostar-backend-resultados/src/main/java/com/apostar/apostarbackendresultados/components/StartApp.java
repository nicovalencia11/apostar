package com.apostar.apostarbackendresultados.components;

import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import com.apostar.apostarbackendresultados.services.implementation.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
import java.util.concurrent.ScheduledFuture;

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
            if (configuration != null) {
                taskScheduler = new ThreadPoolTaskScheduler();
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
        String resp = "";
        Configuration configuration;
        try {
            configuration = configurationService.getConfiguration(1);
            String url = configuration.getEndPoint();
            System.out.println(url);
            resp =  restTemplate.getForObject(url,String.class);
            System.out.println(resp);
            configuration.setBackground(generateImage(configuration));
            configuration = configurationService.createOrUpdateConfiguration(configuration);
            System.out.println(resp);
            emailService.enviarEmail("nicolasvalenciamadrid@gmail.com", "prueba", "Envio de imagen con los resultados", configuration.getBackground());
            System.out.println("funcionaaaaa");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public byte[] generateImage(Configuration configuration) throws IOException {
        int width = 250;
        int height = 250;

        ByteArrayInputStream bais = new ByteArrayInputStream(configuration.getBackground());
        BufferedImage bufferedImage = ImageIO.read(bais);

        Graphics2D g2d = bufferedImage.createGraphics();

        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLUE);

        g2d.drawString("Texto de ejemplo", 50, 50);

        g2d.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
        } catch (IOException e) {
            return new byte[111];
        }

        return baos.toByteArray();
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
}
