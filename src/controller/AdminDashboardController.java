package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminDashboardController {
    @FXML private StackPane adminContentArea;

    @FXML public void goToDashboard() throws Exception {
        loadView("/the_haya_quest/admin_dashboard_home.fxml");
    }

    @FXML public void goToManageHadith() throws Exception {
        loadView("/the_haya_quest/manage_hadith.fxml");
    }

    @FXML public void goToAddHadith() throws Exception {
        loadView("/the_haya_quest/add_hadith.fxml");
    }

    @FXML public void logout() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/the_haya_quest/login.fxml"));
        Stage stage = (Stage) adminContentArea.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    private void loadView(String path) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        adminContentArea.getChildren().setAll(root);
    }
}
