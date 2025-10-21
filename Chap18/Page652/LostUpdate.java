package Chap18.Page652;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// This class demonstrates the "Lost Update" concurrency problem
public class LostUpdate {
    public static void main(String[] args) throws InterruptedException {
        // Create a thread pool with 6 threads
        // These threads will all try to increment the balance at the same time
        ExecutorService pool = Executors.newFixedThreadPool(6);

        // Create one shared Balance object
        Balance balance = new Balance();

        // Run 1,000 tasks that each try to increment the balance by 1
        for (int i = 0; i < 1000; i++) {
            pool.execute(() -> balance.increment());  // Each thread runs increment()
        }

        // Shut down the pool so it won't accept more tasks
        pool.shutdown();

        // Wait until all threads finish (max wait = 1 minute)
        if (pool.awaitTermination(1, TimeUnit.MINUTES)) {
            // Print the final balance
            System.out.println("Final balance = " + balance.balance);
        }
    }
}

// This class holds the shared balance value
class Balance {
    int balance = 0;  // Starting value is 0

    // This method is called by multiple threads to increase the balance by 1
    public synchronized void increment() {
        balance++;
    }
}