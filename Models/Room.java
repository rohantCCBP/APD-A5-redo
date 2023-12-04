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

    public int getRoomId() { return roomId.get(); }
    public String getRoomType() { return roomType.get(); }
}