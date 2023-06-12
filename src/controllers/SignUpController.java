//
//Copyright Alexandru Vrincianu
//2023
//
package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.sql.DbUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button bt_log_in;

    @FXML
    private Button bt_signup;

    @FXML
    private ChoiceBox<String> ch_occupation;

    private final String[] occupations = {"principal", "staff", "teacher", "student"};

    @FXML
    private TextField tf_first_name;

    @FXML
    private TextField tf_last_name;

    @FXML
    private PasswordField tf_password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ch_occupation.getItems().addAll(occupations);

        bt_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.signUpUser(event, tf_first_name.getText(), tf_last_name.getText(), ch_occupation.getValue(), tf_password.getText());
            }
        });

        bt_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event, "designs/log-in.fxml", "Log in");
            }
        });
    }
}