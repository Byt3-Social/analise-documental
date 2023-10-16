package com.byt3social.analisedocumental.controllers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.byt3social.analisedocumental.dto.DadoDTO;
import com.byt3social.analisedocumental.models.Dado;
import com.byt3social.analisedocumental.repositories.DadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DadoControllerTest {

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DadoRepository dadoRepository;

    @Test
    public void testConsultarDados() throws Exception {
        mockMvc.perform(get("/dados"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testConsultarDado() throws Exception {
        Dado dado = new Dado(new DadoDTO(1, "TestDado", "TEXT", true));
        dadoRepository.save(dado);

        mockMvc.perform(get("/dados/{id}", dado.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar consultar um dado que não existe")
    public void testConsultarDadoInexistente() throws Exception {
        mockMvc.perform(get("/dados/{id}", 12345)  
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar status 400, já que os valores são nulos")
    public void testCadastrarDado1() throws Exception {

        var response = mockMvc.perform(post("/dados")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve retornar status 201, já que os valores são válidos")
    public void testCadastrarDado2() throws Exception {
        DadoDTO dadoDTO = new DadoDTO(null, "NewDado", "TEXT", true);

        mockMvc.perform(post("/dados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar status 400, já que o tipo é inválido")
    public void testCadastrarDado3() throws Exception {
        DadoDTO dadoDTO = new DadoDTO(null, "NewDado", "OUTRO", true);

        mockMvc.perform(post("/dados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadoDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAtualizarDado() throws Exception {
        Dado dado = new Dado(new DadoDTO(1, "TestDado", "TEXT", true));
        dadoRepository.save(dado);

        DadoDTO updatedDadoDTO = new DadoDTO(null, "UpdatedDado", "TEXTAREA", false);

        mockMvc.perform(put("/dados/{id}", dado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedDadoDTO)))
                .andExpect(status().isNoContent());

        Dado updatedDado = dadoRepository.findById(dado.getId()).orElse(null);
        assertNotNull(updatedDado);
        assertEquals("UpdatedDado", updatedDado.getNome());
        assertEquals("TEXTAREA", updatedDado.getTipo());
        assertEquals(false, updatedDado.getPadrao());
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar atualizar um dado que não existe")
    public void testAtualizarDadoInexistente() throws Exception {
        DadoDTO updatedDadoDTO = new DadoDTO(null, "UpdatedDado", "TEXTAREA", false);
    
        mockMvc.perform(put("/dados/{id}", 12345) 
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updatedDadoDTO)))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testExcluirDado() throws Exception {
        Dado dado = new Dado(new DadoDTO(1, "TestDado", "TEXT", true));
        dadoRepository.save(dado);

        mockMvc.perform(delete("/dados/{id}", dado.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar status 404 (Not Found) ao tentar excluir um dado que não existe")
    public void testExcluirDadoInexistente() throws Exception {
        mockMvc.perform(delete("/dados/{id}", 12345) 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
