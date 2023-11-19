package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();
    
private Map<String, List<ItemInCart>> cartDetails = new HashMap<>();

private String generateUniqueCartNumber() {
    LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    return now.format(formatter);
}

private double calculateTotalPrice(ObservableList<ItemInCart> items) {
    double totalPrice = 0;
    for (ItemInCart item : items) {
        totalPrice += item.getQuantity() * item.getUnitPrice();
    }
    return totalPrice;
}



public void saveCart(ObservableList<ItemInCart> items)  throws IOException {
    String cartNumber = generateUniqueCartNumber();
    double totalPrice = calculateTotalPrice(items);

    saveCartSummary(cartNumber, totalPrice);
    saveCartDetails(cartNumber, new ArrayList<>(items));
}



private void saveCartSummary(String cartNumber, double totalPrice) throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter("savedCarts.csv", true))) {
        pw.println(cartNumber + "," + totalPrice);
    }
}
private void saveCartDetails(String cartNumber, List<ItemInCart> items) {
    cartDetails.put(cartNumber, new ArrayList<>(items));
}

public List<ItemInCart> loadCartDetails(String cartNumber) {
    return cartDetails.get(cartNumber);
}

    public List<String> getUncompletedCarts() {
        List<String> uncompletedCarts = new ArrayList<>();
        File file = new File("savedCarts.csv");
        if (file.exists()) {
            uncompletedCarts.add(file.getName());
        }
        return uncompletedCarts;
    }

    public boolean checkOutCart() {
        File cartFile = new File("savedCarts.csv");
        return cartFile.delete();
    }

    public ObservableList<Item> getItemsObservableList() {
        return itemsObservableList;
    }

    public void loadData() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("ItemsMaster.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] itemData = line.split(",");
                if (itemData.length >= 3) {
                    String name = itemData[0];
                    String unit = itemData[1];
                    double quantity = Double.parseDouble(itemData[2]);
                    double unitPrice = Double.parseDouble(itemData[3]);
                    itemsObservableList.add(new Item(name, unit, quantity, unitPrice));
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number from file");
        }
    }
}
