package com.lyra_tarot.lyra.service;

import com.lyra_tarot.lyra.model.TarotCard;
import com.lyra_tarot.lyra.repository.TarotCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import com.lyra_tarot.lyra.model.Arcano;
import com.lyra_tarot.lyra.dto.TarotCardDTO;

@Service
public class SorteiaTarotService implements ISorteiaTarotService {

    @Autowired
    private TarotCardRepository repository;

    @Override
    public TarotCardDTO sortearCarta() {
        List<TarotCard> arcanosMaioresList = repository.findByArcano(Arcano.MAIOR); 
        if (arcanosMaioresList.isEmpty()) {
            throw new RuntimeException("O deck não possui arcanos maiores!");
        }
        Collections.shuffle(arcanosMaioresList); // Embaralha
        TarotCard arcanoMaior = arcanosMaioresList.get(0); // Pega a primeira

        List<TarotCard> arcanosMenoresList = repository.findByArcano(Arcano.MENOR);
        if (arcanosMenoresList.isEmpty()) {
            throw new RuntimeException("O deck não possui arcanos menores!");
        }
        Collections.shuffle(arcanosMenoresList); // Embaralha
        TarotCard arcanoMenor = arcanosMenoresList.get(0); // Pega a primeira

        return new TarotCardDTO(arcanoMaior, arcanoMenor);
    }
}