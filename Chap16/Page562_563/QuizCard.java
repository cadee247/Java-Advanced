public class QuizCard {
    private String question;
    private String answer;

    // Constructor to initialize question and answer
    public QuizCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getter for question
    public String getQuestion() {
        return question;
    }

    // Getter for answer
    public String getAnswer() {
        return answer;
    }
}
