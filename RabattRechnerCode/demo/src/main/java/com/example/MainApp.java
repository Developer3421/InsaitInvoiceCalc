package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Entry point of the application. Opens the main window and loads the
 * user interface from the FXML file "primary.fxml".
 */
public class MainApp extends Application {

    /** Title of the application, also used as the window title. */
    private static final String APP_TITLE = "Insait Invoice Calc";

    /** Stylesheet that gives the window its look. */
    private static final String STYLESHEET = "/styles/Styles.css";

    /** The only main window of the application. */
    private static Stage stage;

    /**
     * Called by JavaFX on program start. Sets up the window without
     * decorations, sets the program icon and shows the user interface
     * from "primary.fxml".
     *
     * @param s the main window provided by JavaFX
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage = s;
        stage.initStyle(StageStyle.UNDECORATED);
        loadIcon();
        setRoot("primary", APP_TITLE);
        stage.setMinWidth(780);
        stage.setMinHeight(600);
        stage.centerOnScreen();
    }

    /**
     * Loads the program icon, trying the PNG first and falling back to the
     * ICO variant. A missing icon is only reported, it does not stop the
     * application.
     */
    private void loadIcon() {
        InputStream stream = MainApp.class.getResourceAsStream("/image/appicon.png");

        if (stream != null) {
            try {
                stage.getIcons().add(new Image(stream));
            } finally {
                try {
                    stream.close();
                } catch (IOException ignore) {
                    // nothing to do, the icon has been read already
                }
            }
            return;
        }

        URL url = MainApp.class.getResource("/image/appicon.ico");

        if (url == null) {
            System.err.println("Warning: app icon resource not found in /image");
            return;
        }

        stage.getIcons().add(new Image(url.toExternalForm()));
    }

    /**
     * Switches the main window to another user interface and keeps the
     * current window title.
     *
     * @param fxml name of the FXML file without the extension, for example "primary"
     * @throws IOException if the FXML file cannot be loaded
     */
    static void setRoot(String fxml) throws IOException {
        setRoot(fxml, stage.getTitle());
    }

    /**
     * Switches the main window to another user interface and sets the
     * window title.
     *
     * @param fxml  name of the FXML file without the extension, for example "primary"
     * @param title the new window title
     * @throws IOException if the FXML file cannot be loaded
     */
    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        scene.getStylesheets().add(Objects.requireNonNull(
                MainApp.class.getResource(STYLESHEET), "Styles.css not found on classpath").toExternalForm());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads an FXML file from the folder /fxml of the program package.
     *
     * @param fxml name of the FXML file without the extension, for example "primary"
     * @return the loaded root of the user interface
     * @throws IOException if the FXML file cannot be found or read
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Starts the JavaFX application. This method runs when the EXE file
     * is double-clicked or when the JAR file is started.
     *
     * @param args command line arguments, which are not used here
     */
    public static void main(String[] args) {
        launch(args);
    }

}
