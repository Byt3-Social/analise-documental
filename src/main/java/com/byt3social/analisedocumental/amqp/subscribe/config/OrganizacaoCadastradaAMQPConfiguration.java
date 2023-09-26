package com.byt3social.analisedocumental.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrganizacaoCadastradaAMQPConfiguration {
    @Bean
    public Queue organicazaoCadastradaQueue() {
        return QueueBuilder.nonDurable("organizacao.cadastrada").build();
    }

    @Bean
    public DirectExchange prospeccaoDirectExchange() {
        return ExchangeBuilder.directExchange("prospeccao.ex").build();
    }

    @Bean
    public Binding bindOrganizacaoCadastradaToProspeccao() {
        return BindingBuilder.bind(organicazaoCadastradaQueue()).to(prospeccaoDirectExchange()).with("organizacao.cadastrada");
    }
}
