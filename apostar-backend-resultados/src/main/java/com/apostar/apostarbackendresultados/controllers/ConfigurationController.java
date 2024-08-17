package com.apostar.apostarbackendresultados.controllers;

import com.apostar.apostarbackendresultados.components.StartApp;
import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/configuration")
@CrossOrigin(origins = "http://localhost:4200")
public class ConfigurationController {

    @Autowired
    private ConfigurationServiceImpl configurationService;

    @Autowired
    private StartApp startApp;

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    @PostMapping("/")
    public ResponseEntity<String> createOrUpdateConfiguration(@RequestBody Configuration configuration) {
        try {
            configurationService.createOrUpdateConfiguration(configuration);
            startApp.rescheduleTask();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al crear o actualizar la configuración: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<Configuration> getConfigurationById(@PathVariable("code") Integer code) {
        try {
            Configuration configuration = configurationService.getConfiguration(code);
            if (configuration != null) {
                return new ResponseEntity<>(configuration, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error al obtener la configuración: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Configuration>> getAllConfigurations() {
        try {
            List<Configuration> configurations = configurationService.getAllConfigurations();
            return new ResponseEntity<>(configurations, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al obtener todas las configuraciones: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
