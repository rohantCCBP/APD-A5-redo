package Controllers;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.print.DocFlavor.URL;

import Models.Item;
import Models.ItemInCart;
import Models.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import Controllers.UncompletedCartsController;
import Controllers.UncompletedCartsController.Cart;

public class ViewController {
    @FXML private ComboBox<Item> itemsComboBox;
    @FXML private Label unitValueLabel;
    @FXML private Slider quantitySlider;
    @FXML private Label purchaseQuantityValueLabel;
    @FXML private Label purchasePriceValueLabel;
    
    @FXML private Button addButton;
    @FXML
    private Button btnSaveCart;
    @FXML
    private Button btnShowCarts;
    @FXML
    private Button btnCheckout;
    
    @FXML private TableView<ItemInCart> itemsTableView;
    @FXML private TextArea itemDetailsTextArea;

    @FXML private TableColumn<ItemInCart, String> itemNameColumn;
    @FXML private TableColumn<ItemInCart, Number> purchasedUnitsColumn;
    @FXML private TableColumn<ItemInCart, Number> purchasePriceColumn;

    @FXML private Label totalAmountLabel;

    private ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();
    private ObservableList<ItemInCart> cartObservableList = FXCollections.observableArrayList();

    private Model model;

    
    public void initialize() {
        loadItemsFromCSV();
       
DoubleBinding totalBinding = Bindings.createDoubleBinding(() ->
                cartObservableList.stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum(),
                cartObservableList);

        totalAmountLabel.textProperty().bind(Bindings.format("Total amount: $%.2f", totalBinding));

        
        itemsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                
                String details = "Name: " + newSelection.getName() + "\n" +
                                 "Quantity: " + newSelection.getQuantity() + "\n" +
                                 "Unit Price: " + newSelection.getUnitPrice() + "\n" +
                                 "Total Price: " + newSelection.getTotalPrice();
                itemDetailsTextArea.setText(details);
            } else {
                itemDetailsTextArea.clear();
            }
        });
        

        itemsComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                String itemDetails = String.format("Name: %s\nUnit: %s\nPrice: %.2f\nQuantity: %.2f",
                        newItem.getName(), newItem.getUnit(), newItem.getUnitPrice(), newItem.getQuantity());
                itemDetailsTextArea.setText(itemDetails); // Update your text area with this string.
            } else {
                itemDetailsTextArea.clear();
            }
        });
        
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchasedUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        purchasePriceColumn.setCellValueFactory(cellData -> 
                cellData.getValue().unitPriceProperty().multiply(cellData.getValue().quantityProperty()));
        
        itemsTableView.setItems(cartObservableList);

        model = new Model();
        try {
            model.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        itemsComboBox.setItems(model.getItemsObservableList());
        itemsComboBox.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (newItem != null) {
                unitValueLabel.setText(newItem.getUnit());
                purchasePriceValueLabel.setText(String.format("%.2f", newItem.getUnitPrice()));
            }
        });

        quantitySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            purchaseQuantityValueLabel.setText(String.format("%.0f", newVal.doubleValue()));
            updateTotalPrice();
        });
    }

    private void updateTotalPrice() {
        Item selectedItem = itemsComboBox.getValue();
        if (selectedItem != null) {
            double totalPrice = selectedItem.getUnitPrice() * quantitySlider.getValue();
            purchasePriceValueLabel.setText(String.format("%.2f", totalPrice));
        }
    }
public void loadItemsFromCSV() {
    String line;
    String splitBy = ",";
    try (BufferedReader br = new BufferedReader(new FileReader("ItemsMaster.csv"))) {
        while ((line = br.readLine()) != null) {
            String[] itemDetails = line.split(splitBy);
            if (itemDetails.length > 1) {
                try {
                    String name = itemDetails[0];
                    double price = Double.parseDouble(itemDetails[1]);
                    itemsObservableList.add(new Item(name, name, 1, price));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping line due to NumberFormatException: " + line);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

@FXML
public void handleAddAction(ActionEvent event) {
    Item selectedItem = itemsComboBox.getSelectionModel().getSelectedItem();
    double quantity = quantitySlider.getValue();
    if (selectedItem != null && quantity > 0) {
        ItemInCart newItem = new ItemInCart(selectedItem.getName(), quantity, selectedItem.getUnitPrice());
        cartObservableList.add(newItem);
    }
}

@FXML
public void handleRemoveAction(ActionEvent event) {
    int selectedIndex = itemsTableView.getSelectionModel().getSelectedIndex();
    if (selectedIndex >= 0) {
        cartObservableList.remove(selectedIndex);
    }
}

@FXML
private void handleSaveCart() {
    try {
        model.saveCart(cartObservableList);
        showAlert("Cart saved successfully.");
    } catch (IOException e) {
        showAlert("Failed to save cart.");
        e.printStackTrace();
    }
}

@FXML
public void onSaveCartClicked() {
    try {
        model.saveCart(cartObservableList);
        showAlert("Cart saved successfully.");
    } catch (IOException e) {
        showAlert("Failed to save cart.");
        e.printStackTrace();
    }
}

//  @FXML
//     public void onShowCartsClicked() {
//         // Here you would load the uncompleted carts view
//         // For simplicity, let's just print the uncompleted carts
//         List<String> uncompletedCarts = model.getUncompletedCarts();
//         uncompletedCarts.forEach(System.out::println);
//     }


 @FXML
    public void onCheckoutClicked() {
        if (confirmCheckout()) {
            boolean isDeleted = model.checkOutCart();
            if (isDeleted) {
                cartObservableList.clear();
                showAlert("Checkout successful.");
            } else {
                showAlert("Checkout failed.");
            }
        }
    }

    private boolean confirmCheckout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to check out?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
    private ObservableList<Cart> readCartsFromCSV(String filePath) {
        ObservableList<Cart> carts = FXCollections.observableArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("savedCarts.csv"))) { 
             String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); 
                String cartNumber = values[0];
                double totalPrice = Double.parseDouble(values[1]);
                carts.add(new Cart(cartNumber, totalPrice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carts;
    }
    
    @FXML
    private void handleShowCarts() {
        try {
            VBox root = new VBox();
            root.setSpacing(10);
    
            Label titleLabel = new Label("List of Carts");
            root.getChildren().add(titleLabel);
    
            TableView<Cart> tableView = new TableView<>();
            TableColumn<Cart, String> cartNumberColumn = new TableColumn<>("Cart Number");
            cartNumberColumn.setCellValueFactory(new PropertyValueFactory<>("cartNumber"));
            TableColumn<Cart, Double> totalPriceColumn = new TableColumn<>("Total Price");
            totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
            tableView.getColumns().addAll(cartNumberColumn, totalPriceColumn);
    
            // Populate the TableView
            ObservableList<Cart> cartData = readCartsFromCSV("/savedCarts.csv"); 
            tableView.setItems(cartData);
    
            root.getChildren().add(tableView);
    
            Button loadCartButton = new Button("Load Cart");
        // Instantiate UncompletedCartsController
        UncompletedCartsController uncompletedCartsController = new UncompletedCartsController();

        // Use the handleLoadCart method from UncompletedCartsController
        loadCartButton.setOnAction(event -> uncompletedCartsController.handleLoadCart());  

        root.getChildren().add(loadCartButton);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Uncompleted Carts");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

@FXML
private void handleCheckout() {
    // Get confirmation from the user first, then:
    boolean isDeleted = model.checkOutCart();
    if (isDeleted) {
        cartObservableList.clear();
    } else {
    }
}

}
