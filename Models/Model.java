package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Model {
    private ObservableList<Item> itemsObservableList = FXCollections.observableArrayList();

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
