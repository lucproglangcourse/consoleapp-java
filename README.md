[![Java sbt CI](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/java-maven.yml/badge.svg)](https://github.com/lucproglangcourse/consoleapp-java/actions/workflows/java-maven.yml)
[![codecov](https://codecov.io/github/lucproglangcourse/consoleapp-java/branch/main/graph/badge.svg?token=506MZ2VOP0)](https://codecov.io/github/lucproglangcourse/consoleapp-java)

# Learning objectives

* stream processing (finite vs. infinite/unbounded)
* pipes and filters architecture
* representing streams using the Iterator design pattern
* separation of processing and I/O concerns
* testability using the Observer design pattern
* time/space complexity and scalability

# System requirements

* Java 11 SDK or later (Java 21 LTS release recommended)
* [SBT](https://www.scala-sbt.org/1.x/docs/Setup.html)

You may also be able to install these requirements through your package manager or [SDKMAN!](https://sdkman.io/).

Alternatively, you can use an IDE such as IntelliJ IDEA or Android Studio.

# Description

This application maintains a running FIFO queue of the last n words read from the standard input (stdin).
For each word read, the application prints the updated queue to the standard output (stdout) in the format

    [w1, w2, ..., wn]

where wn is the most recent word read, and w1 is the oldest word still in the queue.

The default queue capacity is 10, and a different capacity can be passed as a command-line argument.

If multiple words are entered on the same line, the application processes them separately, using non-word characters, such as whitespace, as separators.

# Examples

```
$ sbt "run 3"
[info] ...
Multiple main classes detected, select one to run:

 [1] hw.Main
 [2] hw.MainLeaky
 [3] hw.MainTestable
1
[info] running hw.Main
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
EOF
```

# Running the application

Without command-line arguments:

    $ sbt run

With a specific command-line argument (sliding queue capacity):

    $ sbt "run 3"

SBT will then prompt you to choose the specific main class you want to run.

# Running a specific main class directly

    $ sbt "runMain edu.luc.cs.consoleapp.Main"

or

    $ sbt "runMain edu.luc.cs.consoleapp.Main 3"

# Running the tests

    $ sbt test

# Generating the test coverage reports

    $ sbt jacoco

You will then see a summary report in the terminal, and you can view the full report in a web browser.
(Note that these will get generated only after all tests pass.)

On macOS:

    $ open target/scala-3.7.3/jacoco/report/html/index.html

On Linux:

    $ xdg-open target/scala-3.7.3/jacoco/report/html/index.html

On Windows: please let me know if you know how to do this from the WSL
command line. Otherwise you can open the index file in your web browser.

*Note that the report will show 0% coverage as long as there are failing tests.*

# Running the application outside SBT

This avoids the performance overhead of running the application through SBT and allows passing command-line arguments directly:

On Linux or Mac OS X:

    $ sbt stage
    $ ./target/universal/stage/bin/main 3

On Windows:

    > sbt stage
    > .\target\universal\stage\bin\main 3
