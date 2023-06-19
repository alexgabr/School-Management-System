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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.sql.DbUtils;

public class ResetPassController implements Initializable {
    @FXML
    private Button bt_resetpass;

    @FXML
    private PasswordField tf_newpass;

    @FXML
    private TextField tf_username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bt_resetpass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DbUtils.passForget(event, tf_username.getText(), tf_newpass.getText());
                //TODO add back button on page
                //TODO display allert if empty field
                //TODO display allert if user not found
            }
        });
    }
}
