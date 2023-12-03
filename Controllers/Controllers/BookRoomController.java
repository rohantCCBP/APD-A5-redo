package Controllers;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// ...

public class BookRoomController {
    @FXML private TextField txtNumberOfGuests;
    @FXML private ComboBox<String> comboRoomType;
    @FXML private TextField txtNumberOfDays;
    @FXML private TextField txtRatePerDay;

    private String url = "jdbc:mysql://localhost:3306/apd-db";
    private String dbUsername = "root";
    private String dbPassword = "password";

    @FXML
    public void initialize() {
        comboRoomType.setItems(FXCollections.observableArrayList("Single", "Double"));
    }

    @FXML
    private void handleNext() {
        try {
            int numberOfGuests = Integer.parseInt(txtNumberOfGuests.getText());
            int numberOfDays = Integer.parseInt(txtNumberOfDays.getText());
            double ratePerDay = Double.parseDouble(txtRatePerDay.getText());
            
            // Validate the user input here...
            // For example, check if the number of guests corresponds to the room type, etc.

            // Assuming you have a method to get the appropriate room type based on number of guests:
            String roomType = getRoomTypeForGuests(numberOfGuests);
            if (roomType == null) {
                showAlert("Error", "No room available for the number of guests.");
                return;
            }
            
            comboRoomType.setValue(roomType);
            txtRatePerDay.setText(String.valueOf(ratePerDay));

            // Now, open the next view to collect guest information
            // You can use FXMLLoader to load guest_information.fxml here

        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for guests and number of days.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private String getRoomTypeForGuests(int numberOfGuests) {
        if (numberOfGuests == 1 || numberOfGuests == 2) {
            return "Single";
        } else if (numberOfGuests > 2 && numberOfGuests < 4) {
            return "Double";
        } else if (numberOfGuests >= 4) {
            // Here you could implement additional logic to handle multiple rooms
            return "Double"; // Placeholder, actual implementation needed
        }
        return null; // In case the number of guests doesn't fit any category
    }

    private void insertBookingIntoDatabase(int guestId, int roomId, LocalDate checkInDate, int numberOfDays) {
        // Calculate check-out date based on number of days
        LocalDate checkOutDate = checkInDate.plusDays(numberOfDays);

        // Format dates to MySQL date format for insertion
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedCheckIn = checkInDate.format(dateFormatter);
        String formattedCheckOut = checkOutDate.format(dateFormatter);

        String sql = "INSERT INTO Reservations (guest_id, room_id, check_in_date, check_out_date) VALUES (?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement pst = conn.prepareStatement(sql)) {
             
            pst.setInt(1, guestId);
            pst.setInt(2, roomId);
            pst.setString(3, formattedCheckIn);
            pst.setString(4, formattedCheckOut);

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                showAlert("Success", "The booking has been successfully saved.");
            } else {
                showAlert("Error", "No booking was created.");
            }

        } catch (SQLException e) {
            showAlert("Database Error", "Error occurred while saving the booking.");
            e.printStackTrace();
        }
    }

    // ... Additional methods for handling guest information view and booking logic
}
