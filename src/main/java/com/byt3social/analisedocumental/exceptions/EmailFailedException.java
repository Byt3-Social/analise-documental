package com.byt3social.analisedocumental.exceptions;

public class EmailFailedException extends RuntimeException {
    public EmailFailedException() {
        super("não foi possível enviar o email");
    }
}
