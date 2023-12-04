// package Controllers;

// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;

// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
// import java.sql.SQLException;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import java.time.LocalDate;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;

// // ...

// public class BookRoomController {
//     @FXML private TextField txtNumberOfGuests;
//     @FXML private ComboBox<String> comboRoomType;
//     @FXML private TextField txtNumberOfDays;
//     @FXML private TextField txtRatePerDay;

//     private String url = "jdbc:mysql://localhost:3306/apd-db";
//     private String dbUsername = "root";
//     private String dbPassword = "password";

//     @FXML
//     public void initialize() {
//             List<String> availableRooms = getAvailableRooms();

//         comboRoomType.setItems(FXCollections.observableArrayList("Single", "Double"));
//     }

//     private List<String> getAvailableRooms() {
//     List<String> rooms = new ArrayList<>();
//     String sql = "SELECT room_type FROM Rooms WHERE is_available = TRUE;";
    
//     // Execute SQL and populate 'rooms' list
//     // Return the list of available rooms
//     return rooms;
// }

// private void handleGuestCount(int guestCount) {
//     int roomsNeeded = (guestCount + 1) / 2; // Assuming a maximum of 2 adults per room
//     // Update UI to suggest 'roomsNeeded'
// }


//     @FXML
//     private void handleNext() {
//         try {
//             int numberOfGuests = Integer.parseInt(txtNumberOfGuests.getText());
//             int numberOfDays = Integer.parseInt(txtNumberOfDays.getText());
//             double ratePerDay = Double.parseDouble(txtRatePerDay.getText());
            
//             // Validate the user input here...
//             // For example, check if the number of guests corresponds to the room type, etc.

//             // Assuming you have a method to get the appropriate room type based on number of guests:
//             String roomType = getRoomTypeForGuests(numberOfGuests);
//             if (roomType == null) {
//                 showAlert("Error", "No room available for the number of guests.");
//                 return;
//             }
            
//             comboRoomType.setValue(roomType);
//             txtRatePerDay.setText(String.valueOf(ratePerDay));

//             // Now, open the next view to collect guest information
//             // You can use FXMLLoader to load guest_information.fxml here

//         } catch (NumberFormatException e) {
//             showAlert("Input Error", "Please enter valid numbers for guests and number of days.");
//         }
//     }

//     private void showAlert(String title, String content) {
//         Alert alert = new Alert(AlertType.ERROR);
//         alert.setTitle(title);
//         alert.setHeaderText(null);
//         alert.setContentText(content);
//         alert.showAndWait();
//     }

//     private String getRoomTypeForGuests(int numberOfGuests) {
//         if (numberOfGuests == 1 || numberOfGuests == 2) {
//             return "Single";
//         } else if (numberOfGuests > 2 && numberOfGuests < 4) {
//             return "Double";
//         } else if (numberOfGuests >= 4) {
//             // Here you could implement additional logic to handle multiple rooms
//             return "Double"; // Placeholder, actual implementation needed
//         }
//         return null; // In case the number of guests doesn't fit any category
//     }

//     private void insertBookingIntoDatabase(int guestId, int roomId, LocalDate checkInDate, int numberOfDays) {
//         // Calculate check-out date based on number of days
//         LocalDate checkOutDate = checkInDate.plusDays(numberOfDays);

//         // Format dates to MySQL date format for insertion
//         DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//         String formattedCheckIn = checkInDate.format(dateFormatter);
//         String formattedCheckOut = checkOutDate.format(dateFormatter);

//         String sql = "INSERT INTO Reservations (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?);";

//         try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
//              PreparedStatement pst = conn.prepareStatement(sql)) {
             
//             pst.setInt(1, guestId);
//             pst.setInt(2, roomId);
//             pst.setString(3, formattedCheckIn);
//             pst.setString(4, formattedCheckOut);

//             int affectedRows = pst.executeUpdate();
//             if (affectedRows > 0) {
//                 showAlert("Success", "The booking has been successfully saved.");
//             } else {
//                 showAlert("Error", "No booking was created.");
//             }

//         } catch (SQLException e) {
//             showAlert("Database Error", "Error occurred while saving the booking.");
//             e.printStackTrace();
//         }
//     }

//     private void completeReservation(int guestId, String roomType) {
//         int roomId = getAvailableRoomId(roomType); // Implement this to find an available room of the given type
//         if (roomId == -1) {
//             showAlert("Booking Error", "No available rooms of the selected type.");
//             return;
//         }
    
//         LocalDate checkInDate = ...; // Logic to get check-in date
//         LocalDate checkOutDate = ...; // Logic to get check-out date
    
//         if (insertReservation(guestId, roomId, checkInDate, checkOutDate)) {
//             updateRoomAvailability(roomId, false); // Set room as not available
//             showAlert("Booking Success", "Room booked successfully.");
//         } else {
//             showAlert("Booking Error", "Could not complete the booking.");
//         }
//     }

//     @FXML
// private void openGuestInformationView() {
//     try {
//         // Load the FXML file for GuestInformationController
//         FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/GuestInformation.fxml"));
//         Parent root = loader.load();

//         // Get the controller
//         GuestInformationController guestController = loader.getController();

//         // Pass data to GuestInformationController
//         guestController.setRoomData(...); // Pass any necessary data like room type, room ID, etc.

//         // Now display the GuestInformation view in your scene
//         Stage stage = (Stage) someUIElement.getScene().getWindow(); // someUIElement is a UI component in your current view
//         stage.setScene(new Scene(root));
//         stage.show();

//     } catch (IOException e) {
//         e.printStackTrace();
//         // Handle the exception
//     }
// }

    
// }
