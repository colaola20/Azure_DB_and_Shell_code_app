package com.example.module03_basicgui_db_interface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class DB_GUI_Controller implements Initializable {
    final String DB_URL = "jdbc:mysql://csc311sorychserver.mysql.database.azure.com/newPerson2";
    final String USERNAME = "csc311admin";
    final String PASSWORD = "MvT$!qp9c26ZY!V";
    private static ConnDbOps cdbop = new ConnDbOps();

    @FXML
    private Button addBtn, clearBtn, deleteBtn, editBtn, ThemeBtn, uploadIMG;

    private boolean isDarkMode = false;

    @FXML
    TextField first_name, last_name, department, major, course;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_dept, tv_major, tv_course;

    @FXML
    ImageView img_view;





    /**
     * The ObservableList is used to store the data from the database
     */
    private final ObservableList<Person> data =
            FXCollections.observableArrayList();

    /**
     * Load data from the database and add it to the ObservableList
     */
    public void loadDataFromDB() {

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql = "SELECT * FROM users ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Person p = new Person(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("department"), resultSet.getString("major"), resultSet.getString("course"));
                data.add(p);
            }

            preparedStatement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tv_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));
        tv_course.setCellValueFactory(new PropertyValueFactory<>("course"));

        // Load data from the database
        loadDataFromDB();

        tv.setItems(data);

        // Add key event handler to the scene
        Scene scene = tv.getScene();
        if (scene != null) {
            scene.addEventHandler(KeyEvent.KEY_PRESSED, this::shortcuts);
        } else {
            tv.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.addEventHandler(KeyEvent.KEY_PRESSED, this::shortcuts);
                }
            });
        }
    }

    /**
     * Add a new record to the TableView and the database using the text field in the application
     */
    @FXML
    protected void addNewRecord() {
        Person p = new Person(
                data.size()+1,
                first_name.getText(),
                last_name.getText(),
                department.getText(),
                major.getText(),
                "CSS311"
        );
        cdbop.insertUser(p);
        data.add(p);
    }

    @FXML
    protected void clearForm() {
        first_name.clear();
        last_name.setText("");
        department.setText("");
        major.setText("");
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    /**
     * Edit a record in the TableView and the database using the text field in the application
     */
    @FXML
    protected void editRecord() {
        Person p= tv.getSelectionModel().getSelectedItem();
        int c=data.indexOf(p);
        Person p2= new Person();
        p2.setId(c+1);
        p2.setFirstName(first_name.getText());
        p2.setLastName(last_name.getText());
        p2.setDept(department.getText());
        p2.setMajor(major.getText());
        p2.setCourse("CSS311");
        cdbop.editUser(p2);
        data.remove(c);
        data.add(c,p2);
        tv.getSelectionModel().select(c);
    }

    /**
     * Delete a record from the TableView and the database
     */
    @FXML
    protected void deleteRecord() {
        Person p= tv.getSelectionModel().getSelectedItem();
        cdbop.deleteUser(p);
        data.remove(p);
    }

    /**
     * Change the theme of the application
     * @param event
     */
    @FXML
    void themeRecord(ActionEvent event) {
        Scene scene = ThemeBtn.getScene();
        String darkTheme = getClass().getResource("styling/dark_mode.css").toString();
        String lightTheme = getClass().getResource("styling/light_mode.css").toString();

        if (isDarkMode) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(lightTheme);
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(darkTheme);
        }
        isDarkMode = !isDarkMode;
    }

    @FXML
    protected void showImage() {
        File file= (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if(file!=null){
            img_view.setImage(new Image(file.toURI().toString()));

        }
    }





    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Person p= tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDept());
        major.setText(p.getMajor());


    }

    @FXML
    void openMenu(ActionEvent event) {

    }

    /**
     * Handle keyboard shortcuts particularly for Ctrl+F
     * @param event
     */
    @FXML
    public void shortcuts(KeyEvent event) {
        if (new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN).match(event)) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // Handle the file (e.g., read its content, display it, etc.)
                System.out.println("File selected: " + file.getAbsolutePath());
            }
        }
    }

    /**
     * Handle the upload image button
     * @param event
     */
    @FXML
    void uploadIMG(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Handle the file (e.g., read its content, display it, etc.)
            System.out.println("File selected: " + file.getAbsolutePath());
        }
        img_view.setImage(new Image(file.toURI().toString()));
    }

}