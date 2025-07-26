package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField username;  // This represents email input
    @FXML private PasswordField password;
    @FXML private Label wrongLogIn;

    @FXML
    public void userLogIn() {
        String email = username.getText().trim();
        String pass = password.getText().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            wrongLogIn.setText("Please enter email and password.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check users table
            PreparedStatement userStmt = conn.prepareStatement(
                "SELECT * FROM users WHERE email = ? AND password = ?");
            userStmt.setString(1, email);
            userStmt.setString(2, pass);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                loadFXML("/the_haya_quest/user_dashboard.fxml");
                return;
            }

            // Check admins table
            PreparedStatement adminStmt = conn.prepareStatement(
                "SELECT * FROM admins WHERE username = ? AND password = ?");
            adminStmt.setString(1, email); // reusing email input for admin username
            adminStmt.setString(2, pass);
            ResultSet adminRs = adminStmt.executeQuery();

            if (adminRs.next()) {
                loadFXML("/the_haya_quest/admin_dashboard.fxml");
                return;
            }

            wrongLogIn.setText("Invalid credentials.");

        } catch (Exception e) {
            e.printStackTrace();
            wrongLogIn.setText("Database error.");
        }
    }

    @FXML
    public void goToRegister() {
        try {
            loadFXML("/the_haya_quest/register.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToAdminLogin() {
        try {
            loadFXML("/the_haya_quest/admin_login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadFXML(String path) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = loader.load();
        Stage stage = (Stage) username.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }
}
