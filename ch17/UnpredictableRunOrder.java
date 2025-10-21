package ch17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UnpredictableRunOrder {
  public static void main(String[] args) {
    for (int i = 0; i < 100; i++) {
      ExecutorTestDrive.main(args);
    }
  }
}

class ThreadTestDrive {
  public static void main (String[] args) {
    Thread myThread = new Thread(() ->
      System.out.println("top o’ the stack"));
    myThread.start();
    System.out.println("back in main");
  }
}

class ExecutorTestDrive {
  public static void main (String[] args) {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> System.out.println("top o’ the stack"));
    System.out.println("back in main");
    executor.shutdown();
  }
}

