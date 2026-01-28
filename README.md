[![Java Gradle CI](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/gradle.yml/badge.svg)](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/gradle.yml)
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
* [Gradle](https://gradle.org/) 9.3 or later (using Gradle Wrapper)

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
$ ./gradlew run --args="3"
> Task :run
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

    $ ./gradlew run

With a specific command-line argument (sliding queue capacity):

    $ ./gradlew run --args="3"

# Running a specific main class directly

    $ ./gradlew run

or

    $ ./gradlew run -PmainClass=edu.luc.cs.consoleapp.MainLeaky --args="3"

or

    $ ./gradlew run -PmainClass=edu.luc.cs.consoleapp.MainTestable --args="3"

# Running the tests

    $ ./gradlew test

# Generating the test coverage reports

    $ ./gradlew test jacocoTestReport

You can then view the full report in a web browser.
(Note that this will get generated only after all tests pass.)

On macOS:

    $ open build/reports/jacoco/test/html/index.html

On Linux:

    $ xdg-open build/reports/jacoco/test/html/index.html

On Windows: please let me know if you know how to do this from the WSL
command line. Otherwise you can open the index file in your web browser.

*Note that the report will show 0% coverage as long as there are failing tests.*

# Running the application outside Gradle

This uses the executable JAR with all dependencies included, avoiding the overhead of running through Gradle:

On Linux or macOS:

    $ ./gradlew clean shadowJar
    $ java -jar build/libs/consoleapp-0.3-jar-with-dependencies.jar 3

On Windows:

    > gradlew clean shadowJar
    > java -jar build\libs\consoleapp-0.3-jar-with-dependencies.jar 3

## Running with a different main class

To run a different main class (e.g., `MainLeaky`) using the standalone JAR, use the `-cp` (classpath) option with the fully qualified class name:

On Linux or macOS:

    $ java -cp build/libs/consoleapp-0.3-jar-with-dependencies.jar edu.luc.cs.consoleapp.MainLeaky 3

On Windows:

    > java -cp build\libs\consoleapp-0.3-jar-with-dependencies.jar edu.luc.cs.consoleapp.MainLeaky 3

Similarly, for other main classes:

    $ java -cp build/libs/consoleapp-0.3-jar-with-dependencies.jar edu.luc.cs.consoleapp.MainTestable 3
    $ java -cp build/libs/consoleapp-0.3-jar-with-dependencies.jar edu.luc.cs.consoleapp.MainStream 3
