package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.service.IUserService;
import com.lyra_tarot.lyra.service.IInterpretationService;
import com.lyra_tarot.lyra.service.ITarotService;
import com.lyra_tarot.lyra.service.ILeituraTarotService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/carta-do-dia")
    public ResponseEntity<String> realizarConsulta(@AuthenticationPrincipal User usuarioLogado) {

        TarotCard cartaSorteada = tarotService.sortearCarta();

        String leituraFinal = interpretationService.interpretarCartaDoDia(usuarioLogado, cartaSorteada);

        leituraTarotService.salvarLeitura(usuarioLogado, cartaSorteada, leituraFinal);

        return ResponseEntity.status(200).body(leituraFinal);
    }
}