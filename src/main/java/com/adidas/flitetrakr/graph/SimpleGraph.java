package com.adidas.flitetrakr.graph;

import com.adidas.flitetrakr.util.Pair;

import java.util.*;

/**
 * A graph consisting of a set of vertices of type V and a set of edges
 * of type E. This graph enables the implementation of the following graph
 * variations:
 *
 * - directed and undirected edges
 * - vertices and edges with attributes (for example, weighted edges)
 * - simple graph (no parallel edges between two vertices)
 *
 * @author Bogdan Zafirov
 */
public interface SimpleGraph<V, P> {

    /**
     * Adds vertex to the graph. If the graph
     * contains the vertex, the input vertex is ignored.
     *
     * @param vertex The vertex to be added to the graph.
     * @return True if the vertex is added to the graph, false otherwise.
     * @throws IllegalArgumentException Thrown if the vertex is null.
     */
    boolean addVertex(final V vertex);

    /**
     * Connects the two graph vertex and assigns the payload to the edge.
     *
     * @param source The source vertex.
     * @param dest The target vertex.
     * @param edgePayload The edge payload.
     * @return True if the edge is created, false otherwise.
     * @throws IllegalArgumentException Thrown if the vertices are not
     *                                  part of the graph or they are null.
     */
    boolean addEdge(final V source, final V dest, final P edgePayload);

    /**
     * Returns a list of pairs representing the target vertices and
     * the payload/attribute of the connecting edge.
     *
     * @param source The source vertex.
     * @return Set of pairs of target vertices and their corresponding edges.
     * @throws IllegalArgumentException Thrown if the vertex is not part of the graph or it's null.
     */
    Set<Pair<V, P>> getOutEdges(final V source);

    /**
     * Returns a set of all vertices in the graph.
     *
     * @return S Set containing all the vertices in the graph.
     */
    Set<V> getAllVertices();

    /**
     * Checks if the graph contains the vertex.
     *
     * @param vertex The vertex.
     * @return True if the graph contains the vertex, false otherwise.
     */
    boolean containsVertex(final V vertex);

    /**
     * Checks if there is an edge between the two vertices.
     *
     * @param source The source vertex.
     * @param dest The target vertex.
     * @return True if an edge between the vertices exist, false otherwise.
     * @
     */
    boolean existsEdge(final V source, final V dest);

    /**
     * Returns the payload/attribute of the edge between
     * the two vertices.
     *
     * @param source The source vertex.
     * @param dest The target vertex.
     * @return The payload of the edge between the vertices, or null if no edge exists.
     * @throws IllegalArgumentException Thrown if the vertices are null.
     */
    P getEdge(final V source, final V dest);

    /**
     * Returns all adjacent vertices to the specified vertex.
     *
     * @param vertex The source vetex.
     * @return Set containing all neighboring vertices.
     * @throws IllegalArgumentException Thrown if the vertex is not part of the graph or it's null.
     */
    Set<V> getNeighbors(final V vertex);
}

