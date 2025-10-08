//import java.util.List;
//import java.util.stream.Collectors;
//
//public class JukeboxStreams {
//    public static void main(String[] args) {
//        List<Song> songs = new Songs().getSongs();
//
//        String songTitle = "With a Little Help from My Friends";
//
//        List<String> result = songs.stream()
//                .filter(song -> song.getTitle().equals(songTitle))     // match song title
//                .map(song -> song.getArtist())                         // get artist
//                .filter(artist -> !artist.equals("The Beatles"))       // exclude The Beatles
//                .collect(Collectors.toList());                         // collect to list
//
//        System.out.println(result);
//    }
//}
//
//
