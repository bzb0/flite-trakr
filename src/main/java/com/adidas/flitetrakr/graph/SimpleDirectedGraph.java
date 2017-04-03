package com.adidas.flitetrakr.graph;

import com.adidas.flitetrakr.util.Pair;

import java.util.*;

/**
 * Implementation of a simple directed graph.
 * <p>
 * An undirected graph can also be simulated
 * by creating edges in both directions.
 *
 * @author Bogdan Zafirov
 */
public class SimpleDirectedGraph<V, P> implements SimpleGraph<V, P> {

    /* Adjacency list, storing the all the vertices in the graph and their adjacents. */
    private final Map<V, Map<V, P>> adjacencyMap;

    public SimpleDirectedGraph() {
        adjacencyMap = new HashMap<V, Map<V, P>>();
    }

    public boolean addVertex(final V vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("The vertex may not be null");
        }
        if (adjacencyMap.containsKey(vertex)) {
            return false;
        }
        adjacencyMap.put(vertex, new HashMap<V, P>());
        return true;
    }

    public boolean addEdge(final V source, final V dest, final P edgePayload) {
        if (edgePayload == null) {
            throw new IllegalArgumentException("The payload may not be null.");
        }
        if ((source == null) || (dest == null)) {
            throw new IllegalArgumentException("The source and target vertices may not be null.");
        }

        if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(dest)) {
            return false;
        }

        adjacencyMap.get(source).put(dest, edgePayload);
        return true;
    }

    public boolean existsEdge(final V source, final V dest) {
        if ((source == null) || (dest == null)) {
            throw new IllegalArgumentException("The vertices may not be null.");
        }

        if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(dest)) {
            return false;
        }
        return adjacencyMap.get(source).containsKey(dest);
    }

    public P getEdge(final V source, final V dest) {
        if ((source == null) || (dest == null)) {
            throw new IllegalArgumentException("The vertices may not be null.");
        }

        if (!adjacencyMap.containsKey(source) || !adjacencyMap.containsKey(dest)) {
            return null;
        }
        return adjacencyMap.get(source).get(dest);
    }

    public Set<Pair<V, P>> getOutEdges(final V source) {
        if (source == null) {
            throw new IllegalArgumentException("The vertex may not be null.");
        }
        if (!adjacencyMap.containsKey(source)) {
            throw new IllegalArgumentException("The vertex is not part of the graph.");
        }

        final Set<Pair<V, P>> edges = new HashSet<>();
        final Map<V, P> adjacents = adjacencyMap.get(source);

        adjacents.entrySet().forEach(edge -> edges.add(new Pair<V, P>(edge.getKey(), edge.getValue())));
        return edges;
    }

    public Set<V> getAllVertices() {
        return Collections.unmodifiableSet(adjacencyMap.keySet());
    }

    public boolean containsVertex(final V vertex) {
        return adjacencyMap.containsKey(vertex);
    }

    public Set<V> getNeighbors(final V source) {
        if (source == null) {
            throw new IllegalArgumentException("The vertex may not be null.");
        }
        if (!adjacencyMap.containsKey(source)) {
            throw new IllegalArgumentException("The vertex doesn't exist in the graph.");
        }
        return Collections.unmodifiableSet(adjacencyMap.get(source).keySet());
    }
}
