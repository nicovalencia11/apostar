package com.apostar.apostarbackendresultados.repositories;

import com.apostar.apostarbackendresultados.entities.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

    /**
     * Busqueda de configuracion por codigo
     * @param code
     * @return
     */
    Configuration findByCode(Integer code);
}
