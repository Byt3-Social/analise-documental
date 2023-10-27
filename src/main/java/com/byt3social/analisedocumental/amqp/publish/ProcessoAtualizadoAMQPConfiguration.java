package com.byt3social.analisedocumental.amqp.publish;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessoAtualizadoAMQPConfiguration {
    @Bean
    public FanoutExchange complianceFanoutExchange() {
        return new FanoutExchange("compliance.ex");
    }
}
