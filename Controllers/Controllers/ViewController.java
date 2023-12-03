package Controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ViewController {
 @FXML
    public void handleBookRoom() {
        // VBox with padding and spacing
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        // Title text
        Text title = new Text("Book a Room");
        vbox.getChildren().add(title);

        // GridPane for form elements
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Number of Guests
        grid.add(new Label("Number of Guests:"), 0, 0);
        TextField txtNumberOfGuests = new TextField();
        grid.add(txtNumberOfGuests, 1, 0);

        // Room Type
        grid.add(new Label("Room Type:"), 0, 1);
        ComboBox<String> comboRoomType = new ComboBox<>();
        comboRoomType.getItems().addAll("Single", "Double");
        grid.add(comboRoomType, 1, 1);

        // Number of Days
        grid.add(new Label("Number of Days:"), 0, 2);
        TextField txtNumberOfDays = new TextField();
        grid.add(txtNumberOfDays, 1, 2);

        // Rate per Day
        grid.add(new Label("Rate per Day:"), 0, 3);
        TextField txtRatePerDay = new TextField();
        grid.add(txtRatePerDay, 1, 3);

        // Next button
        Button btnNext = new Button("Next");
        btnNext.setOnAction(event -> handleNext());  // Assuming handleNext() is a method you have defined
        grid.add(btnNext, 1, 4);

        // Add GridPane to VBox
        vbox.getChildren().add(grid);

        // Create a new Stage and set the scene
        Stage stage = new Stage();
        stage.setTitle("Book a Room");
        stage.setScene(new Scene(vbox));
        stage.show();

        // If you want to replace the content in the current window, uncomment the following lines
        // Stage primaryStage = (Stage) btnBookRoom.getScene().getWindow();
        // primaryStage.setScene(new Scene(vbox));
    }

    private void handleNext() {
        // Handle the 'Next' button action here
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
