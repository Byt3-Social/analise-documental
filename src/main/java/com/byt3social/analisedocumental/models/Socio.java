package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Socio")
@Table(name = "socios")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
public class Socio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String cpf;
    private String qualificacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "processo_id")
    private Processo processo;
}
