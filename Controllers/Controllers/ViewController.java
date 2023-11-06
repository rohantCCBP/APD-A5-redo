package Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Models.Item;
import Models.ItemInCart;
import Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ViewController {
    @FXML private ComboBox<Item> itemsComboBox;
    @FXML private Label unitValueLabel;
    @FXML private Slider quantitySlider;
    @FXML private Label purchaseQuantityValueLabel;
    @FXML private Label purchasePriceValueLabel;

 @FXML private Button addButton;
    
 
 @FXML private TableView<ItemInCart> itemsTableView;
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
// cartObservableList.addListener((ListChangeListener.Change<? extends ItemInCart> c) -> {
//     while (c.next()) {
//         if (c.wasAdded() || c.wasRemoved()) {
//             System.out.println("Change detected in cartObservableList");
//         }
//     }
// });

        // Add selection change listener for the itemsTableView
        itemsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Find Item by name in itemsObservableList
                Item item = itemsObservableList.stream()
                    .filter(i -> i.getName().equals(newSelection.getName()))
                    .findFirst()
                    .orElse(null);
                
                // Update ComboBox and Slider
                if (item != null) {
                    itemsComboBox.setValue(item);
                    quantitySlider.setValue(newSelection.getQuantity());
                }
            }
        });
        
    



        // Initialize columns
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        purchasedUnitsColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        // This is a simplified example, you will need to implement the calculation logic
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
    String splitBy = ","; // CSV delimiter.
    try (BufferedReader br = new BufferedReader(new FileReader("ItemsMaster.csv"))) {
        while ((line = br.readLine()) != null) {
            String[] itemDetails = line.split(splitBy);
            if (itemDetails.length > 1) {
                try {
                    String name = itemDetails[0];
                    double price = Double.parseDouble(itemDetails[1]);
                    itemsObservableList.add(new Item(name, name, 1, price)); // Quantity is set to 1 by default
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

}
