package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ManageHadithController implements Initializable {

    @FXML private ListView<HadithItem> hadithListView;
    private ObservableList<HadithItem> hadithList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadHadiths();
    }

    private void loadHadiths() {
        hadithList.clear();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title FROM hadiths ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hadithList.add(new HadithItem(rs.getInt("id"), rs.getString("title")));
            }
            hadithListView.setItems(hadithList);
            hadithListView.setCellFactory(list -> new HadithCell());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class HadithItem {
        public int id;
        public String title;

        public HadithItem(int id, String title) {
            this.id = id;
            this.title = title;
        }
    }

    public class HadithCell extends ListCell<HadithItem> {
        HBox hbox = new HBox();
        Text titleText = new Text();
        ImageView deleteIcon = new ImageView();

        public HadithCell() {
            hbox.setSpacing(20);
            hbox.getChildren().addAll(titleText, deleteIcon);

            // Configure delete icon
            Image img = new Image(getClass().getResourceAsStream("/image/delete.png"));
            deleteIcon.setImage(img);
            deleteIcon.setFitWidth(18);
            deleteIcon.setFitHeight(18);
            deleteIcon.setStyle("-fx-cursor: hand;");

            deleteIcon.setOnMouseClicked(e -> {
                HadithItem item = getItem();
                if (item != null) {
                    deleteHadith(item.id);
                }
            });
        }

        @Override
        protected void updateItem(HadithItem item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                titleText.setText(item.title);
                setGraphic(hbox);
            }
        }
    }

    private void deleteHadith(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM hadiths WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            loadHadiths(); // Refresh
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
