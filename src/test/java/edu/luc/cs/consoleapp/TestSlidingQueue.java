package edu.luc.cs.consoleapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSlidingQueue {

  @Test
  public void testSlidingWindowEmpty() {
    final var input = Stream.<String>empty();
    final var outputToList = new OutputToList();
    final var sut = new SlidingQueue(3, input, outputToList);
    sut.process();
    final var result = outputToList.result;
    assertTrue(result.isEmpty());
  }

  @Test
  public void testSlidingWindowNonempty() {
    final var input = Stream.of("asdf", "qwer", "oiui", "zxcv");
    final var outputToList = new OutputToList();
    final var sut = new SlidingQueue(3, input, outputToList);
    sut.process();
    final var result = outputToList.result;
    assertEquals(4, result.size());
    assertEquals(List.of("asdf"), result.get(0));
    assertEquals(List.of("asdf", "qwer"), result.get(1));
    assertEquals(List.of("asdf", "qwer", "oiui"), result.get(2));
    assertEquals(List.of("qwer", "oiui", "zxcv"), result.get(3));
  }

  private static class OutputToList implements OutputObserver {

    final List<Queue<String>> result = new ArrayList<>();

    @Override
    public boolean test(final Queue<String> value) {
      final var snapshot = new LinkedList<>(value);
      result.add(snapshot);
      return true;
    }
  }
}
