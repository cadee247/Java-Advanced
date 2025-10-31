import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // Create a map to store each team's total points
        Map<String, Integer> scores = new HashMap<>();

        // Read match results from the input file
        BufferedReader reader = new BufferedReader(new FileReader("matches.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            // Process each match line and update scores
            processMatch(line, scores);
        }
        reader.close();

        // TreeMap to sort teams by score (descending) and group teams with same score
        TreeMap<Integer, List<String>> sorted = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            // Add team to the list for its score group
            sorted.computeIfAbsent(entry.getValue(), k -> new ArrayList<>()).add(entry.getKey());
        }

        // Print the final scoreboard with ranks
        int rank = 1;
        for (Map.Entry<Integer, List<String>> entry : sorted.entrySet()) {
            List<String> teams = entry.getValue();
            // Sort tied teams alphabetically
            Collections.sort(teams);
            for (String team : teams) {
                // Print rank, team name, and points
                System.out.println(rank + ". " + team + " " + entry.getKey());
            }
            // Increment rank by number of teams in this score group
            rank += teams.size();
        }
    }

    // Parses a match line and updates scores for both teams
    private static void processMatch(String line, Map<String, Integer> scores) {
        // Split the line into two team segments
        String[] parts = line.split(",");

        // Extract team names and scores
        String[] team1 = parts[0].trim().split(" ");
        String[] team2 = parts[1].trim().split(" ");

        // Reconstruct team names (excluding score) and normalize them
        String name1 = normalizeTeamName(String.join(" ", Arrays.copyOf(team1, team1.length - 1)));
        String name2 = normalizeTeamName(String.join(" ", Arrays.copyOf(team2, team2.length - 1)));

        // Parse scores from the last word in each segment
        int score1 = Integer.parseInt(team1[team1.length - 1]);
        int score2 = Integer.parseInt(team2[team2.length - 1]);

        // Initialize scores if teams are new
        scores.putIfAbsent(name1, 0);
        scores.putIfAbsent(name2, 0);

        // Apply league rules: win = 3, draw = 1, loss = 0
        if (score1 > score2) {
            scores.put(name1, scores.get(name1) + 3);
        } else if (score1 < score2) {
            scores.put(name2, scores.get(name2) + 3);
        } else {
            scores.put(name1, scores.get(name1) + 1);
            scores.put(name2, scores.get(name2) + 1);
        }
    }

    // Cleans and standardizes team names to avoid duplicates
    private static String normalizeTeamName(String rawName) {
        // Remove extra spaces and normalize spacing
        String cleaned = rawName.trim().replaceAll("\\s+", " ");

        // Normalize known variants (e.g., "FC Awesome" → "FCAwesome")
        if (cleaned.equalsIgnoreCase("FC Awesome") || cleaned.equalsIgnoreCase("FCAwesome")) {
            return "FCAwesome";
        }

        return cleaned;
    }
}