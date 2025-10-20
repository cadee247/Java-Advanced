import java.io.FileWriter;
import java.io.IOException;

public class WriteAFile {
    public static void main(String[] args) {
        try {
            // Create a FileWriter to write to a file named "Foo.txt"
            FileWriter writer = new FileWriter("Foo.txt");

            // Write a string to the file
            writer.write("hello foo!");

            // Close the writer to save the changes
            writer.close();

            // Inform the user that writing was successful
            System.out.println("Data has been written to Foo.txt");
        } catch (IOException ex) {
            // Print the error if something goes wrong
            ex.printStackTrace();
        }
    }
}

