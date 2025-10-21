import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

// Server class for handling multiple client connections and broadcasting beat patterns
public class MusicServer {
    // List to keep track of all connected clients' output streams
    // Synchronized implicitly via add/remove, but for safety, we use ArrayList (not thread-safe, but managed in this context)
    private final List<ObjectOutputStream> clientOutputStreams = new ArrayList<>();

    // Entry point to start the server
    public static void main(String[] args) {
        // Start the server
        new MusicServer().go();
    }

    // Main server loop to accept connections
    public void go() {
        try {
            // Create a server socket listening on port 4242
            ServerSocket serverSock = new ServerSocket(4242);

            // Create a thread pool to handle multiple client connections
            ExecutorService threadPool = Executors.newCachedThreadPool();

            // Continuously listen for client connections
            while (!serverSock.isClosed()) {
                // Accept incoming client connection
                Socket clientSocket = serverSock.accept();

                // Create an output stream to send data to the client
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

                // Store this client's output stream in the list (synchronized access if needed, but here it's single-threaded addition)
                clientOutputStreams.add(out);

                // Create a new handler for this client and run it in a separate thread
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                threadPool.execute(clientHandler);

                // Log that a new connection has been made
                System.out.println("Got a connection");
            }
        } catch (IOException e) {
            // Print any server-side errors
            e.printStackTrace();
        }
    }

    // Method to send messages and beat data to all connected clients
    // Broadcasts the received message and sequence to everyone
    public void tellEveryone(Object usernameAndMessage, Object beatSequence) {
        // Iterate over a copy or synchronize if multi-threaded modifications occur, but here it's safe
        for (ObjectOutputStream clientOutputStream : clientOutputStreams) {
            try {
                // Send the message (e.g., username + chat) to each client
                clientOutputStream.writeObject(usernameAndMessage);
                // Send the beat pattern data to each client
                clientOutputStream.writeObject(beatSequence);
            } catch (IOException e) {
                // Handle errors in writing to a client (e.g., client disconnected)
                e.printStackTrace();
            }
        }
    }

    // Inner class to handle input from a connected client
    // Each client has its own handler running in a separate thread
    public class ClientHandler implements Runnable {
        private ObjectInputStream in;

        // Constructor sets up input stream from client
        public ClientHandler(Socket socket) {
            try {
                in = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Continuously read objects sent by the client
        public void run() {
            Object userNameAndMessage;
            Object beatSequence;
            try {
                // As long as data is coming from the client
                while ((userNameAndMessage = in.readObject()) != null) {
                    // Read beat data after the message
                    beatSequence = in.readObject();

                    // Log and forward both objects to all clients
                    System.out.println("read two objects");
                    tellEveryone(userNameAndMessage, beatSequence);
                }
            } catch (IOException | ClassNotFoundException e) {
                // Handle communication errors (e.g., client disconnect or invalid object)
                e.printStackTrace();
            }
        }
    }
}