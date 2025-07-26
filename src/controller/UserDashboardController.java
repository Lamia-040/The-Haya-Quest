package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class UserDashboardController {

    @FXML
    private BorderPane dashboardContainer;

    @FXML
    private StackPane contentArea;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button browseHadithButton;

    @FXML
    private Button logoutButton;

    // 👉 Static reference for other controllers like QuizController
    public static StackPane staticContentArea;

    @FXML
    public void initialize() {
        staticContentArea = contentArea;  // link static reference
        goToDashboard();
    }

    @FXML
    private void goToDashboard() {
        loadFXMLIntoContentArea("/the_haya_quest/user_dashboard_home.fxml");
    }

    @FXML
    private void goToBrowseHadith() {
        loadFXMLIntoContentArea("/the_haya_quest/browse_hadith.fxml");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/the_haya_quest/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) dashboardContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFXMLIntoContentArea(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent content = loader.load();
            contentArea.getChildren().setAll(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 👉 Allow content injection from other controllers (e.g. quiz result)
    public static void setContent(Parent node) {
        if (staticContentArea != null) {
            staticContentArea.getChildren().setAll(node);
        }
    }
}
