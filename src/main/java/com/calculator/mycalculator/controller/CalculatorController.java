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
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnDelete, btnPlusMinus, btnPoint, btnReset, btnPlus, btnMinus, btnMultiply, btnDivide, btnPi;

    private final StringBuilder currentFormula = new StringBuilder();
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
        btnDelete.setOnAction(event -> handleDeleteButtonClick());
        btnPlusMinus.setOnAction(event -> handlePlusMinusButtonClick());
        btnPoint.setOnAction(event -> handlePointButtonClick());
        btnReset.setOnAction(event -> handleResetButtonClick());
        btnPlus.setOnAction(event -> handleOperatorButtonClick("+"));
        btnMinus.setOnAction(event -> handleOperatorButtonClick("-"));
        btnMultiply.setOnAction(event -> handleOperatorButtonClick("x"));
        btnDivide.setOnAction(event -> handleOperatorButtonClick("÷"));
        btnPi.setOnAction(event -> handlePiButtonClick());
    }

    private void handleButtonClick(String value) {
        String currentText = viewer.getText();
        if (currentFormula.isEmpty() && value.equals("0")) {
            return;
        }
        if (currentText.length() < MAX_DIGITS) {
            if (currentText.equals("0")) {
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

    private void handleDeleteButtonClick() {
        String currentText = viewer.getText();
        if (!currentText.equals("0")) {
            if (currentText.endsWith(".")) {
                String newText = currentText.substring(0, currentText.length() - 1);
                viewer.setText(newText);
                currentFormula.deleteCharAt(currentFormula.length() - 1);
                if (newText.equals("-0") || newText.equals("0")) {
                    viewer.setText("0");
                    if (currentFormula.length() > 2) {
                        currentFormula.deleteCharAt(currentFormula.length() - 1);
                    } else {
                        currentFormula.setLength(0);
                    }
                }
            } else {
                if (currentText.length() == 1 || (currentText.length() == 2 && currentText.contains("-"))) {
                    viewer.setText("0");
                    if (currentFormula.length() > 2 && currentText.length() == 2) {
                        currentFormula.delete(currentFormula.length() - 2, currentFormula.length());
                    } else if (currentFormula.length() > 1 && currentText.length() == 1) {
                        currentFormula.deleteCharAt(currentFormula.length() - 1);
                    } else {
                        currentFormula.setLength(0);
                    }
                } else {
                    viewer.setText(currentText.substring(0, currentText.length() - 1));
                    currentFormula.deleteCharAt(currentFormula.length() - 1);
                }
            }
            formula.setText(currentFormula.toString());
        }
    }

    private void handlePlusMinusButtonClick() {
        String currentText = viewer.getText();
        if (!currentText.equals("0")) {
            if (currentText.startsWith("-")) {
                viewer.setText(currentText.substring(1));
                currentFormula.deleteCharAt(currentFormula.length() - currentText.length());
            } else {
                viewer.setText("-" + currentText);
                currentFormula.insert(currentFormula.length() - currentText.length(), "-");
            }
        }
        formula.setText(currentFormula.toString());
    }

    private void handlePointButtonClick() {
        String currentText = viewer.getText();
        if (!currentText.contains(".")) {
            if (viewer.getText().equals("0")) {
                viewer.setText("0.");
                currentFormula.append("0.");
            } else {
                viewer.appendText(".");
                currentFormula.append(".");
            }
        }
        formula.setText(currentFormula.toString());
    }

    private void handleResetButtonClick() {
        viewer.setText("0");
        currentFormula.setLength(0);
        formula.setText(currentFormula.toString());
    }

    private void handleOperatorButtonClick(String operator) {
        String currentText = viewer.getText();
        if (!currentText.isEmpty()) {
            if (currentFormula.length() > 2 && isOperator(currentFormula.charAt(currentFormula.length() - 2))) {
                currentFormula.delete(currentFormula.length() - 3, currentFormula.length());
            }
            currentFormula.append(" ").append(operator).append(" ");
            formula.setText(currentFormula.toString());
            viewer.setText("0");
        }
    }

    private void handlePiButtonClick() {
        String pi = String.valueOf(Math.PI);
        if (viewer.getText().equals("0")) {
            viewer.setText(pi);
            currentFormula.append(pi);
            formula.setText(currentFormula.toString());
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Limite de chiffres atteinte", ButtonType.OK);
        alert.setTitle("Alerte");
        alert.setHeaderText("Vous ne pouvez pas entrer plus de " + MAX_DIGITS + " chiffres.");
        alert.showAndWait();
    }

    private boolean isOperator(char operator) {
        return operator == '+' || operator == '-' || operator == 'x' || operator == '÷';
    }
}
