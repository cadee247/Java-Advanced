import java.io.*;
import java.util.*;

public class OldStyle {
    private List<QuizCard> cardList = new ArrayList<>();

    public static void main(String[] args) {
        OldStyle builder = new OldStyle();
        builder.cardList.add(new QuizCard("What is Java?", "A programming language"));
        builder.saveFile(new File("src\\Chap16\\Page576\\old_quizcards.txt"));
    }

    private void saveFile(File file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for (QuizCard card : cardList) {
                writer.write(card.getQuestion() + "/");
                writer.write(card.getAnswer() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Couldn't write the cardList out: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Couldn't close writer: " + e.getMessage());
            }
        }
    }
}

