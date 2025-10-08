//import java.util.*;
//import java.util.stream.Collectors;
//
//public class JukeboxStreams {
//    public static void main(String[] args) {
//        List<Song> songs = new Songs().getSongs();
//
//        List<Song> rockSongs = songs.stream()
//                .filter(song -> song.getGenre().contains("Rock"))
//                .collect(Collectors.toList());
//
//        System.out.println(rockSongs);
//    }
//}
