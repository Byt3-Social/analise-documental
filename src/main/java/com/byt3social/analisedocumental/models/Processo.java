package com.byt3social.analisedocumental.models;

import com.byt3social.analisedocumental.dto.DadoSolicitadoDTO;
import com.byt3social.analisedocumental.dto.DocumentoSolicitadoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.SocioDTO;
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
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
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
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<Socio> socios;
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    @JsonProperty("documentos_solicitados")
    private List<DocumentoSolicitado> documentosSolicitados;
    @OneToMany(mappedBy = "processo", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, orphanRemoval = true)
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

    public void atualizaProcesso(ProcessoDTO dados, List<Dado> listaDados, List<Documento> listaDocumentos) {
        if(dados.cnpj() != null) {
            this.cnpj = dados.cnpj();
        }

        if(dados.dataAbertura() != null) {
            this.dataAbertura = dados.dataAbertura();
        }

        if(dados.nomeEmpresarial() != null) {
            this.nomeEmpresarial = dados.nomeEmpresarial();
        }

        if(dados.nomeFantasia() != null) {
            this.nomeFantasia = dados.nomeFantasia();
        }

        if(dados.endereco() != null) {
            this.endereco = new Endereco(dados.endereco());
        }

        if(dados.porte() != null) {
            this.porte = dados.porte();
        }

        if(dados.email() != null) {
            this.email = dados.email();
        }

        if(dados.telefone() != null) {
            this.telefone = dados.telefone();
        }

        if(dados.dataCriacao() != null) {
            this.dataCriacao = dados.dataCriacao();
        }

        if(dados.status() != null) {
            this.status = dados.status();
        }

        if(dados.dataAtualizacao() != null) {
            this.dataAtualizacao = dados.dataAtualizacao();
        }

        if(dados.responsavel() != null) {
            this.responsavel = new Responsavel(dados.responsavel());
        }

        if(dados.link() != null) {
            this.link = dados.link();
        }

        if(dados.feedback() != null) {
            this.feedback = dados.feedback();
        }

        if(dados.socios() != null) {
            atualizaSocios(dados);
        }

        if(dados.documentosSolicitados() != null) {
            atualizaDocumentosSolicitados(dados, listaDocumentos);
        }

        if(dados.dadosSolicitados() != null) {
            atualizaDadosSolicitados(dados, listaDados);
        }
    }

    private void atualizaDocumentosSolicitados(ProcessoDTO dados, List<Documento> listaDocumentos) {
        ListIterator<DocumentoSolicitado> documentoSolicitadoListIterator
                = this.documentosSolicitados.listIterator();

        while(documentoSolicitadoListIterator.hasNext()) {
            DocumentoSolicitado documentoSolicitado = documentoSolicitadoListIterator.next();

            if(!existeDocumentoSolicitado(dados.documentosSolicitados(), documentoSolicitado.getId())) {
                documentoSolicitadoListIterator.remove();
            } else {
                DocumentoSolicitadoDTO documentosSolicitadosDados = dados.documentosSolicitados().stream().filter(documentoSolicitadoDTO -> documentoSolicitadoDTO.id().equals(documentoSolicitado.getId())).findFirst().get();

                documentoSolicitado.atualizaDocumentoSolicitado(documentosSolicitadosDados, this);
            }
        }

        dados.documentosSolicitados().forEach(documentoSolicitadoDTO -> {
            if(documentoSolicitadoDTO.id() == null) {
                DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(documentoSolicitadoDTO, this, listaDocumentos);
                this.documentosSolicitados.add(documentoSolicitado);
            }
        });
    }

    private void atualizaDadosSolicitados(ProcessoDTO dados, List<Dado> listaDados) {
        ListIterator<DadoSolicitado> dadoSolicitadoListIterator
                = this.dadosSolicitados.listIterator();

        while(dadoSolicitadoListIterator.hasNext()) {
            DadoSolicitado dadoSolicitado = dadoSolicitadoListIterator.next();

            if(!existeDadoSolicitado(dados.dadosSolicitados(), dadoSolicitado.getId())) {
                dadoSolicitadoListIterator.remove();
            } else {
                DadoSolicitadoDTO dadoDTO = dados.dadosSolicitados().stream().filter(dadoSolicitadoDTO -> dadoSolicitadoDTO.id().equals(dadoSolicitado.getId())).findFirst().get();

                dadoSolicitado.atualizaDado(dadoDTO, this);
            }
        }

        dados.dadosSolicitados().forEach(dadoSolicitadoDTO -> {
            if(dadoSolicitadoDTO.id() == null) {
                System.out.println("n existe");
                DadoSolicitado dado = new DadoSolicitado(dadoSolicitadoDTO, this, listaDados);
                this.dadosSolicitados.add(dado);
                System.out.println(dado.getDado().getId());
            }
        });
    }

    private void atualizaSocios(ProcessoDTO dados) {
        ListIterator<Socio> socioListIterator
                = this.socios.listIterator();

        while(socioListIterator.hasNext()) {
            Socio socio = socioListIterator.next();

            if(!existeSocio(dados.socios(), socio.getId())) {
                socioListIterator.remove();
            } else {
                SocioDTO socioDados = dados.socios().stream().filter(socioDTO -> socioDTO.id().equals(socio.getId())).findFirst().get();

                socio.atualizaSocio(socioDados);
            }
        }

        dados.socios().forEach(socioDTO -> {
            if(socioDTO.id() == null) {
                Socio socio = new Socio(socioDTO, this);
                this.socios.add(socio);
            }
        });
    }

    private Boolean existeSocio(List<SocioDTO> socios, Integer id) {
        return socios.stream().anyMatch(socioDTO -> {
            if(socioDTO.id() != null) {
                return socioDTO.id().equals(id);
            }

            return false;
        });
    }

    private Boolean existeDadoSolicitado(List<DadoSolicitadoDTO> dadosSolicitados, Integer id) {
        return dadosSolicitados.stream().anyMatch(dadoSolicitadoDTO -> {
            if(dadoSolicitadoDTO.id() != null) {
                return dadoSolicitadoDTO.id().equals(id);
            }

            return false;
        });
    }

    private Boolean existeDocumentoSolicitado(List<DocumentoSolicitadoDTO> documentosSolicitados, Integer id) {
        return documentosSolicitados.stream().anyMatch(documentoSolicitadoDTO -> {
            if(documentoSolicitadoDTO.id() != null) {
                return documentoSolicitadoDTO.id().equals(id);
            }

            return false;
        });
    }
}
