package com.apostar.apostarbackendresultados.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class Loteria {
    @JsonProperty("LOTERIA_ID")
    private int LOTERIA_ID;

    @JsonProperty("NOMBRE")
    private String NOMBRE;

    @JsonProperty("NUMERO_SORTEO")
    private String NUMERO_SORTEO;

    @JsonProperty("NUMERO_GANADOR")
    private String NUMERO_GANADOR;

    @JsonProperty("SERIE")
    private String SERIE;

    @JsonProperty("TIPO_LOTERIA_ID")
    private int TIPO_LOTERIA_ID;
}