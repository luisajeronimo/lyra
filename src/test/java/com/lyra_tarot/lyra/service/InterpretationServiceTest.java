package com.lyra_tarot.lyra.service;

import com.google.genai.Client;
import com.google.genai.Models; 
import com.google.genai.types.GenerateContentResponse;
import com.lyra_tarot.lyra.config.exception.IntegracaoGeminiException;
import com.lyra_tarot.lyra.model.Arcano;
import com.lyra_tarot.lyra.model.Signo;
import com.lyra_tarot.lyra.model.TarotCard;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterpretationServiceTest {

    @Mock
    private Client geminiClient;

    @Mock
    private Models modelsMock; 

    @InjectMocks
    private InterpretationService interpretationService;

    private User usuario;
    private TarotCard carta;

    @BeforeEach
    void setUp() {
        usuario = new User();
        usuario.setNome("TestUser");
        usuario.setSigno(Signo.AQUARIO);

        carta = new TarotCard();
        carta.setNome("A Estrela");
        carta.setArcano(Arcano.MAIOR);
        carta.setElemento("Ar");
        carta.setNumero(18);
        carta.setSignificadoGeral("Luz.");

        ReflectionTestUtils.setField(interpretationService, "geminiModel", "gemini-2.0-flash");
        ReflectionTestUtils.setField(interpretationService, "promptTemplate", "%s %s %s %s %s %s %s %s %s");

        ReflectionTestUtils.setField(geminiClient, "models", modelsMock);
    }

    @Test
    @DisplayName("Retorna a interpretação gerada pela IA com sucesso")
    void RetornaInterpretacaoComSucesso() {
        String respostaEsperada = "Hoje será um dia de esperança e luz.";
        GenerateContentResponse responseMock = mock(GenerateContentResponse.class);
        when(responseMock.text()).thenReturn(respostaEsperada);
        
        when(modelsMock.generateContent(anyString(), anyString(), any()))
                .thenReturn(responseMock);

        String resultado = interpretationService.interpretarCartaDoDia(usuario, carta);

        assertEquals(respostaEsperada, resultado);
    }

    @Test
    @DisplayName("Lança IntegracaoGeminiException quando o método recover for acionado")
    void LancaExcecaoNoRecover() {
        Exception erroOriginal = new RuntimeException("Erro de conexão");

        assertThrows(IntegracaoGeminiException.class, () -> {
            interpretationService.recover(erroOriginal, usuario, carta);
        });
    }
}