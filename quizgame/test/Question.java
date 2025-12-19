import java.util.List;

public class Question {
    private String text;
    private List<Answer> answers;

    public Question(String text, List<Answer> answers) {
        this.text = text;
        this.answers = answers;
    }

    public String getText() { return text; }
    public List<Answer> getAnswers() { return answers; }

    // Wewnętrzna klasa dla pojedynczej odpowiedzi
    public static class Answer {
        private String text;
        private boolean isCorrect;

        public Answer(String text, boolean isCorrect) {
            this.text = text;
            this.isCorrect = isCorrect;
        }

        public String getText() { return text; }
        public boolean isCorrect() { return isCorrect; }
    }
}