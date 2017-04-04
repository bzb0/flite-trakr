package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConnectionPriceSolverTest {

    @Test
    public void connectionPrice() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 43L);
        graph.addEdge("A", "C", 67L);
        graph.addEdge("B", "C", 17L);
        graph.addEdge("B", "D", 27L);
        graph.addEdge("D", "A", 23L);

        ConnectionPriceSolver<String, Long> solver = new ConnectionPriceSolver<>(graph);

        String answer1 = solver.solveQuestion("What is the price of the connection A-B-D?");
        assertEquals("Wrong answer", "70", answer1);

        String answer2 = solver.solveQuestion("What is the price of the connection A-B-D-A?");
        assertEquals("Wrong answer", "93", answer2);
    }

    @Test
    public void noConnectionFound() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 43L);
        graph.addEdge("A", "C", 67L);
        graph.addEdge("B", "C", 17L);
        graph.addEdge("B", "D", 27L);
        graph.addEdge("D", "A", 23L);

        ConnectionPriceSolver<String, Long> solver = new ConnectionPriceSolver<>(graph);

        String answer1 = solver.solveQuestion("What is the price of the connection A-C-D?");
        assertEquals("Wrong answer", "No such connection found!", answer1);

        String answer2 = solver.solveQuestion("What is the price of the connection A-D-C?");
        assertEquals("Wrong answer", "No such connection found!", answer2);
    }

    @Test(expected = UnsupportedQuestionException.class)
    public void unsupportedQuestion() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addEdge("A", "B", 43L);
        graph.addEdge("A", "C", 67L);
        graph.addEdge("B", "C", 17L);
        graph.addEdge("B", "D", 27L);
        graph.addEdge("D", "A", 23L);

        ConnectionPriceSolver<String, Long> solver = new ConnectionPriceSolver<>(graph);
        solver.solveQuestion("What is the cheapest connection from A to D?");
    }


}
