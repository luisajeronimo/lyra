package com.lyra_tarot.lyra.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AstrologyApiPlanetDTO {
    private String name;        // Ex: "Sun"
    private String sign;        // Ex: "Taurus"
    private Double normDegree;  // Ex: 14.5 (Grau dentro do signo)
    private Integer house;      // Ex: 10
}