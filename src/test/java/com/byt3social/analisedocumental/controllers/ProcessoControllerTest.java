package com.byt3social.analisedocumental.controllers;

import com.byt3social.analisedocumental.dto.EnderecoDTO;
import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.dto.PDSignProcessoDTO;
import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.dto.ResponsavelDTO;
import com.byt3social.analisedocumental.enums.StatusProcesso;
import com.byt3social.analisedocumental.models.DadoSolicitado;
import com.byt3social.analisedocumental.models.DocumentoSolicitado;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Autowired
    private ProcessoController processoController;

    @MockBean
    private ProcessoService processoService;

    @Test
    public void testConsultarProcessos() throws Exception {
        when(processoService.consultarProcessos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/processos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(processoService, times(1)).consultarProcessos();
    }

    @Test
    public void testConsultarProcessosOrganizacao() throws Exception {
        String organizacaoId = "1";
        when(processoService.consultarProcessosOrganizacao(eq(1))).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/processos/organizacoes")
                .header("B3Social-Organizacao", organizacaoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(processoService, times(1)).consultarProcessosOrganizacao(eq(1));
    }

    @Test
    public void testConsultarProcesso() throws Exception {
        Integer processoID = 1;
        Processo processo = new Processo(); // Criar um objeto de exemplo
        when(processoService.consultarProcesso(eq(processoID))).thenReturn(processo);

        mockMvc.perform(get("/processos/{id}", processoID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));

        verify(processoService, times(1)).consultarProcesso(eq(processoID));
    }

    @Test
    public void testConsultarProcessoPDSign() throws Exception {
        Integer processoID = 1;
        List<PDSignProcessoDTO> pdSignProcessoDTOList = Collections.emptyList();
        when(processoService.consultarProcessoPDSign(eq(processoID))).thenReturn(pdSignProcessoDTOList);

        mockMvc.perform(get("/processos/{id}/pdsign", processoID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(processoService, times(1)).consultarProcessoPDSign(eq(processoID));
    }

    @Test
    public void testAbrirProcesso() throws Exception {
        OrganizacaoDTO organizacaoDTO = new OrganizacaoDTO(null, null, null, null, null, null); // Criar um objeto de exemplo
        doNothing().when(processoService).abrirProcesso(eq(organizacaoDTO));

        mockMvc.perform(post("/processos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(organizacaoDTO)))
                .andExpect(status().isCreated());

        verify(processoService, times(1)).abrirProcesso(eq(organizacaoDTO));
    }

    @Test
    public void testSalvarProcesso() throws Exception {
        Integer processoID = 1;
        ProcessoDTO processoDTO = new ProcessoDTO(null, null, null, null, null, null, null, null, null, null, null, null, null); // Criar um objeto de exemplo

        mockMvc.perform(put("/processos/{id}/atualizar", processoID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(processoDTO)))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).salvarProcesso(eq(processoID), eq(processoDTO), eq(false));
    }

    @Test
    public void testSolicitarDocumento() throws Exception {
        Integer processoID = 1;
        Integer documentoID = 1;
        DocumentoSolicitado documentoSolicitado = new DocumentoSolicitado(); // Criar um objeto de exemplo
        when(processoService.vincularDocumentoAoProcesso(eq(processoID), eq(documentoID))).thenReturn(documentoSolicitado);

        mockMvc.perform(post("/processos/{id}/documentos/{documento}", processoID, documentoID))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).vincularDocumentoAoProcesso(eq(processoID), eq(documentoID));
    }

    @Test
    public void testExcluirDocumentoSolicitado() throws Exception {
        Integer processoID = 1;
        Integer documentoSolicitadoID = 1;
        doNothing().when(processoService).desvincularDocumentoDoProcesso(eq(processoID), eq(documentoSolicitadoID));

        mockMvc.perform(delete("/processos/{id}/documentos-solicitados/{documento}", processoID, documentoSolicitadoID))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).desvincularDocumentoDoProcesso(eq(processoID), eq(documentoSolicitadoID));
    }

    @Test
    public void testSolicitarDado() throws Exception {
        Integer processoID = 1;
        Integer dadoID = 1;
        DadoSolicitado dadoSolicitado = new DadoSolicitado(); // Criar um objeto de exemplo
        when(processoService.vincularDadoAoProcesso(eq(processoID), eq(dadoID))).thenReturn(dadoSolicitado);

        mockMvc.perform(post("/processos/{id}/dados/{dado}", processoID, dadoID))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).vincularDadoAoProcesso(eq(processoID), eq(dadoID));
    }

    @Test
    public void testExcluirDadoSolicitado() throws Exception {
        Integer processoID = 1;
        Integer dadoSolicitadoID = 1;
        doNothing().when(processoService).desvincularDadoDoProcesso(eq(processoID), eq(dadoSolicitadoID));

        mockMvc.perform(delete("/processos/{id}/dados-solicitados/{dado}", processoID, dadoSolicitadoID))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).desvincularDadoDoProcesso(eq(processoID), eq(dadoSolicitadoID));
    }

    @Test
    public void testAtualizarStatusProcesso() throws Exception {
        Integer processoID = 1;
        ProcessoDTO processoDTO = new ProcessoDTO(null, null, null, null, null, null, null, null, null, null, null, null, null); // Criar um objeto de exemplo
        doNothing().when(processoService).atualizarStatusProcesso(eq(processoID), eq(processoDTO));

        mockMvc.perform(put("/processos/{id}/status", processoID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(processoDTO)))
                .andExpect(status().isNoContent());

        verify(processoService, times(1)).atualizarStatusProcesso(eq(processoID), eq(processoDTO));
    }

    
}