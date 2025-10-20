import java.io.*;  // Import classes for input and output streams

public class GameSaverTest {
    public static void main(String[] args) {

        // Create three GameCharacter objects with different stats and weapons
        GameCharacter one = new GameCharacter(50, "Elf",
                new String[]{"bow", "sword", "dust"});

        GameCharacter two = new GameCharacter(200, "Troll",
                new String[]{"bare hands", "big ax"});

        GameCharacter three = new GameCharacter(120, "Magician",
                new String[]{"spells", "invisibility"});

        // Imagine we modify their state here during gameplay...

        // Serialize (save) the GameCharacter objects to a file
        try {
            // Create an ObjectOutputStream to write objects to "Game.ser"
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("Game.ser"));

            // Write each GameCharacter object to the file
            os.writeObject(one);
            os.writeObject(two);
            os.writeObject(three);

            // Close the stream
            os.close();

        } catch (IOException ex) {
            // Handle exceptions related to file writing or serialization
            ex.printStackTrace();
        }

        // Deserialize (load) the GameCharacter objects back from the file
        try {
            // Create an ObjectInputStream to read objects from "Game.ser"
            ObjectInputStream is = new ObjectInputStream(
                    new FileInputStream("Game.ser"));

            // Read the objects back in the same order they were saved
            GameCharacter oneRestore = (GameCharacter) is.readObject();
            GameCharacter twoRestore = (GameCharacter) is.readObject();
            GameCharacter threeRestore = (GameCharacter) is.readObject();

            // Use the restored objects (e.g., print their type to verify)
            System.out.println("One's type: " + oneRestore.getType());
            System.out.println("Two's type: " + twoRestore.getType());
            System.out.println("Three's type: " + threeRestore.getType());

        } catch (Exception ex) {
            // Handle exceptions related to file reading or class casting
            ex.printStackTrace();
        }
    }
}
