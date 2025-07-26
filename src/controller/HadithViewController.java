package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Hadith;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HadithViewController {

    @FXML private ListView<String> hadithListView;
    @FXML private TextArea hadithContentArea;
    @FXML private Button startQuizButton;

    private ObservableList<Hadith> hadithList = FXCollections.observableArrayList();
    private Hadith selectedHadith;

    @FXML
    public void initialize() {
        loadHadiths();

        hadithListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int index = hadithListView.getSelectionModel().getSelectedIndex();
                selectedHadith = hadithList.get(index);
                hadithContentArea.setText(selectedHadith.getContent());
                startQuizButton.setDisable(false);
            }
        });
    }

    private void loadHadiths() {
        hadithList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM hadiths ORDER BY id DESC");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Hadith hadith = new Hadith();
                hadith.setId(rs.getInt("id"));
                hadith.setTitle(rs.getString("title"));
                hadith.setContent(rs.getString("content"));
                hadithList.add(hadith);
            }

            ObservableList<String> titles = FXCollections.observableArrayList();
            for (Hadith h : hadithList) {
                titles.add("[" + h.getId() + "] " + h.getTitle());
            }

            hadithListView.setItems(titles);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error loading hadiths.");
        }
    }

    @FXML
    private void startQuiz() {
        if (selectedHadith == null) {
            showAlert("Please select a hadith first.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/the_haya_quest/quiz.fxml"));
            Parent root = loader.load();

            QuizController controller = loader.getController();
            controller.loadQuizForHadith(selectedHadith.getId());


            Stage stage = (Stage) hadithListView.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Failed to load quiz.");
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
