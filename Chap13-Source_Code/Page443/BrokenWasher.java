//public class BrokenWasher {
//    Laundry laundry = new Laundry();
//
//    public void foo() throws ClothingException {
//        laundry.doLaundry();
//    }
//
//    public static void main(String[] args) {
//        BrokenWasher a = new BrokenWasher();
////        a.foo(); //This will cause a compile-time error because it's unhandled
//    }
//}
//
//class Laundry {
//    void doLaundry() throws ClothingException {
//        throw new ClothingException("Shirt shrunk!");
//    }
//}
//
//class ClothingException extends Exception {
//    public ClothingException(String message) {
//        super(message);
//    }
//}
