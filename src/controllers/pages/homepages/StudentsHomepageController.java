//
//Copyright Alexandru Vrincianu
//2023
//
package controllers.pages.homepages;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentsHomepageController implements Initializable{

    @FXML
    private Button bt_dashboard;

    @FXML
    private Button bt_homeworks;

    @FXML
    private Button bt_settings;

    @FXML
    private Label lb_welcome;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
