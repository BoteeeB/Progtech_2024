package com.manager.manager.Products;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int price;
    private int type;

    private int quantity;

    public Product(String name, int price, int quantity, int type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this. type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + price+ " Ft" + " - " + quantity + " db" + " - " + getTypeString(type);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    private String getTypeString(int typeID){
        switch (typeID){
            case 1:
                return "elektronikai";
            case 2:
                return "háztartási";
            case 3:
                return "higéniai";
            default:
                return "unknown";
        }
    }

}
