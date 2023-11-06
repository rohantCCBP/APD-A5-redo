package Controllers;

import java.io.IOException;

import Models.Item;
import Models.Model;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class ViewController {
    @FXML private ComboBox<Item> itemsComboBox;
    @FXML private Label unitValueLabel;
    @FXML private Slider quantitySlider;
    @FXML private Label purchaseQuantityValueLabel;
    @FXML private Label purchasePriceValueLabel;

    private Model model;

    public void initialize() {
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
}
