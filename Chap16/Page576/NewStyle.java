import java.io.*;
import java.util.*;

public class NewStyle {
    private List<QuizCard> cardList = new ArrayList<>();

    public static void main(String[] args) {
        NewStyle builder = new NewStyle();
        builder.cardList.add(new QuizCard("What is Java?", "A programming language"));
        builder.saveFile(new File("src\\Chap16\\Page576\\new_quizcards.txt"));
    }

    private void saveFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Couldn't write the cardList out: " + e.getMessage());
        }
    }
}
