package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.SocioDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "Socio")
@Table(name = "socios")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
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

    public Socio(SocioDTO socioDTO, Processo processo) {
        this.nome = socioDTO.nome();
        this.cpf = socioDTO.cpf();
        this.qualificacao = socioDTO.qualificacao();
        this.processo = processo;
    }

    public void atualizar(SocioDTO socioDTO, Processo processo) {
        this.nome = socioDTO.nome();
        this.cpf = socioDTO.cpf();
        this.qualificacao = socioDTO.qualificacao();
        this.processo = processo;
    }
}
