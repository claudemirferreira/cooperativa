package com.siscred.cooperativa.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pauta_id")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String nome;

}
