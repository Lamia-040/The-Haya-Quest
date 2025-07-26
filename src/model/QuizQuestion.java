package model;

public class QuizQuestion {
    private int id, hadithId, correctOption;
    private String question, option1, option2, option3;

    public QuizQuestion(int id, int hadithId, String question, String option1, String option2, String option3, int correctOption) {
        this.id = id;
        this.hadithId = hadithId;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctOption = correctOption;
    }

    public String getQuestion() { return question; }
    public String getOption1() { return option1; }
    public String getOption2() { return option2; }
    public String getOption3() { return option3; }
    public int getCorrectOption() { return correctOption; }

    public String getCorrectOptionText() {
        return switch (correctOption) {
            case 1 -> option1;
            case 2 -> option2;
            case 3 -> option3;
            default -> "";
        };
    }
}
