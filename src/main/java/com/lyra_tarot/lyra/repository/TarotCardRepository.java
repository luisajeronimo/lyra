package com.lyra_tarot.lyra.repository;

import com.lyra_tarot.lyra.model.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
import com.lyra_tarot.lyra.model.Arcano;


@Repository
public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {

    Optional<TarotCard> findByNome(String nome);
    List<TarotCard> findByArcano(Arcano arcano);
}