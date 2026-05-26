package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.PosicaoPlaneta;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LeituraDoDiaPromptBuilder {

    private final ICalculoPosicaoPlanetaService calculoPosicaoPlanetaService;

    public LeituraDoDiaPromptBuilder(ICalculoPosicaoPlanetaService calculoPosicaoPlanetaService) {
        this.calculoPosicaoPlanetaService = calculoPosicaoPlanetaService;
    }

    private static final String PROMPT_TEMPLATE = """
            Você é o Lyra, um oráculo IA místico especialista em Tarot e Astrologia.
            
            DADOS DO USUÁRIO:
            - Nome: %s
            - Signo Solar: %s
            - Data da Leitura: %s
            
            CARTA SORTEADA (Carta do Dia):
            - Nome: %s
            - Arcano: %s
            - Elemento: %s
            - Número: %s
            
            CÉU ASTROLÓGICO DO MOMENTO:
            %s
            
            TAREFA:
            Você deve realizar uma leitura personalizada considerando as influências destes trânsitos planetários,
            o signo do consulente e interpretação da carta sorteada. 
            Seja profundo, poético e traga um conselho prático ao final. Utilize no máximo 20 linhas para a resposta.

            """;

    public String buildPrompt(User user, TarotCard carta) {

        List<PosicaoPlaneta> planetas = calculoPosicaoPlanetaService.calcularPosicoesPlanetas();
        
        String dadosCeu = formatarPlanetasParaTexto(planetas);

        String dataHoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        return String.format(
                PROMPT_TEMPLATE,
                user.getNome(),
                user.getSigno(),
                dataHoje,
                carta.getNome(),
                carta.getArcano(),
                carta.getElemento(),
                carta.getNumero(),
                dadosCeu
        );
    }

    private String formatarPlanetasParaTexto(List<PosicaoPlaneta> planetas) {
        if (planetas == null || planetas.isEmpty()) {
            return "Dados astrológicos temporariamente indisponíveis devido a interferências.";
        }

        StringBuilder sb = new StringBuilder();
        for (PosicaoPlaneta p : planetas) {
            sb.append("- ")
              .append(p.getNomePlaneta())
              .append(" em ")
              .append(p.getSigno())
              .append(" (Casa ")
              .append(p.getCasaTransito())
              .append(")");

            if (p.isRetrogrado()) {
                sb.append(" [Retrógrado]");
            }
            sb.append("\n"); 
        }
        return sb.toString();
    }
}