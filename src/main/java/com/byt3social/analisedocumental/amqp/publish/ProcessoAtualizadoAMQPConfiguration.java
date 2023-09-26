package com.byt3social.analisedocumental.amqp.publish;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAtualizadoAMQPConfiguration {
    @Bean
    public DirectExchange complianceDirectExchange() {
        return new DirectExchange("compliance.ex");
    }
}
