package Controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Models.Booking;
import Models.Guest;
import Models.Room;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


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
    // Create the Stage and TableView
    Stage currentBookingsStage = new Stage();
    TableView<Booking> table = new TableView<>();
    currentBookingsStage.setTitle("Current Bookings");

    // Define the table columns
    TableColumn<Booking, Integer> bookingCol = new TableColumn<>("Booking #");
    bookingCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));

    TableColumn<Booking, String> customerNameCol = new TableColumn<>("Customer Name");
    customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

    TableColumn<Booking, String> roomTypeCol = new TableColumn<>("Room Type");
    roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

    TableColumn<Booking, Integer> noRoomsCol = new TableColumn<>("No of Rooms");
    noRoomsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfRooms"));

    TableColumn<Booking, Integer> noDaysCol = new TableColumn<>("No of Days");
    noDaysCol.setCellValueFactory(new PropertyValueFactory<>("numberOfDays"));

    table.getColumns().addAll(bookingCol, customerNameCol, roomTypeCol, noRoomsCol, noDaysCol);

    // Fetch the bookings from the database and add to the table
    ObservableList<Booking> bookings = FXCollections.observableArrayList(fetchCurrentBookings());
    table.setItems(bookings);

    // Layout and scene setting
    VBox vbox = new VBox();
    vbox.getChildren().addAll(table);
    Scene scene = new Scene(vbox);
    currentBookingsStage.setScene(scene);
    currentBookingsStage.show();
}

private List<Booking> fetchCurrentBookings() {
    List<Booking> bookings = new ArrayList<>();
    String query = "SELECT res.reservation_id, CONCAT(g.first_name, ' ', g.last_name) AS customer_name, " +
                   "r.room_type, COUNT(res.room_id) as number_of_rooms, DATEDIFF(res.check_out_date, res.check_in_date) as number_of_days " +
                   "FROM Reservations res " +
                   "JOIN Guests g ON res.guest_id = g.guest_id " +
                   "JOIN Rooms r ON res.room_id = r.room_id " +
                   "GROUP BY res.reservation_id, g.first_name, g.last_name, r.room_type";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            int bookingId = rs.getInt("reservation_id");
            String customerName = rs.getString("customer_name");
            String roomType = rs.getString("room_type");
            int numberOfRooms = rs.getInt("number_of_rooms");
            int numberOfDays = rs.getInt("number_of_days");
            
            bookings.add(new Booking(bookingId, customerName, roomType, numberOfRooms, numberOfDays));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return bookings;
}



@FXML
public void handleAvailableRooms() {
    Stage availableRoomsStage = new Stage();
    TableView<Room> table = new TableView<>();
    availableRoomsStage.setTitle("Available Rooms");

    // Define the table columns
    TableColumn<Room, Integer> roomIdCol = new TableColumn<>("Room ID");
    roomIdCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));

    TableColumn<Room, String> roomTypeCol = new TableColumn<>("Room Type");
    roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("roomType"));

    table.getColumns().addAll(roomIdCol, roomTypeCol);

    // Fetch the available rooms from the database and add to the table
    ObservableList<Room> rooms = FXCollections.observableArrayList(fetchAvailableRooms());
    table.setItems(rooms);

    // Layout and scene setting
    VBox vbox = new VBox();
    vbox.getChildren().addAll(table);
    Scene scene = new Scene(vbox);
    availableRoomsStage.setScene(scene);
    availableRoomsStage.show();
}

private List<Room> fetchAvailableRooms() {
    List<Room> availableRooms = new ArrayList<>();
    String query = "SELECT room_id, room_type FROM Rooms WHERE is_available = TRUE";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            int roomId = rs.getInt("room_id");
            String roomType = rs.getString("room_type");
            
            availableRooms.add(new Room(roomId, roomType));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return availableRooms;
}


