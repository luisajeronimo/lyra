package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.UserRepository;
import com.lyra_tarot.lyra.service.IInterpretationService;
import com.lyra_tarot.lyra.service.ITarotService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interpretacao")
@Tag(name = "Interpretação da Carta Tarot do dia", description = "Interpretação personalizada da carta de Tarot sorteada para o dia, baseada no signo do usuário")
public class InterpretationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ITarotService tarotService;

    @Autowired
    private IInterpretationService interpretationService;

    @PostMapping("/informarDados")
        @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dados informados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Algum dado não informado")
    })
    public ResponseEntity<String> realizarConsulta(@Valid @RequestBody User user) {

        User usuarioSalvo = userRepository.save(user);

        TarotCard cartaSorteada = tarotService.sortearCarta();

        String leituraFinal = interpretationService.interpretarCartaDoDia(usuarioSalvo, cartaSorteada);

        return ResponseEntity.status(201).body(leituraFinal);
    }
}