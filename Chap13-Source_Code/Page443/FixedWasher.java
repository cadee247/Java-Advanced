public class FixedWasher {
    LaundryW laundry = new LaundryW();

    public void foo() throws ClothingE {
        laundry.doLaundry();
    }

    public static void main(String[] args) {
        FixedWasher a = new FixedWasher();
        try {
            a.foo(); //Now the exception is properly handled
        } catch (ClothingE cex) {
            System.out.println("Caught exception: " + cex.getMessage());
        }
    }
}

class LaundryW {
    void doLaundry() throws ClothingE {
        throw new ClothingE("Shirt shrunk!");
    }
}

class ClothingE extends Exception {
    public ClothingE(String message) {
        super(message);
    }
}

