package com.apostar.apostarbackendresultados.services.implementation;

import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.repositories.ConfigurationRepository;
import com.apostar.apostarbackendresultados.services.services.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;


    /**
     * Metodo que permite crear una configuracion para el sistema
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Override
    public Configuration createOrUpdateConfiguration(Configuration configuration) throws Exception {
        return configurationRepository.save(configuration);
    }

    @Override
    public Configuration getConfiguration(Integer code) throws Exception {
        return configurationRepository.findByCode(code);
    }
}
