package test;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Songs songs = new Songs();
        List<Song> allSongs = songs.getSongs();

        System.out.println("🎶 All Songs Played on Lou's Jukebox:");
        allSongs.forEach(System.out::println);

        System.out.println("\n🎼 Genres Played:");
        allSongs.stream()
                .map(Song::getGenre)      // 🎯 Extracts the genre from each song
                .distinct()               // 🧹 Removes repeated genres
                .forEach(System.out::println); // 📢 Prints each unique genre
    }
}