//import java.util.function.Consumer;
//
//public class ConsumerCrash {
//    public static void main(String[] args) {
//        // ❌ This will NOT compile: lambda returns a String, but Consumer expects void
//        Consumer<String> c = s -> "String" + s;
//
//        c.accept("Arcade");
//    }
//}