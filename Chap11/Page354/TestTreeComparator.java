//import java.util.*;
//
//class Book implements Comparable<Book> {
//    private String title;
//
//    public Book(String t) {
//        title = t;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public int compareTo(Book other) {
//        return title.compareTo(other.title);
//    }
//
//    public String toString() {
//        return title;
//    }
//}
//
//class BookCompare implements Comparator<Book> {
//    public int compare(Book one, Book two) {
//        return one.getTitle().compareTo(two.getTitle());
//    }
//}
//
//public class TestTreeComparator {
//    public void go() {
//        Book b1 = new Book("How Cats Work");
//        Book b2 = new Book("Remix your Body");
//        Book b3 = new Book("Finding Emo");
//
//        BookCompare bookCompare = new BookCompare();
//        Set<Book> tree = new TreeSet<>(bookCompare);
//
//        tree.add(b1);
//        tree.add(b2);
//        tree.add(b3);
//
//        System.out.println(tree);
//    }
//
//    public static void main(String[] args) {
//        new TestTreeComparator().go();
//    }
//}
