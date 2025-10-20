import java.io.*;

// Pond class is marked as Serializable, so it *can* be serialized
public class Pond implements Serializable {

    // This is an instance variable of type Duck
    // However, Duck is NOT Serializable, and this will cause problems
//    private Duck duck = new Duck();               //uncomment to test

    public static void main(String[] args) {
        // Create a new Pond object
        Pond myPond = new Pond();

        try {
            // Create a FileOutputStream to write to a file named "Pond.ser"
            FileOutputStream fs = new FileOutputStream("Pond.ser");

            // Wrap it in an ObjectOutputStream to serialize the object
            ObjectOutputStream os = new ObjectOutputStream(fs);

            // Attempt to write the Pond object to the file
            os.writeObject(myPond); // This will throw an exception!

            os.close();
        } catch (Exception ex) {
            // Catch and print the exception
            ex.printStackTrace();  // Output will show java.io.NotSerializableException: Duck
        }
    }
}

// This class is missing "implements Serializable"
// So Java can't serialize it, even if it's part of a serializable object
//public class Duck {                  //uncomment
//}

