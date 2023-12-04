package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewController {
    @FXML
    private Button btnBookRoom;
    private TextField txtFirstName;
    private TextField txtLastName;
    private TextField txtAddress;
    private TextField txtPhone;
    private TextField txtEmail;

    private Stage bookRoomStage;
    private Stage guestInfoStage;



public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/apd-db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

public class GuestDAO {
    public void insertGuest(String firstName, String lastName, String address, String phone, String email) throws SQLException {
        String query = "INSERT INTO Guests (first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.executeUpdate();
        }
    }
}

    
@FXML
public void handleBookRoom() {
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(20, 20, 20, 20));

    Text title = new Text("Book a Room");
    vbox.getChildren().add(title);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

       grid.add(new Label("Number of Guests:"), 0, 0);
    TextField txtNumberOfGuests = new TextField();
    grid.add(txtNumberOfGuests, 1, 0);

    grid.add(new Label("Room Type:"), 0, 1);
    ComboBox<String> comboRoomType = new ComboBox<>();
    comboRoomType.getItems().addAll("Single", "Double");
    grid.add(comboRoomType, 1, 1);

      grid.add(new Label("Number of Days:"), 0, 2);
    TextField txtNumberOfDays = new TextField();
    grid.add(txtNumberOfDays, 1, 2);

     grid.add(new Label("Rate per Day:"), 0, 3);
    TextField txtRatePerDay = new TextField();
    grid.add(txtRatePerDay, 1, 3);

      //  vbox.getChildren().add(grid);

    bookRoomStage = new Stage();
    bookRoomStage.setTitle("Book a Room");
   
    Button btnNext = new Button("Next");
    btnNext.setOnAction(event -> {
        handleNext();
        bookRoomStage.close(); 
    });
    grid.add(btnNext, 1, 4);

    vbox.getChildren().add(grid);
    bookRoomStage = new Stage();
    bookRoomStage.setTitle("Book a Room");
    Scene scene = new Scene(vbox);
    bookRoomStage.setScene(scene);
     bookRoomStage.show();
}


    private void handleNext() {
        if (bookRoomStage != null) {
            bookRoomStage.close();
        }
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        txtFirstName = new TextField();
        txtLastName = new TextField();
        txtAddress = new TextField();
        txtPhone = new TextField();
        txtEmail = new TextField();
    
        Text title = new Text("Guest Information");
        vbox.getChildren().add(title);
    
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
    
       grid.add(new Label("Title:"), 0, 0);
        TextField txtTitle = new TextField();
        grid.add(txtTitle, 1, 0);
    
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(txtFirstName, 1, 1);
    
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(txtLastName, 1, 2);
    
        grid.add(new Label("Address:"), 0, 3);
        grid.add(txtAddress, 1, 3);
    
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(txtPhone, 1, 4);
        
        grid.add(new Label("Email:"), 0, 5);
                grid.add(txtEmail, 1, 5);
    
        Button btnSubmit = new Button("Submit");
        btnSubmit.setOnAction(event -> handleSubmit());  
        grid.add(btnSubmit, 1, 6);

        vbox.getChildren().add(grid);
    
         guestInfoStage = new Stage();
        guestInfoStage.setTitle("Guest Information");
        guestInfoStage.setScene(new Scene(vbox));
        guestInfoStage.show();
    }
    
    private void handleSubmit() {
         if (guestInfoStage != null) {
            guestInfoStage.close();
        }
        try {
             String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String address = txtAddress.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
    
            GuestDAO guestDAO = new GuestDAO();
            guestDAO.insertGuest(firstName, lastName, address, phone, email);
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }
    

@FXML
public void handleBillService() {
    
}

@FXML
public void handleCurrentBookings() {
    
}
@FXML
public void handleAvailableRooms() {
}


@FXML
public void handleExit() {
    Platform.exit();
    System.exit(0);
}


}
