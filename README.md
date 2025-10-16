[![Java Maven CI](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/java-maven.yml/badge.svg)](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/java-maven.yml)
[![codecov](https://codecov.io/github/lucproglangcourse/consoleapp-java/branch/main/graph/badge.svg?token=506MZ2VOP0)](https://codecov.io/github/lucproglangcourse/consoleapp-java)

# Learning objectives

* stream processing (finite vs. infinite/unbounded)
* pipes and filters architecture
* representing streams using the Iterator design pattern
* separation of processing and I/O concerns
* testability using the Observer design pattern
* time/space complexity and scalability

# System requirements

* Java 21 LTS release (recommended) or later
* [Maven](https://maven.apache.org/) 3.6.3 or later

You may also be able to install these requirements through your package manager or [SDKMAN!](https://sdkman.io/).

Alternatively, you can use an IDE such as IntelliJ IDEA or Eclipse.

# Description

This application maintains a running FIFO queue of the last n words read from the standard input (stdin).
For each word read, the application prints the updated queue to the standard output (stdout) in the format

    [w1, w2, ..., wn]

where wn is the most recent word read, and w1 is the oldest word still in the queue.

The default queue capacity is 10, and a different capacity can be passed as a command-line argument.

If multiple words are entered on the same line, the application processes them separately, using non-word characters, such as whitespace, as separators.

# Examples

```
$ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.Main" -Dexec.args="3"
[INFO] Scanning for projects...
[INFO] ...
[INFO] --- exec:3.1.0:java (default-cli) @ consoleapp ---
w1 w2
[w1]
[w1, w2]
w3
[w1, w2, w3]
w4 w5
[w2, w3, w4]
[w3, w4, w5]
w6
[w4, w5, w6]
^C
```

# Running the application

Without command-line arguments:

    $ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.Main"

With a specific command-line argument (sliding queue capacity):

    $ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.Main" -Dexec.args="3"

# Running a specific main class directly

    $ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.Main"

or

    $ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.MainLeaky" -Dexec.args="3"

or

    $ mvn compile exec:java -Dexec.mainClass="edu.luc.cs.consoleapp.MainTestable" -Dexec.args="3"

# Running the tests

    $ mvn test

# Generating the test coverage reports

    $ mvn test jacoco:report

You can then view the full report in a web browser.
(Note that this will get generated only after all tests pass.)

On macOS:

    $ open target/site/jacoco/index.html

On Linux:

    $ xdg-open target/site/jacoco/index.html

On Windows: please let me know if you know how to do this from the WSL
command line. Otherwise you can open the index file in your web browser.

*Note that the report will show 0% coverage as long as there are failing tests.*

# Running the application outside Maven

This uses the executable JAR with all dependencies included, avoiding the overhead of running through Maven:

On Linux or macOS:

    $ mvn clean package
    $ java -jar target/consoleapp-0.3-jar-with-dependencies.jar 3

On Windows:

    > mvn clean package
    > java -jar target\consoleapp-0.3-jar-with-dependencies.jar 3
