package com.manager.manager.customercommands;

import com.manager.manager.Products.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestLoadProducts {
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement statement;
    @Mock
    private ResultSet resultSet;

    private LoadProducts loadProducts;
    private ObservableList<Product> products;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        products = FXCollections.observableArrayList();
        loadProducts = new LoadProducts(products);
        loadProducts.setConnection(connection); // feltételezve, hogy van ilyen metódus az osztályban
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
    }
}
