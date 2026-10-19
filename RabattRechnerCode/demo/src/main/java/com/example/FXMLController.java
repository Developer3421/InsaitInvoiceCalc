package com.example;

import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller of the main view of the final price calculator.
 *
 * <p>The controller reads the four input fields for unit price, quantity,
 * discount and VAT, calls the calculation logic in {@link RabattRechner}
 * and writes the result into the invoice summary. Nothing is calculated
 * on its own: the "Calculate" button and the Enter key start the
 * calculation, a mere change of the input does not.</p>
 */
public class FXMLController {

    /** Format for all amounts: English notation with a comma as thousands separator. */
    private static final Locale LOCALE = Locale.US;

    /** Text shown in the invoice summary as long as nothing has been calculated. */
    private static final String PLACEHOLDER = "–";

    /** Message shown when a text field does not contain a number. */
    private static final String NOT_A_NUMBER = "Please enter numbers only.";

    /** Message shown when the quantity is not a whole number of at least one. */
    private static final String INVALID_QUANTITY = "The quantity must be a whole number of 1 or more.";

    /** Message shown when unit price, discount or VAT are outside the allowed range. */
    private static final String INVALID_INPUT =
        "Unit price and VAT must not be negative; the discount must be between 0 and 100.";

    /** Distance between the mouse and the window frame when the dragging starts. */
    private double dragOffsetX;

    /** Distance between the mouse and the window frame when the dragging starts. */
    private double dragOffsetY;

    /** Values of the last valid input; they are set again on every calculation. */
    private double unitPrice;
    private int quantity = RabattRechner.DEFAULT_QUANTITY;
    private double discountPercent;
    private double vatPercent;

    /** Input field for the net price of a single unit. */
    @FXML
    private TextField txtEinzelpreis;

    /** Input field for the number of units in the batch. */
    @FXML
    private TextField txtMenge;

    /** Input field for the discount in percent. */
    @FXML
    private TextField txtEchterRabatt;

    /** Input field for the VAT in percent. */
    @FXML
    private TextField txtSteuern;

    /** Display of the unit price in the invoice summary. */
    @FXML
    private Label lblEinzelpreisWert;

    /** Display of the quantity in the invoice summary. */
    @FXML
    private Label lblMengeWert;

    /** Display of the subtotal before the discount. */
    @FXML
    private Label lblZwischensumme;

    /** Display of the discount amount with a leading minus sign. */
    @FXML
    private Label lblRabattBetrag;

    /** Display of the subtotal after the discount, which is the tax base. */
    @FXML
    private Label lblBasisNachRabatt;

    /** Display of the tax amount with a leading plus sign. */
    @FXML
    private Label lblSteuerBetrag;

    /** Highlighted display of the final price of the whole batch. */
    @FXML
    private Label lblEndpreis;

    /** Display of the final price of a single unit. */
    @FXML
    private Label lblEndpreisJeStueck;

    /** Row for error messages; invisible as long as there is no error. */
    @FXML
    private Label lblOut;

    /** Status line at the bottom edge of the window. */
    @FXML
    private Label lblStatus;

    /**
     * Called by JavaFX right after the view has been loaded.
     * Sets the default quantity and shows the empty invoice summary.
     * The calculation only starts when the button is pressed.
     */
    @FXML
    private void initialize() {
        txtMenge.setText(String.valueOf(RabattRechner.DEFAULT_QUANTITY));

        showEmptyResults();
        lblStatus.setText("Ready");
    }

    /**
     * Remembers the position of the mouse relative to the window when the
     * title bar is clicked, so that the window can be moved afterwards.
     *
     * @param event the mouse event of the click
     */
    @FXML
    private void handleWindowPressed(MouseEvent event) {
        dragOffsetX = event.getScreenX() - getStage().getX();
        dragOffsetY = event.getScreenY() - getStage().getY();
    }

    /**
     * Moves the window while the title bar is dragged by exactly the
     * distance the mouse has travelled.
     *
     * @param event the mouse event of the drag
     */
    @FXML
    private void handleWindowDragged(MouseEvent event) {
        Stage stage = getStage();
        stage.setX(event.getScreenX() - dragOffsetX);
        stage.setY(event.getScreenY() - dragOffsetY);
    }

