package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.*;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.*;

@Entity(name = "Processo")
@Table(name = "processos")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cadastro_id")
    @JsonProperty("cadastro_id")
    private Integer cadastroId;
    private String cnpj;
    @Column(name = "data_abertura")
    @JsonProperty("data_abertura")
    private Date dataAbertura;
    @Column(name = "nome_empresarial")
    @JsonProperty("nome_empresarial")
    private String nomeEmpresarial;
    @Column(name = "nome_fantasia")
    @JsonProperty("nome_fantasia")
    private String nomeFantasia;
    private String porte;
    @Embedded
    private Endereco endereco;
    private String email;
    private String telefone;
    @CreationTimestamp
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private Timestamp dataAtualizacao;
    @Enumerated(value = EnumType.STRING)
    private StatusProcesso status;
    @Embedded
    private Responsavel responsavel;
    private String uuid;
    private String feedback;
    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Socio> socios = new ArrayList<>();
    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonProperty("documentos_solicitados")
    private List<DocumentoSolicitado> documentosSolicitados = new ArrayList<>();
    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonProperty("dados_solicitados")
    private List<DadoSolicitado> dadosSolicitados = new ArrayList<>();

    public Processo(OrganizacaoDTO organizacaoDTO) {
        this.cadastroId = organizacaoDTO.id();
        this.cnpj = organizacaoDTO.cnpj();
        this.nomeEmpresarial = organizacaoDTO.nome();
        this.email = organizacaoDTO.email();
        this.responsavel = new Responsavel(organizacaoDTO.responsavel());
        this.status = StatusProcesso.ABERTO;
        this.uuid = UUID.randomUUID().toString();
    }

    public void vincularDadosSolicitados(List<DadoSolicitado> dadosSolicitados) {
        this.dadosSolicitados = dadosSolicitados;
    }

    public void vincularDocumentosSolicitados(List<DocumentoSolicitado> documentosSolicitados) {
        this.documentosSolicitados = documentosSolicitados;
    }

    public void atualizar(ProcessoDTO processoDTO) {
        if(processoDTO.cnpj() != null) {
            this.cnpj = processoDTO.cnpj();
        }

        if(processoDTO.dataAbertura() != null) {
            this.dataAbertura = processoDTO.dataAbertura();
        }

        if(processoDTO.nomeEmpresarial() != null) {
            this.nomeEmpresarial = processoDTO.nomeEmpresarial();
        }

        if(processoDTO.nomeFantasia() != null) {
            this.nomeFantasia = processoDTO.nomeFantasia();
        }

        if(processoDTO.endereco() != null) {
            this.endereco = new Endereco(processoDTO.endereco());
        }

        if(processoDTO.porte() != null) {
            this.porte = processoDTO.porte();
        }

        if(processoDTO.email() != null) {
            this.email = processoDTO.email();
        }

        if(processoDTO.telefone() != null) {
            this.telefone = processoDTO.telefone();
        }

        if(processoDTO.status() != null) {
            this.status = processoDTO.status();
        }

        if(processoDTO.responsavel() != null) {
            this.responsavel = new Responsavel(processoDTO.responsavel());
        }

        if(processoDTO.uuid() != null) {
            this.uuid = processoDTO.uuid();
        }

        if(processoDTO.feedback() != null) {
            this.feedback = processoDTO.feedback();
        }
    }
}
