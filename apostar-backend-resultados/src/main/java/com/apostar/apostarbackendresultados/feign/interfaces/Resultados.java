package com.apostar.apostarbackendresultados.feign.interfaces;

import com.apostar.apostarbackendresultados.feign.dtos.ResultadosDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "resultadosClient", url = "http://localhost:8080")
public interface Resultados {

    @GetMapping("/")
    ResultadosDTO getResultados();

}
