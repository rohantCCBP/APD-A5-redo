package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.ResultSet;

public class GuestInformationController {
    @FXML private TextField txtTitle;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtAddress;
    @FXML private TextField txtPhone;
    @FXML private TextField txtEmail;

    private int roomId;

    private String url = "jdbc:mysql://localhost:3306/apd-db";
    private String dbUsername = "root";
    private String dbPassword = "password";

   @FXML
private void handleSubmit() {
    if (!isInputValid()) {
        showAlert("Validation Error", "Please fill in all the fields with valid information.");
        return;
    }

   // int guestId = insertGuestIntoDatabase();
    // if (guestId == -1) {
    //     showAlert("Database Error", "Could not save guest information.");
    //     return;
    // }

    // Retrieve the room information based on the room type
   // int roomId = getAvailableRoomId(comboRoomType.getValue());
    if (roomId == -1) {
        showAlert("Booking Error", "No available rooms of the selected type.");
        return;
    }

    // Calculate check-in and check-out dates
    LocalDate checkInDate = LocalDate.now(); // or any other logic to determine the check-in date
    //LocalDate checkOutDate = checkInDate.plusDays(Integer.parseInt(txtNumberOfDays.getText()));

    // Insert reservation and update room availability
    // if (insertReservation(guestId, roomId, checkInDate, checkOutDate)) {
    //     updateRoomAvailability(roomId, false); // Set room as not available

        // Calculate billing amount
      //  double totalAmount = calculateBillingAmount(Double.parseDouble(txtRatePerDay.getText()), Integer.parseInt(txtNumberOfDays.getText()));
      //  insertBillingRecord(guestId, roomId, totalAmount);

        showAlert("Booking Success", "Room booked successfully.");
    // } else {
    //     showAlert("Booking Error", "Could not complete the booking.");
    // }
}

// private int getAvailableRoomId(String roomType) {
//     // Logic to retrieve an available room ID from the database based on room type
//     // Return -1 if no available room is found
// }

// private boolean insertReservation(int guestId, int roomId, LocalDate checkIn, LocalDate checkOut) {
//     // Insert reservation into the database and return true if successful
// }

private void updateRoomAvailability(int roomId, boolean isAvailable) {
    // Update the availability of the room in the database
}

private double calculateBillingAmount(double ratePerDay, int numberOfDays) {
    // Calculate the total billing amount
    return ratePerDay * numberOfDays;
}

private void insertBillingRecord(int guestId, int roomId, double amount) {
    // Insert billing information into the database



        // Insert the guest information into the database
       // int guestId = insertGuestIntoDatabase();

        if (guestId != -1) {
            // Assuming you have a method to proceed to the next booking step
           // proceedToNextBookingStep(guestId);
        } else {
            showAlert("Database Error", "Could not save guest information.");
        }
    

    private void insertGuestIntoDatabase() {
        String sql = "INSERT INTO Guests (title, first_name, last_name, address, phone, email) VALUES (?, ?, ?, ?, ?, ?);";
        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pst = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
             
            pst.setString(1, txtTitle.getText());
            pst.setString(2, txtFirstName.getText());
            pst.setString(3, txtLastName.getText());
            pst.setString(4, txtAddress.getText());
            pst.setString(5, txtPhone.getText());
            pst.setString(6, txtEmail.getText());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                    //    return generatedKeys.getInt(1); // Return the generated guest ID
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors and possibly show an alert to the user
        }
      //  return -1; // Return an invalid ID if the insertion failed
    }

    private boolean isInputValid() {
        // Here you would implement your actual validation logic
        return !txtFirstName.getText().isEmpty() &&
               !txtLastName.getText().isEmpty() &&
               !txtAddress.getText().isEmpty() &&
               !txtPhone.getText().isEmpty() &&
               txtEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void proceedToNextBookingStep(int guestId) {
        // Here you would transition to the next part of the booking process
        // For example, showing a confirmation screen or closing the guest information view
    }
}


