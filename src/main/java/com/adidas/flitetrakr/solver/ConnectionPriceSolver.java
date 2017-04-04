package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.NoPathException;
import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleGraph;

/**
 * Calculates the price for a list of connections.
 * For example if we have the following connections:
 *
 * A-B-10, A-C-30, C-D-20 and the following path A-C-D
 *
 * we get the value 50. If a path doesn't exist the String
 * "No such connection found!" is found.
 *
 * @param <V> The vertex type, which can be a string subtype.
 * @param <E> The edge payload/attribute representing a number.
 * @author Bogdan Zafirov
 */
public class ConnectionPriceSolver<V extends String, E extends Number> extends Solver<V, E> {

    private final static String PRICE_QUESTION = "What is the price of the connection";

    public ConnectionPriceSolver(final SimpleGraph<V, E> graph) {
        super(graph);
    }

    @Override
    protected String processQuestion(final String question) {
        if (!question.startsWith(PRICE_QUESTION)) {
            if(successor != null) {
                return successor.processQuestion(question);
            } else {
                throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
            }
        }
        final String connection = question.substring(PRICE_QUESTION.length(), question.indexOf("?"));

        try {
            return Long.toString(calculatePrice(connection.split("-")));
        } catch(NoPathException e) {
            return NO_CONNECTION;
        }
    }

    private long calculatePrice(final String [] path) {
        if(path.length == 1) {
            throw new IllegalArgumentException("The path should consist from at least two locations.");
        }

        long price = 0;
        V source = (V) path[0].trim();
        for (int i = 1; i < path.length; i++) {
            final V destination = (V) path[i].trim();
            if (graph.containsVertex(source) && (graph.getEdge(source, destination) != null)) {
                price += graph.getEdge(source, destination).longValue();
            } else {
                throw new NoPathException("The path doesn't exist");
            }
            source = destination;
        }
        return price;
    }
}
