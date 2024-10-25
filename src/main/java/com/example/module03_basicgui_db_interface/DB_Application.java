package com.example.module03_basicgui_db_interface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Scanner;


public class DB_Application extends Application {
    private static ConnDbOps cdbop;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(false);
        showScene1();
    }

    private void showScene1() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("splash_screen.fxml"));
            if (root == null) {
                throw new IOException("Failed to load FXML file for splash screen.");
            }
            Scene scene = new Scene(root, 900, 560);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws ClassNotFoundException {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);

        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ======================");
            System.out.println("| To start GUI,                press 'g' |");
            System.out.println("| To connect to DB,            press 'c' |");
            System.out.println("| To display all users,        press 'a' |");
            System.out.println("| To insert to the DB,         press 'i' |");
            System.out.println("| To query by last_name,       press 'q' |");
            System.out.println("| To exit,                     press 'e' |");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'g':
                    launch(args); //GUI
                    break;

                case 'c':
                    cdbop.connectToDatabase(); //Your existing method
                    break;
                case 'a':
                    cdbop.listAllUsers(); //all users in DB
                    break;

                case 'i':
                    System.out.print("Enter ID: ");
                    int id = scan.nextInt();
                    System.out.print("Enter First Name: ");
                    String firstName = scan.next();
                    System.out.print("Enter Last Name: ");
                    String lastName = scan.next();
                    System.out.print("Enter department: ");
                    String department = scan.next();
                    System.out.print("Enter major: ");
                    String major = scan.next();
                    System.out.print("Enter course: ");
                    String course = scan.next();
                    Person p = new Person(id, firstName, lastName, department, major, course);
                    cdbop.insertUser(p); //Your insertUser method
                    break;
                case 'q':
                    System.out.print("Enter the last name to query: ");
                    String queryName = scan.next();
                    cdbop.queryUserByName(queryName); //Your queryUserByName method
                    break;
                case 'e':
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'e');

        scan.close();
    }


}