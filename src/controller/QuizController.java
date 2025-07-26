package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import model.QuizQuestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QuizController {

    @FXML private Label quizTitle;
    @FXML private Label question1Label, question2Label, question3Label;
    @FXML private RadioButton q1Option1, q1Option2, q1Option3;
    @FXML private RadioButton q2Option1, q2Option2, q2Option3;
    @FXML private RadioButton q3Option1, q3Option2, q3Option3;
    
    private ToggleGroup q1Group, q2Group, q3Group;

    private List<QuizQuestion> quizQuestions;

    @FXML
    public void initialize() {
        // Initialize and assign toggle groups
        q1Group = new ToggleGroup();
        q1Option1.setToggleGroup(q1Group);
        q1Option2.setToggleGroup(q1Group);
        q1Option3.setToggleGroup(q1Group);

        q2Group = new ToggleGroup();
        q2Option1.setToggleGroup(q2Group);
        q2Option2.setToggleGroup(q2Group);
        q2Option3.setToggleGroup(q2Group);

        q3Group = new ToggleGroup();
        q3Option1.setToggleGroup(q3Group);
        q3Option2.setToggleGroup(q3Group);
        q3Option3.setToggleGroup(q3Group);
    }

    public void loadQuizForHadith(int hadithId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM quiz_questions WHERE hadith_id = ?");
            stmt.setInt(1, hadithId);
            ResultSet rs = stmt.executeQuery();

            quizQuestions = new ArrayList<>();
            while (rs.next()) {
                QuizQuestion q = new QuizQuestion(
                        rs.getInt("id"),
                        rs.getInt("hadith_id"),
                        rs.getString("question"),
                        rs.getString("option1"),
                        rs.getString("option2"),
                        rs.getString("option3"),
                        rs.getInt("correct_option")
                );
                quizQuestions.add(q);
            }

            if (quizQuestions.size() >= 3) {
                quizTitle.setText("Quiz for Hadith ID: " + hadithId);
                populateQuestion(0, question1Label, q1Option1, q1Option2, q1Option3);
                populateQuestion(1, question2Label, q2Option1, q2Option2, q2Option3);
                populateQuestion(2, question3Label, q3Option1, q3Option2, q3Option3);
            } else {
                showAlert("Not enough quiz questions (minimum 3 required).");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading quiz.");
        }
    }

    private void populateQuestion(int index, Label qLabel, RadioButton opt1, RadioButton opt2, RadioButton opt3) {
        QuizQuestion q = quizQuestions.get(index);
        qLabel.setText((index + 1) + ". " + q.getQuestion());
        opt1.setText(q.getOption1());
        opt2.setText(q.getOption2());
        opt3.setText(q.getOption3());
    }

    @FXML
    private void submitQuiz() {
        int correct = 0;
        List<String> correctAnswers = new ArrayList<>();

        if (getSelectedOption(q1Group) == quizQuestions.get(0).getCorrectOption()) correct++;
        correctAnswers.add(quizQuestions.get(0).getCorrectOptionText());

        if (getSelectedOption(q2Group) == quizQuestions.get(1).getCorrectOption()) correct++;
        correctAnswers.add(quizQuestions.get(1).getCorrectOptionText());

        if (getSelectedOption(q3Group) == quizQuestions.get(2).getCorrectOption()) correct++;
        correctAnswers.add(quizQuestions.get(2).getCorrectOptionText());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/the_haya_quest/quiz_result.fxml"));
            Parent resultPane = loader.load();

            QuizResultController resultController = loader.getController();
            resultController.setResult(correct, 3, correctAnswers);

            StackPane root = (StackPane) quizTitle.getScene().lookup("#contentArea");
            root.getChildren().clear();
            root.getChildren().add(resultPane);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to load quiz result.");
        }
    }

    private int getSelectedOption(ToggleGroup group) {
        Toggle selected = group.getSelectedToggle();
        if (selected == null) return -1;
        RadioButton rb = (RadioButton) selected;
        String text = rb.getText();
        QuizQuestion current = null;

        if (group == q1Group) current = quizQuestions.get(0);
        else if (group == q2Group) current = quizQuestions.get(1);
        else if (group == q3Group) current = quizQuestions.get(2);

        if (current != null) {
            if (text.equals(current.getOption1())) return 1;
            if (text.equals(current.getOption2())) return 2;
            if (text.equals(current.getOption3())) return 3;
        }
        return -1;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
