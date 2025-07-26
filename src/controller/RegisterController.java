package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void handleRegister() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String pass = passwordField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            messageLabel.setText("Please fill all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (name, email, password) VALUES (?, ?, ?)"
            );
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, pass);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                messageLabel.setText("Registration successful!");
                messageLabel.setStyle("-fx-text-fill: green;");
                nameField.clear();
                emailField.clear();
                passwordField.clear();
            } else {
                messageLabel.setText("Registration failed.");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Email already registered or DB error.");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void backToLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/the_haya_quest/login.fxml"));
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
