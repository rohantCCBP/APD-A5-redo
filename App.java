/**********************************************
Workshop #5
Course:APD545 - 2237
Last Name:Tankala
First Name:Rohan
ID:122836166
Section:NAA
This assignment represents my own work in accordance with Seneca Academic Policy.
Signature Rohan Tankala
Date:11-19-2023
**********************************************/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class App extends Application {

    // @Override
    // public void start(Stage primaryStage) throws Exception {
    //   Parent root = FXMLLoader.load(getClass().getResource("/Views/view.fxml")); 
    //   primaryStage.setTitle("Item Selector");
    //     primaryStage.setScene(new Scene(root, 400, 375));   
    //     primaryStage.show();
    // }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Item Selector");

        showLoginView();
    }

    private void showLoginView() {
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText()));
        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> showRegistrationView());

        layout.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);

        Scene scene = new Scene(layout, 400, 375);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

  private void handleLogin(String username, String password) {
    String url = "jdbc:mysql://localhost:3306/apd-db";
    String dbUsername = "root";
    String dbPassword = "password";

    try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
         PreparedStatement pst = conn.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
        pst.setString(1, username);
        pst.setString(2, password);

        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            // User found, load main view
            Parent root = FXMLLoader.load(getClass().getResource("/Views/view.fxml"));
            primaryStage.setScene(new Scene(root, 400, 375));
        } else {
            // User not found, show error
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid credentials", ButtonType.OK);
            alert.showAndWait();
        }
    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}


private void showRegistrationView() {
    VBox layout = new VBox(10);
    TextField usernameField = new TextField();
    usernameField.setPromptText("Username");
    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("Password");
    Button registerButton = new Button("Register");
    registerButton.setOnAction(e -> handleRegister(usernameField.getText(), passwordField.getText()));

    layout.getChildren().addAll(usernameField, passwordField, registerButton);

    Scene scene = new Scene(layout, 400, 375);
    primaryStage.setScene(scene);
}

private void handleRegister(String username, String password) {
    String url = "jdbc:mysql://localhost:3306/apd-db";
    String dbUsername = "root";
    String dbPassword = "password";

    try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
         PreparedStatement pst = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
        pst.setString(1, username);
        pst.setString(2, password);

        int affectedRows = pst.executeUpdate();
        if (affectedRows > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful", ButtonType.OK);
            alert.showAndWait();
            showLoginView(); // Redirect back to login view after successful registration
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed", ButtonType.OK);
            alert.showAndWait();
        }
    } catch (SQLException e) {
        if (e.getErrorCode() == 1062) { // MySQL error code for duplicate entry
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username already exists", ButtonType.OK);
            alert.showAndWait();
        } else {
            e.printStackTrace();
        }
    }
}


    public static void main(String[] args) {
        launch(args);
    }
}
