package com.lyra_tarot.lyra.service;

import com.google.genai.types.GenerateContentResponse;
import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.model.User;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.google.genai.Client;

@Service
public class InterpretationService implements IInterpretationService {

    @Override
    public String interpretarCartaDoDia(User user, TarotCard carta) {
        Client client = new Client();


        // Prompt Engineering
        String prompt = String.format(
            "Você é o Oráculo Lyra, uma IA mística especialista em Tarot e Astrologia. " +
            "Realize uma leitura personalizada para o(a) consulente %s, que é do signo de %s. " +
            "A data de hoje é %s. Considere os trânsitos planetários atuais para este dia (ex: posição do Sol, Lua e planetas regentes). " +
            "A carta sorteada foi: '%s' (Arcano %s, Elemento %s, Número %d). " +
            "Significado base: %s. " +
            "Instrução: Conecte a energia da carta com a personalidade do signo de %s e as influências do céu de hoje. " +
            "Seja profundo, poético e traga um conselho prático ao final.",
            user.getNome(), 
            user.getSigno(), 
            user.getDataHoje().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
            carta.getNome(), 
            carta.getArcano(), 
            carta.getElemento(), 
            carta.getNumero(),
            carta.getSignificadoGeral(),
            user.getSigno()
        );

        try {
            // Chamada para o modelo Gemini
            GenerateContentResponse response = client.models.generateContent("gemini-2.5-flash", prompt, null);
            return response.text();
        } catch (Exception e) {
            return "Ocorreu uma interferência nas energias astrais... (Erro: " + e.getMessage() + ")";
        }
    }
}
