package com.manager.manager.tests;

import com.manager.manager.Products.Product;
import com.manager.manager.admincommands.ClearInputFields;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class TestClearFields extends ApplicationTest {

    private TextField mockProductNameField;
    private TextField mockPriceField;
    private TextField mockQuantityField;
    private ListView<Product> mockProductList;

    @Mock
    private ClearInputFields clearInputFields;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockProductNameField = new TextField();
        mockPriceField = new  TextField();
        mockQuantityField = new TextField();
        mockProductList = new ListView();
        clearInputFields = new ClearInputFields(mockProductNameField, mockPriceField, mockQuantityField, mockProductList);
    }

    @Test
    public void testClearInputFields() {
        clearInputFields.clearInputFields();
        assertEquals("", mockProductNameField.getText());
        assertEquals("", mockPriceField.getText());
        assertEquals("", mockQuantityField.getText());
    }
}
