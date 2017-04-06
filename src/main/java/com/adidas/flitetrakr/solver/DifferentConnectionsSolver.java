package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleGraph;
import com.adidas.flitetrakr.solver.algo.PathFinder;
import com.adidas.flitetrakr.solver.algo.TarjanSimpleCycles;
import com.adidas.flitetrakr.util.Triple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Finds how many different connections/paths with a specified number of stops
 * between two vertices in a graph exist.
 *
 * @param <V> The vertex type, which can be a string subtype.
 * @param <E> The edge payload/attribute representing a number.
 * @author Bogdan Zafirov
 */
public class DifferentConnectionsSolver<V extends String, E extends Number> extends Solver<V, E> {

    private final static String DIFFERENT_PATHS_QUESTION = "How many different connections with";

    public DifferentConnectionsSolver(final SimpleGraph<V, E> graph) {
        super(graph);
    }

    @Override
    protected String processQuestion(final String question) {
        if (!question.startsWith(DIFFERENT_PATHS_QUESTION)) {
            if(successor != null) {
                return successor.processQuestion(question);
            } else {
                throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
            }
        }

        final Triple<V, V, Evaluator> values = getSourceDestinationAndStops(question);
        final V source = values.getFirst(), destination = values.getSecond();
        final Evaluator evaluator = values.getThird();

        final List<List<V>> cycles = new TarjanSimpleCycles<>(graph).findSimpleCycles();
        /* Removing cycles on which the destination isn't included. */
        final Iterator<List<V>> iterator = cycles.iterator();
        while (iterator.hasNext()) {
            List<V> cycle = iterator.next();
            if (!cycle.contains(destination)) {
                iterator.remove();
                continue;
            }
            rotateCycle(destination, cycle);
        }

        /* Special case, if the source and destination are the same,
         * we only search for cycle paths/connections. */
        if (source.equals(destination)) {
            final List<List<V>> cyclePaths = new ArrayList<>();
            /* Generating cycled paths. */
            for (List<V> cycle : cycles) {
                if (evaluator.compare(cycle.size() - 2)) {
                    cyclePaths.add(cycle);
                }
                expandPath(cycle, cyclePaths, evaluator, null);
            }
            return Integer.toString(cyclePaths.size());
        }

        /* Merging the simple paths with the cycles if there are cycles. */
        final List<List<V>> allPaths = new ArrayList<>();
        final List<List<V>> paths = new PathFinder<>(graph).findAllPaths(source, destination);
        for (List<V> path : paths) {
            /* Simple non-cycled paths. */
            if (evaluator.compare(path.size() - 2)) {
                allPaths.add(path);
            }
            /* Generating cycled paths. */
            for (List<V> cycle : cycles) {
                expandPath(cycle, allPaths, evaluator, path);
            }
        }

        return Integer.toString(allPaths.size());
    }

    private void expandPath(final List<V> cycle, final List<List<V>> cyclePaths, Evaluator evaluator, List<V> initialPath) {
        int numLoops = 1;
        while (true) {
            final List<V> concatLoop = new ArrayList<V>();
            if(initialPath != null) {
                concatLoop.addAll(initialPath);
            } else {
                concatLoop.addAll(cycle);
            }
            for (int i = 0; i < numLoops; i++) {
                concatLoop.addAll(cycle.stream().skip(1).collect(Collectors.toList()));
            }
            if (evaluator.compare(concatLoop.size() - 2)) {
                cyclePaths.add(concatLoop);
            } else {
                break;
            }
            numLoops++;
        }
    }

    private Triple<V, V, Evaluator> getSourceDestinationAndStops(final String question) {
        final int fromIndex = question.indexOf("between");
        final int toIndex = question.indexOf("and");
        final int minIndex = question.indexOf("minimum");
        final int maxIndex = question.indexOf("maximum");
        final int eqIndex = question.indexOf("exactly");
        final int stopsIndex = question.indexOf("stop");
        if (fromIndex == -1 || toIndex == -1 || stopsIndex == -1) {
            throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
        }

        int operatorIndex = 0;
        String operator = null;
        if (minIndex != -1) {
            operator = "min";
            operatorIndex = minIndex;
        } else if (maxIndex != -1) {
            operator = "max";
            operatorIndex = maxIndex;
        } else if (eqIndex != -1) {
            operator = "eq";
            operatorIndex = eqIndex;
        }

        final Evaluator comp = new Evaluator(Integer.parseInt(question.substring(operatorIndex + 7, stopsIndex - 1).trim()), operator);
        final String source = question.substring(fromIndex + 7, toIndex).trim();
        final String dest = question.substring(toIndex + 3, question.indexOf("?")).trim();

        return new Triple<>((V) source, (V) dest, comp);
    }

    /**
     * Helper class used for evaluating if a certain
     * path fulfills the condition specified by this comparator.
     */
    private static class Evaluator {
        private final int stops;
        private final String operator;

        public Evaluator(final int stops, final String operator) {
            this.stops = stops;
            this.operator = operator;
        }

        public boolean compare(int noStops) {
            switch (operator) {
                case "min":
                    return noStops >= stops;
                case "max":
                    return noStops <= stops;
                case "eq":
                    return noStops == stops;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        }
    }
}
