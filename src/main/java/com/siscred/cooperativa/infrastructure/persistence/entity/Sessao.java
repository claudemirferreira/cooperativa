package com.siscred.cooperativa.infrastructure.persistence.entity;

import com.siscred.cooperativa.infrastructure.enuns.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessao_id")
    private Long id;

    private OffsetDateTime inicio;

    private OffsetDateTime fim;

    @ManyToOne
    @JoinColumn(name = "pauta_id", nullable = false)
    private Pauta pauta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private StatusEnum status;

}
