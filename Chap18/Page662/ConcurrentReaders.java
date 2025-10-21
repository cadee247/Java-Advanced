package Chap18.Page662;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.time.format.DateTimeFormatter.ofLocalizedTime;
import static java.time.format.FormatStyle.MEDIUM;

public class ConcurrentReaders {
    public static void main(String[] args) {
        // Create a shared list to store chat messages
        List<Chat> chatHistory = new ArrayList<>();

        // Create a thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit tasks to the executor in a loop
        for (int i = 0; i < 5; i++) {
            // One thread adds a new Chat message to the list
            executor.execute(() -> chatHistory.add(new Chat("Hi there!")));

            // Two threads print the current chat history
            executor.execute(() -> System.out.println(chatHistory));
            executor.execute(() -> System.out.println(chatHistory));
        }

        // Shutdown the executor after tasks are submitted
        executor.shutdown();
    }
}

// The Chat class represents a single chat message
final class Chat {
    private final String message;              // The text of the message
    private final LocalDateTime timestamp;     // The time the message was created

    // Constructor sets the message and captures the current time
    public Chat(String message) {
        this.message = message;
        timestamp = LocalDateTime.now();
    }

    // Return a formatted string representation of the chat message
    public String toString() {
        String time = timestamp.format(ofLocalizedTime(MEDIUM));
        return time + " " + message;
    }
}

