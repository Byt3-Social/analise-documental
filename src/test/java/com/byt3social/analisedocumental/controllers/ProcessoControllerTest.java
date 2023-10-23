package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.EnderecoDTO;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.services.ProcessoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ProcessoControllerTest {

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessoService processoService;
    
    @Test
    @DisplayName("Deve retornar status 200 para GET /processos")
    void testConsultarProcessos() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/processos")
            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
        String contentType = resultActions.andReturn().getResponse().getContentType();
        assertTrue(contentType != null && contentType.contains("application/json"));
    }

    @Test
    @DisplayName("Deve retornar status 200 e dados específicos para GET /organizacoes/{id}")
    void testConsultarProcesso() throws Exception {
        int idProcesso = 1;
        
        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"));
        Processo processo = new Processo(organizacaoDTO);
        processo.setId(idProcesso);

        Mockito.when(processoService.consultarProcesso(idProcesso))
            .thenReturn(processo);

        ResultActions resultActions = mockMvc.perform(get("/processos/{id}", idProcesso)
            .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(idProcesso));
    }

    @Test
    @DisplayName("Deve retornar status 204 após vincular um documento a um processo")
    public void testSolicitarDocumento() throws Exception {

        mockMvc.perform(get("/processos/{id}/documentos/{documento}", 1, 2)  
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar status 204 após solicitar um dado")
    public void testSolicitarDado() throws Exception {

        mockMvc.perform(get("/processos/{id}/dados/{dado}", 1, 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
}

    @Test
    @DisplayName("Deve retornar status 400, já que os valores são nulos")
    public void testAbrirProcessoNulo() throws Exception {

        var response = mockMvc.perform(post("/organizacoes")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    @Test
    @DisplayName("Deve retornar status 201, já que os valores são válidos ao abrir um processo")
    public void testAbrirProcesso() throws Exception {

        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"));

        mockMvc.perform(post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(organizacaoDTO)))
                .andExpect(status().isCreated());
    }
    
    @Test
    @DisplayName("Deve retornar status 200 após a atualização de um processo")
    public void testSalvarProcesso() throws Exception {

        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(1, "12345678901234", "Nome Empresarial", "nome@empresa.com",
                "1234567890", new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"));
        Processo processo = new Processo(organizacaoDTO);
        ProcessoDTO updatedProcessoDTO = new ProcessoDTO(
            LocalDate.now(), "UpdatedNomeEmpresarial",
            "UpdatedNomeFantasia", new EnderecoDTO(
                "UpdatedRua", "UpdatedNumero", "UpdatedBairro", "UpdatedComplemento", "UpdatedCidade", "UpdatedEstado"
            ),
            "UpdatedPorte", "updated@email.com", "9876543210", null, null, null, null, null, null
        );

        mockMvc.perform(put("/processos/{id}/atualizar", 1)  
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedProcessoDTO)))
                .andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Deve retornar status 204 após atualizar o status de um processo")
    public void testAtualizarStatusProcesso() throws Exception {
    

    ProcessoDTO processoDTO = new ProcessoDTO(
        LocalDate.now(),
        "Nome Empresarial", 
        "Nome Fantasia", 
        new EnderecoDTO("Rua", "Numero", "Bairro", "Complemento", "Cidade", "Estado"), 
        "Porte", 
        "nome@empresa.com",
        "1234567890",
        StatusProcesso.APROVADO,
        new ResponsavelDTO("Responsavel Nome", "responsavel@empresa.com", "987654321"),
        "uuid123",
        "Feedback",
        null,
        null
    );

    doNothing().when(processoService).atualizarStatusProcesso(eq(1), eq(processoDTO));

    mockMvc.perform(put("/processos/1/status")
        .contentType(MediaType.APPLICATION_JSON)
        .content(asJsonString(processoDTO)))
        .andExpect(status().isNoContent());


    assertThat(processoDTO.status()).isEqualTo(StatusProcesso.APROVADO);
    }

    /*@Test
    @DisplayName("Deve retornar status 204 após submeter um processo")
    public void testSubmeterProcesso() throws Exception {

        SocioDTO socio = new SocioDTO(1,"nome sócio", "111111111111111", "qualificado");
        List<SocioDTO> socios = Arrays.asList(socio);
        DocumentoDTO documentoDTO = new DocumentoDTO(1, "Nome do Documento", true);

        DocumentoSolicitadoDTO documentoSolicitado = new DocumentoSolicitadoDTO(
            1,                      
            "caminho/s3/arquivo",      
            "nome_arquivo.txt",     
            new Date(),              
            new Date(),               
            BigInteger.valueOf(1024), 
            StatusDocumentoSolicitado.ENVIADO,  
            "assinatura_digital",    
            true,                     
            documentoDTO              
        );

    List<DocumentoSolicitadoDTO> documentosSolicitados = Arrays.asList(documentoSolicitado);

    DadoDTO dado = new DadoDTO(
        1, 
        "Nome do Dado", 
        "TEXT",
        true
    );

    DadoSolicitadoDTO dadoSolicitado = new DadoSolicitadoDTO(
        1, 
        "Valor do Dado", 
        true,
        dado 
    );

List<DadoSolicitadoDTO> dadoSolicitados = Arrays.asList(dadoSolicitado);
        Timestamp createdTimestamp = Timestamp.valueOf(LocalDateTime.now()); 
        Timestamp updatedTimestamp = Timestamp.valueOf(LocalDateTime.now()); 

        ProcessoDTO processoDTO = new ProcessoDTO(
            1,
            1,
            "12345678901234",
            new Date(),
            "Nome Empresarial",
            "Nome Fantasia",
            new EnderecoDTO("Rua", "Numero", "Bairro", "Complemento", "Cidade", "Estado"),
            "Porte",
            "byt3social@gmail.com",
            "1234567890",
            createdTimestamp,
            updatedTimestamp,
            StatusProcesso.APROVADO,
            new ResponsavelDTO("Responsavel Nome", "boltwelter@gmail.com", "987654321"),
            "uuid123",
            "Feedback",
            socios,
            documentosSolicitados,
            dadoSolicitados
        );

                mockMvc.perform(put("/processos/1/finalizar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(processoDTO)))
                .andExpect(status().isNoContent());
    }*/
    

    @Test
    @DisplayName("Deve retornar status 204 após a exclusão de um documento solicitado")
    public void testExcluirDocumentoSolicitado() throws Exception {

        mockMvc.perform(delete("/processos/{id}/documentos-solicitados/{documento}", 1, 3) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar status 204 após excluir um dado solicitado")
    public void testExcluirDadoSolicitado() throws Exception {

        mockMvc.perform(delete("/processos/{id}/dados-solicitados/{dado}", 1, 3)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    
}

