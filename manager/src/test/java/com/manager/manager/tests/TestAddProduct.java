package com.manager.manager.tests;

import com.manager.manager.admincommands.ClearInputFields;
import com.manager.manager.admincommands.SaveData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import com.manager.manager.Products.Product;
import com.manager.manager.admincommands.AddProduct;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;

public class TestAddProduct extends ApplicationTest {

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    @Mock
    private SaveData mockSaveData;
    @Mock
    private ClearInputFields mockClear;

    @InjectMocks
    private AddProduct addProduct;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productNameField = new TextField();
        priceField = new TextField();
        quantityField = new TextField();
        productList = new ListView<>();
        products = FXCollections.observableArrayList();
        addProduct = new AddProduct(productNameField, priceField, quantityField, productList, products);
        addProduct.newSaveData = mockSaveData;
        addProduct.newClear = mockClear;
    }

    @Test
    public void testExecute_AddsProduct_WhenFieldsAreValid() {
        // Arrange
        productNameField.setText("TestProduct");
        priceField.setText("19.99");
        quantityField.setText("10");

        // Act
        addProduct.execute();

        // Assert
        Assertions.assertEquals(1, products.size());
        Product addedProduct = products.get(0);
        Assertions.assertEquals("TestProduct", addedProduct.getName());
        Assertions.assertEquals(19, addedProduct.getPrice());
        Assertions.assertEquals(10, addedProduct.getQuantity());
        verify(mockSaveData).execute();
        verify(mockClear).clearInputFields();
    }

    @Test
    public void testExecute_DoesNotAddProduct_WhenFieldsAreInvalid() {
        // Arrange
        productNameField.setText("");
        priceField.setText("invalidPrice");
        quantityField.setText("invalidQuantity");

        // Act
        addProduct.execute();

        // Assert
        Assertions.assertEquals(0, products.size());
        verify(mockSaveData, never()).execute();
        verify(mockClear, never()).clearInputFields();
    }
}
