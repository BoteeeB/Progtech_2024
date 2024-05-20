package com.manager.manager.tests;

import com.manager.manager.abstraction.databaseCon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDatabase {

    @Test
    public void testDatabaseConnection() {
        databaseCon connection = new databaseCon();

        assertEquals("jdbc:mysql://localhost:3306/nyilvantartas", connection.getUrl());
        assertEquals("root", connection.getUsername());
        assertEquals("", connection.getPassword());
    }

}