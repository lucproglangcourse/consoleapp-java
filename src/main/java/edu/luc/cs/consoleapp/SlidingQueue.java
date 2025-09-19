package edu.luc.cs.consoleapp;

import java.util.Queue;
import java.util.stream.Stream;

import org.apache.commons.collections4.queue.CircularFifoQueue;

class SlidingQueue {

  protected final Queue<String> queue;

  protected final Stream<String> input;

  protected final OutputObserver output;

  public SlidingQueue(final int queueSize, final Stream<String> input, final OutputObserver output) {
    this.queue = new CircularFifoQueue<>(queueSize);
    this.input = input;
    this.output = output;
  }

  public void process() {
    input
        .takeWhile(
            word -> {
              queue.add(word); // the oldest item automatically gets evicted
              return output.test(queue);
            })
        .count(); // forces evaluation of the entire stream
  }
}
