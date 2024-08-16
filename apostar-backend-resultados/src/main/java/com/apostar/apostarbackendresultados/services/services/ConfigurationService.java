package com.apostar.apostarbackendresultados.services.services;

import com.apostar.apostarbackendresultados.entities.Configuration;

public interface ConfigurationService {

    /**
     * Metodo que permite crear una configuracion para el sistema
     * @param configuration
     * @return
     * @throws Exception
     */
    Configuration createOrUpdateConfiguration(Configuration configuration) throws Exception;

    Configuration getConfiguration(Integer code) throws Exception;

}
