package com.adidas.flitetrakr.solver;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AllConnectionsSolverTest {

    @Test
    public void findAllConnectionsBelowPrice() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 43L);
        graph.addEdge("A", "C", 67L);
        graph.addEdge("B", "C", 17L);
        graph.addEdge("B", "D", 27L);
        graph.addEdge("D", "A", 23L);
        graph.addEdge("E", "A", 23L);

        AllConnectionsSolver<String, Long> solver = new AllConnectionsSolver<>(graph);

        String answer1 = solver.solveQuestion("Find all connections from A to D below 170 Euros!");
        assertEquals("Wrong answer", "A-B-D-70, A-B-D-A-B-D-163", answer1);

        String answer2 = solver.solveQuestion("Find all connections from A to A below 250 Euros!");
        assertEquals("Wrong answer", "A-B-D-A-93, A-B-D-A-B-D-A-186", answer2);

        String answer3 = solver.solveQuestion("Find all connections from A to A below 350 Euros!");
        assertEquals("Wrong answer", "A-B-D-A-93, A-B-D-A-B-D-A-186, A-B-D-A-B-D-A-B-D-A-279", answer3);
    }

    @Test
    public void noConnectionFound() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();

        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");

        graph.addEdge("A", "B", 43L);
        graph.addEdge("A", "C", 67L);
        graph.addEdge("B", "C", 17L);
        graph.addEdge("B", "D", 27L);
        graph.addEdge("D", "A", 23L);
        graph.addEdge("E", "A", 23L);

        AllConnectionsSolver<String, Long> solver = new AllConnectionsSolver<>(graph);

        String answer1 = solver.solveQuestion("Find all connections from A to E below 350 Euros!");
        assertEquals("Wrong answer", "No such connection found!", answer1);

        String answer2 = solver.solveQuestion("Find all connections from E to E below 350 Euros!");
        assertEquals("Wrong answer", "No such connection found!", answer2);
    }

    @Test(expected = UnsupportedQuestionException.class)
    public void unsupportedQuestion() {
        SimpleDirectedGraph<String, Long> graph = new SimpleDirectedGraph<String, Long>();
        graph.addVertex("A");
        graph.addEdge("A", "B", 43L);

        AllConnectionsSolver<String, Long> solver = new AllConnectionsSolver<>(graph);
        solver.solveQuestion("What is the price of the connection A-C-B?");
    }

}
