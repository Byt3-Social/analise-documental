package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.enums.StatusProcesso;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "Processo")
@Table(name = "processos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String cnpj;
    private String dataAbertura;
    private String nomeEmpresarial;
    private String nomeFantasia;
    private String porte;
    @Embedded
    private Endereco endereco;
    private String email;
    private String telefone;
    private Date dataCriacao;
    private Date dataAtualizacao;
    @Enumerated(value = EnumType.STRING)
    private StatusProcesso status;
    @Embedded
    private Responsavel responsavel;
    private String link;
    private String feedback;
}
