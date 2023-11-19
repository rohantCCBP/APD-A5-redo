package Models;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;




public class ItemInCart implements Serializable{
    private Map<String, List<ItemInCart>> cartDetails = new HashMap<>();

    private final SimpleStringProperty name;
    private final SimpleDoubleProperty quantity;
    private final SimpleDoubleProperty unitPrice;

    public ItemInCart(String name, double quantity, double unitPrice) {
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleDoubleProperty(quantity);
        this.unitPrice = new SimpleDoubleProperty(unitPrice);
    }
    
private void saveCartSummary(String cartNumber, double totalPrice) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter("savedCarts.csv", true))) { 
        pw.println(cartNumber + "," + totalPrice);
    }
}


private void saveCartDetails(String cartNumber, List<ItemInCart> items) {
    cartDetails.put(cartNumber, items);
}

// public void saveCart(ObservableList<ItemInCart> items) throws IOException {
//         String cartNumber = generateUniqueCartNumber();
//     double totalPrice = calculateTotalPrice(items);

//     saveCartSummary(cartNumber, totalPrice); 
//     saveCartDetails(cartNumber, items); 
// }
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
