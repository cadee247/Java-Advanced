//import java.util.*;
//
//public class SortMountains {
//    public static void main(String[] args) {
//        new SortMountains().go();
//    }
//
//    public void go() {
//        List<Mountain> mountains = new ArrayList<>();
//        mountains.add(new Mountain("Longs", 14255));
//        mountains.add(new Mountain("Elbert", 14433));
//        mountains.add(new Mountain("Maroon", 14156));
//        mountains.add(new Mountain("Castle", 14265));
//
//        System.out.println("as entered:\n" + mountains);
//
//        mountains.sort((m1, m2) -> m1.name.compareTo(m2.name));
//        System.out.println("by name:\n" + mountains);
//
//        Collections.sort(mountains, (m1, m2) -> m2.height - m1.height);
//        System.out.println("by height:\n" + mountains);
//    }
//}
//
//class Mountain {
//    String name;
//    int height;
//
//    Mountain(String n, int h) {
//        name = n;
//        height = h;
//    }
//
//    public String toString() {
//        return name + " " + height;
//    }
//}


