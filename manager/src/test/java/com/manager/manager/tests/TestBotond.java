package com.manager.manager.tests;
import com.manager.manager.abstraction.databaseCon;
import com.manager.manager.controller.VasarloController;
import com.manager.manager.Products.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestBotond {

    @Test
    public void testDatabaseConnection() {
        databaseCon connection = new databaseCon();

        assertEquals("jdbc:mysql://localhost:3306/nyilvantartas", connection.getUrl());
        assertEquals("root", connection.getUsername());
        assertEquals("", connection.getPassword());
    }
}