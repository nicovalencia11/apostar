package com.apostar.apostarbackendresultados.services.services;

import com.apostar.apostarbackendresultados.entities.Configuration;

import java.util.List;

public interface ConfigurationService {

    /**
     * Metodo que permite crear una configuracion para el sistema
     * @param configuration
     * @return
     * @throws Exception
     */
    Configuration createOrUpdateConfiguration(Configuration configuration) throws Exception;

    /**
     * Metodo que permite obtener una configuracion por codigo
     * @param code
     * @return
     * @throws Exception
     */
    Configuration getConfiguration(Integer code) throws Exception;

    /**
     * Metodo que permite obtener todas las configuraciones
     * @return
     * @throws Exception
     */
    List<Configuration> getAllConfigurations() throws Exception;

}
