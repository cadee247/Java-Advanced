package Chap18.Page643;

import java.util.concurrent.*;

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

    // This simulates the shopping action
    private void goShopping(int amount) {
        // Check if there is enough money before spending
        if (account.getBalance() >= amount) {
            System.out.println(name + " is about to spend");
            account.spend(amount); // Spend the money
            System.out.println(name + " finishes spending");
        } else {
            System.out.println("Sorry, not enough for " + name);
        }
    }
}

// This class represents the shared bank account
class BankAccount {
    private int balance = 100; // Starting balance is $100

    // Method to check the current balance
    public int getBalance() {
        return balance;
    }

    // Method to spend money from the account
    public void spend(int amount) {
        balance = balance - amount; // Subtract the amount from balance

        // Check if the account went below zero
        if (balance < 0) {
            System.out.println("Overdrawn!");
        }
    }
}

