package ch17;

import java.util.concurrent.*;

public class PredictableRunOrder {
  public static void main(String[] args) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    for (int i = 0; i < 100; i++) {
      PredictableSleep.main(args);
    }
    long endTime = System.currentTimeMillis();
    System.out.println(endTime-startTime);
  }
}

class PredictableSleep {
  public static void main (String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> sleepThenPrint());
    System.out.println("back in main");
    executor.shutdown();
    executor.awaitTermination(2, TimeUnit.SECONDS);
  }

  private static void sleepThenPrint() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("top o’ the stack");
  }
}

class SleepWithTimeUnit {
  public static void main (String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(() -> sleepThenPrint());
    System.out.println("back in main");
    executor.shutdown();
    executor.awaitTermination(2, TimeUnit.SECONDS);
  }

  private static void sleepThenPrint() {
    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("top o’ the stack");
  }
}

class PredictableLatch {
  public static void main (String[] args) throws InterruptedException {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    CountDownLatch latch = new CountDownLatch(1);
    executor.execute(() -> waitForLatchThenPrint(latch));
    System.out.println("back in main");
    latch.countDown();
    executor.shutdown();
    executor.awaitTermination(2, TimeUnit.SECONDS);
  }

  private static void waitForLatchThenPrint(CountDownLatch latch) {
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("top o’ the stack");
  }
}

