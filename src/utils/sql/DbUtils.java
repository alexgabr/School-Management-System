//
//Copyright Alexandru Vrincianu
//2023
//
package utils.sql;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;

public class DbUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(DbUtils.class.getResource(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/school", "user", "pass");
            PreparedStatement userExists = connection.prepareStatement(DbCom.select("password, acc_type", "users", "username = ?"));

            userExists.setString(1, username);
            ResultSet resultSet = userExists.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("password").equals(password)) {
                    switch (resultSet.getString("acc_type")) {
                        case "principal":
                            changeScene(event, "designs/homepage-principal.fxml", "Welcome!");
                            break;
                        case "staff":
                            changeScene(event, "designs/homepage-staff.fxml", "Welcome!");
                            break;
                        case "teacher":
                            changeScene(event, "designs/homepage-teacher.fxml", "Welcome!");
                            break;
                        case "student":
                            changeScene(event, "designs/homepage.fxml", "Welcome!");
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);

                    alert.setContentText("User not found!");
                    alert.show();
                }
            }
            userExists.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
