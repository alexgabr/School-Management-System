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
import java.util.Objects;

public class DbUtils {

    private final static String url = "jdbc:mysql://localhost:3306/school", user = "root", pass = "pass";

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(DbUtils.class.getResource(fxmlFile)));
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
            Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement userExists = connection
                    .prepareStatement(DbCom.select("password, acc_type", "users", "username = ?"));

            userExists.setString(1, username);
            ResultSet resultSet = userExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    if (resultSet.getString("password").equals(password)) {
                        switch (resultSet.getString("acc_type")) {
                            case "principal":
                                changeScene(event, "/designs/homepage-principal.fxml", "Welcome!");
                                break;
                            case "staff":
                                changeScene(event, "/designs/homepage-staff.fxml", "Welcome!");
                                break;
                            case "teacher":
                                changeScene(event, "/designs/homepage-teacher.fxml", "Welcome!");
                                break;
                            case "student":
                                changeScene(event, "/designs/homepage.fxml", "Welcome!");
                                break;
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setContentText("User not found!");
                        alert.show();
                    }
                }
            }
            userExists.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String firstName, String lastName, String type,
            String password, String registrationId) {
        String username = firstName + " " + lastName;
        ResultSet resultSet;

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement userExists = connection
                    .prepareStatement(DbCom.select("*", "users", "username = ?"));
            userExists.setString(1, username);
            resultSet = userExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User already exists!");
                alert.show();
            }

            PreparedStatement regID = connection.prepareStatement(DbCom.select("*", "registration_ids", "reg_id = ?"));
            regID.setString(1, registrationId);
            resultSet = regID.executeQuery();

            if (resultSet == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Registration id not found!");
                alert.show();
            } else if (resultSet.getString("reg_id").equals(registrationId)
                    && resultSet.getString("acc_type").equals(type)) {

                PreparedStatement insertUser = connection.prepareStatement(
                        DbCom.insertSpecColumns("users", "username, password, acc_type", "(?, ?, ?)"));
                insertUser.setString(1, username);
                insertUser.setString(2, password);
                insertUser.setString(3, type);

                insertUser.executeUpdate();
                insertUser.close();

                PreparedStatement insert;

                switch (type) {
                    case "principal":
                        insert = connection.prepareStatement(
                                DbCom.insertSpecColumns("staff", "first_name, last_name, position",
                                        "(?, ?, ?)"));
                        insert.setString(1, firstName);
                        insert.setString(2, lastName);
                        insert.setString(3, type);

                        insert.executeUpdate();
                        insert.close();
                        break;
                    case "staff":
                        insert = connection
                                .prepareStatement(DbCom.insertSpecColumns("staff",
                                        "first_name, last_name", "(?, ?)"));
                        insert.setString(1, firstName);
                        insert.setString(2, lastName);

                        insert.executeUpdate();
                        insert.close();
                        break;
                    case "teacher":
                        insert = connection.prepareStatement(
                                DbCom.insertSpecColumns("teachers", "first_name, last_name",
                                        "(?, ?)"));
                        insert.setString(1, firstName);
                        insert.setString(2, lastName);

                        insert.executeUpdate();
                        insert.close();
                        break;
                    case "student":
                        break;
                }

                changeScene(event, "/designs/log-in.fxml", "Log in");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("Incorrect data input!");
                alert.show();
            }

            regID.close();
            userExists.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void passForget(ActionEvent event, String username, String newpass) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement select = connection
                    .prepareStatement(DbCom.select("username", "users", "username = ?"));
            select.setString(1, username);
            ResultSet resultSet = select.executeQuery();

            PreparedStatement insert = connection
                    .prepareStatement(
                            DbCom.updateRows("users", "password", newpass, "varchar", "username = ?"));
            insert.setString(1, username);

            if (resultSet == null) { // de testat
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User not found");
                alert.show();
            } else {
                insert.executeUpdate();

                changeScene(event, "/designs/log-in.fxml", "Log in");
            }

            select.close();
            insert.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
