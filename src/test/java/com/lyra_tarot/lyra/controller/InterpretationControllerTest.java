package com.lyra_tarot.lyra.controller;

import com.lyra_tarot.lyra.config.security.TokenService;
import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.Signo;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import com.lyra_tarot.lyra.service.IInterpretationService;
import com.lyra_tarot.lyra.service.ILeituraTarotService;
import com.lyra_tarot.lyra.service.ITarotService;
import com.lyra_tarot.lyra.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InterpretationController.class)
@AutoConfigureMockMvc(addFilters = false) 
class InterpretationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ITarotService tarotService;

    @MockitoBean
    private IInterpretationService interpretationService;

    @MockitoBean
    private ILeituraTarotService leituraTarotService;

    private User usuarioMock;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private IUserService userService;

    @BeforeEach
    void setUp() {
        usuarioMock = new User();
        usuarioMock.setNome("TestUser");
    }

    @Test
    @DisplayName("Gera leitura do dia com sucesso e retorna 201")
    @WithMockUser
    void GeraLeituraComSucesso() throws Exception {
        // Arrange
        when(leituraTarotService.verificarLeituraDoDia(any())).thenReturn(false);
        when(tarotService.sortearCarta()).thenReturn(new TarotCard());
        when(interpretationService.interpretarCartaDoDia(any(), any())).thenReturn("Interpretação de teste");

        // Act & Assert
        mockMvc.perform(post("/api/interpretacao/gerar-leitura-do-dia"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Leitura do dia gerada com sucesso!"));

        // Verifica se o save foi chamado
        verify(leituraTarotService, times(1)).salvarLeitura(any(), any(), anyString());
    }

    @Test
    @DisplayName("Retorna 400 se o usuário já realizou leitura do dia")
    @WithMockUser
    void RetornaErroSeJaLeuHoje() throws Exception {
        when(leituraTarotService.verificarLeituraDoDia(any())).thenReturn(true);

        mockMvc.perform(post("/api/interpretacao/gerar-leitura-do-dia"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Você já tirou sua carta hoje!"));
    }

    @Test
    @DisplayName("Retorna 200 e a leitura")
    @WithMockUser
    void BuscaLeituraDeHojeComSucesso() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setSigno(Signo.AQUARIO); 

        TarotCard carta = new TarotCard();
        carta.setNome("A Estrela");
        carta.setImagemUrl("");

        LeituraTarot leitura = new LeituraTarot();
        leitura.setId(1L);
        leitura.setLeitura("Hoje será um dia de esperança e luz.");
        leitura.setDataLeitura(LocalDateTime.now()); 
        leitura.setUser(user);
        leitura.setCartaTarot(carta);

        when(leituraTarotService.buscarLeituraDeHoje(any())).thenReturn(Optional.of(leitura));

        mockMvc.perform(get("/api/interpretacao/leitura-de-hoje"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeCarta").value("A Estrela"))
                .andExpect(jsonPath("$.leituraTexto").value("Hoje será um dia de esperança e luz."))
                .andExpect(jsonPath("$.signo").value("AQUARIO"));
    }
    
    @Test
    @DisplayName("Retorna 404 quando não houver leitura para hoje")
    @WithMockUser
    void Retorna404QuandoNaoHouverLeitura() throws Exception {
        when(leituraTarotService.buscarLeituraDeHoje(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/interpretacao/leitura-de-hoje"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Nenhuma carta foi tirada hoje."));
    }
}