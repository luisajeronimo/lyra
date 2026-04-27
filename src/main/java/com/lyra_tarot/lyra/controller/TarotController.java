package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.service.ITarotService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tarot")
@Tag(name = "Tarot", description = "Sorteio da carta de Tarot que definirá seu dia")
public class TarotController {

    @Autowired
    private ITarotService tarotService;

    @GetMapping("/sortear")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Carta sorteada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Carta não encontrada")
    })
    public ResponseEntity<TarotCard> sortear() {
        TarotCard cartaSorteada = tarotService.sortearCarta();
        return ResponseEntity.ok(cartaSorteada);
    }
}
