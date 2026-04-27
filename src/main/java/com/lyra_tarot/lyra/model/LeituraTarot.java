package com.lyra_tarot.lyra.model;

import java.time.LocalDateTime;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "tb_leituras")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LeituraTarot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leitura", columnDefinition = "TEXT", nullable = false)
    private String leitura; 

    @ManyToOne
    @JoinColumn(name = "carta_tarot_id", nullable = false)
    private TarotCard cartaTarot;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "data_nascimento_user", nullable = false)
    private LocalDate dataNascimentoUser;


    @Column(name = "data_leitura", nullable = false, updatable = false)
    private LocalDateTime dataLeitura = LocalDateTime.now();
    
}
