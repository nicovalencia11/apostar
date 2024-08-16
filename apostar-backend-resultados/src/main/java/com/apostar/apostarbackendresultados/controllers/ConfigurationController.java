package com.apostar.apostarbackendresultados.controllers;

import com.apostar.apostarbackendresultados.components.StartApp;
import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.services.implementation.ConfigurationServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationServiceImpl configurationService;

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    @PostMapping("/")
    public ResponseEntity<String> createConfiguration(@RequestBody Configuration configuration) {
        try {
            configurationService.createOrUpdateConfiguration(configuration);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error al crear o actualizar la configuración: {}", e.getMessage(), e);
            return new ResponseEntity<>("Error al procesar la solicitud: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
