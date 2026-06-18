package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.Arcano;
import com.lyra_tarot.lyra.model.PosicaoPlaneta;
import com.lyra_tarot.lyra.model.Signo;
import com.lyra_tarot.lyra.dto.TarotCardDTO;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeituraDoDiaPromptBuilderTest {

    @Mock
    private ICalculoPosicaoPlanetaService calculoPosicaoPlanetaService;

    @InjectMocks
    private LeituraDoDiaPromptBuilder promptBuilder;

    private User usuario;
    private TarotCardDTO cartas;
    private String dataDeHoje;

    @BeforeEach
    void setUp() {
        usuario = new User();
        usuario.setNome("Teste");
        usuario.setSigno(Signo.AQUARIO); 

        TarotCard carta = new TarotCard();
        carta.setNome("A Estrela");
        carta.setArcano(Arcano.MAIOR);
        carta.setElemento("Ar");
        carta.setNumero(17);
        
        cartas = new TarotCardDTO(carta, carta);

        dataDeHoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    @Test
    @DisplayName("Deve montar o prompt completo quando a API de astrologia retornar os planetas")
    void constroiPromptComPlanetasComSucesso() {
        PosicaoPlaneta sol = new PosicaoPlaneta("Sun", "Taurus", "10", false);
        sol.setRetrogrado(false);

        PosicaoPlaneta mercurio = new PosicaoPlaneta("Mercury", "Aries", "9", true);
        mercurio.setRetrogrado(true); // Planeta retrógrado para testar a formatação específica

        when(calculoPosicaoPlanetaService.calcularPosicoesPlanetas())
                .thenReturn(List.of(sol, mercurio));

        String promptGerado = promptBuilder.buildPrompt(usuario, cartas);

        assertTrue(promptGerado.contains("Nome: Teste"));
        assertTrue(promptGerado.contains(dataDeHoje));
        assertTrue(promptGerado.contains("Nome: A Estrela"));
        assertTrue(promptGerado.contains("Número: 17"));

        assertTrue(promptGerado.contains("- Sun em Taurus (Casa 10)"));
        assertTrue(promptGerado.contains("- Mercury em Aries (Casa 9) [Retrógrado]")); 
    }

    @Test
    @DisplayName("Deve montar o prompt com aviso de indisponibilidade quando a API não retornar os planetas")
    void constroiPromptComFalhaNosPlanetas() {
        when(calculoPosicaoPlanetaService.calcularPosicoesPlanetas())
                .thenReturn(Collections.emptyList());

        String promptGerado = promptBuilder.buildPrompt(usuario, cartas);

        assertTrue(promptGerado.contains("Nome: Teste"));
        assertTrue(promptGerado.contains("Nome: A Estrela"));

        assertTrue(promptGerado.contains("Dados astrológicos temporariamente indisponíveis"));

        assertTrue(!promptGerado.contains("[Retrógrado]")); 
    }
}