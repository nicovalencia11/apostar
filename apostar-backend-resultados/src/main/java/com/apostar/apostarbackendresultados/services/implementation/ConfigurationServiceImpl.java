package com.apostar.apostarbackendresultados.services.implementation;

import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.repositories.ConfigurationRepository;
import com.apostar.apostarbackendresultados.services.services.ConfigurationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Transactional
    @Override
    public Configuration createOrUpdateConfiguration(Configuration configuration) throws Exception {
        return configurationRepository.save(configuration);
    }

    /**
     * Metodo que permite obtener una configuracion por codigo
     *
     * @param code
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public Configuration getConfiguration(Integer code) throws Exception {
        return configurationRepository.findByCode(code);
    }

    /**
     * Metodo que permite obtener todas las configuraciones
     *
     * @return
     * @throws Exception
     */
    @Transactional
    @Override
    public List<Configuration> getAllConfigurations() throws Exception {
        return configurationRepository.findAll();
    }

}
