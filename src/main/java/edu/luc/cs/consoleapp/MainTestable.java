package edu.luc.cs.consoleapp;

import java.util.Scanner;

// see https://stackoverflow.com/questions/1963806/#21699069
// why we're using this implementation instead of java.util.ArrayQueue!

public class MainTestable {

  public static final int LAST_N_WORDS = 10;

  public static void main(final String[] args) {

    // TODO consider using a command-line option library

    // perform argument validity checking
    if (args.length > 1) {
      System.err.println("usage: ./target/universal/stage/bin/consoleapp [ last_n_words ]");
      System.exit(2);
    }

    var lastNWords = LAST_N_WORDS;
    try {
      if (args.length == 1) {
        lastNWords = Integer.parseInt(args[0]);
        if (lastNWords < 1) {
          throw new NumberFormatException();
        }
      }
    } catch (final NumberFormatException ex) {
      System.err.println("argument should be a natural number");
      System.exit(4);
    }

    final var input = new Scanner(System.in).useDelimiter("(?U)[^\\p{Alpha}0-9']+");
    // a handler instance that sends updates to the console
    final OutputHandler outputToConsole =
      value -> {
        System.out.println(value);
        // terminate on I/O error such as SIGPIPE
        if (System.out.checkError()) {
          System.exit(1);
        }
      };
    final var slidingQueue = new SlidingQueue(lastNWords, input, outputToConsole);

    slidingQueue.process();
  }

  public static int getLastNWords() {
		return LAST_N_WORDS;
	}

	@Override
  public String toString() {
    return "MainTestable []";
  }
}
