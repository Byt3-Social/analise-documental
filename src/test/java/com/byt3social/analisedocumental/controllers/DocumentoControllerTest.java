package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.DocumentoDTO;
import com.byt3social.analisedocumental.models.Documento;
import com.byt3social.analisedocumental.repositories.DocumentoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DocumentoControllerTest {

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Test
    @DisplayName("Deve retornar status 200 ao consultar todos os documentos")
    public void testConsultarDocumentos() throws Exception {
        mockMvc.perform(get("/documentos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testConsultarDocumento() throws Exception {
        Documento documento = new Documento(new DocumentoDTO("TestDado", true));
        documentoRepository.save(documento);

        mockMvc.perform(get("/documentos/{id}", documento.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar consultar um documento que não existe")
    public void testConsultarDocumentoInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/documentos/{id}", 12345)  
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 201, já que os valores são válidos")
    public void testCadastrarDocumento() throws Exception {
        DocumentoDTO documentoDTO = new DocumentoDTO("NewDocumento", true);

        mockMvc.perform(MockMvcRequestBuilders.post("/documentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(documentoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar status 400, já que os valores são nulos")
    public void testCadastrarDocumentoComValoresNulos() throws Exception {
        DocumentoDTO documentoDTO = new DocumentoDTO(null, null);

        mockMvc.perform(MockMvcRequestBuilders.post("/documentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(documentoDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Deve retornar status 204 após a alteração de um documento existente")
    public void testAlterarDocumento() throws Exception {
        Documento documento = new Documento(new DocumentoDTO("TestDocumento", true));
        documentoRepository.save(documento);

        DocumentoDTO updatedDocumentoDTO = new DocumentoDTO("UpdatedDocumento", false);

        mockMvc.perform(MockMvcRequestBuilders.put("/documentos/{id}", documento.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedDocumentoDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar alterar um documento que não existe")
    public void testAlterarDocumentoInexistente() throws Exception {
        DocumentoDTO updatedDocumentoDTO = new DocumentoDTO("UpdatedDocumento", false);

        mockMvc.perform(MockMvcRequestBuilders.put("/documentos/{id}", 12345)  
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedDocumentoDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 200 após a exclusão de um documento existente")
    public void testExcluirDocumento() throws Exception {
        Documento documento = new Documento(new DocumentoDTO("TestDocumento", true));
        documentoRepository.save(documento);

        mockMvc.perform(MockMvcRequestBuilders.delete("/documentos/{id}", documento.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar excluir um documento que não existe")
    public void testExcluirDocumentoInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/documentos/{id}", 12345)  
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
 
}
