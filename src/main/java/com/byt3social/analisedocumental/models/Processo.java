package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.ProcessoDTO;
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
import java.util.List;
import java.util.UUID;

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
    private String cnpj;
    @Column(name = "data_abertura")
    @JsonProperty("data_abertura")
    private String dataAbertura;
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
    @Column(name = "data_criacao", updatable = false)
    @JsonProperty("data_criacao")
    private Timestamp dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    @JsonProperty("data_atualizacao")
    private Timestamp dataAtualizacao;
    @Enumerated(value = EnumType.STRING)
    private StatusProcesso status;
    @Embedded
    private Responsavel responsavel;
    private String link;
    private String feedback;
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Socio> socios;
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonProperty("documentos_solicitados")
    private List<DocumentoSolicitado> documentosSolicitados;
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonProperty("dados_solicitados")
    private List<DadoSolicitado> dadosSolicitados;

    public Processo(ProcessoDTO dadosProcesso) {
        this.cnpj = dadosProcesso.cnpj();
        this.status = StatusProcesso.CRIADO;
        this.link = UUID.randomUUID().toString();
    }

    public void adicionaDadosSolicitados(List<DadoSolicitado> dadosSolicitados) {
        this.dadosSolicitados = dadosSolicitados;
    }

    public void adicionaDocumentosSolicitados(List<DocumentoSolicitado> documentosSolicitados) {
        this.documentosSolicitados = documentosSolicitados;
    }
}
