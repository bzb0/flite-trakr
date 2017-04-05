package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleGraph;

import java.util.*;

/**
 * Implementation of the Tarjan's strongly connected components algorithm.
 * <p>
 * Since any strongly connected component (SCC) with a size greater than
 * 1 implies a cycle, we can use this algorithm to find simple cycles in a graph.
 * Self loops in this case will not be found (this is not a requirement of
 * com.adidas.flitetrakr.main.FliteTrakr).
 *
 * @param <V> The type vertex type.
 * @param <E> The edge attribute/payload type.
 * @author Bogdan Zafirov
 */
public class TarjanSimpleCycles<V, E> {

    /* The graph. */
    private final SimpleGraph<V, E> graph;

    /* Data structures used for the tarjan simple cycle */
    private List<List<V>> cycles;
    private Set<V> marked;
    private ArrayDeque<V> markedVertices;
    private ArrayDeque<V> pointVertices;
    private Map<V, Integer> vertexIndexMap;
    private Map<V, Set<V>> removedVertices;

    /**
     * Constructs the tarjan SCC algorithm.
     *
     * @param graph The input graph.
     * @throws IllegalArgumentException Thrown if the graph is null.
     */
    public TarjanSimpleCycles(final SimpleGraph<V, E> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph can't be null.");
        }
        this.graph = graph;
    }

    /**
     * Finds all simple cycles in the graph.
     *
     * @return List of cycles in the graph.
     */
    public List<List<V>> findSimpleCycles() {
        cycles = new ArrayList<List<V>>();
        marked = new HashSet<V>();
        markedVertices = new ArrayDeque<V>();
        pointVertices = new ArrayDeque<V>();
        vertexIndexMap = new HashMap<V, Integer>();
        removedVertices = new HashMap<V, Set<V>>();

        int index = 0;
        for (final V v : graph.getAllVertices()) {
            vertexIndexMap.put(v, index++);
        }

        for (final V currentVertex : graph.getAllVertices()) {
            backtrack(currentVertex, currentVertex);
            while (!markedVertices.isEmpty()) {
                marked.remove(markedVertices.pop());
            }
        }
        return cycles;
    }

    private boolean backtrack(final V start, final V vertex) {
        boolean foundCycle = false;
        pointVertices.push(vertex);
        marked.add(vertex);
        markedVertices.push(vertex);

        for (final V currentVertex : graph.getNeighbors(vertex)) {
            if (getRemoved(vertex).contains(currentVertex)) {
                continue;
            }
            final int comparison = vertexIndexMap.get(currentVertex).compareTo(vertexIndexMap.get(start));
            if (comparison < 0) {
                getRemoved(vertex).add(currentVertex);
            } else if (comparison == 0) {
                foundCycle = true;
                final List<V> cycle = new ArrayList<V>();
                final Iterator<V> iterator = pointVertices.descendingIterator();

                V v = null;
                while (iterator.hasNext()) {
                    v = iterator.next();
                    if (start.equals(v)) {
                        break;
                    }
                }

                cycle.add(start);
                while (iterator.hasNext()) {
                    cycle.add(iterator.next());
                }
                cycles.add(cycle);
            } else if (!marked.contains(currentVertex)) {
                final boolean gotCycle = backtrack(start, currentVertex);
                foundCycle = foundCycle || gotCycle;
            }
        }

        if (foundCycle) {
            while (!markedVertices.peek().equals(vertex)) {
                marked.remove(markedVertices.pop());
            }
            marked.remove(markedVertices.pop());
        }

        pointVertices.pop();
        return foundCycle;
    }

    private Set<V> getRemoved(final V v) {
        Set<V> result = removedVertices.get(v);
        if (result == null) {
            result = new HashSet<V>();
            removedVertices.put(v, result);
        }
        return result;
    }
}
