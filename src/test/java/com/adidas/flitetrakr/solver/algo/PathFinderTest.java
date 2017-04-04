package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class PathFinderTest {

    @Test
    public void findAllPaths() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A", "B", 1L);
        graph.addEdge("A", "C", 1L);
        graph.addEdge("B", "D", 1L);
        graph.addEdge("B", "E", 1L);
        graph.addEdge("C", "F", 1L);
        graph.addEdge("C", "G", 1L);
        graph.addEdge("F", "G", 1L);
        graph.addEdge("D", "E", 1L);
        graph.addEdge("G", "E", 1L);

        PathFinder<String, Long> pathFinder = new PathFinder<>(graph);
        List<List<String>> paths = pathFinder.findAllPaths("A", "E");

        paths.sort((o1, o2) -> {
            return o1.size() - o2.size();
        });

        assertEquals("Wrong number of path vertices", 4, paths.size());
        assertEquals("Wrong path size", 3, paths.get(0).size());
        assertEquals("Wrong path size", 4, paths.get(1).size());
        assertEquals("Wrong path size", 4, paths.get(2).size());
        assertEquals("Wrong path size", 5, paths.get(3).size());
    }

    @Test
    public void noPath() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A", "B", 1L);
        graph.addEdge("A", "C", 1L);
        graph.addEdge("B", "D", 1L);
        graph.addEdge("C", "F", 1L);
        graph.addEdge("C", "G", 1L);
        graph.addEdge("F", "G", 1L);

        PathFinder<String, Long> pathFinder = new PathFinder<>(graph);
        List<List<String>> paths = pathFinder.findAllPaths("A", "E");

        assertEquals("Wrong number of path vertices", 0, paths.size());
    }


}
