package com.adidas.flitetrakr.solver.factory;

import com.adidas.flitetrakr.graph.SimpleGraph;
import com.adidas.flitetrakr.solver.*;

/**
 * Solver factory.
 *
 * @author Bogdan Zafirov
 */
public class SolverFactory {

    /**
     * Creates a chain of graph solvers that can solve the following questions:
     *
     * 1. What is the price of the connection A-B-C-D?
     * 2. What is the cheapest connection form A to B?
     * 3: How many different connections with (minimum|maximum|exactly) X stops exists between A and B?
     * 4: Find all connections from A to B below X Euros!
     *
     * @param graph The input graph.
     * @return The created chain of solvers.
     * @throws IllegalArgumentException Thrown if the input graph is null.
     */
    public Solver createSolverChain(final SimpleGraph<String, Long> graph) {
        if(graph == null) {
            throw new IllegalArgumentException("The input graph must not be null.");
        }

        /* Creating the solvers. */
        final ConnectionPriceSolver priceSolver = new ConnectionPriceSolver(graph);
        final CheapestConnectionSolver cheapestConnectionSolver = new CheapestConnectionSolver(graph);
        final AllConnectionsSolver allConnectionsSolver = new AllConnectionsSolver(graph);
        final DifferentConnectionsSolver differentConnectionsSolver = new DifferentConnectionsSolver(graph);

        /* Chaining the solvers. */
        priceSolver.setSuccessor(cheapestConnectionSolver);
        cheapestConnectionSolver.setSuccessor(allConnectionsSolver);
        allConnectionsSolver.setSuccessor(differentConnectionsSolver);

        return priceSolver;
    }
}
