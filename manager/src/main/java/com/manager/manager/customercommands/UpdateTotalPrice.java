package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.Products.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UpdateTotalPrice implements CustomerFactory {
    private ObservableList<Product> cart;
    private Label totalPriceLabel;
    private Label userBalanceLabel;

    GetUserMoney getUserMoney = new GetUserMoney();

    public UpdateTotalPrice(ObservableList<Product> cart, Label totalPriceLabel, Label userBalanceLabel){
        this.cart = cart;
        this.totalPriceLabel = totalPriceLabel;
        this.userBalanceLabel = userBalanceLabel;
    }
    @Override
    public void execute() {
        int totalPrice = cart.stream().mapToInt(Product::getPrice).sum();
        totalPriceLabel.setText("Végösszeg: " + totalPrice + " Ft");
        int userMoney = getUserMoney.returnExecute();
        userBalanceLabel.setText("Egyenleg: " + userMoney + " Ft");
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
