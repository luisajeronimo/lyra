package com.lyra_tarot.lyra.service;

import com.google.genai.types.GenerateContentResponse;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Recover;

import java.time.LocalDateTime;

import com.google.genai.Client;

@Service
public class InterpretationService implements IInterpretationService {

    @Autowired
    private Client geminiClient;

    @Value("${lyra.gemini.model}")
    private String geminiModel;

    @Value("${lyra.gemini.prompt}")
    private String promptTemplate;

    @Override
    @Retryable(
        retryFor = { Exception.class },
        maxAttemptsExpression = "${lyra.gemini.retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${lyra.gemini.retry.delay}")
    )
    public String interpretarCartaDoDia(User user, TarotCard carta) {
        // Prompt Engineering
        String prompt = String.format(
            promptTemplate,
            user.getNome(), 
            user.getSigno(), 
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
            carta.getNome(), 
            carta.getArcano(), 
            carta.getElemento(), 
            carta.getNumero(),
            carta.getSignificadoGeral(),
            user.getSigno()
        );

        // Chamada para o modelo Gemini 
        GenerateContentResponse response = geminiClient.models.generateContent(geminiModel, prompt, null);
        return response.text();
    }

    @Recover
    public String recover(Exception e, User user, TarotCard carta) {
        return "Ocorreu uma interferência nas energias astrais... (Timeout/Erro final: " + e.getMessage() + ")";
    }
}
