package com.manager.manager.tests;

import com.manager.manager.Products.Product;
import com.manager.manager.admincommands.UpdateProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestUpdateProduct extends ApplicationTest {

    @Mock
    private TextField mockProductNameField;

    @Mock
    private TextField mockPriceField;

    @Mock
    private TextField mockQuantityField;

    @Mock
    private ListView<Product> mockProductList;

    private ObservableList<Product> products;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        products = FXCollections.observableArrayList();

        MultipleSelectionModel<Product> mockSelectionModel = mock(MultipleSelectionModel.class);
        when(mockProductList.getSelectionModel()).thenReturn(mockSelectionModel);
    }

    @Test
    public void testExecute_WithValidInput() {

        Product existingProduct = new Product("Existing Product", 10, 5);
        products.add(existingProduct);

        when(mockProductList.getSelectionModel().getSelectedItem()).thenReturn(existingProduct);
        when(mockProductNameField.getText()).thenReturn("Updated Product");
        when(mockPriceField.getText()).thenReturn("20");
        when(mockQuantityField.getText()).thenReturn("8");

        UpdateProduct updateProduct = new UpdateProduct(
                mockProductNameField,
                mockPriceField,
                mockQuantityField,
                mockProductList,
                products
        );

        updateProduct.execute();

        assertEquals("Updated Product", existingProduct.getName());
        assertEquals(20, existingProduct.getPrice());
        assertEquals(8, existingProduct.getQuantity());
    }

    @Test
    public void testExecute_WithEmptyInput() {
        when(mockProductList.getSelectionModel().getSelectedItem()).thenReturn(null);

        UpdateProduct updateProduct = new UpdateProduct(
                mockProductNameField,
                mockPriceField,
                mockQuantityField,
                mockProductList,
                products
        );

        updateProduct.execute();

        assertTrue(products.isEmpty());
    }
}
