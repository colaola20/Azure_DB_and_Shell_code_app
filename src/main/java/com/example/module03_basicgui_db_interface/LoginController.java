package com.example.module03_basicgui_db_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class LoginController {
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/newPerson2";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";

    private Person p = new Person();

    @FXML
    private Label incorrectPassword;

    @FXML
    private Button loginBtn, registerBtn;

    @FXML
    private TextField password, username;

    @FXML
    private Label registerText;

    @FXML
    void login(ActionEvent event) {
        String user = username.getText();
        String pass = password.getText();
        Person p = null;

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                p = new Person(resultSet.getString("email"), resultSet.getString("password"));
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if ((user.equals("admin") && pass.equals("admin"))||(p != null && (user.equals(p.getEmail()) && pass.equals(p.getPassword())))) {
            System.out.println("Login successful");
            try {
                // Load the new FXML file
                Parent newRoot = FXMLLoader.load(getClass().getResource("/com/example/module03_basicgui_db_interface/db_interface_gui.fxml"));
                // Create a new Scene with the loaded FXML root
                Scene newScene = new Scene(newRoot);
                // Get the current stage
                Stage primaryStage = (Stage) loginBtn.getScene().getWindow();
                // Set the new Scene on the primary stage
                primaryStage.setScene(newScene);
                incorrectPassword.setText("");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            incorrectPassword.setText("Incorrect username or password");
        }
    }

    @FXML
    void register(ActionEvent event) {
        try {
            // Load the new FXML file
            Parent newRoot = FXMLLoader.load(getClass().getResource("/com/example/module03_basicgui_db_interface/Registration.fxml"));
            // Create a new Scene with the loaded FXML root
            Scene newScene = new Scene(newRoot);
            // Get the current stage
            Stage primaryStage = (Stage) registerText.getScene().getWindow();
            // Set the new Scene on the primary stage
            primaryStage.setScene(newScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
