package Chap18.Page657;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// This is the main class to run the program
public class RyanAndMonicaTest {
    public static void main(String[] args) {
        // Create one shared bank account with $100
        BankAccount account = new BankAccount();

        // Create two jobs: one for Ryan and one for Monica
        RyanAndMonicaJob ryan = new RyanAndMonicaJob("Ryan", account, 50);
        RyanAndMonicaJob monica = new RyanAndMonicaJob("Monica", account, 100);

        // Create a thread pool with 2 threads to run the jobs at the same time
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Start both jobs (they run in separate threads)
        executor.execute(ryan);
        executor.execute(monica);

        // Shutdown the executor when jobs are finished
        executor.shutdown();
    }
}

// This class represents the task each person will do: check balance and spend money
class RyanAndMonicaJob implements Runnable {
    private final String name;           // Name of the person (Ryan or Monica)
    private final BankAccount account;   // Shared bank account
    private final int amountToSpend;     // How much this person wants to spend

    RyanAndMonicaJob(String name, BankAccount account, int amountToSpend) {
        this.name = name;
        this.account = account;
        this.amountToSpend = amountToSpend;
    }

    // This method is run when the thread starts
    public void run() {
        goShopping(amountToSpend); // Try to spend money
    }

    // Try to spend money using the shared account
    private void goShopping(int amount) {
        System.out.println(name + " is about to spend");
        // Let the BankAccount handle the balance check and deduction
        account.spend(name, amount);
        System.out.println(name + " finishes spending");
    }
}

class BankAccount {
    // The account balance is stored as an AtomicInteger for safe concurrent access
    private final AtomicInteger balance = new AtomicInteger(100); // Starting balance: $100

    // Method to get the current balance
    public int getBalance() {
        return balance.get(); // Retrieves the current balance value
    }

    // Method to spend money from the account
    public void spend(String name, int amount) {
        // Get the current balance
        int initialBalance = balance.get();

        // Check if there's enough money before trying to spend
        if (initialBalance >= amount) {
            // Attempt to atomically update the balance using compareAndSet
            // It will only succeed if no other thread has changed the balance since we read it
            boolean success = balance.compareAndSet(initialBalance, initialBalance - amount);

            if (success) {
                // The update was successful; money was spent
                System.out.println(name + " successfully spent $" + amount);
            } else {
                // Another thread changed the balance before we could update it
                System.out.println("Sorry " + name + ", you haven't spent the money.");
            }
        } else {
            // Not enough money in the account
            System.out.println("Sorry, not enough for " + name);
        }
    }
}

