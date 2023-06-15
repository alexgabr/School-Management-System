//
//Copyright Alexandru Vrincianu
//2023
//
package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import utils.sql.DbUtils;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInController implements Initializable {
    @FXML
    private Button bt_login;

    @FXML
    private Button bt_reset_pass;

    @FXML
    private Button bt_sign_up;

    @FXML
    private PasswordField tf_password;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.logInUser(event, tf_username.getText(), tf_password.getText());
            }
        });

        bt_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event, "/designs/sign-up.fxml", "Sign up");
            }
        });

        bt_reset_pass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event, "/designs/reset-pass-page.fxml", "Reset password");
            }
        });
    }

}
