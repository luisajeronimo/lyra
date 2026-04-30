package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.dto.LeituraTarotResponseDTO;
import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.service.IInterpretationService;
import com.lyra_tarot.lyra.service.ITarotService;
import com.lyra_tarot.lyra.service.ILeituraTarotService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "http://localhost:5173") // URL padrão do Vite/React
@RestController
@RequestMapping("/api/interpretacao")
@Tag(name = "Interpretação da Carta Tarot do dia", description = "Interpretação personalizada da carta de Tarot sorteada para o dia, baseada no signo do usuário")
public class InterpretationController {

    @Autowired
    private ITarotService tarotService;

    @Autowired
    private IInterpretationService interpretationService;

    @Autowired
    private ILeituraTarotService leituraTarotService;

    @PostMapping("/gerar-leitura-do-dia")
    public ResponseEntity<?> gerarLeituraDoDia(@AuthenticationPrincipal User usuarioLogado) {
        
        if (leituraTarotService.verificarLeituraDoDia(usuarioLogado)) {
            return ResponseEntity.badRequest().body("Você já tirou sua carta hoje!");
        }

        TarotCard cartaSorteada = tarotService.sortearCarta();
        String leituraFinal = interpretationService.interpretarCartaDoDia(usuarioLogado, cartaSorteada);
        
        leituraTarotService.salvarLeitura(usuarioLogado, cartaSorteada, leituraFinal);

        return ResponseEntity.status(201).body("Leitura do dia gerada com sucesso!");
    }

    @GetMapping("/leitura-de-hoje")
    public ResponseEntity<?> buscarLeituraDeHoje(@AuthenticationPrincipal User usuarioLogado) {
        Optional<LeituraTarot> leitura = leituraTarotService.buscarLeituraDeHoje(usuarioLogado);
        if (leitura.isPresent()) {
            return ResponseEntity.ok(new LeituraTarotResponseDTO(leitura.get()));
        }
        return ResponseEntity.status(404).body("Nenhuma carta foi tirada hoje.");
    }
}