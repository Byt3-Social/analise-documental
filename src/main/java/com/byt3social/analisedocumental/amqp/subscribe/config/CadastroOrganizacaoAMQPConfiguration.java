package com.byt3social.analisedocumental.amqp.subscribe.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CadastroOrganizacaoAMQPConfiguration {
    @Bean
    public Queue organicazaoCadastradaQueue() {
        return QueueBuilder.nonDurable("organizacao.cadastrada").build();
    }

    @Bean
    public DirectExchange cadastrosDirectExchange() {
        return ExchangeBuilder.directExchange("cadastros.ex").build();
    }

    @Bean
    public Binding bindOrganizacaoCadastradaToCadastros() {
        return BindingBuilder.bind(organicazaoCadastradaQueue()).to(cadastrosDirectExchange()).with("organizacao.cadastrada");
    }
}
