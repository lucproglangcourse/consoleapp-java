package edu.luc.cs.consoleapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestSlidingQueueInteractive {

  @Test
  public void testInteractiveBehavior() {
    // the test input
    final var input = Stream.of("asdf", "qwer", "oiui", "zxcv");
    // the expected interaction trace
    final var expectedTrace = new LinkedList<TraceEvent>();
    expectedTrace.add(new InputEvent("asdf"));
    expectedTrace.add(new OutputEvent("asdf"));
    expectedTrace.add(new InputEvent("qwer"));
    expectedTrace.add(new OutputEvent("asdf", "qwer"));
    expectedTrace.add(new InputEvent("oiui"));
    expectedTrace.add(new OutputEvent("asdf", "qwer", "oiui"));
    expectedTrace.add(new InputEvent("zxcv"));
    expectedTrace.add(new OutputEvent("qwer", "oiui", "zxcv"));
    // create and exercise the SUT
    final var sut = new SlidingQueue(3, input,);
    final var actualTrace = new Tracing(sut).run(input);
    // make sure the expected and actual traces match
    assertEquals(expectedTrace, actualTrace);
  }
}

// A mini-framework for trace-based testing of interactive behavior (WIP)

interface TraceEvent {}

class InputEvent implements TraceEvent {
  private final String value;

  public InputEvent(final String value) {
    this.value = value;
  }

  @Override
  public boolean equals(final Object that) {
    return that == this
        || that != null
            && (that instanceof InputEvent)
            && this.value.equals(((InputEvent) that).value);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public String toString() {
    return "InputEvent(" + value + ")";
  }
}

class OutputEvent implements TraceEvent {
  private final List<String> value;

  public OutputEvent(final String... values) {
    this.value = Arrays.asList(values);
  }

  @Override
  public int hashCode() {
    return value.hashCode();
  }

  @Override
  public boolean equals(final Object that) {
    return that == this
        || that != null
            && (that instanceof OutputEvent)
            && this.value.equals(((OutputEvent) that).value);
  }

  @Override
  public String toString() {
    return "OutputEvent(" + value + ")";
  }
}

class Tracing {

  private final SlidingQueue sut;

  /** The actual interaction event trace. */
  private final List<TraceEvent> trace = new LinkedList<>();

  public Tracing(final SlidingQueue sut) {
    this.sut = sut;
  }

  public List<TraceEvent> run(final Stream<String> input) {
    final var tracedInput =
        input.map(
            value -> {
              trace.add(new InputEvent(value));
              return value;
            });
    // output sink that traces every invocation of update()
    final OutputObserver outputToTrace =
        value -> {
          final var snapshot = new LinkedList<>(value);
          trace.add(new OutputEvent(snapshot.toArray(new String[] {})));
          return true;
        };
    sut.process(tracedInput, outputToTrace);
    return Collections.unmodifiableList(trace);
  }
}
