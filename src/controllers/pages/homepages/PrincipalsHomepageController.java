//
//Copyright Alexandru Vrincianu
//2023
//
package controllers.pages.homepages;

import java.net.URL;
import java.util.ResourceBundle;

import controllers.LogInController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PrincipalsHomepageController implements Initializable {
    @FXML
    private Button bt_dashboard;

    @FXML
    private Button bt_salaries_menu;

    @FXML
    private Button bt_students_menu;

    @FXML
    private Button bt_teachers_menu;

    @FXML
    private Label lb_welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LogInController login = new LogInController();
        String username = login.getUsername();

        lb_welcome.setText("Welcome, " + username + "!");

        bt_dashboard.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }

        });

        bt_teachers_menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }

        });

        bt_students_menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }

        });

        bt_salaries_menu.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

            }

        });
    }

    public void setWelcome(String username) {

    }
}
