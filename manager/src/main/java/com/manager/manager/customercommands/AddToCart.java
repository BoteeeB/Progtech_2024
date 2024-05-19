package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AddToCart extends databaseConnection implements CustomerFactory {

    private ObservableList<Product> products;
    private ListView<Product> productList;
    private TextField quantityField;
    private ObservableList<Product> cart;
    private Label totalPriceLabel;
    private Label userBalanceLabel;
    private String message;
    private UpdateTotalPrice updateTotalPrice;
    private ShowAlert showAlert;

    public AddToCart(ObservableList<Product> products, ListView<Product> productList, TextField quantityField, ObservableList<Product> cart, Label totalPriceLabel, Label userBalanceLabel){
        this.products = products;
        this.productList = productList;
        this.quantityField = quantityField;
        this.cart = cart;
        this.totalPriceLabel = totalPriceLabel;
        this.userBalanceLabel = userBalanceLabel;
        this.updateTotalPrice = new UpdateTotalPrice(cart, totalPriceLabel, userBalanceLabel);
        this.showAlert = new ShowAlert(message);
    }

    @Override
    public void execute() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0 && quantity <= selectedProduct.getQuantity()) {
                    for (int i = 0; i < quantity; i++) {
                        cart.add(selectedProduct);
                    }
                    updateTotalPrice.execute();

                    // Frissítjük a termék mennyiségét az adatbázisban
                    UpdateProductAmount updateProductQuantity = new UpdateProductAmount(selectedProduct, quantity);
                    updateProductQuantity.execute();
                } else {
                    showAlert.setMessage("Invalid quantity.");
                    showAlert.execute();
                }
            } catch (NumberFormatException e) {
                showAlert.setMessage("Please enter a valid number for quantity.");
                showAlert.execute();
            }
        } else {
            showAlert.setMessage("Please select a product.");
            showAlert.execute();
        }
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
