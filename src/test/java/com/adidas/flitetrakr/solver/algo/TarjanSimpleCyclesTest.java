package com.adidas.flitetrakr.solver.algo;

import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TarjanSimpleCyclesTest {

    @Test
    public void findAllSimpleCycles() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A", "B", 1L);
        graph.addEdge("B", "C", 1L);
        graph.addEdge("C", "A", 1L);
        graph.addEdge("B", "D", 1L);
        graph.addEdge("C", "D", 1L);
        graph.addEdge("D", "E", 1L);
        graph.addEdge("E", "F", 1L);
        graph.addEdge("F", "C", 1L);
        graph.addEdge("F", "A", 1L);

        TarjanSimpleCycles<String, Long> tarjanSimpleCycles = new TarjanSimpleCycles<>(graph);
        List<List<String>> cycles = tarjanSimpleCycles.findSimpleCycles();

        cycles.sort((o1, o2) -> {
            return o1.size() - o2.size();
        });

        assertEquals("Wrong number of path vertices", 5, cycles.size());
        assertEquals("Wrong cycle size", 3, cycles.get(0).size());
        assertArrayEquals("Wrong graph cycle", new String[]{"A", "B", "C"}, cycles.get(0).toArray());
        assertEquals("Wrong cycle size", 4, cycles.get(1).size());
        assertArrayEquals("Wrong graph cycle", new String[]{"C", "D", "E", "F"}, cycles.get(1).toArray());
        assertEquals("Wrong cycle size", 5, cycles.get(2).size());
        assertArrayEquals("Wrong graph cycle", new String[]{"A", "B", "D", "E", "F"}, cycles.get(2).toArray());
        assertEquals("Wrong cycle size", 6, cycles.get(3).size());
        assertEquals("Wrong cycle size", 6, cycles.get(4).size());
    }

    @Test
    public void noCycles() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 1L);
        graph.addEdge("A", "E", 1L);
        graph.addEdge("B", "C", 1L);
        graph.addEdge("B", "D", 1L);
        graph.addEdge("C", "D", 1L);
        graph.addEdge("C", "E", 1L);

        TarjanSimpleCycles<String, Long> tarjanSimpleCycles = new TarjanSimpleCycles<>(graph);
        List<List<String>> cycles = tarjanSimpleCycles.findSimpleCycles();
        assertEquals("There should be no cycles", 0, cycles.size());
    }
}
