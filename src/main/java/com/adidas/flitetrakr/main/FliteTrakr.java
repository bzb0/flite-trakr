package com.adidas.flitetrakr.main;

import com.adidas.flitetrakr.exception.UnsupportedQuestionException;
import com.adidas.flitetrakr.graph.SimpleDirectedGraph;
import com.adidas.flitetrakr.graph.SimpleGraph;
import com.adidas.flitetrakr.parse.FileReader;
import com.adidas.flitetrakr.solver.Solver;
import com.adidas.flitetrakr.solver.factory.SolverFactory;
import com.adidas.flitetrakr.util.Triple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

/**
 * The Main class. This class reads the input file,
 * creates the flight graph and processes the questions.
 *
 * @author Bogdan Zafirov
 */
public class FliteTrakr {

    public static void main(final String[] args) throws IOException {
        if (args == null || args.length < 1) {
            System.err.println("Usage: java -jar FliteTrakr-jar-with-dependencies.jar <PATH TO INPUT FILE>");
            System.exit(0);
        }

        final String fileName = args[0];
        if (!Files.exists(Paths.get(fileName), new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
            System.err.println("File for the given path doesn't exist. Path: " + fileName);
            System.exit(0);
        }

        final SimpleGraph<String, Long> flightGraph = new SimpleDirectedGraph<>();
        /* Parsing the connections & creating the flight graph. */
        final FileReader reader = new FileReader(fileName);
        for (final Triple<String, String, Long> triple : reader.getConnections()) {
            flightGraph.addVertex(triple.getFirst());
            flightGraph.addVertex(triple.getSecond());
            flightGraph.addEdge(triple.getFirst(), triple.getSecond(), triple.getThird());
        }

        /* Processing & answering the questions. */
        final Solver solver = new SolverFactory().createSolverChain(flightGraph);
        reader.getQuestions().forEach(question -> {
            int noIndex = question.indexOf(":");
            if(noIndex == -1) {
                throw new UnsupportedQuestionException("Can't process question: " + question);
            }

            final String answer = solver.solveQuestion(question.substring(noIndex + 1).trim());
            System.out.println(question.substring(0, noIndex + 1) + " " + answer);
        });
    }
}
