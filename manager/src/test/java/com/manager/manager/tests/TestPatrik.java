package com.manager.manager.tests;

import com.manager.manager.Products.Product;
import com.manager.manager.admincommands.AddProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

public class TestPatrik extends ApplicationTest{
    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    private AddProduct addProduct;

    @Override
    public void start(Stage stage) {
        // This method needs to be overridden, but you can leave it empty if you don't need to use the stage.
    }


    @BeforeEach
    public void Setup(){
        productNameField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        productList = new ListView<>();
        products = FXCollections.observableArrayList();

        addProduct = new AddProduct(productNameField, priceField, quantityField, productList, products);
    }
    @Test
    public void TestAddProduct(){
        productNameField.setText("Test Product");
        priceField.setText("100");
        quantityField.setText("10");

        addProduct.execute();

        assertEquals(1, products.size());
        Product product = products.get(0);
        assertEquals("Test Product", product.getName());
        assertEquals(100, product.getPrice());
        assertEquals(10, product.getQuantity());
    }
}