    /** Minimizes the main window into the taskbar. */
    @FXML
    private void minimizeWindow() {
        getStage().setIconified(true);
    }

    /** Closes the main window and thereby ends the program. */
    @FXML
    private void closeWindow() {
        getStage().close();
    }

    /** Opens the terms of use in their own window. */
    @FXML
    private void showTerms() {
        TermsController.showTerms(getStage());
    }

    /**
     * Returns the window the controller lives in.
     *
     * @return the main window of the application
     */
    private Stage getStage() {
        return (Stage) lblOut.getScene().getWindow();
    }

    /**
     * Calculates the batch. Only the "Calculate" button and the Enter key
     * trigger this, a change of the input does not.
     */
    @FXML
    private void btnClickAction() {
        update();
    }

    /** Increases the quantity by one. */
    @FXML
    private void mengeErhoehen() {
        setQuantity(quantityOrDefault() + 1);
    }

    /** Decreases the quantity by one, but never below one unit. */
    @FXML
    private void mengeVerringern() {
        setQuantity(Math.max(1, quantityOrDefault() - 1));
    }

    /** Clears all input fields and resets the invoice summary. */
    @FXML
    private void resetAction() {
        txtEinzelpreis.clear();
        txtMenge.setText(String.valueOf(RabattRechner.DEFAULT_QUANTITY));
        txtEchterRabatt.clear();
        txtSteuern.clear();
        showEmptyResults();
        showMessage(null);
        lblStatus.setText("Ready");
    }

    /**
     * Writes a new quantity into the quantity field and places the cursor
     * behind the number.
     *
     * @param quantity the new quantity, at least 1
     */
    private void setQuantity(int quantity) {
        txtMenge.setText(String.valueOf(quantity));
        txtMenge.requestFocus();
        txtMenge.positionCaret(txtMenge.getText().length());
    }

    /**
     * Calculates the batch and writes the result into the invoice summary.
     * Only called by the "Calculate" button and the Enter key.
     */
    private void update() {
        if (isEmpty(txtEinzelpreis)) {
            showEmptyResults();
            showMessage(null);
            lblStatus.setText("Please enter a unit price");
            return;
        }

        String error = readInputs();

        if (error != null) {
            showMessage(error);
            lblStatus.setText("Please check the input");
            return;
        }

        RechnungsErgebnis result = RabattRechner.calculateBatch(
            unitPrice, quantity, discountPercent, vatPercent);

        lblEinzelpreisWert.setText(amount(result.getUnitPrice()));
        lblMengeWert.setText(quantity + (quantity == 1 ? " unit" : " units"));
        lblZwischensumme.setText(amount(result.getOriginalTotal()));
        lblRabattBetrag.setText("– " + amount(result.getDiscountAmount())
            + "  (" + percent(result.getDiscountPercent()) + ")");
        lblBasisNachRabatt.setText(amount(result.getDiscountedTotal()));
        lblSteuerBetrag.setText("+ " + amount(result.getTaxAmount())
            + "  (" + percent(result.getVatPercent()) + ")");
        lblEndpreis.setText(amount(result.getFinalPrice()));
        lblEndpreisJeStueck.setText(amount(result.getFinalPricePerUnit()));

        showMessage(null);
        lblStatus.setText(String.format(LOCALE,
            "Batch of %d units calculated - final price %s", quantity, amount(result.getFinalPrice())));
    }

    /**
     * Reads the four input fields into {@link #unitPrice},
     * {@link #quantity}, {@link #discountPercent} and {@link #vatPercent}
     * and validates them. An empty discount or tax field counts as
     * 0 percent, an empty quantity field as one unit.
     *
     * @return {@code null} if all inputs are valid, otherwise the message for the user
     */
    private String readInputs() {
        String priceText = txtEinzelpreis.getText().trim();
        String quantityText = txtMenge.getText().trim();
        String discountText = txtEchterRabatt.getText().trim();
        String taxText = txtSteuern.getText().trim();

        if (!isNumber(priceText) || !isNumber(discountText) || !isNumber(taxText)) {
            return NOT_A_NUMBER;
        }

        unitPrice = toDouble(priceText);
        discountPercent = discountText.isEmpty() ? 0 : toDouble(discountText);
        vatPercent = taxText.isEmpty() ? 0 : toDouble(taxText);

        if (quantityText.isEmpty()) {
            quantity = RabattRechner.DEFAULT_QUANTITY;
        } else if (isWholeNumber(quantityText)) {
            quantity = Integer.parseInt(quantityText);
        } else {
            return INVALID_QUANTITY;
        }

        if (quantity < 1) {
            return INVALID_QUANTITY;
        }
        if (unitPrice < 0 || vatPercent < 0 || discountPercent < 0 || discountPercent > 100) {
            return INVALID_INPUT;
        }

        return null;
    }

