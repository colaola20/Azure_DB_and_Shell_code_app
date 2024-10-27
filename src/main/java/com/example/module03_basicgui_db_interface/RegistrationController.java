package com.example.module03_basicgui_db_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/newPerson2";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";
    private static ConnDbOps cdbop = new ConnDbOps();

    @FXML
    private TextField dobTF;

    @FXML
    private TextField emailTF;

    @FXML
    private Label errorMessage;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private Button registerBtn;

    @FXML
    private TextField zipCodeTF;

    private Person p = null;

    @FXML
    void isInputDOBValid(ActionEvent event) {

    }

    @FXML
    void isInputEmailValid(ActionEvent event) {

    }

    @FXML
    void isInputFNameValid(InputMethodEvent event) {

    }

    @FXML
    void isInputLNameValid(ActionEvent event) {

    }

    @FXML
    void isInputZCValid(ActionEvent event) {

    }

    @FXML
    void register(ActionEvent event) {
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = emailTF.getText();
        String dob = dobTF.getText();
        int zipCode = Integer.parseInt(zipCodeTF.getText());
        String password = lastName + zipCode + "!";

        String DOBforSQL = dob.substring(6, 10) + "-" + dob.substring(0, 2) + "-" + dob.substring(3, 5);

        if (isNameValid(firstName) && isNameValid(lastName) && isEmailValid(email) && isDobValid(dob) && isZipCodeValid(String.valueOf(zipCode))) {
            p = new Person(firstName, lastName, email, DOBforSQL, zipCode, password);
            cdbop.connectToDatabase();
            cdbop.insertUser(p);
            errorMessage.setText("Registration successful!" + "Your password: " + p.getPassword());
            try {
                // Load the new FXML file
                Parent newRoot = FXMLLoader.load(getClass().getResource("/com/example/module03_basicgui_db_interface/db_interface_gui.fxml"));
                // Create a new Scene with the loaded FXML root
                Scene newScene = new Scene(newRoot);
                // Get the current stage
                Stage primaryStage = (Stage) registerBtn.getScene().getWindow();
                // Set the new Scene on the primary stage
                primaryStage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (!isNameValid(firstName)) {
                errorMessage.setText("Invalid first name.");
            } else if (!isNameValid(lastName)) {
                errorMessage.setText("Invalid last name.");
            } else if (!isEmailValid(email)) {
                errorMessage.setText("Invalid email.");
            } else if (!isDobValid(dob)) {
                errorMessage.setText("Invalid date of birth.");
            } else if (!isZipCodeValid(String.valueOf(zipCode))) {
                errorMessage.setText("Invalid zip code.");
            }
        }
    }

    private boolean isZipCodeValid(String zipCode) {
        final String regex = "[0-9]{5}";
        return zipCode.matches(regex);
    }

    private boolean isDobValid(String dob) {
        final String regex = "(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(20[0-2][0-4]|19[2-9][0-9])";
        return dob.matches(regex);
    }

    private boolean isEmailValid(String email) {
        final String regex = "(\\w+)@farmingdale.edu";
        return email.matches(regex);
    }

    private boolean isNameValid(String name) {
        final String regex = "([a-zA-Z]{2,25})";
        return name.matches(regex);
    }

}
