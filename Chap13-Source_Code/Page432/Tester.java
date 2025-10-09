//class Foo {
//    int getNum() {
//        return 42;
//    }
//}
//
//class X {
//    boolean shouldThrow;
//
//    X(boolean shouldThrow) {
//        this.shouldThrow = shouldThrow;
//    }
//
//    Foo doRiskyThing() throws Exception {
//        if (shouldThrow) {
//            throw new Exception("Something went wrong");
//        }
//        return new Foo();
//    }
//}
//
//public class Tester {
//    public static void main(String[] args) {
//        System.out.println("=== Test 1: Success path ===");
//        test(false);  // Should NOT throw
//
//        System.out.println("\n=== Test 2: Exception path ===");
//        test(true);   // Should throw
//    }
//
//    static void test(boolean shouldThrow) {
//        X x = new X(shouldThrow);
//        try {
//            Foo f = x.doRiskyThing();
//            int b = f.getNum(); // This will be skipped if exception occurs
//        } catch (Exception e) {
//            System.out.println("failed");
//        }
//        System.out.println("We made it!");
//    }
//}
