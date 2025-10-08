//import java.util.List;
//import java.util.stream.Collectors;
//
//public class SkipExample {
//    public static void main(String[] args) {
//        List<String> strings = List.of("I", "am", "a", "list", "of", "Strings");
//
//        List<String> result = strings.stream()
//                .sorted((s1, s2) -> s1.compareToIgnoreCase(s2))
//                .skip(2)
//                .limit(4)
//                .collect(Collectors.toList());
//
//        System.out.println("result = " + result);
//    }
//}
