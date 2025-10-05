//import java.util.*;
//
//public class Jukebox10 {
//    public static void main(String[] args) {
//        new Jukebox10().go();
//    }
//
//    public void go() {
//        List<SongV4> songList = MockMoreSongs.getSongsV4();
//        System.out.println("Original List: " +songList);
//
//        // Sort using the compareTo method (sort by title)
//        songList.sort((one, two) -> one.getTitle().compareTo(two.getTitle()));
//        System.out.println("Sorted by Title: " +songList);
//
//        // Use a TreeSet to store songs - removes duplicates and keeps sorted by title
//        Set<SongV4> songSet = new TreeSet<>(songList);
//        System.out.println("TreeSet: " + songSet);
//
//        // Alternative: TreeSet sorted by bpm using a Comparator
//        Set<SongV4> songSetByBpm = new TreeSet<>((o1, o2) -> o1.getBpm() - o2.getBpm());
//        songSetByBpm.addAll(songList);
//        System.out.println("Sorted by Bpm : " + songSetByBpm);
//    }
//}
