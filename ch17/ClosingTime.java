package ch17;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class ClosingTime {
  public static void main(String[] args) throws InterruptedException {
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(new LongJob("Long Job"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.shutdown();

    try {
      boolean finished = threadPool.awaitTermination(4, TimeUnit.SECONDS);
      System.out.println("Finished? " + finished);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    threadPool.shutdownNow();
  }

  static void fullShutdown() throws InterruptedException {
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(new LongJob("Long Job 1"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.execute(new LongJob("Long Job 2"));
    threadPool.execute(new LongJob("Long Job 3"));
    threadPool.execute(new LongJob("Long Job 4"));
    threadPool.execute(new ShortJob("Shouldn't start job"));
    threadPool.shutdown();
    ShortJob tooLateJob = new ShortJob("Too late job");
    try {
      threadPool.execute(tooLateJob);
    } catch (RejectedExecutionException e) {
      System.out.println("Too late to start another job!! " + tooLateJob);
    }
    threadPool.awaitTermination(4, TimeUnit.SECONDS);

    List<Runnable> unfinished = threadPool.shutdownNow();

    System.out.println("unfinished.size() = " + unfinished.size());
    for (Runnable runnable : unfinished) {
      System.out.println(runnable);
    }

  }

  static void shutdownGracefully() {
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(new LongJob("Long Job 1"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.execute(new LongJob("Long Job 2"));
    threadPool.execute(new ShortJob("Queued job"));
    threadPool.shutdown();
    ShortJob tooLateJob = new ShortJob("Too late job");
    try {
      threadPool.execute(tooLateJob);
    } catch (RejectedExecutionException e) {
      System.out.println("Too late to start another job!! " + tooLateJob);
    }
    System.out.println("threadPool.isShutdown() = " + threadPool.isShutdown());
    System.out.println("threadPool.isTerminated() = " + threadPool.isTerminated());

    try {
      boolean poolFinished = threadPool.awaitTermination(100, TimeUnit.SECONDS);
      System.out.println("Finished? " + poolFinished);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("threadPool.isShutdown() = " + threadPool.isShutdown());
    System.out.println("threadPool.isTerminated() = " + threadPool.isTerminated());
  }

  static void shutdownGracefullyWorkstealing() {
    ExecutorService threadPool = Executors.newWorkStealingPool(2);
    threadPool.execute(new LongJob("Long Job 1"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.execute(new LongJob("Long Job 2"));
    threadPool.execute(new ShortJob("Queued job"));
    threadPool.shutdown();
    ShortJob tooLateJob = new ShortJob("Too late job");
    try {
      threadPool.execute(tooLateJob);
    } catch (RejectedExecutionException e) {
      System.out.println("Too late to start another job!! " + tooLateJob);
    }
    System.out.println("threadPool.isShutdown() = " + threadPool.isShutdown());
    System.out.println("threadPool.isTerminated() = " + threadPool.isTerminated());

    try {
      System.out.println("Finished? " + threadPool.awaitTermination(4, TimeUnit.SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("threadPool.isShutdown() = " + threadPool.isShutdown());
    System.out.println("threadPool.isTerminated() = " + threadPool.isTerminated());
  }

  static void forceShutdown() {
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(new LongJob("Long Job 1"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.execute(new LongJob("Long Job 2"));
    threadPool.execute(new ShortJob("Shouldn't start job"));
    List<Runnable> unfinished = threadPool.shutdownNow();

    System.out.println("unfinished.size() = " + unfinished.size());
    for (Runnable runnable : unfinished) {
      System.out.println(runnable);
    }
  }

  static void forceShutdownWorkStealing() {
    ExecutorService threadPool = Executors.newWorkStealingPool(2);
    threadPool.execute(new LongJob("Long Job 1"));
    threadPool.execute(new ShortJob("Short Job"));
    threadPool.execute(new LongJob("Long Job 2"));
    threadPool.execute(new ShortJob("Shouldn't start job"));
    List<Runnable> unfinished = threadPool.shutdownNow();
    try {
      System.out.println("Finished? " + threadPool.awaitTermination(1, TimeUnit.SECONDS));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("unfinished.size() = " + unfinished.size());
    for (Runnable runnable : unfinished) {
      System.out.println(runnable);
    }
  }

}

abstract class NamedJob implements Runnable {
  protected String jobName;

  NamedJob(String jobName) {
    this.jobName = jobName;
  }

  @Override
  public String toString() {
    return "NamedJob{" +
            "jobName='" + jobName + '\'' +
            '}';
  }
}

class ShortJob extends NamedJob {
  ShortJob(String jobName) {
    super(jobName);
  }

  public void run() {
    System.out.println(jobName);
  }
}

class LongJob extends NamedJob {
  LongJob(String jobName) {
    super(jobName);
  }

  public void run() {
    try {
      TimeUnit.SECONDS.sleep(3);
      System.out.println(jobName);
    } catch (InterruptedException e) {
      System.out.println("Job interrupted: " + jobName);
      e.printStackTrace();
    }
  }
}
