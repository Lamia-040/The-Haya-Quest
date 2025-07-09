package the.haya.quest;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private Label wrongLogIn;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;
    @FXML
    private Button button;

    @FXML
    public void userLogIn() {
        checkLogin();
    }

    private void checkLogin() {
        String user = username.getText();
        String pass = password.getText();

        if (user.equals("Lamia") && pass.equals("Lammi")) {
            wrongLogIn.setText("Login Successful");
            
        } else {
            wrongLogIn.setText("Wrong username or password");
        }
    }
}
