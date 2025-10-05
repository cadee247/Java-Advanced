//import java.util.*;
//
//public class Jukebox8 {
//    public static void main(String[] args) {
//        new Jukebox8().go();
//    }
//
//    public void go() {
//        List<SongV3> songList = MockMoreSongs.getSongsV3();
//        System.out.println("Original List: " +songList);
//
//        songList.sort((one, two) -> one.getTitle().compareTo(two.getTitle()));
//        System.out.println("Sorted by Title: " +songList);
//
//        Set<SongV3> songSet = new HashSet<>(songList);
//        System.out.println("HashSet: " + songSet);
//    }
//}
//
