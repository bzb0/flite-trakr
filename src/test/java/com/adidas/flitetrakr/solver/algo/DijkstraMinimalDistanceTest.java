package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DijkstraMinimalDistanceTest {

    @Test
    public void shortestPath() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("A", "E", 10L);
        graph.addEdge("B", "C", 2L);
        graph.addEdge("E", "D", 2L);
        graph.addEdge("C", "D", 4L);
        graph.addEdge("C", "F", 100L);
        graph.addEdge("D", "F", 9L);

        DijkstraMinimalDistance<String, Long> dijkstraAlgorithm = new DijkstraMinimalDistance<>(graph);
        List<String> path = dijkstraAlgorithm.findPath("A", "F");

        assertEquals("Wrong number of path vertices", 5, path.size());
        assertEquals("Wrong vertex on shortest path", "A", path.get(0));
        assertEquals("Wrong vertex on shortest path", "B", path.get(1));
        assertEquals("Wrong vertex on shortest path", "C", path.get(2));
        assertEquals("Wrong vertex on shortest path", "D", path.get(3));
        assertEquals("Wrong vertex on shortest path", "F", path.get(4));
    }

    @Test
    public void nonExistingPath() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("B", "C", 10L);
        graph.addEdge("D", "B", 2L);
        graph.addEdge("D", "C", 2L);

        DijkstraMinimalDistance<String, Long> dijkstraAlgorithm = new DijkstraMinimalDistance<>(graph);
        List<String> path = dijkstraAlgorithm.findPath("A", "F");

        assertNull("The path shouldn't exist", path);
    }
}
