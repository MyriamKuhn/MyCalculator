package com.calculator.mycalculator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (mainPane == null) {
            System.out.println("mainPane is null!");
        } else {
            System.out.println("mainPane is successfully injected.");
        }
    }
}
