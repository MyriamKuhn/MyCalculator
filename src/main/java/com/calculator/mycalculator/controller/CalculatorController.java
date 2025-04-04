package com.calculator.mycalculator.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Calculator application.
 * This class manages the logic and user interaction for the calculator's user interface.
 * It handles button clicks, performs the calculations, and updates the display.
 */
public class CalculatorController implements Initializable {

    /**
     * UI Elements.
     * These are the FXML-injected UI components that represent the calculator's user interface.
     */
    @FXML
    private TextField viewer; // TextField that displays the current input or result.

    @FXML
    private Label formula; // Label that displays the current formula being entered by the user.

    @FXML
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero, btnDelete, btnPlusMinus, btnPoint, btnReset, btnPlus, btnMinus, btnMultiply, btnDivide, btnPi, btnSqrt, btnSquare, btnInverse, btnExponent, btnEqual; // Buttons for calculator operations.

    /**
     * Stores the current formula being built by the user.
     * This is updated as the user interacts with the calculator's buttons.
     */
    private final StringBuilder currentFormula = new StringBuilder();

    /**
     * Maximum number of digits allowed for the formula input.
     * If this limit is exceeded, the user will be notified with an alert.
     */
    private static final int MAX_DIGITS = 22;

    /**
     * Initializes the event handlers for all buttons in the calculator UI.
     * This method sets up the actions triggered when the user clicks on the calculator's buttons.
     * Each button is associated with a specific handler method.
     *
     * @param location the location used to resolve relative paths for the root object, or null if the location is not used
     * @param resources the resources used to localize the root object, or null if the resources are not used
     */
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
        btnMultiply.setOnAction(event -> handleOperatorButtonClick("*"));
        btnDivide.setOnAction(event -> handleOperatorButtonClick("/"));
        btnPi.setOnAction(event -> handlePiButtonClick());
        btnSqrt.setOnAction(event -> handleSqrtButtonClick());
        btnSquare.setOnAction(event -> handleSquareButtonClick());
        btnInverse.setOnAction(event -> handleInverseButtonClick());
        btnExponent.setOnAction(event -> handleExponentButtonClick());
        btnEqual.setOnAction(event -> handleEqualButtonClick());
    }

    /**
     * Handles the button click event, updating the displayed text and formula.
     * <p>
     * This method is triggered when a button on the calculator is clicked. It appends
     * the clicked value to the current displayed text and updates the formula being built.
     * If the formula is empty and the clicked value is "0", the method does nothing. If the
     * current displayed text reaches the maximum number of digits (MAX_DIGITS), an alert is shown.
     *
     * @param value The value of the button that was clicked (typically a number or operator).
     */
    private void handleButtonClick(String value) {
        String currentText = viewer.getText();

        // If the current formula is empty and the value is "0", do nothing
        if (currentFormula.isEmpty() && value.equals("0")) {
            return;
        }

        // Only append the value if the current text has not exceeded MAX_DIGITS
        if (currentText.length() < MAX_DIGITS) {
            // If the current text is "0", replace it with the new value
            if (currentText.equals("0")) {
                viewer.setText(value);
            } else {
                viewer.appendText(value);
            }

            // Append the clicked value to the formula
            currentFormula.append(value);
            formula.setText(currentFormula.toString());
        } else {
            // Show an alert if the maximum number of digits is exceeded
            showAlert();
        }
    }

    /**
     * Handles the delete button click event, updating the displayed text and formula.
     * <p>
     * This method is triggered when the delete button on the calculator is clicked.
     * It removes the last character from the displayed text and the formula. Special
     * cases are handled when the text ends with a decimal point or when the text is
     * a single character or negative single character. If the text becomes "0", it
     * resets the displayed text and the formula appropriately.
     */
    private void handleDeleteButtonClick() {
        String currentText = viewer.getText();

        // Only proceed if the current text is not "0"
        if (!currentText.equals("0")) {
            if (currentText.endsWith(".")) {
                String newText = currentText.substring(0, currentText.length() - 1);
                viewer.setText(newText);
                currentFormula.deleteCharAt(currentFormula.length() - 1);

                // If the result is "-0" or "0", reset the display and formula
                if (newText.equals("-0") || newText.equals("0")) {
                    viewer.setText("0");
                    if (currentFormula.length() > 2) {
                        currentFormula.deleteCharAt(currentFormula.length() - 1);
                    } else {
                        currentFormula.setLength(0);
                    }
                }
            } else {
                // Handle the case where the current text has one or two characters
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
                    // Otherwise, just remove the last character
                    viewer.setText(currentText.substring(0, currentText.length() - 1));
                    currentFormula.deleteCharAt(currentFormula.length() - 1);
                }
            }
            // Update the formula display
            formula.setText(currentFormula.toString());
        }
    }

    /**
     * Handles the plus-minus button click event, toggling the sign of the displayed number.
     * <p>
     * This method is triggered when the plus-minus button (for switching between positive
     * and negative) is clicked. If the current text displayed is not "0", the sign of the
     * number is toggled: if it's positive, a minus sign is added at the beginning; if it's
     * negative, the minus sign is removed. The formula is updated accordingly.
     */
    private void handlePlusMinusButtonClick() {
        String currentText = viewer.getText();

        // Only proceed if the current text is not "0"
        if (!currentText.equals("0")) {

            // If the current text starts with a minus sign, remove it
            if (currentText.startsWith("-")) {
                viewer.setText(currentText.substring(1));
                currentFormula.deleteCharAt(currentFormula.length() - currentText.length());
            } else {
                // Otherwise, add a minus sign at the beginning
                viewer.setText("-" + currentText);
                currentFormula.insert(currentFormula.length() - currentText.length(), "-");
            }
        }
        // Update the formula display
        formula.setText(currentFormula.toString());
    }

    /**
     * Handles the decimal point button click event, adding a decimal point to the displayed number.
     * <p>
     * This method is triggered when the decimal point button is clicked. If the current number
     * does not already contain a decimal point, it adds one to the displayed text. If the current
     * displayed number is "0", it will set the display to "0." and update the formula accordingly.
     */
    private void handlePointButtonClick() {
        String currentText = viewer.getText();

        // Only add a decimal point if one is not already present in the current text
        if (!currentText.contains(".")) {

            // If the current text is "0", set it to "0."
            if (viewer.getText().equals("0")) {
                viewer.setText("0.");
                currentFormula.append("0.");
            } else {
                // Otherwise, just append the decimal point
                viewer.appendText(".");
                currentFormula.append(".");
            }
        }
        // Update the formula display
        formula.setText(currentFormula.toString());
    }

    /**
     * Resets the calculator's display and the current formula.
     * <p>
     * This method is triggered when the reset button is clicked. It resets the displayed value to "0"
     * and clears the current formula. The formula display is also updated to reflect the empty formula.
     */
    private void handleResetButtonClick() {
        // Reset the displayed value to "0"
        viewer.setText("0");

        // Clear the current formula
        currentFormula.setLength(0);

        // Update the formula display
        formula.setText(currentFormula.toString());
    }

    /**
     * Handles the button click for an operator in the calculator.
     * <p>
     * This method is called when an operator button (e.g., +, -, *, /) is clicked. It updates the
     * current formula by appending the operator and resets the displayed value to "0".
     * If the previous formula ends with an operator, it removes the last operator before appending the new one.
     *
     * @param operator The operator to be added to the formula (e.g., "+", "-", "*", "/").
     */
    private void handleOperatorButtonClick(String operator) {
        // Get the current text from the viewer (display)
        String currentText = viewer.getText();

        // If the current text is not empty, process the operator
        if (!currentText.isEmpty()) {

            // If the previous operator exists, remove it and the number before it
            if (currentFormula.length() > 2 && isOperator(currentFormula.charAt(currentFormula.length() - 2))) {
                currentFormula.delete(currentFormula.length() - 3, currentFormula.length());
            }

            // Append the operator to the formula with surrounding spaces for readability
            currentFormula.append(" ").append(operator).append(" ");

            // Update the formula display
            formula.setText(currentFormula.toString());

            // Reset the displayed value to "0"
            viewer.setText("0");
        }
    }

    /**
     * Handles the button click for the Pi (π) button in the calculator.
     * <p>
     * This method is called when the Pi button is clicked. It adds the value of Pi to the display and
     * appends it to the current formula. If the display is currently showing "0", it will replace it with Pi.
     */
    private void handlePiButtonClick() {
        // Get the string representation of Pi
        String pi = String.valueOf(Math.PI);

        // If the current display is "0", replace it with Pi
        if (viewer.getText().equals("0")) {
            viewer.setText(pi);
            currentFormula.append(pi);
            formula.setText(currentFormula.toString());
        }
    }

    /**
     * Handles the button click for the square root (√) operation in the calculator.
     * <p>
     * This method is called when the square root button is clicked. It calculates the square root of the
     * current value on the display. If the value is negative, the display is reset to "0". The square root
     * result is then displayed and added to the current formula.
     */
    private void handleSqrtButtonClick() {
        // Get the current text from the display
        String currentText = viewer.getText();

        // Parse the current text to a double
        double value = Double.parseDouble(currentText);

        // Check if the value is negative
        if (value < 0) {
            // Reset display to 0 and remove the last value in the formula
            viewer.setText("0");
            int deleteStart = Math.max(0, currentFormula.length() - currentText.length());
            currentFormula.delete(deleteStart, currentFormula.length());
        } else {
            // Calculate the square root and format the result
            String newText = formatNumber(Math.sqrt(value));

            // If the current display is not "0", update the display and the formula
            if (!currentText.equals("0")) {
                viewer.setText(newText);
                int deleteStart = Math.max(0, currentFormula.length() - currentText.length());
                currentFormula.delete(deleteStart, currentFormula.length());
                currentFormula.append(newText);
            }
        }
        // Update the formula display
        formula.setText(currentFormula.toString());
    }

    /**
     * Handles the button click for the square (x²) operation in the calculator.
     * <p>
     * This method is called when the square button is clicked. It calculates the square of the
     * current value displayed on the calculator. The result is then displayed and added to the current formula.
     */
    private void handleSquareButtonClick() {
        // Get the current text from the display
        String currentText = viewer.getText();

        // Parse the current text to a double
        double value = Double.parseDouble(currentText);

        // Calculate the square of the value and format the result
        String newText = formatNumber(value * value);

        // Update the display with the new squared value
        viewer.setText(newText);

        // Update the formula and the display
        updateFormula(currentFormula, currentText, newText);
    }

    /**
     * Handles the button click for the inverse (1/x) operation in the calculator.
     * <p>
     * This method is called when the inverse button is clicked. It calculates the inverse (1 divided by the current value)
     * of the number displayed on the calculator. The result is then displayed and added to the current formula.
     */
    private void handleInverseButtonClick() {
        // Get the current text from the display
        String currentText = viewer.getText();

        // Check if the current value is not 0 to avoid division by zero
        if (!currentText.equals("0")) {
            // Parse the current text to a double
            double value = Double.parseDouble(currentText);

            // Calculate the inverse of the value and format the result
            String newText = formatNumber(1 / value);

            // Update the display with the inverse result
            viewer.setText(newText);

            // Update the formula and the display
            updateFormula(currentFormula, currentText, newText);
        }
    }

    /**
     * Handles the button click for the exponentiation operation (10^x) in the calculator.
     * <p>
     * This method is called when the exponent button (10^x) is clicked. It calculates the exponentiation of 10 raised
     * to the power of the current value displayed on the calculator. The result is then displayed and added to the current formula.
     */
    private void handleExponentButtonClick() {
        // Get the current text from the display
        String currentText = viewer.getText();

        // Check if the current value is not 0 to avoid invalid operations
        if (!currentText.equals("0")) {
            // Parse the current text to a double
            double value = Double.parseDouble(currentText);

            // Calculate 10 raised to the power of the current value and format the result
            String newText = formatNumber(Math.pow(10, value));

            // Update the display with the calculated result
            viewer.setText(newText);

            // Update the formula and the display
            updateFormula(currentFormula, currentText, newText);
        }
    }

    /**
     * Handles the button click for the equal (=) operation in the calculator.
     * <p>
     * This method is called when the equal button is clicked. It attempts to evaluate the mathematical expression
     * represented by the current formula, and displays the result. If the formula is invalid, an error message is shown.
     */
    private void handleEqualButtonClick() {
        // Get the current formula text and trim any leading or trailing whitespace
        String formulaText = currentFormula.toString().trim();

        // Proceed if the formula is not empty
        if (!formulaText.isEmpty()) {
            try {
                // Build the expression and evaluate the result
                Expression expression = new ExpressionBuilder(formulaText).build();
                double result = expression.evaluate();

                // Format and display the result on the viewer
                String newText = formatNumber(result);
                viewer.setText(newText);

                // Clear the current formula and update it with the result
                currentFormula.setLength(0);
                currentFormula.append(newText);

                // Update the formula display with the result
                formula.setText(currentFormula.toString());
            } catch (Exception e) {
                // In case of an error, display "Erreur" and clear the formula
                viewer.setText("Erreur");
                currentFormula.setLength(0);
                formula.setText("");
            }
        }
    }

    /**
     * Displays an alert when the maximum number of digits is reached in the calculator input.
     * <p>
     * This method is called when the user tries to input more digits than the allowed maximum. It shows a warning alert
     * informing the user that they have reached the digit limit and cannot enter more.
     */
    private void showAlert() {
        // Create a warning alert with a message and an OK button
        Alert alert = new Alert(Alert.AlertType.WARNING, "Limite de chiffres atteinte", ButtonType.OK);

        // Set the title of the alert window
        alert.setTitle("Alerte");

        // Set the header text, which will inform the user about the digit limit
        alert.setHeaderText("Vous ne pouvez pas entrer plus de " + MAX_DIGITS + " chiffres.");

        // Display the alert and wait for the user to click "OK"
        alert.showAndWait();
    }

    /**
     * Checks if the given character is a valid operator (+, -, *, /).
     * <p>
     * This method is used to verify if the provided character represents a mathematical operator.
     * It returns true if the character is one of the valid operators: +, -, *, or /.
     *
     * @param operator the character to check
     * @return true if the character is a valid operator, false otherwise
     */
    private boolean isOperator(char operator) {
        return operator == '+' || operator == '-' || operator == '*' || operator == '/';
    }

    /**
     * Formats a number as a string, removing the decimal point if the number is an integer.
     * <p>
     * This method checks if the provided number is an integer by comparing it to its casted integer
     * value. If the number is an integer, it is returned as an integer string (without decimals).
     * If the number has a decimal part, it is returned as a floating-point string.
     *
     * @param number the number to format
     * @return a string representing the formatted number
     */
    private @NotNull String formatNumber(double number) {
        if (number == (int) number) {
            return String.valueOf((int) number);
        } else {
            return String.valueOf(number);
        }
    }

    /**
     * Updates the formula by removing the previous value and appending the new result.
     * This method also updates the formula display.
     *
     * @param currentFormula the StringBuilder holding the current formula
     * @param currentText the previous text (value to be replaced in the formula)
     * @param newText the new value to append to the formula
     */
    private void updateFormula(@NotNull StringBuilder currentFormula, @NotNull String currentText, String newText) {
        // Remove the previous value from the formula and append the new result
        int deleteStart = Math.max(0, currentFormula.length() - currentText.length());
        currentFormula.delete(deleteStart, currentFormula.length());
        currentFormula.append(newText);

        // Update the formula display
        formula.setText(currentFormula.toString());
    }
}
