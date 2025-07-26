package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminDashboardHomeController implements Initializable {

    @FXML private Label hadithCountLabel;
    @FXML private Label userCountLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadCounts();
    }

    private void loadCounts() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // Count Hadiths
            String hadithQuery = "SELECT COUNT(*) FROM hadiths";
            PreparedStatement hadithStmt = conn.prepareStatement(hadithQuery);
            ResultSet hadithRs = hadithStmt.executeQuery();
            if (hadithRs.next()) {
                hadithCountLabel.setText(String.valueOf(hadithRs.getInt(1)));
            }

            // Count Users
            String userQuery = "SELECT COUNT(*) FROM users";
            PreparedStatement userStmt = conn.prepareStatement(userQuery);
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                userCountLabel.setText(String.valueOf(userRs.getInt(1)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
