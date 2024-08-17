package com.apostar.apostarbackendresultados.services.implementation;

import com.apostar.apostarbackendresultados.entities.Configuration;
import com.apostar.apostarbackendresultados.repositories.ConfigurationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigurationServiceImplTest {

    @Mock
    private ConfigurationRepository configurationRepository;

    @InjectMocks
    private ConfigurationServiceImpl configurationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateConfiguration() throws Exception {
        int id = 1;

        Configuration configuration = new Configuration();
        configuration.setCode(id);

        when(configurationRepository.save(configuration)).thenReturn(configuration);

        Configuration result = configurationService.createOrUpdateConfiguration(configuration);

        assertNotNull(result);
        assertEquals(id, result.getCode());

        verify(configurationRepository, times(1)).save(configuration);
    }

    @Test
    void testGetConfiguration() throws Exception {
        int id = 1;

        Configuration configuration = new Configuration();
        configuration.setCode(id);

        when(configurationRepository.findByCode(1)).thenReturn(configuration);

        Configuration result = configurationService.getConfiguration(1);

        assertNotNull(result);
        assertEquals(id, result.getCode());

        verify(configurationRepository, times(1)).findByCode(1);
    }

    @Test
    void testGetAllConfigurations() throws Exception {
        int id = 1;
        int id2 = 2;

        Configuration configuration1 = new Configuration();
        configuration1.setCode(id);

        Configuration configuration2 = new Configuration();
        configuration2.setCode(id2);

        List<Configuration> configurations = Arrays.asList(configuration1, configuration2);

        when(configurationRepository.findAll()).thenReturn(configurations);

        List<Configuration> result = configurationService.getAllConfigurations();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(id, result.get(0).getCode());

        verify(configurationRepository, times(1)).findAll();
    }
}
