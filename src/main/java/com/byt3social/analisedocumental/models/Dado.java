package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Dado")
@Table(name = "dados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private Boolean padrao;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "dado_id", referencedColumnName = "id")
    private List<Dado> dados;
}
