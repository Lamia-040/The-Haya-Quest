package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.sql.*;

public class EditHadithController {
    @FXML private ListView<String> hadithListView;

    public void initialize() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT title FROM hadiths");
            while (rs.next()) {
                hadithListView.getItems().add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}