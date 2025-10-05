//import java.util.*;
//
//public class Jukebox6 {
//    public static void main(String[] args) {
//        new Jukebox6().go();
//    }
//
//    public void go() {
//        List<SongV3> songList = MockSongs.getSongsV3();
//
//        System.out.println("Original List: " +songList);
//
//        // Sort by title using a lambda
//        songList.sort((one, two) -> one.getTitle().compareTo(two.getTitle()));
//        System.out.println("Sorted by Title: " +songList);
//
//        // Sort by artist using a lambda
//        songList.sort((one, two) -> one.getArtist().compareTo(two.getArtist()));
//        System.out.println("Sorted by Artist: " +songList);
//    }
//}
//
