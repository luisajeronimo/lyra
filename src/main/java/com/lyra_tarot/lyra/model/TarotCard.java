package com.lyra_tarot.lyra.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_cartas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TarotCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "numero", nullable = false)
    private int numero; // 0-21 para Arcanos Maiores

    @Column(name = "elemento", nullable = false)
    private String elemento; // Fogo, Água, Ar, Terra

    @Column(name = "imagem_url")
    private String imagemUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "arcano")
    private Arcano arcano; // Maior ou Menor

    @Column(columnDefinition = "TEXT")
    private String significadoGeral;
}