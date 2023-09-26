package com.byt3social.analisedocumental.amqp.subscribe;

import com.byt3social.analisedocumental.dto.OrganizacaoDTO;
import com.byt3social.analisedocumental.services.ProcessoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrganizacaoCadastradaListener {
    @Autowired
    private ProcessoService processoService;

    @RabbitListener(queues = "organizacao.cadastrada")
    public void organizacaoCadastrada(@Payload OrganizacaoDTO organizacaoDTO) {
        processoService.abrirProcesso(organizacaoDTO);
    }
}
