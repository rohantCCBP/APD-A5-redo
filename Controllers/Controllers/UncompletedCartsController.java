package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UncompletedCartsController {

    @FXML
    private TableView<Cart> cartsTableView;
    @FXML
    private TableColumn<Cart, String> cartNumberColumn;
    @FXML
    private TableColumn<Cart, Double> totalPriceColumn;

    public static class Cart {
        private final String cartNumber;
        private final double totalPrice;

        public Cart(String cartNumber, double totalPrice) {
            this.cartNumber = cartNumber;
            this.totalPrice = totalPrice;
        }
        public String getCartNumber() { return cartNumber; }
        public double getTotalPrice() { return totalPrice; }
    }

    public void initialize() {
        cartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("cartNumber"));
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        loadUncompletedCarts();
    }

    private void loadUncompletedCarts() {
        cartsTableView.getItems().add(new Cart("Cart1", 123.45));
        cartsTableView.getItems().add(new Cart("Cart2", 234.56));
    }

    @FXML public void handleLoadCart() {
                Cart selectedCart = cartsTableView.getSelectionModel().getSelectedItem();
        if (selectedCart != null) {
        }
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) cartsTableView.getScene().getWindow();
        stage.close();
    }
}
