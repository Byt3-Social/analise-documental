package com.byt3social.analisedocumental.services;

import com.byt3social.analisedocumental.dto.ProcessoDTO;
import com.byt3social.analisedocumental.models.Processo;
import com.byt3social.analisedocumental.repositories.ProcessoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessoService {
    @Autowired
    private ProcessoRepository processoRepository;

    @Transactional
    public void criaProcesso(ProcessoDTO dados) {
        Processo novoProcesso = new Processo(dados.cnpj());
        processoRepository.save(novoProcesso);
    }
}
