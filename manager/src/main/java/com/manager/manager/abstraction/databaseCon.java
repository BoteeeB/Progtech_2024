package com.manager.manager.abstraction;

public class databaseCon extends databaseConnection{
    public String getUrl() {
        return URL;
    }

    public String getUsername() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }
}
