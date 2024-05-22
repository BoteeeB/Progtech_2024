package com.manager.manager.admincommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddProduct extends databaseConnection implements ProductFactory {

    private static final Logger logger = LogManager.getLogger(AddProduct.class);

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;
    private ComboBox<String> typeComboBox;
    public LoadCombobox loaddata;
    public SaveData newSaveData;
    public ClearInputFields newClear;

    public AddProduct(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList,
                      ObservableList<Product> products, ComboBox<String> typeComboBox){
        this.productNameField = productNameField;
        this.priceField = priceField;
        this.quantityField = quantityField;
        this.productList = productList;
        this.products = products;
        this.typeComboBox = typeComboBox;
        this.loaddata = new LoadCombobox(typeComboBox);

        this.newSaveData = new SaveData(productNameField, priceField, quantityField, productList, products);
        this.newClear = new ClearInputFields(productNameField, priceField, quantityField, productList);
    }

    @Override
    public void execute() {
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String type = typeComboBox.getValue();

        if (!name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty() && type != null) {
            try {
                double price = Double.parseDouble(priceText);
                int quantity = Integer.parseInt(quantityText);
                int typeID = loaddata.getID(type);
                Product newProduct = new Product(name,(int) price, quantity, typeID);
                products.add(newProduct);

                newSaveData.execute();
                newClear.clearInputFields();

                logger.info("Új termék hozzáadva: " + name);
            } catch (NumberFormatException e) {
                logger.error("Hiba történt a termék hozzáadásakor: " + e.getMessage());
            }
        }
    }
}
