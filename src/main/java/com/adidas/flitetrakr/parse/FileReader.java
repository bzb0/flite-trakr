package com.adidas.flitetrakr.parse;

import com.adidas.flitetrakr.util.Triple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * File utility class offering functionality for parsing question-input files.
 *
 * @author Bogdan Zafirov
 */
public class FileReader {

    /* The file name. */
    private final String fileName;
    /* Connections prefix. */
    private static final String CONNECTION_STR = "Connections:";

    public FileReader(final String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parses the flight connections from the input file.
     *
     * @return List of triples containing the source & target vertices,
     *         as well as the payload between the edge.
     * @throws IllegalArgumentException Thrown if the input file can't be parsed or read.
     * @throws NumberFormatException Thrown if the edge attribute is not a number.
     */
    public List<Triple<String, String, Long>> getConnections() {
        final String header;
        try {
            header = Files.lines(Paths.get(fileName)).findFirst().get();
        } catch(IOException exception) {
            throw new IllegalArgumentException("Exception while reading input file.", exception);
        }

        int connectionIndex = header.indexOf(CONNECTION_STR);
        if(connectionIndex == -1) {
            throw new IllegalArgumentException("Invalid file format.");
        }
        connectionIndex += CONNECTION_STR.length();

        final String [] connections = header.substring(connectionIndex).split(",");
        final List<Triple<String, String, Long>> result = new ArrayList<>(connections.length);
        for(String connection : connections) {
            final String [] data = connection.split("-");
            if(data.length != 3) {
                throw new IllegalArgumentException("Invalid file format.");
            }
            result.add(new Triple(data[0].trim(), data[1].trim(), Long.parseLong(data[2].trim())));
        }
        return result;
    }

    /**
     * Returns a lazy stream for iterating over the file questions.
     *
     * @return Stream of all the lines in the input file without the header (first) line.
     * @throws IllegalArgumentException Thrown if the file can't be read.
     */
    public Stream<String> getQuestions() {
        try {
            return Files.lines(Paths.get(fileName)).skip(1);
        } catch (IOException e) {
            throw new IllegalArgumentException("Exception while reading input file.", e);
        }
    }
}