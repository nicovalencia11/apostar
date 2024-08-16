package com.apostar.apostarbackendresultados.feign.services;

import com.apostar.apostarbackendresultados.feign.dtos.ResultadosDTO;
import com.apostar.apostarbackendresultados.feign.interfaces.Resultados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultadosService {

    @Autowired
    private Resultados resultadosFeignClient;

    public ResultadosDTO getUserById(Long id) {
        return resultadosFeignClient.getResultados();
    }

}
