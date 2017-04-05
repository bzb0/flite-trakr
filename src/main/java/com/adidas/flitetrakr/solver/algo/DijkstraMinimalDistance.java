package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleGraph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of the dijkstra algorithm for minimal distance in a graph.
 *
 * @param <V> The type vertex type.
 * @param <E> The edge attribute type. The type must be a number (e.g. long, int, double).
 * @author Bogdan Zafirov
 */
public class DijkstraMinimalDistance<V, E extends Number> {

    private final SimpleGraph<V, E> graph;
    private final Set<V> processedVertices;
    private final Set<V> unprocessedVertices;
    private final Map<V, V> predecessors;
    private final Map<V, E> vertexDistances;

    public DijkstraMinimalDistance(final SimpleGraph<V, E> graph) {
        this.graph = graph;
        processedVertices = new HashSet<>();
        unprocessedVertices = new HashSet<>();
        vertexDistances = new HashMap<>();
        predecessors = new HashMap<>();
    }

    /**
     * Finds the path with shortest distance from the
     * source to the target vertex.
     *
     * @param source The source vertex.
     * @param target The target vertex.
     * @return List of vertices along the path from the source to the target vertex.
     */
    public List<V> findPath(final V source, final V target) {
        /* Clearing the auxiliary data structures. */
        processedVertices.clear();
        unprocessedVertices.clear();
        vertexDistances.clear();
        predecessors.clear();
        vertexDistances.clear();

        vertexDistances.put(source, (E) new Long(0));
        unprocessedVertices.add(source);
        /* Calculating the shortest distances to all vertices in the graph. */
        while (unprocessedVertices.size() > 0) {
            V node = findShortestDistance(unprocessedVertices);
            processedVertices.add(node);
            unprocessedVertices.remove(node);
            calculateDistancesForVertex(node);
        }

        /* Finally we simply create the shortest path. */
        return createPath(target);
    }

    private void calculateDistancesForVertex(final V vertex) {
        /* Fetching the neighbors of the current vertex.
         * Here we also filter the already processed vertices. */
        final Set<V> neighbors = graph.getNeighbors(vertex).stream().filter(v -> !processedVertices.contains(v)).collect(Collectors.toSet());

        /* Upading the distances map, with the newly calculated distances. */
        for (final V target : neighbors) {
            final long vertexDistance = getDistanceTo(vertex);
            final long targetDistance = getDistanceTo(target);
            final long edgeDistance = graph.getEdge(vertex, target).longValue();
            if (targetDistance > (vertexDistance + edgeDistance)) {
                vertexDistances.put(target, (E) new Long(vertexDistance + edgeDistance));
                predecessors.put(target, vertex);
                unprocessedVertices.add(target);
            }
        }
    }

    private V findShortestDistance(final Set<V> vertices) {
        V minDistVertex = null;
        for (final V vertex : vertices) {
            if (minDistVertex == null) {
                minDistVertex = vertex;
            } else {
                minDistVertex = getDistanceTo(vertex) < getDistanceTo(minDistVertex) ? vertex : minDistVertex;
            }
        }
        return minDistVertex;
    }

    private Long getDistanceTo(final V destination) {
        final E distance = vertexDistances.get(destination);
        return (distance == null) ? Long.MAX_VALUE : distance.longValue();
    }

    private List<V> createPath(final V destination) {
        final LinkedList<V> path = new LinkedList<V>();
        V currentVertex = destination;
        /* First we check if a path exists at all. */
        if (predecessors.get(currentVertex) == null) {
            return null;
        }
        /* We iterate the predecessors map, by going backwards. */
        path.addFirst(currentVertex);
        V predecessor = null;
        while ((predecessor = predecessors.get(currentVertex)) != null) {
            currentVertex = predecessor;
            path.addFirst(currentVertex);
        }
        return path;
    }
}
