package com.calculator.mycalculator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private TextField viewer;

    @FXML
    private Label formula;

    @FXML
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;

    private StringBuilder currentFormula = new StringBuilder();
    private static final int MAX_DIGITS = 22;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnOne.setOnAction(event -> handleButtonClick("1"));
        btnTwo.setOnAction(event -> handleButtonClick("2"));
        btnThree.setOnAction(event -> handleButtonClick("3"));
        btnFour.setOnAction(event -> handleButtonClick("4"));
        btnFive.setOnAction(event -> handleButtonClick("5"));
        btnSix.setOnAction(event -> handleButtonClick("6"));
        btnSeven.setOnAction(event -> handleButtonClick("7"));
        btnEight.setOnAction(event -> handleButtonClick("8"));
        btnNine.setOnAction(event -> handleButtonClick("9"));
        btnZero.setOnAction(event -> handleButtonClick("0"));
    }

    private void handleButtonClick(String value) {
        if (currentFormula.isEmpty() && value.equals("0")) {
            return;
        }

        if (currentFormula.length() < MAX_DIGITS) {
            if (viewer.getText().equals("0")) {
                viewer.setText(value);
            } else {
                viewer.appendText(value);
            }

            currentFormula.append(value);
            formula.setText(currentFormula.toString());
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Limite de chiffres atteinte", ButtonType.OK);
        alert.setTitle("Alerte");
        alert.setHeaderText("Vous ne pouvez pas entrer plus de " + MAX_DIGITS + " chiffres.");
        alert.showAndWait();
    }
}
