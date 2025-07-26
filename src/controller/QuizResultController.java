package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.util.List;

public class QuizResultController {

    @FXML private Label scoreLabel;
    @FXML private TextArea correctAnswersArea;

    public void setResult(int correct, int total, List<String> correctAnswers) {
        scoreLabel.setText("You scored " + correct + " out of " + total);
        scoreLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24px;");

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < correctAnswers.size(); i++) {
            sb.append("Q").append(i + 1).append(": ").append(correctAnswers.get(i)).append("\n");
        }
        correctAnswersArea.setText(sb.toString());
    }

}
