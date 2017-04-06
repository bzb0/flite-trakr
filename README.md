# Adidas FliteTrakr Coding challenge

## Description

Implementation of the Adidas Backend coding challenge ``FliteTrakr``. The coding challenge is implemented in Java 8 and doesn't use any other framework
besides Junit 4, which was used for the unit tests. The coding challenge is implemented as an Apache Maven project. 

## Building the application

The project can be compiled with the following maven command:

```
mvn clean compile
```

In order to run the unit tests we have to execute the following command:

```
mvn clean test
```

Finally, the standalone Java command line application can be build with this command:

```
mvn clean package
```

The previous command will generate the ``FliteTrakr-1.0-SNAPSHOT-jar-with-dependencies.jar`` Fat JAR file in the ``target`` directory.

## Running the command line application

As specified in the coding challenge description, the CLI application can be started with the following command: 

```
java -jar FliteTrakr-1.0-SNAPSHOT-jar-with-dependencies.jar <PATH_TO_INPUT_FILE>
```

If no file is specified the CLI application will print a simple usage guide.
