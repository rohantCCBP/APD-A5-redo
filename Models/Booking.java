package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Booking {
    private final SimpleIntegerProperty bookingId;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty roomType;
    private final SimpleIntegerProperty numberOfRooms;
    private final SimpleIntegerProperty numberOfDays;

    public Booking(int bookingId, String customerName, String roomType, int numberOfRooms, int numberOfDays) {
        this.bookingId = new SimpleIntegerProperty(bookingId);
        this.customerName = new SimpleStringProperty(customerName);
        this.roomType = new SimpleStringProperty(roomType);
        this.numberOfRooms = new SimpleIntegerProperty(numberOfRooms);
        this.numberOfDays = new SimpleIntegerProperty(numberOfDays);
    }

    // Getters (required for PropertyValueFactory)
    public int getBookingId() { return bookingId.get(); }
    public String getCustomerName() { return customerName.get(); }
    public String getRoomType() { return roomType.get(); }
    public int getNumberOfRooms() { return numberOfRooms.get(); }
    public int getNumberOfDays() { return numberOfDays.get(); }

    // Additional setters and property getters if necessary
}