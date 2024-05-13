package com.manager.manager;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String password;

    private int money;

    public User(String name, String password, int money) {
        this.name = name;
        this.password = password;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(double price) {
        this.password = password;
    }

    @Override
    public String toString() {
        return name + " - $" + password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
