package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddToCart extends databaseConnection implements CustomerFactory {

    private static final Logger logger = LogManager.getLogger(AddToCart.class);

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

                    UpdateProductAmount updateProductQuantity = new UpdateProductAmount(selectedProduct, quantity);
                    updateProductQuantity.execute();

                    logger.info(quantity + " darab " + selectedProduct.getName() + " hozzáadva a kosárhoz.");
                } else {
                    showAlert.setMessage("Érvénytelen mennyiség.");
                    showAlert.execute();
                    logger.warn("Érvénytelen mennyiség megadva a következő termékhez: " + selectedProduct.getName());
                }
            } catch (NumberFormatException e) {
                showAlert.setMessage("Kérem, adjon meg érvényes számot a mennyiséghez.");
                showAlert.execute();
                logger.error("Érvénytelen mennyiség formátum megadva a következő termékhez: " + selectedProduct.getName());
            }
        } else {
            showAlert.setMessage("Kérem, válasszon ki egy terméket.");
            showAlert.execute();
            logger.warn("Nincs termék kiválasztva.");
        }
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
