package com.byt3social.analisedocumental.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "DadoSolicitado")
@Table(name = "dados_solicitados")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class DadoSolicitado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String valor;
    private Boolean obrigatorio;
    @Column(name = "processo_id")
    private Integer processoId;
    @Column(name = "dado_id")
    private Integer dadosId;
}
