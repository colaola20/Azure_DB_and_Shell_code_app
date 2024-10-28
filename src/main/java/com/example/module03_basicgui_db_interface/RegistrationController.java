package com.example.module03_basicgui_db_interface;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    private static ConnDbOps cdbop = new ConnDbOps();

    @FXML
    private Label errorMessage;

    @FXML
    private TextField lastNameTF, zipCodeTF, firstNameTF, emailTF, dobTF;

    @FXML
    private Button registerBtn;

    private Person p = null;

    /**
     * Set up the initial state of the GUI, add listeners to input fields, and disable the register button until all fields are valid.
     */
    @FXML
    public void initialize() {
        // Add listeners to input fields
        firstNameTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        lastNameTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        emailTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        dobTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());
        zipCodeTF.textProperty().addListener((observable, oldValue, newValue) -> validateInputs());

        // Initially disable the register button
        registerBtn.setDisable(true);
    }

    /**
     * Validate the input fields. If all fields are valid, enable the register button.
     */
    private void validateInputs() {
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = emailTF.getText();
        String dob = dobTF.getText();
        String zipC = zipCodeTF.getText();

        if (!firstName.isEmpty() && (!isNameValid(firstName))) {
            errorMessage.setText("Invalid first name.");
            return;
        }
        else {
            errorMessage.setText("");
        }

        if (!lastName.isEmpty() && (!isNameValid(lastName))) {
            errorMessage.setText("Invalid last name.");
            return;
        }
        else {
            errorMessage.setText("");
        }

        if (!email.isEmpty() && (!isEmailValid(email))) {
            errorMessage.setText("Invalid email.");
            return;
        }
        else {
            errorMessage.setText("");
        }

        if (!dob.isEmpty() && (!isDobValid(dob))) {
            errorMessage.setText("Invalid date of birth.");
            return;
        }
        else {
            errorMessage.setText("");
        }

        if (!zipC.isEmpty() && (!isZipCodeValid(zipC))) {
            errorMessage.setText("Invalid zip code.");
            return;
        }
        else {
            errorMessage.setText("");
        }


        boolean isValid = isNameValid(firstName) &&
                isNameValid(lastName) &&
                isEmailValid(email) &&
                isDobValid(dob) &&
                isZipCodeValid(zipC);

        registerBtn.setDisable(!isValid);
    }

    /**
     * Register a new user. Checks if the input is valid, and if it is, adds the user to the database and allows the user log in.
     * @param event
     */
    @FXML
    void register(ActionEvent event) {
        String firstName = firstNameTF.getText();
        String lastName = lastNameTF.getText();
        String email = emailTF.getText();
        String dob = dobTF.getText();
        String zipC = zipCodeTF.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dob.isEmpty() || zipC.isEmpty()) {
            errorMessage.setText("All fields must be filled out.");
            return;
        }

        int zipCode = Integer.parseInt(zipCodeTF.getText());
        if (isNameValid(firstName) && isNameValid(lastName) && isEmailValid(email) && isDobValid(dob) && isZipCodeValid(String.valueOf(zipCode))) {
            String password = lastName + zipCode + "!";
            String DOBforSQL = dob.substring(6, 10) + "-" + dob.substring(0, 2) + "-" + dob.substring(3, 5);

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
        }
    }

    /**
     * Check if the zip code is valid (should be 5-digit number)
     * @param zipCode
     * @return
     */
    private boolean isZipCodeValid(String zipCode) {
        final String regex = "[0-9]{5}";
        return zipCode.matches(regex);
    }

    /**
     * Check if the date of birth is valid (should be in the format MM/DD/YYYY and the year should be between 1920 and 2024)
     * @param dob
     * @return
     */
    private boolean isDobValid(String dob) {
        final String regex = "(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(20[0-2][0-4]|19[2-9][0-9])";
        return dob.matches(regex);
    }

    /**
     * Check if the email is valid (should be in the format <word>@farmingdale.edu)
     * @param email
     * @return
     */
    private boolean isEmailValid(String email) {
        final String regex = "(\\w+)@farmingdale.edu";
        return email.matches(regex);
    }

    /**
     * Check if the name is valid (should be between 2 and 25 characters long)
     * @param name
     * @return
     */
    private boolean isNameValid(String name) {
        final String regex = "([a-zA-Z]{2,25})";
        return name.matches(regex);
    }

}
