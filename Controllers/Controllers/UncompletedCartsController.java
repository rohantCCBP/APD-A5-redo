package Controllers;

import java.util.List;

import Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class UncompletedCartsController {

    @FXML
    private ListView<String> listViewCarts;

    private Model model;

    public void initialize() {
        model = new Model();
        loadUncompletedCarts();
    }

    private void loadUncompletedCarts() {
        // Implement loading of uncompleted carts here
        // For example, you could have a method in your Model that returns the filenames of uncompleted cart files
        List<String> uncompletedCarts = model.getUncompletedCarts();
        listViewCarts.getItems().setAll(uncompletedCarts);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) listViewCarts.getScene().getWindow();
        stage.close();
    }
}