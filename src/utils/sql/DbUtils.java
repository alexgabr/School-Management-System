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

    public static void insertIntoLogsBook(String info) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement insert = connection
                    .prepareStatement(DbCom.insertSpecColumns("logsbook", "info, date_time", "(?, NOW())"));
            insert.setString(1, info);
            insert.executeUpdate();

            insert.close();

            System.out.println(info);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String insertUserID(String table, String columnNameId, String firstName, String lastName) {
        String username = firstName + " " + lastName;
        String select = null;
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement selectId = connection.prepareStatement(DbCom.select("user_id", "users", "username = ?"));
            selectId.setString(1, username);

            ResultSet resultSet = selectId.executeQuery();

            while (resultSet.next()) {
                select = resultSet.getString("user_id");
            }

            PreparedStatement insert = connection.prepareStatement(
                    DbCom.updateRows(table, columnNameId, select, "VARCHAR", "first_name = ? AND last_name = ?"));
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.executeUpdate();

            insert.close();
            resultSet.close();
            selectId.close();
            connection.close();

            System.out.println("The user_id " + select + " has been succesfully added to table " + table + "!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return select;
    }

    public static void insertSalaryID(String table, String columnNameID, String id, String firstName, String lastName) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement insert = connection.prepareStatement(
                    DbCom.insertSpecColumns(table, columnNameID, "(" + id + ")"));
            insert.executeUpdate();

            insert.close();
            connection.close();

            System.out.println("The id " + id + " has been succesfully added to salary table " + table + "!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            PreparedStatement userExists = connection
                    .prepareStatement(DbCom.select("*", "users", "username = ?"));
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
                        System.out.println("User " + username + " has logged in!");

                        insertIntoLogsBook("User " + username + " has logged in!");
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setContentText("User not found!");
                        alert.show();

                        System.out.println("Unsuccesfull attempt to log in with username " + username + "!");

                        insertIntoLogsBook("ALERT! Unsuccesfull attempt to log in with username " + username + "!");
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User not found!");
                alert.show();

                insertIntoLogsBook("Unsuccesfull attempt to log in with unknown username!");
            }

            userExists.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String firstName, String lastName, String type,
            String password) {
        String username = firstName + " " + lastName;
        ResultSet resultSet;

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            // checking if the user already exists
            PreparedStatement userExists = connection
                    .prepareStatement(DbCom.select("*", "users", "username = ?"));
            userExists.setString(1, username);
            resultSet = userExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User already exists!");
                alert.show();
            } else {
                // inserting the info into users&specific acc type table
                PreparedStatement insertUserInfo = connection.prepareStatement(DbCom.insertSpecColumns("users",
                        "username, password, acc_type, sign_up_date", "(?, ?, ?, NOW())"));
                insertUserInfo.setString(1, username);
                insertUserInfo.setString(2, password);
                insertUserInfo.setString(3, type);

                insertUserInfo.executeUpdate();

                PreparedStatement insertType;
                String id;
                switch (type) {
                    case "principal":
                        insertType = connection.prepareStatement(
                                DbCom.insertSpecColumns("staff", "first_name, last_name, position", "(?, ?, ?)"));
                        insertType.setString(1, firstName);
                        insertType.setString(2, lastName);
                        insertType.setString(3, type);

                        insertType.executeUpdate();
                        insertType.close();

                        // inserting the user id in the table
                        id = insertUserID("staff", "staff_id", firstName, lastName);
                        // inserting the id in the salary table
                        insertSalaryID("staff_salaries", "id", id, firstName, lastName);
                        break;
                    case "staff":
                        insertType = connection
                                .prepareStatement(DbCom.insertSpecColumns("staff", "first_name, last_name", "(?, ?)"));
                        insertType.setString(1, firstName);
                        insertType.setString(2, lastName);

                        insertType.executeUpdate();
                        insertType.close();

                        // inserting the user id in the table
                        id = insertUserID("staff", "staff_id", firstName, lastName);
                        // inserting the id in the salary table
                        insertSalaryID("staff_salaries", "id", id, firstName, lastName);
                        break;
                    case "teacher":
                        insertType = connection.prepareStatement(
                                DbCom.insertSpecColumns("teachers", "first_name, last_name", "(?, ?)"));
                        insertType.setString(1, firstName);
                        insertType.setString(2, lastName);

                        insertType.executeUpdate();
                        insertType.close();

                        // inserting the user id in the table
                        id = insertUserID("teachers", "teacher_id", firstName, lastName);
                        // inserting the id in the salary table
                        insertSalaryID("teachers_salaries", "id", id, firstName, lastName);
                        break;
                    case "student":
                        insertType = connection
                                .prepareStatement(
                                        DbCom.insertSpecColumns("students", "first_name, last_name", "(?, ?)"));
                        insertType.setString(1, firstName);
                        insertType.setString(2, lastName);

                        insertType.executeUpdate();
                        insertType.close();

                        // inserting the user id in the table
                        insertUserID("students", "student_id", firstName, lastName);
                        break;
                }
            }

            // insert into logsbook table
            PreparedStatement selecStatement = connection
                    .prepareStatement(DbCom.select("user_id", "users", "username = ?"));
            selecStatement.setString(1, username);

            ResultSet rSet = selecStatement.executeQuery();
            String result = null;

            if (rSet.isBeforeFirst()) {
                while (rSet.next()) {
                    result = rSet.getString("user_id");
                }
                insertIntoLogsBook(
                        "New account of the type " + type + " with user_id = " + result + " has been created!");
            } else {
                insertIntoLogsBook("New account has been created!");
            }

            // change the scene
            changeScene(event, "/designs/log-in.fxml", "Log in");

            userExists.close();
            selecStatement.close();
            resultSet.close();
            rSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void passForget(ActionEvent event, String username, String newpass) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            PreparedStatement select = connection
                    .prepareStatement(DbCom.select("*", "users", "username = ?"));
            select.setString(1, username);
            ResultSet resultSet = select.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setContentText("User not found");
                alert.show();

                insertIntoLogsBook("ALERT! Unknown users attempted to reset a password!");
            } else {
                System.out.println("Begin password reset");

                PreparedStatement insert = connection
                        .prepareStatement(DbCom.updateRows("users", "password", "?", "varchar", "username = ?"));
                insert.setString(1, newpass);
                insert.setString(2, username);

                insert.executeUpdate();
                insert.close();

                changeScene(event, "/designs/log-in.fxml", "Log in");

                String id = resultSet.getString("user_id");

                insertIntoLogsBook("User with user_id = " + id + " reseted his password!");
                System.out.println("Password reset completed!");
            }

            resultSet.close();
            select.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
