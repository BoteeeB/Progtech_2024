package com.manager.manager.tests;

import com.manager.manager.Products.Product;
import com.manager.manager.customercommands.AddToCart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAddToCartInvalidQuantity extends ApplicationTest {

    private AddToCart addToCart;
    private ObservableList<Product> products;
    private ListView<Product> productList;
    private TextField quantityField;
    private ObservableList<Product> cart;
    private Label totalPriceLabel;
    private Label userBalanceLabel;

    @BeforeEach
    public void setUp() {
        products = FXCollections.observableArrayList();
        productList = new ListView<>(products);
        quantityField = new TextField();
        cart = FXCollections.observableArrayList();
        totalPriceLabel = new Label();
        userBalanceLabel = new Label();

        addToCart = new AddToCart(products, productList, quantityField, cart, totalPriceLabel, userBalanceLabel, true);
    }

    @Test
    public void testAddToCartInvalidQuantity() {
        Product product = new Product("Test Product", 100, 10, 2);
        products.add(product);
        productList.getSelectionModel().select(0);
        quantityField.setText("15");

        addToCart.execute();

        assertEquals(0, cart.size());
    }

}
