package com.manager.manager.controller;

import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
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
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (quantity > 0 && quantity <= selectedProduct.getQuantity()) {
                    for (int i = 0; i < quantity; i++) {
                        cart.add(selectedProduct);
                    }
                    updateTotalPrice();
                    // Frissítjük a termék mennyiségét az adatbázisban
                    updateProductQuantity(selectedProduct, quantity);
                } else {
                    showAlert("Invalid quantity selected!");
                }
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid quantity!");
            }
        } else {
            showAlert("Please select a product!");
        }
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
        int loggedInUserId = UserSession.getLoggedInUserId();
        int money = 0;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT money FROM felhasznalok WHERE id = ?")) {
            statement.setInt(1, loggedInUserId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                money = resultSet.getInt("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return money;
    }


    private void updateUserMoney(int newBalance) {
        int loggedInUserId = UserSession.getLoggedInUserId();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE felhasznalok SET money = ? WHERE id = ?")) {
            statement.setInt(1, newBalance);
            statement.setInt(2, loggedInUserId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserBalance() {
        int userMoney = getUserMoney();
        userBalanceLabel.setText("Egyenleg: " + userMoney + " Ft");
    }



    private void updateProductQuantity(Product product, int quantityChange) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE termekek SET quantity = quantity - ? WHERE name = ?")) {
            statement.setInt(1, Math.abs(quantityChange)); // Negatív érték a levonáshoz
            statement.setString(2, product.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void loadData() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM termekek");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                products.add(new Product(name, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateTotalPrice() {
        int totalPrice = cart.stream().mapToInt(Product::getPrice).sum();
        totalPriceLabel.setText("Végösszeg: " + totalPrice + " Ft");
        int userMoney = getUserMoney();
        userBalanceLabel.setText("Egyenleg: " + userMoney + " Ft");
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
