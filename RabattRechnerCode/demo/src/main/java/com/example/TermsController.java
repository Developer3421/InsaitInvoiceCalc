package com.example;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Controller for the separate window with the terms of use.
 * The window is built from the FXML file /fxml/terms.fxml.
 */
public class TermsController {

    /** Location of the FXML file with the terms of use inside the package. */
    private static final String FXML_PATH = "/fxml/terms.fxml";

    /** Stylesheet that gives the window the same look as the main window. */
    private static final String STYLESHEET = "/styles/Styles.css";

    /** Title of the terms window, identical to the title of the main window. */
    private static final String APP_TITLE = "Insait Invoice Calc";

    /** Width of the terms window. */
    private static final double WINDOW_WIDTH = 780;

    /** Height of the terms window. */
    private static final double WINDOW_HEIGHT = 680;

    /** Distance between the mouse and the window frame when the dragging starts. */
    private double dragOffsetX;

    /** Distance between the mouse and the window frame when the dragging starts. */
    private double dragOffsetY;

    /** Root of the view, needed to move the window. */
    @FXML
    private VBox rootPane;

    /** Button "Understood" that closes the window. */
    @FXML
    private Button btnClose;

    /**
     * Opens the terms of use in their own modal window.
     * The window blocks the main window until it is closed.
     *
     * @param owner the window the terms belong to
     */
    public static void showTerms(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader(TermsController.class.getResource(FXML_PATH));
            Parent root = loader.load();

            Stage termsStage = new Stage(StageStyle.UNDECORATED);
            termsStage.setTitle(APP_TITLE);
            if (owner != null) {
                termsStage.initOwner(owner);
            }
            termsStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.getStylesheets().add(TermsController.class.getResource(STYLESHEET).toExternalForm());
            termsStage.setScene(scene);
            termsStage.show();
        } catch (IOException | NullPointerException e) {
            System.err.println("The terms of use could not be loaded: " + e.getMessage());
        }
    }

    /**
     * Remembers the position of the mouse relative to the window when the
     * title bar is clicked, so that the window can be moved afterwards.
     *
     * @param event the mouse event of the click
     */
    @FXML
    private void handleWindowPressed(javafx.scene.input.MouseEvent event) {
        Stage stage = getStage();
        if (stage == null) {
            return;
        }
        dragOffsetX = event.getScreenX() - stage.getX();
        dragOffsetY = event.getScreenY() - stage.getY();
    }

    /**
     * Moves the window while the title bar is dragged by exactly the
     * distance the mouse has travelled.
     *
     * @param event the mouse event of the drag
     */
    @FXML
    private void handleWindowDragged(javafx.scene.input.MouseEvent event) {
        Stage stage = getStage();
        if (stage == null) {
            return;
        }
        stage.setX(event.getScreenX() - dragOffsetX);
        stage.setY(event.getScreenY() - dragOffsetY);
    }

    /** Minimizes the window with the terms of use. */
    @FXML
    private void minimizeWindow() {
        Stage stage = getStage();
        if (stage != null) {
            stage.setIconified(true);
        }
    }

    /** Closes the window with the terms of use. */
    @FXML
    private void closeWindow() {
        Stage stage = getStage();
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Returns the window in which the terms of use are displayed.
     *
     * @return the window of the terms of use or {@code null} if it is not shown yet
     */
    private Stage getStage() {
        if (btnClose == null || btnClose.getScene() == null) {
            return null;
        }
        return (Stage) btnClose.getScene().getWindow();
    }
}