@FXML
public void searchGuests() {
    // Prompt for guest name or phone number
    TextInputDialog searchDialog = new TextInputDialog();
    searchDialog.setTitle("Search Guests");
    searchDialog.setHeaderText("Search for guests by name or phone number:");
    searchDialog.setContentText("Enter name or phone number:");

    Optional<String> result = searchDialog.showAndWait();
    result.ifPresent(searchTerm -> {
        // Assuming a method to search for guests and return results
        List<Guest> guests = searchForGuests(searchTerm);
        // Display the results in a new window or as a dialog
        // This part of the implementation would be similar to the previous examples
        // where you create a Stage and display the results in a TableView.
    });
}

private List<Guest> searchForGuests(String searchTerm) {
    List<Guest> foundGuests = new ArrayList<>();
    String query = "SELECT * FROM Guests WHERE first_name LIKE ? OR last_name LIKE ? OR phone LIKE ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, "%" + searchTerm + "%");
        pstmt.setString(2, "%" + searchTerm + "%");
        pstmt.setString(3, "%" + searchTerm + "%");
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            Guest guest = new Guest(
                rs.getInt("guest_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getString("email")
            );
            foundGuests.add(guest);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return foundGuests;
}

@FXML
public void cancelBooking() {
    TextInputDialog cancelDialog = new TextInputDialog();
    cancelDialog.setTitle("Cancel Booking");
    cancelDialog.setHeaderText("Cancel a booking:");
    cancelDialog.setContentText("Enter booking ID:");

    Optional<String> result = cancelDialog.showAndWait();
    result.ifPresent(bookingId -> {
        // Assuming a method to cancel the booking
        cancelBookingById(bookingId);
        // Notify the user of success or handle the case if the booking was not found
    });
}

private void cancelBookingById(String bookingId) {
    String query = "DELETE FROM Reservations WHERE reservation_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, Integer.parseInt(bookingId));
        int affectedRows = pstmt.executeUpdate();
        
        if (affectedRows > 0) {
            System.out.println("Booking with ID " + bookingId + " was successfully canceled.");
        } else {
            System.out.println("No booking found with ID " + bookingId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Invalid booking ID format.");
    }
}



@FXML
public void checkoutGuest() {
    TextInputDialog checkoutDialog = new TextInputDialog();
    checkoutDialog.setTitle("Check Out Guest");
    checkoutDialog.setHeaderText("Check out a guest:");
    checkoutDialog.setContentText("Enter guest ID or booking ID:");

    Optional<String> result = checkoutDialog.showAndWait();
    result.ifPresent(id -> {
        // Assuming a method to check out the guest
        checkoutGuestById(id);
        // Notify the user of success or handle the case if the guest/booking was not found
    });
}

private void checkoutGuestById(String id) {
    String queryUpdateRoom = "UPDATE Rooms SET is_available = TRUE WHERE room_id IN " +
                             "(SELECT room_id FROM Reservations WHERE guest_id = ? OR reservation_id = ?)";
    String queryDeleteReservation = "DELETE FROM Reservations WHERE guest_id = ? OR reservation_id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmtRoom = conn.prepareStatement(queryUpdateRoom);
         PreparedStatement pstmtReservation = conn.prepareStatement(queryDeleteReservation)) {
        
        // Update room availability
        pstmtRoom.setInt(1, Integer.parseInt(id));
        pstmtRoom.setInt(2, Integer.parseInt(id));
        pstmtRoom.executeUpdate();
        
        // Remove the reservation
        pstmtReservation.setInt(1, Integer.parseInt(id));
        pstmtReservation.setInt(2, Integer.parseInt(id));
        int affectedRows = pstmtReservation.executeUpdate();
        
        if (affectedRows > 0) {
            System.out.println("Guest with ID/Booking ID " + id + " has been checked out.");
        } else {
            System.out.println("No guest/booking found with ID " + id);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } catch (NumberFormatException e) {
        System.out.println("Invalid guest ID/booking ID format.");
    }
}




@FXML
public void handleExit() {
    Platform.exit();
    System.exit(0);
}


}
