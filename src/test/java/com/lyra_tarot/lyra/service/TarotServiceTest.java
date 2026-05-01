package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.repository.TarotCardRepository;
import com.lyra_tarot.lyra.service.TarotService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarotServiceTest {

    @Mock
    private TarotCardRepository repository;

    @InjectMocks
    private TarotService tarotService; 

    private List<TarotCard> baralhoFalso;

    @BeforeEach
    void setUp() {
        TarotCard carta1 = new TarotCard();
        carta1.setId(1L);
        carta1.setNome("O Louco");
        TarotCard carta2 = new TarotCard();
        carta2.setId(2L);
        carta2.setNome("O Mago");
        TarotCard carta3 = new TarotCard();
        carta3.setId(3L);
        carta3.setNome("A Sacerdotisa");

        baralhoFalso = Arrays.asList(carta1, carta2, carta3);
    }

    @Test
    @DisplayName("Retorna uma carta quando o banco contém cartas")
    void SorteiaCartaComSucesso() {
        when(repository.findAll()).thenReturn(baralhoFalso);

        TarotCard cartaSorteada = tarotService.sortearCarta();

        assertNotNull(cartaSorteada, "A carta sorteada não pode ser nula");
        assertTrue(baralhoFalso.contains(cartaSorteada), "A carta sorteada tem que pertencer ao baralho disponível");

        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Lança exceção se tentar sortear mas o banco estiver vazio")
    void SorteiaCartaQuandoBancoVazio() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(RuntimeException.class, () -> {
            tarotService.sortearCarta();
        }, "Deveria lançar um erro ao tentar sortear de um baralho vazio");
    }
}