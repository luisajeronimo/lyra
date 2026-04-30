package com.lyra_tarot.lyra.repository;

import org.springframework.stereotype.Repository;
import com.lyra_tarot.lyra.model.LeituraTarot;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface LeituraTarotRepository extends JpaRepository<LeituraTarot, Long> {}
