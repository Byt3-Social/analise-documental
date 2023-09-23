package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "DadoSolicitado")
@Table(name = "dados_solicitados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class DadoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valor;
    private Boolean obrigatorio;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "dado_id")
    private Dado dado;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "processo_id")
    @JsonBackReference
    private Processo processo;

    public DadoSolicitado(Dado dado, Processo processo) {
        this.obrigatorio = true;
        this.dado = dado;
        this.processo = processo;
    }

    public DadoSolicitado(DadoSolicitadoDTO dado, Processo processo, List<Dado> listaDados) {
        this.obrigatorio = dado.obrigatorio();
        this.valor = dado.valor();

        if(dado.dado().id() != null) {
            this.dado = listaDados.stream().filter(dadoComplementar -> dadoComplementar.getId().equals(dado.dado().id())).findFirst().get();
        } else {
            this.dado = new Dado(dado.dado());
        }

        this.processo = processo;
    }

    public void atualizaDado(DadoSolicitadoDTO dado, Processo processo) {
        if(dado.obrigatorio() != null) {
            this.obrigatorio = dado.obrigatorio();
        }

        if(dado.valor() != null) {
            this.valor = dado.valor();
        }

        if(dado.dado() != null) {
            if(this.dado.getId().equals(dado.dado().id())) {
                this.dado.atualizaDado(dado.dado());
            } else {
                this.dado = new Dado(dado.dado());
            }
        }

        this.processo = processo;
    }
}
