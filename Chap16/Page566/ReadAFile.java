import java.io.*;

class ReadAFile {
    public static void main(String[] args) {
        try {
            // Create a File object for the file named "MyText.txt"
            File myFile = new File("src\\Chap16\\Page566\\MyText.txt");

            // Create a FileReader to read the file
            FileReader fileReader = new FileReader(myFile);

            // Wrap FileReader in BufferedReader for efficient reading line by line
            BufferedReader reader = new BufferedReader(fileReader);

            String line;  // Variable to hold each line read from the file

            // Read lines until there are no more lines (readLine returns null)
            while ((line = reader.readLine()) != null) {
                // Print the line read from the file to the console
                System.out.println(line);
            }

            // Close the BufferedReader to release system resources
            reader.close();
        } catch (IOException e) {
            // If an IOException occurs, print the stack trace to help with debugging
            e.printStackTrace();
        }
    }
}
