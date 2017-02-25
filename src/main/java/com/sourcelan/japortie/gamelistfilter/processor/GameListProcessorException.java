package com.sourcelan.japortie.gamelistfilter.processor;

public class GameListProcessorException extends Exception {
    public GameListProcessorException(String message) {
        super(message);
    }

    public GameListProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameListProcessorException(Throwable cause) {
        super(cause);
    }
}
