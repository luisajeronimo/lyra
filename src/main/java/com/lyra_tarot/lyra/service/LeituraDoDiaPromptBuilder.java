package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.PosicaoPlaneta;
import com.lyra_tarot.lyra.dto.TarotCardDTO;
import com.lyra_tarot.lyra.model.User;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class LeituraDoDiaPromptBuilder {

    private static final Logger logger = LoggerFactory.getLogger(LeituraDoDiaPromptBuilder.class);
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
            
            CARTAS SORTEADAS (Cartas do Dia):
            - Nome: %s
            - Arcano: %s
            - Elemento: %s
            - Número: %s
            
            CÉU ASTROLÓGICO DO MOMENTO:
            %s
            
            TAREFA:
            Você deve realizar uma leitura personalizada considerando as influências do céu astrológico do momento,
            o signo do consulente e os significados conhecidos das cartas sorteadas. 
            Considere que o Arcano Maior sorteado trás uma visão geral e macro do dia, enquanto o Arcano Menor traz detalhes 
            e nuances da aplicação no dia. Considere também que o elemento das cartas pode indicar a área da vida mais impactada 
            (Fogo = Ação, Terra = Material, Ar = Intelecto, Água = Emoções).
            Considere os significados das cartas, os aspectos astrológicos e o signo do usuário para criar uma 
            interpretação profunda e personalizada para o dia do consulente.
            Considere o número das cartas para entender a intensidade ou o estágio dos acontecimentos 
            (1-3 = Início, 4-6 = Desenvolvimento, 7-10 = Conclusão, 11-14 = Transformação).
            Considere como o céu astrológico do momento pode influenciar a energia geral do dia e como isso se relaciona 
            com as cartas sorteadas.
            Seja profundo, mas prático e focado nas interpretações, e traga um conselho prático ao final. 
            Utilize no máximo 50 linhas para a resposta.

            """;

    public String buildPrompt(User user, TarotCardDTO cartas) {

        List<PosicaoPlaneta> planetas = calculoPosicaoPlanetaService.calcularPosicoesPlanetas();
        
        String dadosCeu = formatarPlanetasParaTexto(planetas);

        String dataHoje = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String promptGerado = String.format(
                PROMPT_TEMPLATE,
                user.getNome(),
                user.getSigno(),
                dataHoje,
                cartas.arcanoMaior().getNome() + " e " + cartas.arcanoMenor().getNome(),
                "Maior e Menor",
                cartas.arcanoMaior().getElemento() + " e " + cartas.arcanoMenor().getElemento(),
                cartas.arcanoMaior().getNumero() + " e " + cartas.arcanoMenor().getNumero(),
                dadosCeu
        );
        
        logger.info("\n\n========== PROMPT GERADO PARA IA ==========\n{}\n========== FIM DO PROMPT ==========", promptGerado);
        
        return promptGerado;
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