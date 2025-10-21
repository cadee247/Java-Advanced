package Chap18.Page649;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

// This class represents the shared bank account
class BankAccount {
    private int balance = 100; // Starting balance is $100

    // This method is now synchronized, so only one thread can use it at a time
    public synchronized void spend(String name, int amount) {
        // Check if there's enough money before spending
        if (balance >= amount) {
            balance = balance - amount;
            // Check if the account is overdrawn (shouldn't happen with this protection)
            if (balance < 0) {
                System.out.println("Overdrawn!");
            }
        } else {
            // Not enough money
            System.out.println("Sorry, not enough for " + name);
        }
    }
}

