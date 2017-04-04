package com.adidas.flitetrakr.graph;

import static org.junit.Assert.*;

import com.adidas.flitetrakr.util.Pair;
import org.junit.Test;

import java.util.Set;

public class SimpleDirectedGraphTest {

    @Test
    public void vertexCreation() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        boolean result = graph.addVertex("A");

        assertEquals("Vertex wasn't added to the graph", true, result);
        assertTrue("Vertex is not part of graph", graph.containsVertex("A"));
    }

    @Test
    public void doubleVertexCreation() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        boolean result = graph.addVertex("A");
        assertEquals("Vertex wasn't added to the graph", true, result);

        boolean doubleCommit = graph.addVertex("A");
        assertEquals("Vertex wasn't added to the graph", false, doubleCommit);
        assertTrue("Vertex is not part of graph", graph.containsVertex("A"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void vertexCreation_IllegalArgument() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        graph.addVertex(null);
    }

    @Test
    public void testAddMultipleVertices() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        assertTrue("Vertex not part of graph", graph.containsVertex("A"));
        assertTrue("Vertex not part of graph", graph.containsVertex("B"));
        assertTrue("Vertex not part of graph", graph.containsVertex("C"));
        assertEquals("Wrong number of vertices", 3, graph.getAllVertices().size());
    }

    @Test
    public void edgeCreation() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        graph.addVertex("A");
        graph.addVertex("B");

        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("C", "D", 5L);

        assertTrue("An edge between A and B doesn't exist", graph.existsEdge("A", "B"));
        assertTrue("An edge between C and D doesn't exist", graph.existsEdge("C", "D"));

        assertEquals("Wrong number of vertices", 4, graph.getAllVertices().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void edgeCreation_IllegalVertices() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge(null, "D", 5L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void edgeCreation_IllegalEdgePayload() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("C", "D", null);
    }

    @Test
    public void outgoingEdges() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();

        /* Source vertex. */
        graph.addVertex("A");

        /* Target vertices. */
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("A", "C", 4L);
        graph.addEdge("A", "D", 3L);

        Set<Pair<String, Long>> outEdges = graph.getOutEdges("A");

        assertEquals("Wrong number of vertices", 4, graph.getAllVertices().size());
        assertEquals("Wrong number of outgoing edges", 3, outEdges.size());

        assertTrue("Wrong output edges for vertex A", outEdges.contains(new Pair<String, Long>("B", 5L)));
        assertTrue("Wrong output edges for vertex A", outEdges.contains(new Pair<String, Long>("C", 4L)));
        assertTrue("Wrong output edges for vertex A", outEdges.contains(new Pair<String, Long>("D", 3L)));
    }

    @Test
    public void edgePayload() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addEdge("A", "B", 5L);

        assertEquals("Wrong edge payload/attribute", new Long(5L), graph.getEdge("A", "B"));
    }

    @Test
    public void vertexNeighbors() {
        SimpleGraph<String, Long> graph = new SimpleDirectedGraph<>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addEdge("A", "B", 5L);
        graph.addEdge("A", "C", 5L);

        Set<String> neighbors = graph.getNeighbors("A");

        assertTrue("Vertex A doesn't have this neighbor", neighbors.contains("B"));
        assertTrue("Vertex A doesn't have this neighbor", neighbors.contains("C"));
    }
}
