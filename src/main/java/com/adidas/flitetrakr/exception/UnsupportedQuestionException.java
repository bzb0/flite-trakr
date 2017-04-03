package com.adidas.flitetrakr.exception;

/**
 * Thrown when the application can't process/parse the specified question.
 *
 * @author Bogdan Zafirov
 */
public class UnsupportedQuestionException extends RuntimeException {

    public UnsupportedQuestionException(final String message) {
        super(message);
    }

    public UnsupportedQuestionException(final String message, final Throwable t) {
        super(message, t);
    }
}