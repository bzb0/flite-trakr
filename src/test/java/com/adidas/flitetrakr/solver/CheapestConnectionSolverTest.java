package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheapestConnectionSolverTest {

    @Test
    public void shortestPath() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("B", "C", 100L);
        graph.addEdge("C", "A", 20L);
        graph.addEdge("B", "D", 4L);
        graph.addEdge("D", "C", 2L);
        graph.addEdge("D", "E", 8L);
        graph.addEdge("E", "F", 6L);
        graph.addEdge("F", "C", 3L);
        graph.addEdge("F", "A", 100L);
        graph.addEdge("D", "G", 100L);

        CheapestConnectionSolver<String, Long> solver = new CheapestConnectionSolver<>(graph);

        String answer1 = solver.solveQuestion("What is the cheapest connection from A to F?");
        assertEquals("Wrong answer", "A-B-D-E-F-23", answer1);

        String answer2 = solver.solveQuestion("What is the cheapest connection from A to A?");
        assertEquals("Wrong answer", "A-B-D-C-A-31", answer2);
    }

    @Test
    public void noConnectionFound() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");

        graph.addEdge("A", "B", 5L);
        graph.addEdge("B", "C", 100L);
        graph.addEdge("C", "A", 20L);
        graph.addEdge("B", "D", 4L);
        graph.addEdge("D", "C", 2L);
        graph.addEdge("D", "E", 8L);
        graph.addEdge("E", "F", 6L);
        graph.addEdge("F", "C", 3L);
        graph.addEdge("F", "A", 100L);
        graph.addEdge("D", "G", 100L);

        CheapestConnectionSolver<String, Long> solver = new CheapestConnectionSolver<>(graph);

        String answer1 = solver.solveQuestion("What is the cheapest connection from G to A?");
        assertEquals("Wrong answer", "No such connection found!", answer1);

        String answer2 = solver.solveQuestion("What is the cheapest connection from G to G?");
        assertEquals("Wrong answer", "No such connection found!", answer2);
    }

    @Test(expected = UnsupportedQuestionException.class)
    public void unsupportedQuestion() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();
        graph.addVertex("A");
        graph.addEdge("A", "B", 43L);

        CheapestConnectionSolver<String, Long> solver = new CheapestConnectionSolver<>(graph);
        solver.solveQuestion("What is the price of the connection A-C-B?");
    }

}
