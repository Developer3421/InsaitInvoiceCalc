package com.example;

/**
 * The calculation logic of the final price calculator.
 *
 * <p>The calculation is always done for a whole batch, that is for a
 * quantity {@code quantity} of units at one unit price each. Discount and
 * VAT apply to the whole batch.</p>
 *
 * <p>The calculation runs in four steps:</p>
 * <ol>
 *   <li>Subtotal = unit price x quantity</li>
 *   <li>Discount amount = subtotal x discount / 100</li>
 *   <li>Tax amount = (subtotal - discount amount) x VAT rate / 100</li>
 *   <li>Final price = subtotal - discount amount + tax amount</li>
 * </ol>
 *
 * <p>The class knows neither a user interface nor a text format and works
 * with numbers only. Rounding to cents is done by the view when it
 * writes out the values.</p>
 */
public final class RabattRechner {

    /** Quantity that is used when no batch has been given. */
    public static final int DEFAULT_QUANTITY = 1;

    /**
     * Prevents instances from being created. The calculation logic is
     * used exclusively through the static method.
     */
    private RabattRechner() {
    }

    /**
     * Calculates the final price of a batch of {@code quantity} units.
     *
     * <p>Example: 100 units at 49.99 euro each with a discount of 20
     * percent and 19 percent VAT result in 4,759.05 euro.</p>
     *
     * @param unitPrice      net price of a single unit in euro
     * @param quantity       number of units in the batch
     * @param discountPercent discount on the subtotal in percent
     * @param vatPercent      VAT on the total after the discount in percent
     * @return the complete invoice result
     */
    public static RechnungsErgebnis calculateBatch(
            double unitPrice, int quantity, double discountPercent, double vatPercent) {

        double subtotal = unitPrice * quantity;
        double discountAmount = subtotal * discountPercent / 100;
        double totalAfterDiscount = subtotal - discountAmount;
        double taxAmount = totalAfterDiscount * vatPercent / 100;
        double finalPrice = totalAfterDiscount + taxAmount;

        return new RechnungsErgebnis(
                unitPrice,
                quantity,
                discountPercent,
                vatPercent,
                subtotal,
                totalAfterDiscount,
                discountAmount,
                taxAmount,
                finalPrice,
                finalPrice / quantity
        );
    }
}
