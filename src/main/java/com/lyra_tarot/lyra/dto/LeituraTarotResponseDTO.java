package com.lyra_tarot.lyra.dto;

import java.time.LocalDateTime;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.Signo;

public record LeituraTarotResponseDTO(
    Long id,
    String nomeCarta,
    String imagemUrl,
    String leituraTexto,
    LocalDateTime dataLeitura,
    Signo signo
) {

    public LeituraTarotResponseDTO(LeituraTarot leitura) {
        this(
            leitura.getId(),
            leitura.getCartaTarot().getNome(),
            leitura.getCartaTarot().getImagemUrl(),
            leitura.getLeitura(),
            leitura.getDataLeitura(),
            leitura.getUser().getSigno()
        );
    }
}