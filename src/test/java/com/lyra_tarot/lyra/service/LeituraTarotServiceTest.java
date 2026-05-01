package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.repository.LeituraTarotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeituraTarotServiceTest {

    @Mock
    private LeituraTarotRepository repository;

    @InjectMocks
    private LeituraTarotService leituraTarotService;

    private User usuario;
    private TarotCard carta;

    @BeforeEach
    void setUp() {
        usuario = new User();
        usuario.setId(1L);
        usuario.setNome("TestUser");

        carta = new TarotCard();
        carta.setId(10L);
        carta.setNome("A Estrela");
    }

    @Test
    @DisplayName("Retorna true quando o usuário já tem uma leitura no dia atual")
    void RetornaTrueQuandoUsuarioJaLeuHoje() {
        LeituraTarot leituraExistente = new LeituraTarot();
        when(repository.findByUserAndDataLeituraBetween(eq(usuario), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.of(leituraExistente));

        boolean leituraRealizadaHoje = leituraTarotService.verificarLeituraDoDia(usuario);

        assertTrue(leituraRealizadaHoje, "O método deveria retornar true pois já existe leitura");
        
        verify(repository, times(1)).findByUserAndDataLeituraBetween(any(), any(), any());
    }

    @Test
    @DisplayName("Retorna FALSE quando o usuário ainda NÃO tem leitura no dia")
    void RetornaFalseQuandoUsuarioNaoLeuHoje() {
        when(repository.findByUserAndDataLeituraBetween(eq(usuario), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Optional.empty());

        boolean leituraRealizadaHoje = leituraTarotService.verificarLeituraDoDia(usuario);

        assertFalse(leituraRealizadaHoje, "O método deveria retornar false pois NÃO existe leitura");
    }

    @Test
    @DisplayName("Salva uma nova leitura com sucesso")
    void SalvaNovaLeituraComSucesso() {
        when(repository.save(any(LeituraTarot.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String textoInterpretacao = "Hoje será um dia de esperança e luz.";

        LeituraTarot leituraSalva = leituraTarotService.salvarLeitura(usuario, carta, textoInterpretacao);

        assertNotNull(leituraSalva);
        assertEquals("TestUser", leituraSalva.getUser().getNome());
        assertEquals("A Estrela", leituraSalva.getCartaTarot().getNome());
        assertEquals(textoInterpretacao, leituraSalva.getLeitura());
        assertNotNull(leituraSalva.getDataLeitura(), "A data de leitura deve ter sido preenchida");
        
        verify(repository, times(1)).save(any(LeituraTarot.class));
    }
}