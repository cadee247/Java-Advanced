package Chap13.Page435_437;

// Laundry class that might throw clothing-related exceptions
public class Laundry {

    // This method declares that it might throw PantsException or LingerieException
    public void doLaundry() throws PantsException, LingerieException, TeeShirtException {
        System.out.println("Starting laundry...");

        // For now, we are only throwing PantsException
        throw new PantsException(); // You can change this line to test other exceptions

        // Example:
        // throw new LingerieException();
        // throw new TeeShirtException();
    }
}
