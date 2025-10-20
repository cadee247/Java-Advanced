//import java.io.*;
//
//// This class is marked as Serializable, so its objects can be saved to a file
//public class Square implements Serializable {
//
//    // These are the properties (fields) of the Square class
//    private int width;
//    private int height;
//
//    // Constructor to create a Square object with specific width and height
//    public Square(int width, int height) {
//        this.width = width;
//        this.height = height;
//    }
//
//    // The main method where the program starts
//    public static void main(String[] args) {
//        // Create a new Square object with width 50 and height 20
//        Square mySquare = new Square(50, 20);
//
//        try {
//            // Create a FileOutputStream to write to a file named "foo.ser"
//            FileOutputStream fs = new FileOutputStream("foo.ser");
//
//            // Wrap the file stream with an ObjectOutputStream to write objects
//            ObjectOutputStream os = new ObjectOutputStream(fs);
//
//            // Serialize (save) the mySquare object to the file
//            os.writeObject(mySquare);
//
//            // Always close the stream when you're done
//            os.close();
//        } catch (Exception ex) {
//            // If something goes wrong, print the error
//            ex.printStackTrace();
//        }
//    }
//}
//
