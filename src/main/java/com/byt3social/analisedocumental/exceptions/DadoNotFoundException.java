package com.byt3social.analisedocumental.exceptions;

public class DadoNotFoundException extends RuntimeException {
    public DadoNotFoundException() {
        super("Dado não encontrado no processo informado");
    }
}
