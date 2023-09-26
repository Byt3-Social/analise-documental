package com.byt3social.analisedocumental.exceptions;

public class FailedToDeliverEmailException extends RuntimeException {
    public FailedToDeliverEmailException() {
        super("Não foi possível enviar o email");
    }
}
