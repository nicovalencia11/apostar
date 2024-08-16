package com.apostar.apostarbackendresultados.feign.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultadosDTO {
    private Integer LOTERIA_ID;
    private String NOMBRE;
    private String NUMERO_SORTEO;
    private String NUMERO_GANADOR;
    private String SERIE;
    private Integer TIPO_LOTERIA_ID;
}
