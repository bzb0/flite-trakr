package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleGraph;
import com.adidas.flitetrakr.solver.algo.PathFinder;
import com.adidas.flitetrakr.solver.algo.TarjanSimpleCycles;
import com.adidas.flitetrakr.util.Triple;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Finds all paths/connections between two vertices in a graph,
 * such that the total path cost is below a certain threshold.
 *
 * @param <V> The vertex type, which can be a string subtype.
 * @param <E> The edge payload/attribute representing a number.
 */
public class AllConnectionsSolver<V extends String, E extends Number> extends Solver<V, E> {

    private final static String ALL_CONNECTIONS_QUESTION = "Find all connections from";

    public AllConnectionsSolver(final SimpleGraph<V, E> graph) {
        super(graph);
    }

    @Override
    protected String processQuestion(final String question) {
        if (!question.startsWith(ALL_CONNECTIONS_QUESTION)) {
            if(successor != null) {
                return successor.processQuestion(question);
            } else {
                throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
            }
        }

        final Triple<V, V, E> values = getSourceDestAndPrice(question);
        final V source = values.getFirst(), destination = values.getSecond();
        final E price = values.getThird();

        final List<List<V>> paths = new ArrayList<>();
        if(source.equals(destination)) {
            final List<List<V>> cycles = new TarjanSimpleCycles<>(graph).findSimpleCycles();
            for(final List<V> cycle : cycles) {
                if(!cycle.contains(source)) {
                    continue;
                }

                rotateCycle(source, cycle);
                if (getPrice(cycle).longValue() < price.longValue()) {
                    paths.add(cycle);
                }
                int numAppends = 1;
                while(true) {
                    final List<V> concatenatedPath = new ArrayList<V>(cycle);
                    for (int i = 0; i < numAppends; i++) {
                        concatenatedPath.addAll(cycle.stream().skip(1).collect(Collectors.toList()));
                    }
                    if (getPrice(concatenatedPath).longValue() < price.longValue()) {
                        paths.add(concatenatedPath);
                    } else {
                        break;
                    }
                    numAppends++;
                }
            }
        } else {
            paths.addAll(new PathFinder<>(graph).findAllPaths(source, destination));
            if (graph.getEdge(destination, source) != null) {
                for (int i = 0; i < paths.size(); i++) {
                    for (int j = 0; j < paths.size(); j++) {
                        List<V> concatenatedPath = new ArrayList<>(paths.get(i));
                        concatenatedPath.addAll(paths.get(j));

                        if (getPrice(concatenatedPath).longValue() < price.longValue()) {
                            paths.add(concatenatedPath);
                        }
                    }
                }
            }
        }

        if(paths.isEmpty()) {
            return NO_CONNECTION;
        }

        final StringBuffer buf = new StringBuffer();
        for(final List<V> path : paths) {
            buf.append(pathToString(path) + ", ");
        }
        buf.setLength(buf.length()-2);
        return buf.toString();
    }

    private E getPrice(final List<V> path) {
        Long price = 0L;
        V source = path.get(0);
        for (int i = 1; i < path.size(); i++) {
            V dest = path.get(i);
            price += graph.getEdge(source, dest).longValue();
            source = dest;
        }
        return (E) price;
    }

    private Triple<V, V, E> getSourceDestAndPrice(final String question) {
        final int fromIndex = question.indexOf("from");
        final int toIndex = question.indexOf("to");
        final int belowIndex = question.indexOf("below");
        final int priceIndex = question.indexOf("Euro");
        if(fromIndex == -1 || toIndex == -1 || belowIndex == -1 || priceIndex == -1) {
            throw new UnsupportedQuestionException("Can't process question: \"" + question + "\"");
        }

        final String source = question.substring(fromIndex + 4, toIndex).trim();
        final String dest = question.substring(toIndex + 2, belowIndex).trim();
        final Long price = Long.parseLong(question.substring(belowIndex + 5, priceIndex).trim());

        return new Triple<>((V)source, (V)dest, (E)price);
    }

}
