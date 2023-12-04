package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javafx.scene.control.Slider;
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
    private Stage billingDetailsStage;



public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/apd-db";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

// public class GuestDAO {
//     public void insertGuest(String firstName, String lastName, String address, String phone, String email) throws SQLException {
//         String query = "INSERT INTO Guests (first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?)";
//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(query)) {
//             pstmt.setString(1, firstName);
//             pstmt.setString(2, lastName);
//             pstmt.setString(3, address);
//             pstmt.setString(4, phone);
//             pstmt.setString(5, email);
//             pstmt.executeUpdate();
//         }
//     }
// }
public class GuestDAO {
    public int insertGuest(String firstName, String lastName, String address, String phone, String email) throws SQLException {
        String query = "INSERT INTO Guests (first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Return the guest ID
                } else {
                    throw new SQLException("Creating guest failed, no ID obtained.");
                }
            }
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
    
    // private void handleSubmit() {
    //      if (guestInfoStage != null) {
    //         guestInfoStage.close();
    //     }
    //     try {
    //          String firstName = txtFirstName.getText();
    //         String lastName = txtLastName.getText();
    //         String address = txtAddress.getText();
    //         String phone = txtPhone.getText();
    //         String email = txtEmail.getText();
    
    //         GuestDAO guestDAO = new GuestDAO();
    //         guestDAO.insertGuest(firstName, lastName, address, phone, email);
    //     } catch (SQLException e) {
    //         e.printStackTrace(); 
    //     }
    // }
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
    
            // Insert guest details
            GuestDAO guestDAO = new GuestDAO();
            int guestId = guestDAO.insertGuest(firstName, lastName, address, phone, email);
    
            // Assuming these values are retrieved from the booking form
            String roomType = "Single"; // or "Double", based on user selection
            int numberOfDays = 3; // Example value
            double ratePerDay = 100.00; // Example rate
    
            // Insert reservation details
            insertReservation(guestId, roomType, numberOfDays);
    
            // Insert billing details
            insertBilling(guestId, ratePerDay * numberOfDays); // Simple calculation for total amount
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void insertReservation(int guestId, String roomType, int numberOfDays) throws SQLException {
        // Sample query. Adjust as per your database schema
        String query = "INSERT INTO Reservations (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, guestId);
            // Assuming room_id is fetched based on roomType and availability
            pstmt.setInt(2, fetchRoomId(roomType)); 
            // Using current date as check_in_date and adding numberOfDays for check_out_date
            pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis() + numberOfDays * 24 * 60 * 60 * 1000));
            pstmt.executeUpdate();
        }
    }
    
    private void insertBilling(int guestId, double amount) throws SQLException {
        // Sample query. Adjust as per your database schema
        String query = "INSERT INTO Billing (reservation_id, amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Assuming reservation_id is fetched based on guestId
            pstmt.setInt(1, fetchReservationId(guestId)); 
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        }
    }
    private void insertReservation(int guestId, String roomType, int numberOfDays) throws SQLException {
        // Sample query. Adjust as per your database schema
        String query = "INSERT INTO Reservations (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, guestId);
            pstmt.setInt(2, fetchRoomId(roomType)); // Fetch the room ID based on room type
            pstmt.setDate(3, new java.sql.Date(System.currentTimeMillis())); // Current date as check-in
            pstmt.setDate(4, new java.sql.Date(System.currentTimeMillis() + numberOfDays * 24 * 60 * 60 * 1000)); // Check-out date
            pstmt.executeUpdate();
        }
    }
    
    private int fetchRoomId(String roomType) throws SQLException {
        String query = "SELECT room_id FROM Rooms WHERE room_type = ? LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, roomType);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("room_id");
            } else {
                throw new SQLException("No available rooms of type: " + roomType);
            }
        }
    }
    private void insertBilling(int guestId, double amount) throws SQLException {
        // Sample query. Adjust as per your database schema
        String query = "INSERT INTO Billing (reservation_id, amount) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, fetchReservationId(guestId)); // Fetch the reservation ID based on guest ID
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        }
    }
    
    private int fetchReservationId(int guestId) throws SQLException {
        String query = "SELECT reservation_id FROM Reservations WHERE guest_id = ? ORDER BY check_in_date DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, guestId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("reservation_id");
            } else {
                throw new SQLException("No reservations found for guest ID: " + guestId);
            }
        }
    }
        

@FXML
public void handleBillService() {
    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(20, 20, 20, 20));

    Text title = new Text("Billing Service");
    vbox.getChildren().add(title);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    // Booking ID
    grid.add(new Label("Booking ID:"), 0, 0);
    TextField txtBookingId = new TextField();
    grid.add(txtBookingId, 1, 0);

    // Discount Slider
    grid.add(new Label("Discount (%):"), 0, 1);
    Slider discountSlider = new Slider(0, 25, 0);
    discountSlider.setShowTickLabels(true);
    discountSlider.setShowTickMarks(true);
    discountSlider.setMajorTickUnit(5);
    discountSlider.setMinorTickCount(1);
    discountSlider.setSnapToTicks(true);
    grid.add(discountSlider, 1, 1);

    // Show Bill button
    Button btnShowBill = new Button("Show Bill");
    btnShowBill.setOnAction(event -> {
        // Assuming a method to fetch and calculate billing details
        // You'll need to implement the logic to fetch billing details from the database
        showBillingDetails(txtBookingId.getText(), discountSlider.getValue());
    });
    grid.add(btnShowBill, 1, 2);

    vbox.getChildren().add(grid);

    Stage billServiceStage = new Stage();
    billServiceStage.setTitle("Bill Service");
    billServiceStage.setScene(new Scene(vbox));
    billServiceStage.show();
}


private void showBillingDetails(String bookingId, double discount) {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String query = "SELECT r.room_type, r.rate, COUNT(*) as room_count, g.first_name, g.last_name " +
                       "FROM Reservations res " +
                       "JOIN Rooms r ON res.room_id = r.room_id " +
                       "JOIN Guests g ON res.guest_id = g.guest_id " +
                       "WHERE res.reservation_id = ? " +
                       "GROUP BY r.room_type, r.rate, g.first_name, g.last_name";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setInt(1, Integer.parseInt(bookingId));
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            String guestName = rs.getString("first_name") + " " + rs.getString("last_name");
            String roomType = rs.getString("room_type");
            int numberOfRooms = rs.getInt("room_count");
            double ratePerNight = rs.getDouble("rate");

            double totalAmount = numberOfRooms * ratePerNight;
            double discountedAmount = totalAmount * (1 - discount / 100);

            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20, 20, 20, 20));

            vbox.getChildren().add(new Text("Booking ID: " + bookingId));
            vbox.getChildren().add(new Text("Guest Name: " + guestName));
            vbox.getChildren().add(new Text("No of rooms booked: " + numberOfRooms));
            vbox.getChildren().add(new Text("Type of rooms: " + roomType));
            vbox.getChildren().add(new Text("Rate per night: $" + ratePerNight));
            vbox.getChildren().add(new Text("Discount: " + discount + "%"));
            vbox.getChildren().add(new Text("Total Amount: $" + String.format("%.2f", discountedAmount)));

            billingDetailsStage = new Stage();
            billingDetailsStage.setTitle("Billing Details");
            billingDetailsStage.setScene(new Scene(vbox));
            billingDetailsStage.show();
        } else {
            // Handle case where no reservation is found
            System.out.println("No reservation found with ID: " + bookingId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Invalid Booking ID format.");
    }
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
