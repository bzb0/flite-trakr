package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleGraph;

import java.util.*;

/**
 * Finds all paths from one vertex (source) to another (destination),
 * by performing depth-first search.
 *
 * @param <V> The type vertex type.
 * @param <P> The edge attribute/payload type.
 * @author Bogdan Zafirov
 */
public class PathFinder<V, P> {

    private final SimpleGraph<V, P> graph;

    public PathFinder(final SimpleGraph<V, P> graph) {
        this.graph = graph;
    }

    /**
     * Finds all paths between the source and destionation vertex.
     *
     * @param source      The source vertex.
     * @param destination The target vertex.
     * @return List of paths between the two vertices.
     */
    public List<List<V>> findAllPaths(final V source, final V destination) {
        final List<List<V>> paths = new ArrayList<List<V>>();
        final List<V> visited = new ArrayList<V>();
        visited.add(source);
        findAllPaths0(visited, paths, source, destination);
        return paths;
    }

    private void findAllPaths0(final List<V> visited, final List<List<V>> paths, final V currentVertex, final V destination) {
        if (currentVertex.equals(destination)) {
            paths.add(new ArrayList(visited));
            return;
        } else {
            for (final V neighbor : graph.getNeighbors(currentVertex)) {
                if (visited.contains(neighbor)) {
                    continue;
                }
                final List<V> temp = new ArrayList<V>(visited);
                temp.add(neighbor);
                findAllPaths0(temp, paths, neighbor, destination);
            }
        }
    }
}