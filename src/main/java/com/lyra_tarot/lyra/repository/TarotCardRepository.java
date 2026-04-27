package com.lyra_tarot.lyra.repository;

import com.lyra_tarot.lyra.model.TarotCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarotCardRepository extends JpaRepository<TarotCard, Long> {}