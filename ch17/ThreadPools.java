package ch17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPools {

  public static void main(String[] args) throws InterruptedException {
    createWorkStealingPool();
  }

  private static void createCachedThreadPool() {
    ExecutorService threadPool = Executors.newCachedThreadPool();
    threadPool.execute(() -> run("Job 1"));
    threadPool.execute(() -> run("Job 2"));
    threadPool.execute(() -> run("Job 3"));
    threadPool.execute(() -> run("Job 4"));
    threadPool.shutdown();
  }

  private static void createFixedThreadPoolWithMoreJobsThanThreads() {
    ExecutorService threadPool = Executors.newFixedThreadPool(2);
    threadPool.execute(() -> run("Job 1"));
    threadPool.execute(() -> run("Job 2"));
    threadPool.execute(() -> run("Job 3"));
    threadPool.execute(() -> run("Job 4"));
    threadPool.shutdown();
  }

  private static void createScheduledThreadPoolAndSchedule() {
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);
    threadPool.schedule(() -> System.out.println("1"), 2, TimeUnit.SECONDS);
    threadPool.schedule(() -> System.out.println("2"), 1500, TimeUnit.MILLISECONDS);
    threadPool.schedule(() -> System.out.println("3"), 1, TimeUnit.SECONDS);
    threadPool.schedule(() -> System.out.println("4"), 500, TimeUnit.MILLISECONDS);
    threadPool.shutdown();
  }

  private static void createScheduledThreadPoolAndScheduleAtFixedRate() throws InterruptedException {
    ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(2);
    threadPool.scheduleAtFixedRate(() -> System.out.println("1"), 400, 1000, TimeUnit.MILLISECONDS);
    threadPool.scheduleAtFixedRate(() -> System.out.println("2"), 300, 2000, TimeUnit.MILLISECONDS);
    threadPool.scheduleAtFixedRate(() -> System.out.println("3"), 200, 3000, TimeUnit.MILLISECONDS);
    threadPool.scheduleAtFixedRate(() -> System.out.println("4"), 100, 4000, TimeUnit.MILLISECONDS);
    TimeUnit.SECONDS.sleep(20);
    threadPool.shutdown();
  }

  private static void createSingleThreadExecutor() {
    ExecutorService threadPool = Executors.newSingleThreadExecutor();
    threadPool.execute(() -> run("Job 1"));
    threadPool.execute(() -> run("Job 2"));
    threadPool.execute(() -> run("Job 3"));
    threadPool.execute(() -> run("Job 4"));
    threadPool.shutdown();
  }

  private static void createWorkStealingPool() throws InterruptedException {
    ExecutorService threadPool = Executors.newWorkStealingPool();
    threadPool.execute(() -> run("Job 1"));
    threadPool.execute(() -> run("Job 2"));
    threadPool.execute(() -> run("Job 3"));
    threadPool.execute(() -> run("Job 4"));
    threadPool.execute(() -> run("Job 5"));
    threadPool.execute(() -> run("Job 6"));
    threadPool.execute(() -> run("Job 7"));
    threadPool.execute(() -> run("Job 8"));
    threadPool.execute(() -> run("Job 9"));
    threadPool.execute(() -> run("Job 10"));
    threadPool.execute(() -> run("Job 11"));
    threadPool.execute(() -> run("Job 12"));
    threadPool.shutdown();
    threadPool.awaitTermination(20, TimeUnit.SECONDS);
  }

  public static void run(String jobName) {
    for (int i = 0; i < 25; i++) {
      String threadName = Thread.currentThread().getName();
      System.out.println(jobName + " is running on thread " + threadName);
    }
  }
}