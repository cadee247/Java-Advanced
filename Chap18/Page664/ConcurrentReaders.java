package Chap18.Page664;

import java.time.LocalDateTime; // For capturing the current time
import java.util.List; // For using List interface
import java.util.concurrent.CopyOnWriteArrayList; // Thread-safe list for concurrent access
import java.util.concurrent.ExecutorService; // For managing threads
import java.util.concurrent.Executors; // For creating thread pools

import static java.time.format.DateTimeFormatter.ofLocalizedTime; // For formatting time
import static java.time.format.FormatStyle.MEDIUM; // Medium-style time format (e.g., 10:08:32 AM)

public class ConcurrentReaders {
    public static void main(String[] args) {

        // ✅ Create a thread-safe list to store chat messages
        // CopyOnWriteArrayList allows safe reading and writing from multiple threads
        List<Chat> chatHistory = new CopyOnWriteArrayList<>();

        // ✅ Create a thread pool with 3 threads
        // This pool will run tasks concurrently
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // ✅ Submit tasks to the thread pool
        // This loop runs 5 times, each time submitting 3 tasks
        for (int i = 0; i < 5; i++) {
            // 🔹 Task 1: Add a new chat message to the list
            executor.execute(() -> chatHistory.add(new Chat("Hi there!")));

            // 🔹 Task 2 & 3: Print the current chat history
            // These may run before or after the message is added, depending on thread timing
            executor.execute(() -> System.out.println(chatHistory));
            executor.execute(() -> System.out.println(chatHistory));
        }

        // ✅ Shut down the thread pool after all tasks are submitted
        // This tells the executor to stop accepting new tasks
        executor.shutdown();
    }
}

// ✅ Chat class represents a single chat message
final class Chat {
    private final String message;              // Stores the message text
    private final LocalDateTime timestamp;     // Stores the time the message was created

    // ✅ Constructor: sets the message and captures the current time
    public Chat(String message) {
        this.message = message;
        timestamp = LocalDateTime.now(); // Capture the exact time of creation
    }

    // ✅ toString(): returns a formatted version of the chat message
    public String toString() {
        // Format the timestamp using medium-style time (e.g., 10:08:32 AM)
        String time = timestamp.format(ofLocalizedTime(MEDIUM));
        return time + " " + message; // Combine time and message for display
    }
}