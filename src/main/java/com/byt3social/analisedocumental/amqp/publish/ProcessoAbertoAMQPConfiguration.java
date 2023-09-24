package com.byt3social.analisedocumental.amqp.publish;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAbertoAMQPConfiguration {
    @Bean
    public DirectExchange processosDirectExchange() {
        return new DirectExchange("processos.ex");
    }
}
