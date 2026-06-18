package com.lyra_tarot.lyra.service;

import com.google.genai.Client;
import com.google.genai.Models; 
import com.google.genai.types.GenerateContentResponse;
import com.lyra_tarot.lyra.config.exception.IntegracaoGeminiException;
import com.lyra_tarot.lyra.model.Arcano;
import com.lyra_tarot.lyra.model.Signo;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.dto.TarotCardDTO;
import com.lyra_tarot.lyra.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterpretationServiceTest {

    @Mock
    private Client geminiClient;

    @Mock
    private Models modelsMock; 

    // 1. Adicionamos o Mock do nosso novo Builder
    @Mock
    private LeituraDoDiaPromptBuilder promptBuilder;

    @InjectMocks
    private InterpretationService interpretationService;

    private User usuario;
    private TarotCardDTO cartas;

    @BeforeEach
    void setUp() {
        usuario = new User();
        usuario.setNome("TestUser");
        usuario.setSigno(Signo.AQUARIO);

        TarotCard carta = new TarotCard();
        carta.setNome("A Estrela");
        carta.setArcano(Arcano.MAIOR);
        carta.setElemento("Ar");
        carta.setNumero(18);
        carta.setSignificadoGeral("Luz.");
        
        cartas = new TarotCardDTO(carta, carta);

        ReflectionTestUtils.setField(interpretationService, "geminiModel", "gemini-2.0-flash");

        ReflectionTestUtils.setField(geminiClient, "models", modelsMock);
    }

    @Test
    @DisplayName("Retorna a interpretação gerada pela IA com sucesso")
    void RetornaInterpretacaoComSucesso() {
        String promptGeradoPeloBuilder = "Texto simulado do prompt final";
        String respostaEsperada = "Hoje será um dia de esperança e luz.";

        when(promptBuilder.buildPrompt(usuario, cartas)).thenReturn(promptGeradoPeloBuilder);

        GenerateContentResponse responseMock = mock(GenerateContentResponse.class);
        when(responseMock.text()).thenReturn(respostaEsperada);

        when(modelsMock.generateContent(eq("gemini-2.0-flash"), eq(promptGeradoPeloBuilder), isNull()))
                .thenReturn(responseMock);

        String resultado = interpretationService.interpretarCartaDoDia(usuario, cartas);

        assertEquals(respostaEsperada, resultado);
        
        verify(promptBuilder, times(1)).buildPrompt(usuario, cartas);
    }

    @Test
    @DisplayName("Lança IntegracaoGeminiException quando o método recover for acionado")
    void LancaExcecaoNoRecover() {
        Exception erroOriginal = new RuntimeException("Erro de conexão");

        assertThrows(IntegracaoGeminiException.class, () -> {
            interpretationService.recover(erroOriginal, usuario, cartas);
        });
    }
}