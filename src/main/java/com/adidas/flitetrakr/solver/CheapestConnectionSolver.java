package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleGraph;
import com.adidas.flitetrakr.solver.algo.DijkstraMinimalDistance;
import com.adidas.flitetrakr.solver.algo.TarjanSimpleCycles;
import com.adidas.flitetrakr.util.Pair;

import java.util.*;

/**
 * Finds the cheapest connection from one vertex to another in a graph.
 *
 * @param <V> The vertex type, which can be a string subtype.
 * @param <E> The edge payload/attribute representing a number.
 */
public class CheapestConnectionSolver<V extends String, E extends Number> extends Solver<V, E> {

    private final static String CHEAPEST_PATH_QUESTION = "What is the cheapest connection from";

    public CheapestConnectionSolver(final SimpleGraph<V, E> graph) {
        super(graph);
    }

    @Override
    protected String processQuestion(final String question) {
        if (!question.startsWith(CHEAPEST_PATH_QUESTION)) {
            if(successor != null) {
                return successor.processQuestion(question);
            } else {
                throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
            }
        }

        final Pair<V, V> sourceDest = getSourceAndDestination(question);
        final V source = sourceDest.getFirst(), destination = sourceDest.getSecond();

        List<V> path = null;
        if (source.equals(destination)) {
            path = findCycles(source);
        } else {
            path = new DijkstraMinimalDistance<>(graph).findPath(source, destination);
        }
        return (path == null) ? NO_CONNECTION : pathToString(path);
    }

    private Pair<V, V> getSourceAndDestination(final String question) {
        final int questionMarkIndex = question.indexOf("?");
        if (questionMarkIndex == -1) {
            throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
        }

        final String connection = question.substring(CHEAPEST_PATH_QUESTION.length(), questionMarkIndex);

        final int toIndex = connection.indexOf("to");
        if (toIndex == -1) {
            throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
        }

        final String source = connection.substring(0, toIndex).trim();
        final String destination = connection.substring(toIndex + 2).trim();

        return new Pair<V, V>((V) source, (V) destination);
    }

    /**
     * Finds all simple cycles in the graph.
     *
     * @param source The source vertex, where the loop should start.
     * @return The cycle.
     */
    private List<V> findCycles(final V source) {
        final List<List<V>> cycles = new TarjanSimpleCycles<>(graph).findSimpleCycles();

        /* Setting the source of the cycle as the first element of the list. */
        for(final List<V> cycle : cycles) {
            rotateCycle(source, cycle);
        }

        /* Converting the cycles into paths. */
        final Map<List<V>, Long> paths = new HashMap<>();
        for (final List<V> cycle : cycles) {
            if(!cycle.contains(source)) {
                continue;
            }
            long pathPrice = 0;
            V currentSource = source;
            for (int i = 1; i < cycle.size(); i++) {
                V dest = cycle.get(i);
                pathPrice += graph.getEdge(currentSource, dest).longValue();
                currentSource = dest;
            }
            paths.put(cycle, pathPrice);
        }

        /* Returning the path with the lowest price. */
        return paths.isEmpty() ? null : paths.entrySet().stream().max((path1, path2) -> {
            return path2.getValue().compareTo(path1.getValue().longValue());
        }).get().getKey();
    }
}