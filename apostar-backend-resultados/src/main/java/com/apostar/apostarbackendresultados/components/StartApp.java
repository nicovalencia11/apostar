package com.apostar.apostarbackendresultados.components;

import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.concurrent.ScheduledFuture;

@Component
public class StartApp implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(StartApp.class);

    @Autowired
    private ConfigurationServiceImpl configurationService;

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

    private Configuration getConfiguration() throws Exception {
        Configuration configuration = configurationService.getConfiguration(1);
        if (configuration == null) {
            logger.warn("Advertencia: La configuración solicitada no existe");
        }
        return configuration;
    }

    private void executeTask() {
        // Implementar lógica de la tarea aquí
    }

    public void rescheduleTask() {
        if (scheduledTask != null) {
            scheduledTask.cancel(false);
        }
        // Aquí deberías obtener una nueva configuración para la reprogramación
        // Configuration configuration = getConfiguration();
        // if (configuration != null) {
        //     scheduledTask = taskScheduler.schedule(this::executeTask, new CronTrigger(generateCronExpression(configuration.getTime())));
        // }
    }

    /**
     * Método que genera una expresión cron a partir de una hora.
     * @param time Hora para la expresión cron.
     * @return Expresión cron generada.
     */
    private String generateCronExpression(LocalTime time) {
        int second = time.getSecond();
        int minute = time.getMinute();
        int hour = time.getHour();
        return String.format("%d %d %d * * ?", second, minute, hour);
    }
}
