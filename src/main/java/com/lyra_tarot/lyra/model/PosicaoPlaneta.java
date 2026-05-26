package com.lyra_tarot.lyra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PosicaoPlaneta {

    private String nomePlaneta;
    private String signo;
    private String casaTransito;
    private boolean retrogrado;
}
