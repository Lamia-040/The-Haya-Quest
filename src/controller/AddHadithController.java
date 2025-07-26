package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AddHadithController implements Initializable {

    @FXML private TextField titleField;
    @FXML private TextArea contentArea;

    // Quiz 1
    @FXML private TextField q1Field, q1Option1, q1Option2, q1Option3;
    @FXML private ComboBox<String> q1Correct;

    // Quiz 2
    @FXML private TextField q2Field, q2Option1, q2Option2, q2Option3;
    @FXML private ComboBox<String> q2Correct;

    // Quiz 3
    @FXML private TextField q3Field, q3Option1, q3Option2, q3Option3;
    @FXML private ComboBox<String> q3Correct;

    @FXML private Label statusLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Populate correct option choices
        FXCollections.observableArrayList("1", "2", "3").forEach(opt -> {
            q1Correct.getItems().add(opt);
            q2Correct.getItems().add(opt);
            q3Correct.getItems().add(opt);
        });
    }

    @FXML
    public void handleSave() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();

        if (title.isEmpty() || content.isEmpty()) {
            statusLabel.setText("Hadith title and content are required.");
            return;
        }

        if (!validateQuizInputs()) {
            statusLabel.setText("All quiz questions and correct answers must be filled.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Insert Hadith
            String hadithSql = "INSERT INTO hadiths (title, content) VALUES (?, ?)";
            PreparedStatement hadithStmt = conn.prepareStatement(hadithSql, Statement.RETURN_GENERATED_KEYS);
            hadithStmt.setString(1, title);
            hadithStmt.setString(2, content);
            hadithStmt.executeUpdate();

            // Get generated hadith_id
            ResultSet keys = hadithStmt.getGeneratedKeys();
            if (keys.next()) {
                int hadithId = keys.getInt(1);

                // Insert quiz questions
                insertQuiz(conn, hadithId, q1Field, q1Option1, q1Option2, q1Option3, q1Correct);
                insertQuiz(conn, hadithId, q2Field, q2Option1, q2Option2, q2Option3, q2Correct);
                insertQuiz(conn, hadithId, q3Field, q3Option1, q3Option2, q3Option3, q3Correct);

                statusLabel.setText("Hadith and quiz saved successfully.");
                statusLabel.setStyle("-fx-text-fill: green;");
                clearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Error saving data.");
        }
    }

    private boolean validateQuizInputs() {
        return !q1Field.getText().trim().isEmpty()
                && !q1Option1.getText().trim().isEmpty()
                && !q1Option2.getText().trim().isEmpty()
                && !q1Option3.getText().trim().isEmpty()
                && q1Correct.getValue() != null

                && !q2Field.getText().trim().isEmpty()
                && !q2Option1.getText().trim().isEmpty()
                && !q2Option2.getText().trim().isEmpty()
                && !q2Option3.getText().trim().isEmpty()
                && q2Correct.getValue() != null

                && !q3Field.getText().trim().isEmpty()
                && !q3Option1.getText().trim().isEmpty()
                && !q3Option2.getText().trim().isEmpty()
                && !q3Option3.getText().trim().isEmpty()
                && q3Correct.getValue() != null;
    }

    private void insertQuiz(Connection conn, int hadithId, TextField question, TextField opt1, TextField opt2, TextField opt3, ComboBox<String> correct) throws Exception {
        String sql = "INSERT INTO quiz_questions (hadith_id, question, option1, option2, option3, correct_option) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, hadithId);
        stmt.setString(2, question.getText().trim());
        stmt.setString(3, opt1.getText().trim());
        stmt.setString(4, opt2.getText().trim());
        stmt.setString(5, opt3.getText().trim());
        stmt.setInt(6, Integer.parseInt(correct.getValue()));
        stmt.executeUpdate();
    }

    private void clearFields() {
        titleField.clear();
        contentArea.clear();

        q1Field.clear(); q1Option1.clear(); q1Option2.clear(); q1Option3.clear(); q1Correct.setValue(null);
        q2Field.clear(); q2Option1.clear(); q2Option2.clear(); q2Option3.clear(); q2Correct.setValue(null);
        q3Field.clear(); q3Option1.clear(); q3Option2.clear(); q3Option3.clear(); q3Correct.setValue(null);
    }
}
