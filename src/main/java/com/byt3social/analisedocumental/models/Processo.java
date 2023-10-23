package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "Processo")
@Table(name = "processos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "cadastro_id")
    private Integer cadastroId;
    private String cnpj;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_abertura")
    private LocalDate dataAbertura;
    @Column(name = "nome_empresarial")
    private String nomeEmpresarial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    private String porte;
    @Embedded
    private Endereco endereco;
    private String email;
    private String telefone;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
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
    private List<DocumentoSolicitado> documentosSolicitados = new ArrayList<>();
    @OneToMany(mappedBy = "processo", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DadoSolicitado> dadosSolicitados = new ArrayList<>();

    public Processo(OrganizacaoDTO organizacaoDTO) {
        this.cadastroId = organizacaoDTO.id();
        this.cnpj = organizacaoDTO.cnpj();
        this.nomeEmpresarial = organizacaoDTO.nome();
        this.email = organizacaoDTO.email();
        this.telefone = organizacaoDTO.telefone();
        this.responsavel = new Responsavel(organizacaoDTO.responsavel());
        this.endereco = new Endereco();
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
        this.dataAbertura = processoDTO.dataAbertura();

        if(processoDTO.nomeEmpresarial() != null) {
            this.nomeEmpresarial = processoDTO.nomeEmpresarial();
        }

        this.nomeFantasia = processoDTO.nomeFantasia();

        if(processoDTO.endereco() != null) {
            this.endereco = new Endereco(processoDTO.endereco());
        }

        this.porte = processoDTO.porte();
        this.email = processoDTO.email();
        this.telefone = processoDTO.telefone();
        this.status = processoDTO.status();

        if(processoDTO.responsavel() != null) {
            this.responsavel = new Responsavel(processoDTO.responsavel());
        }

        this.uuid = processoDTO.uuid();
        this.feedback = processoDTO.feedback();
    }

    public void atualizarStatus(StatusProcesso status) {
        this.status = status;
    }
}
