package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.SocioDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @ManyToOne
    @JoinColumn(name = "processo_id")
    @JsonBackReference
    private Processo processo;

    public Socio(SocioDTO socio, Processo processo) {
        this.nome = socio.nome();
        this.cpf = socio.cpf();
        this.qualificacao = socio.qualificacao();
        this.processo = processo;
    }

    public void atualizaSocio(SocioDTO socio) {
        if(socio.nome() != null) {
            this.nome = socio.nome();
        }

        if(socio.cpf() != null) {
            this.cpf = socio.cpf();
        }

        if(socio.qualificacao() != null) {
            this.qualificacao = socio.qualificacao();
        }
    }
}
