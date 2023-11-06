package Models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemInCart {
    private final SimpleStringProperty name;
    private final SimpleDoubleProperty quantity;
    private final SimpleDoubleProperty unitPrice; // Assuming you have a unit price for each item.

    public ItemInCart(String name, double quantity, double unitPrice) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleDoubleProperty quantityProperty() {
        return quantity;
    }

    public SimpleDoubleProperty unitPriceProperty() {
        return unitPrice;
    }
    
    public double getTotalPrice() {
        return quantity.get() * unitPrice.get();
    }
}
