package com.byt3social.analisedocumental.exceptions;

public class FailedToSaveFileException extends RuntimeException {
    public FailedToSaveFileException() {
        super("Falha ao submeter arquivo");
    }
}
