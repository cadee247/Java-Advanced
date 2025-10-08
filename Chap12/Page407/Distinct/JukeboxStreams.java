//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JukeboxStreams {
//    public static void main(String[] args) {
//        List<Song> songs = new Songs().getSongs();
//
//        // Map songs to their genres and remove duplicates
//        List<String> genres = songs.stream()
//                .map(song -> song.getGenre())   // extract genre
//                .distinct()                     // remove duplicates
//                .collect(Collectors.toList());  // collect to list
//
//        System.out.println(genres);
//    }
//}
//
//
