//
//Copyright Alexandru Vrincianu
//2023
//
package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.sql.DbUtils;

public class ResetPassController implements Initializable {
    @FXML
    private Button bt_resetpass;

    @FXML
    private Button bt_back;

    @FXML
    private PasswordField tf_newpass;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_resetpass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (tf_username.getText().trim().isEmpty() || tf_newpass.getText().trim().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setContentText("Incorrect input format! Try again!");
                    alert.show();
                } else {
                    DbUtils.passForget(event, tf_username.getText(), tf_newpass.getText());
                }
            }
        });

        bt_back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.changeScene(event, "/designs/log-in.fxml", "Log in", null);
            }
        });
    }
}
