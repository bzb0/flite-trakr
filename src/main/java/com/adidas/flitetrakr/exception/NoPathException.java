package com.adidas.flitetrakr.exception;

/**
 * Thrown when no path is found between two vertices in the graph.
 *
 * @author Bogdan Zafirov
 */
public class NoPathException extends RuntimeException {

    public NoPathException(final String message) {
        super(message);
    }

    public NoPathException(final String message, final Throwable t) {
        super(message, t);
    }
}