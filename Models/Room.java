package Models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Room {
    private final SimpleIntegerProperty roomId;
    private final SimpleStringProperty roomType;

    public Room(int roomId, String roomType) {
        this.roomId = new SimpleIntegerProperty(roomId);
        this.roomType = new SimpleStringProperty(roomType);
    }

    // Getters (required for PropertyValueFactory)
    public int getRoomId() { return roomId.get(); }
    public String getRoomType() { return roomType.get(); }

    // Additional setters and property getters if necessary
}