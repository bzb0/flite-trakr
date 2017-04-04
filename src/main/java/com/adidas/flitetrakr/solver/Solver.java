package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleGraph;

import java.util.Collections;
import java.util.List;

/**
 * The solver can solve a certain graph question/problem.
 * Every solver inherits this class and represents a solution
 * for a specific graph problem (e.g. shortest distance).
 *
 * @param <V> The type of the vertices in the graph.
 * @param <E> The type of the edge payload/attribute in the graph.
 */
public abstract class Solver<V, E extends Number> {

    protected static final String NO_CONNECTION = "No such connection found!";

    protected final SimpleGraph<V, E> graph;
    protected Solver successor;

    public Solver(final SimpleGraph<V, E> graph) {
        this.graph = graph;
    }

    public void setSuccessor(final Solver successor) {
        this.successor = successor;
    }

    /**
     * The actual implementation of the solution.
     *
     * @param question The graph question to be answered/solved.
     * @return The solution to the question.
     */
    protected abstract String processQuestion(final String question);

    protected String pathToString(final List<V> vertexPath) {
        /* Building the path. */
        final StringBuffer pathString = new StringBuffer();
        long price = 0;
        V source = vertexPath.get(0);
        pathString.append(source + "-");
        for (int i = 1; i < vertexPath.size(); i++) {
            V dest = vertexPath.get(i);
            price += graph.getEdge(source, dest).longValue();
            pathString.append(dest + "-");
            source = dest;
        }
        pathString.append(price);
        return pathString.toString();
    }

    /**
     * Shifts the cycle, such that the start vertex is set as
     * first element in the cycle list.
     *
     * @param start The start vertex.
     * @param cycle List of vertices representing the cycle.
     */
    protected void rotateCycle(final V start, final List<V> cycle) {
        if(!cycle.contains(start)) {
            return;
        }
        int position = 0;
        for (final V location : cycle) {
            if (location.equals(start)) {
                break;
            }
            position--;
        }
        Collections.rotate(cycle, position);
        cycle.add(start);
    }

    public String solveQuestion(String question) {
        return processQuestion(question);
    }
}
