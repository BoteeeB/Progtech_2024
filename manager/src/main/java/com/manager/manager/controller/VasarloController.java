package com.manager.manager.controller;

import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import com.manager.manager.customercommands.*;
import com.manager.manager.user.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class VasarloController extends databaseConnection {

    @FXML
    private ListView<Product> productList;

    @FXML
    private TextField quantityField;

    @FXML
    private Label totalPriceLabel;
    @FXML
    private Label userBalanceLabel;

    private ObservableList<Product> products;

    private ObservableList<Product> cart;

    public VasarloController() {
        this.products = FXCollections.observableArrayList();
        this.cart = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        productList.setItems(products);
        loadData();
        updateTotalPrice();
        updateUserBalance();
    }

    @FXML
    private void handleAddToCart() {
        AddToCart newCart = new AddToCart(products,productList,quantityField,cart,totalPriceLabel,userBalanceLabel);
        newCart.execute();
    }

    @FXML
    private void handleCheckout() {
        int totalPrice = cart.stream().mapToInt(Product::getPrice).sum();
        int userMoney = getUserMoney(); // Felhasználó egyenlegének lekérése az adatbázisból
        if (userMoney >= totalPrice) {
            int newBalance = userMoney - totalPrice;
            updateUserMoney(newBalance); // Felhasználó egyenlegének frissítése az adatbázisban
            cart.clear(); // Kosár ürítése
            // Termék mennyiségének frissítése az adatbázisban
            cart.forEach(product -> updateProductQuantity(product, -1));
            products.clear(); // Termékek listájának frissítése a felhasználói felületen
            loadData();
            updateTotalPrice();
            showAlert("Total Price: $" + totalPrice);
        } else {
            showAlert("Insufficient funds!");
        }
    }

    private int getUserMoney() {
        GetUserMoney newmoney = new GetUserMoney();
        return newmoney.returnExecute();
    }


    private void updateUserMoney(int newBalance) {
        UpdateUserMoney newMoney = new UpdateUserMoney(newBalance);
        newMoney.execute();
    }

    private void updateUserBalance() {
        int userMoney = getUserMoney();
        userBalanceLabel.setText("Egyenleg: " + userMoney + " Ft");
    }



    private void updateProductQuantity(Product product, int quantityChange) {
        UpdateProductAmount newProductQuantity = new UpdateProductAmount(product, quantityChange);
        newProductQuantity.execute();
    }



    private void loadData() {
        LoadProducts newProducts = new LoadProducts(products);
        newProducts.execute();
    }


    private void updateTotalPrice() {
        UpdateTotalPrice newTotal = new UpdateTotalPrice(cart,totalPriceLabel,userBalanceLabel);
        newTotal.execute();
    }


    private void showAlert(String message) {
        ShowAlert newAlert = new ShowAlert(message);
        newAlert.execute();
    }
}
