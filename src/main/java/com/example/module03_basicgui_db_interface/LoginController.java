package com.example.module03_basicgui_db_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class LoginController {
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/Person";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";



    @FXML
    private Button loginBtn;

    @FXML
    private TextField password, username;

    @FXML
    void login(ActionEvent event) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            preparedStatement.close();
            conn.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        String user = username.getText();
        String pass = password.getText();
        System.out.println("Username: " + user);
        System.out.println("Password: " + pass);
        if (user.equals("admin") && pass.equals("admin")) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login failed");
        }
    }
}
