//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.io.*;
//import java.util.*;
//
//public class MainGUI extends JFrame {
//    private JTable table;
//    private DefaultTableModel model;
//    private JButton calculateButton;
//    private File matchFile;
//
//    public MainGUI() {
//        setTitle("Soccer League Rankings");
//        setSize(500, 400);
//        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Table setup
//        model = new DefaultTableModel(new Object[]{"Rank", "Team", "Points"}, 0);
//        table = new JTable(model);
//        add(new JScrollPane(table), BorderLayout.CENTER);
//
//        // Button panel
//        JPanel topPanel = new JPanel();
//        calculateButton = new JButton("Calculate Rankings");
//        topPanel.add(calculateButton);
//        add(topPanel, BorderLayout.NORTH);
//
//        // Auto-load matches.txt from project folder
//        matchFile = new File("matches.txt");
//        if (!matchFile.exists()) {
//            JOptionPane.showMessageDialog(this, "⚠️ matches.txt not found in project folder.");
//        }
//
//        calculateButton.addActionListener(e -> {
//            if (matchFile.exists()) {
//                Map<String, Integer> scores = calculateScores(matchFile);
//                displayScores(scores);
//            } else {
//                JOptionPane.showMessageDialog(this, "⚠️ matches.txt not found. Please add it to your project folder.");
//            }
//        });
//
//        setVisible(true);
//    }
//
//    private Map<String, Integer> calculateScores(File file) {
//        Map<String, Integer> scores = new HashMap<>();
//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                processMatch(line, scores);
//            }
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "❌ Error reading file.");
//        }
//        return scores;
//    }
//
//    private void displayScores(Map<String, Integer> scores) {
//        model.setRowCount(0);
//        TreeMap<Integer, java.util.List<String>> sorted = new TreeMap<>(Collections.reverseOrder());
//        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
//            sorted.computeIfAbsent(entry.getValue(), k -> new java.util.ArrayList<>()).add(entry.getKey());
//        }
//
//        int rank = 1;
//        for (Map.Entry<Integer, java.util.List<String>> entry : sorted.entrySet()) {
//            java.util.List<String> teams = entry.getValue();
//            Collections.sort(teams);
//            for (String team : teams) {
//                model.addRow(new Object[]{rank, team, entry.getKey()});
//            }
//            rank += teams.size();
//        }
//    }
//
//    private void processMatch(String line, Map<String, Integer> scores) {
//        String[] parts = line.split(",");
//        String[] team1 = parts[0].trim().split(" ");
//        String[] team2 = parts[1].trim().split(" ");
//
//        String name1 = normalizeTeamName(String.join(" ", Arrays.copyOf(team1, team1.length - 1)));
//        String name2 = normalizeTeamName(String.join(" ", Arrays.copyOf(team2, team2.length - 1)));
//        int score1 = Integer.parseInt(team1[team1.length - 1]);
//        int score2 = Integer.parseInt(team2[team2.length - 1]);
//
//        scores.putIfAbsent(name1, 0);
//        scores.putIfAbsent(name2, 0);
//
//        if (score1 > score2) {
//            scores.put(name1, scores.get(name1) + 3);
//        } else if (score1 < score2) {
//            scores.put(name2, scores.get(name2) + 3);
//        } else {
//            scores.put(name1, scores.get(name1) + 1);
//            scores.put(name2, scores.get(name2) + 1);
//        }
//    }
//
//    private String normalizeTeamName(String rawName) {
//        String cleaned = rawName.trim().replaceAll("\\s+", " ");
//        if (cleaned.equalsIgnoreCase("FC Awesome") || cleaned.equalsIgnoreCase("FCAwesome")) {
//            return "FCAwesome";
//        }
//        return cleaned;
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(MainGUI::new);
//    }
//}