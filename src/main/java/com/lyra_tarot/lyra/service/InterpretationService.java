package com.lyra_tarot.lyra.service;

import com.google.genai.types.GenerateContentResponse;
import com.lyra_tarot.lyra.config.exception.IntegracaoGeminiException;
import com.lyra_tarot.lyra.dto.TarotCardDTO;
import com.lyra_tarot.lyra.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Recover;
import com.google.genai.Client;

@Service
public class InterpretationService implements IInterpretationService {

    private final Client geminiClient;
    private final LeituraDoDiaPromptBuilder promptBuilder;
    private final String geminiModel;

    public InterpretationService(Client geminiClient, LeituraDoDiaPromptBuilder promptBuilder, @Value("${lyra.gemini.model}") String geminiModel) {
        this.geminiClient = geminiClient;
        this.promptBuilder = promptBuilder;
        this.geminiModel = geminiModel;
    }

    @Override
    @Retryable(
        retryFor = { Exception.class },
        maxAttemptsExpression = "${lyra.gemini.retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${lyra.gemini.retry.delay}")
    )
    public String interpretarCartaDoDia(User user, TarotCardDTO cartas) {
        String promptFinal = promptBuilder.buildPrompt(user, cartas);

        GenerateContentResponse response = geminiClient.models.generateContent(geminiModel, promptFinal, null);
        return response.text();
    }

    @Recover
    public String recover(Exception e, User user, TarotCardDTO cartas) {
        throw new IntegracaoGeminiException("Ocorreu uma interferência nas energias astrais e o oráculo não pôde responder agora. Tente novamente em alguns instantes.");
    }
}
