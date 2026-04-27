package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.repository.TarotCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TarotService implements ITarotService {

    @Autowired
    private TarotCardRepository repository;

    @Override
    public TarotCard sortearCarta() {
        List<TarotCard> todasCartas = repository.findAll();
        if (todasCartas.isEmpty()) {
            throw new RuntimeException("O deck está vazio!");
        }
        Collections.shuffle(todasCartas); // Embaralha
        return todasCartas.get(0); // Pega a primeira
    }
}