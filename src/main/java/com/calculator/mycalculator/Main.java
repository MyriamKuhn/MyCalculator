package com.calculator.mycalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

/**
 * Main class that launches the Calculator application.
 * This class extends Application and initializes the JavaFX UI.
 * It loads the FXML file, applies the styles, and displays the main window.
 */
public class Main extends Application {

    /**
     * The entry point for the JavaFX application.
     * This method is called when the application starts.
     * It loads the FXML layout, applies stylesheets, and sets up the main stage.
     *
     * @param stage The primary stage for this application.
     * @throws Exception If there is an issue loading the FXML or any other error occurs.
     */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Load the FXML layout file for the calculator
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/calculator.fxml"));
            Parent root = loader.load();

            // Set up the scene with stylesheets and the layout
            Scene scene = new Scene(root);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/style.css")).toExternalForm());

            // Set the stage properties (title, scene, etc.)
            stage.setTitle("Ma calculatrice");
            stage.setScene(scene);
            stage.setResizable(false); // Prevent resizing of the window
            stage.show(); // Display the window
        } catch (IOException e) {
            // Show error dialog if FXML file can't be loaded
            showErrorDialog("Erreur de chargement", "Le fichier d'interface est introuvable ou invalide.");
        } catch (NullPointerException e) {
            // Show error dialog if a critical resource is missing
            showErrorDialog("Erreur critique", "Une ressource essentielle est introuvable.");
        } catch (Exception e) {
            // Show error dialog for unexpected errors
            showErrorDialog("Erreur", "Une erreur inattendue s'est produite.");
        }
    }

    /**
     * Displays an error dialog with a given title and content.
     *
     * @param title   The title of the error dialog.
     * @param content The content to display in the error dialog.
     */
    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
