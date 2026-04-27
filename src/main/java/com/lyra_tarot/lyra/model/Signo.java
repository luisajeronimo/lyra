package com.lyra_tarot.lyra.model;

import java.time.LocalDate;

public enum Signo {
    ARIES(3, 21, 4, 19), TOURO(4, 20, 5, 20), GEMEOS(5, 21, 6, 20),
    CANCER(6, 21, 7, 22), LEAO(7, 23, 8, 22), VIRGEM(8, 23, 9, 22),
    LIBRA(9, 23, 10, 22), ESCORPIAO(10, 23, 11, 21), SAGITARIO(11, 22, 12, 21),
    CAPRICORNIO(12, 22, 1, 19), AQUARIO(1, 20, 2, 18), PEIXES(2, 19, 3, 20);

    private final int mesInicio, diaInicio, mesFim, diaFim;

    Signo(int mesInicio, int diaInicio, int mesFim, int diaFim) {
        this.mesInicio = mesInicio;
        this.diaInicio = diaInicio;
        this.mesFim = mesFim;
        this.diaFim = diaFim;
    }

    public static Signo descobrirPorData(LocalDate data) {
        
        if (data == null) return null;
        int mes = data.getMonthValue();
        int dia = data.getDayOfMonth();

        for (Signo s : values()) {
            if ((mes == s.mesInicio && dia >= s.diaInicio) || (mes == s.mesFim && dia <= s.diaFim)) {
                return s;
            }
        }

        return CAPRICORNIO;
    }
}