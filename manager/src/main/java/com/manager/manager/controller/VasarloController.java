package com.manager.manager.controller;

import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import com.manager.manager.customercommands.*;
import com.manager.manager.user.UserSession;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class VasarloController extends databaseConnection {

    private static final Logger logger = LogManager.getLogger(VasarloController.class);

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
        logger.info("A kosárba helyezés sikeres.");
    }

    @FXML
    private void handleCheckout() {
        int totalPrice = cart.stream().mapToInt(Product::getPrice).sum();
        int userMoney = getUserMoney();
        if (userMoney >= totalPrice) {
            int newBalance = userMoney - totalPrice;
            updateUserMoney(newBalance);
            cart.clear();
            cart.forEach(product -> updateProductQuantity(product, -1));
            products.clear();
            loadData();
            updateTotalPrice();
            showAlert("Total Price: $" + totalPrice);
            logger.info("Sikeres vásárlás. Összesen fizetendő: " + totalPrice + " Ft");
        } else {
            showAlert("Insufficient funds!");
            logger.warn("Nincs elegendő egyenleg a vásárláshoz.");
        }
    }

    private int getUserMoney() {
        GetUserMoney newmoney = new GetUserMoney();
        return newmoney.returnExecute();
    }


    private void updateUserMoney(int newBalance) {
        UpdateUserMoney newMoney = new UpdateUserMoney(newBalance);
        newMoney.execute();
        logger.info("Felhasználó egyenlegének frissítése. Új egyenleg: " + newBalance + " Ft");
    }

    private void updateUserBalance() {
        int userMoney = getUserMoney();
        userBalanceLabel.setText("Balance: " + userMoney + " Ft");
    }



    private void updateProductQuantity(Product product, int quantityChange) {
        UpdateProductAmount newProductQuantity = new UpdateProductAmount(product, quantityChange);
        newProductQuantity.execute();
        logger.info(product.getName() + " termék mennyiségének frissítése. Mennyiség változás: " + quantityChange);
    }



    private void loadData() {
        LoadProducts newProducts = new LoadProducts(products);
        newProducts.execute();
        logger.info("Termékek betöltése.");
    }


    private void updateTotalPrice() {
        UpdateTotalPrice newTotal = new UpdateTotalPrice(cart,totalPriceLabel,userBalanceLabel);
        newTotal.execute();
        logger.info("Teljes vásárlási összeg frissítése.");
    }


    private void showAlert(String message) {
        ShowAlert newAlert = new ShowAlert(message);
        newAlert.execute();
        logger.warn("Figyelmeztetés: " + message);
    }
}