    /**
     * Shows an error message or hides the error row.
     *
     * @param text the message to show, {@code null} hides the row
     */
    private void showMessage(String text) {
        boolean present = text != null && !text.isEmpty();
        lblOut.setText(present ? text : "");
        lblOut.setVisible(present);
        lblOut.setManaged(present);
    }

    /** Resets all result rows of the invoice summary to the placeholder. */
    private void showEmptyResults() {
        lblEinzelpreisWert.setText(PLACEHOLDER);
        lblMengeWert.setText(PLACEHOLDER);
        lblZwischensumme.setText(PLACEHOLDER);
        lblRabattBetrag.setText(PLACEHOLDER);
        lblBasisNachRabatt.setText(PLACEHOLDER);
        lblSteuerBetrag.setText(PLACEHOLDER);
        lblEndpreis.setText(PLACEHOLDER);
        lblEndpreisJeStueck.setText(PLACEHOLDER);
    }

    /**
     * Checks whether an input field is empty or contains blanks only.
     *
     * @param field the input field to check
     * @return {@code true} if nothing was entered
     */
    private static boolean isEmpty(TextField field) {
        return field.getText() == null || field.getText().trim().isEmpty();
    }

    /**
     * Checks whether the text can be read as a number, for example "49.99"
     * or "-3". Digits, a leading minus sign and a comma or a dot as
     * decimal separator are allowed. Empty text is allowed as well,
     * because empty discount and tax fields mean 0 percent.
     *
     * @param text the entered text
     * @return {@code true} if the text can be read as a number
     */
    private static boolean isNumber(String text) {
        if (text.isEmpty()) {
            return true;
        }

        String checked = text.startsWith("-") ? text.substring(1) : text;

        if (checked.isEmpty()) {
            return false;
        }

        for (int pos = 0; pos < checked.length(); pos++) {
            char character = checked.charAt(pos);
            boolean isDigit = character >= '0' && character <= '9';
            boolean isSeparator = character == ',' || character == '.';

            if (!isDigit && !isSeparator) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks whether the text contains digits only and therefore yields a
     * whole number. Decimal values such as "2,5" are rejected, because
     * the quantity can only be a whole number.
     *
     * @param text the entered text
     * @return {@code true} if the text contains digits only
     */
    private static boolean isWholeNumber(String text) {
        if (text.isEmpty()) {
            return false;
        }

        for (int pos = 0; pos < text.length(); pos++) {
            char character = text.charAt(pos);
            if (character < '0' || character > '9') {
                return false;
            }
        }

        return true;
    }

    /**
     * Turns already checked text into a number. Comma and dot are both
     * read as decimal separator.
     *
     * @param text the entered text
     * @return the number contained in the text
     */
    private static double toDouble(String text) {
        return Double.parseDouble(text.replace(",", "."));
    }

    /**
     * Reads the quantity and falls back to the default quantity when the
     * field is empty or invalid. Used by the "−" and "+" buttons.
     *
     * @return the entered or the default quantity
     */
    private int quantityOrDefault() {
        String text = txtMenge.getText().trim();

        if (!isWholeNumber(text)) {
            return RabattRechner.DEFAULT_QUANTITY;
        }

        return Integer.parseInt(text);
    }

    /**
     * Formats an amount for the display, for example "1,234.56 EUR".
     *
     * @param value the amount in euro
     * @return the formatted amount as text
     */
    private static String amount(double value) {
        return String.format(LOCALE, "%,.2f EUR", value);
    }

    /**
     * Formats a percentage for the display, for example "19.00 %".
     *
     * @param value the percentage
     * @return the formatted percentage as text
     */
    private static String percent(double value) {
        return String.format(LOCALE, "%.2f %%", value);
    }
}
