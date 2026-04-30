package com.lyra_tarot.lyra.repository;

import org.springframework.stereotype.Repository;
import com.lyra_tarot.lyra.model.LeituraTarot;
import com.lyra_tarot.lyra.model.User;

import java.util.Optional;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LeituraTarotRepository extends JpaRepository<LeituraTarot, Long> {

    Optional<LeituraTarot> findByUserAndDataLeituraBetween(User user, LocalDateTime start, LocalDateTime end);
}
