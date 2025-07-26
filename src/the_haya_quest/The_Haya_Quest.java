package the_haya_quest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class The_Haya_Quest extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/the_haya_quest/login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("The Haya Quest - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Failed to load login.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
