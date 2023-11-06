package Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty unit = new SimpleStringProperty(this, "unit");
    private final DoubleProperty quantity = new SimpleDoubleProperty(this, "quantity");
    private final DoubleProperty unitPrice = new SimpleDoubleProperty(this, "unitPrice");

    public Item(String name, String unit, double quantity, double unitPrice) {
        this.name.set(name);
        this.unit.set(unit);
        this.quantity.set(quantity);
        this.unitPrice.set(unitPrice);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getUnit() {
        return unit.get();
    }

    public void setUnit(String unit) {
        this.unit.set(unit);
    }

    public StringProperty unitProperty() {
        return unit;
    }

    public double getQuantity() {
        return quantity.get();
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice.get();
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice.set(unitPrice);
    }

    public DoubleProperty unitPriceProperty() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return getName();
    }
}
