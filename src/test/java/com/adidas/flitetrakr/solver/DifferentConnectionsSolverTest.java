package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DifferentConnectionsSolverTest {

    @Test
    public void findDifferentPathsMaximumNumberOfStops() {
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

        DifferentConnectionsSolver<String, Long> solver = new DifferentConnectionsSolver<>(graph);

        String answer1 = solver.solveQuestion("How many different connections with maximum 3 stops exists between A and B?");
        assertEquals("Wrong answer", "2", answer1);

        String answer2 = solver.solveQuestion("How many different connections with maximum 8 stops exists between A and B?");
        assertEquals("Wrong answer", "3", answer2);

        String answer3 = solver.solveQuestion("How many different connections with maximum 5 stops exists between A and A?");
        assertEquals("Wrong answer", "2", answer3);

        String answer4 = solver.solveQuestion("How many different connections with maximum 1 stops exists between A and A?");
        assertEquals("Wrong answer", "0", answer4);
    }

    @Test
    public void findDifferentPathsMinimumNumberOfStops() {
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

        DifferentConnectionsSolver<String, Long> solver = new DifferentConnectionsSolver<>(graph);

        String answer1 = solver.solveQuestion("How many different connections with minimum 1 stops exists between A and C?");
        assertEquals("Wrong answer", "1", answer1);

        String answer2 = solver.solveQuestion("How many different connections with minimum 2 stops exists between A and C?");
        assertEquals("Wrong answer", "0", answer2);
    }

    @Test
    public void findDifferentPathsExactNumberOfStops() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");

        graph.addEdge("A", "B", 1L);
        graph.addEdge("A", "H", 1L);
        graph.addEdge("B", "C", 1L);
        graph.addEdge("C", "D", 1L);
        graph.addEdge("C", "F", 1L);
        graph.addEdge("C", "G", 1L);
        graph.addEdge("D", "E", 1L);
        graph.addEdge("E", "C", 1L);
        graph.addEdge("F", "A", 1L);
        graph.addEdge("G", "D", 1L);
        graph.addEdge("H", "C", 1L);

        DifferentConnectionsSolver<String, Long> solver = new DifferentConnectionsSolver<>(graph);

        String answer1 = solver.solveQuestion("How many different connections with exactly 2 stops exists between A and D?");
        assertEquals("Wrong answer", "2", answer1);

        String answer2 = solver.solveQuestion("How many different connections with exactly 3 stops exists between A and D?");
        assertEquals("Wrong answer", "2", answer2);
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

        DifferentConnectionsSolver<String, Long> solver = new DifferentConnectionsSolver<>(graph);
        solver.solveQuestion("What is the price of the connection A-C-B?");
    }
}
