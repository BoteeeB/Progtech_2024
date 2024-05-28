package com.manager.manager.tests;

import com.manager.manager.Products.Product;
import com.manager.manager.customercommands.AddToCart;
import com.manager.manager.customercommands.LoadProducts;
import com.manager.manager.abstraction.databaseCon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestBotond extends ApplicationTest {

    private AddToCart addToCart;
    private ObservableList<Product> products;
    private ListView<Product> productList;
    private TextField quantityField;
    private ObservableList<Product> cart;
    private Label totalPriceLabel;
    private Label userBalanceLabel;

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private LoadProducts loadProducts;

    @BeforeEach
    public void setUp() throws SQLException {
        products = FXCollections.observableArrayList();
        productList = new ListView<>(products);
        quantityField = new TextField();
        cart = FXCollections.observableArrayList();
        totalPriceLabel = new Label();
        userBalanceLabel = new Label();

        addToCart = new AddToCart(products, productList, quantityField, cart, totalPriceLabel, userBalanceLabel, true);

        MockitoAnnotations.initMocks(this);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        loadProducts = new LoadProducts(products);
        loadProducts.setConnection(connection);
    }

    @Test
    public void testAddToCart() {
        Product product = new Product("Test Product", 100, 10, 2);
        products.add(product);
        productList.getSelectionModel().select(0);
        quantityField.setText("5");

        addToCart.execute();

        assertEquals(5, cart.size());
        assertTrue(cart.contains(product));
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

    @Test
    public void testAddToCartNoProductSelected() {
        quantityField.setText("5");

        addToCart.execute();

        assertEquals(0, cart.size());
    }

    @Test
    public void testDatabaseConnection() {
        databaseCon connection = new databaseCon();

        assertEquals("jdbc:mysql://localhost:3306/nyilvantartas", connection.getUrl());
        assertEquals("root", connection.getUsername());
        assertEquals("", connection.getPassword());

        System.out.println("Sikeres adatbázis kapcsolódás!");
    }

    @Test
    public void testLoadProducts() throws SQLException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getString("name")).thenReturn("Product1", "Product2");
        when(resultSet.getInt("price")).thenReturn(100, 200);
        when(resultSet.getInt("quantity")).thenReturn(10, 20);

        loadProducts.execute();

        verify(statement).executeQuery();
        verify(resultSet, times(3)).next();

        assertEquals(2, products.size());
        assertEquals("Product1", products.get(0).getName());
        assertEquals(100, products.get(0).getPrice());
        assertEquals(10, products.get(0).getQuantity());
        assertEquals("Product2", products.get(1).getName());
        assertEquals(200, products.get(1).getPrice());
        assertEquals(20, products.get(1).getQuantity());

        System.out.println("Loaded products:");
        for (Product product : products) {
            System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
        }
    }
}
