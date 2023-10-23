package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "DadoSolicitado")
@Table(name = "dados_solicitados")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class DadoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valor;
    private Boolean obrigatorio;
    @ManyToOne
    @JoinColumn(name = "dado_id")
    private Dado dado;
    @ManyToOne
    @JoinColumn(name = "processo_id")
    @JsonBackReference
    private Processo processo;

    public DadoSolicitado(Dado dado, Processo processo) {
        this.obrigatorio = true;
        this.dado = dado;
        this.processo = processo;
    }

    public void atualizar(DadoSolicitadoDTO dado) {
        this.valor = dado.valor();
    }
}
