package com.example;

/**
 * Unchangeable result of a calculation from {@link RabattRechner}.
 * All amounts refer to the whole batch, that is to {@code quantity} units
 * at {@code unitPrice} euro each.
 *
 * <p>The class only stores values and offers a getter for each of them.
 * It is filled in the controller and read only afterwards.</p>
 */
public class RechnungsErgebnis {

    private final double unitPrice;
    private final int quantity;
    private final double discountPercent;
    private final double vatPercent;
    private final double originalTotal;
    private final double discountedTotal;
    private final double discountAmount;
    private final double taxAmount;
    private final double finalPrice;
    private final double finalPricePerUnit;

    /**
     * Creates a complete invoice result. The values passed in are stored
     * unchanged, rounding to cents is done by the view.
     *
     * @param unitPrice         net price of a single unit in euro
     * @param quantity          number of units in the batch
     * @param discountPercent   discount that has been applied in percent
     * @param vatPercent        VAT that has been applied in percent
     * @param originalTotal     subtotal of the batch before the discount
     * @param discountedTotal   subtotal of the batch after the discount, at the same time the tax base
     * @param discountAmount    discount amount deducted from the batch
     * @param taxAmount         tax amount added to the batch
     * @param finalPrice        final price of the whole batch including VAT
     * @param finalPricePerUnit final price of a single unit of the batch
     */
    public RechnungsErgebnis(double unitPrice, int quantity, double discountPercent, double vatPercent,
                             double originalTotal, double discountedTotal, double discountAmount,
                             double taxAmount, double finalPrice, double finalPricePerUnit) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discountPercent = discountPercent;
        this.vatPercent = vatPercent;
        this.originalTotal = originalTotal;
        this.discountedTotal = discountedTotal;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
        this.finalPrice = finalPrice;
        this.finalPricePerUnit = finalPricePerUnit;
    }

    /**
     * Returns the net price of a single unit.
     *
     * @return net price of a unit in euro
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Returns the number of units of the batch.
     *
     * @return number of calculated units
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the discount rate used for this calculation.
     *
     * @return discount in percent
     */
    public double getDiscountPercent() {
        return discountPercent;
    }

    /**
     * Returns the tax rate used for this calculation.
     *
     * @return VAT in percent
     */
    public double getVatPercent() {
        return vatPercent;
    }

    /**
     * Returns the subtotal of the batch before the discount,
     * that is {@code unitPrice * quantity}.
     *
     * @return net subtotal in euro
     */
    public double getOriginalTotal() {
        return originalTotal;
    }

    /**
     * Returns the subtotal of the batch after the discount has been
     * deducted. This amount is the base for the VAT.
     *
     * @return net amount after the discount in euro
     */
    public double getDiscountedTotal() {
        return discountedTotal;
    }

    /**
     * Returns the discount amount that has been deducted from the subtotal.
     *
     * @return discount amount of the batch in euro
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Returns the tax amount that has been added to the batch.
     *
     * @return tax amount in euro
     */
    public double getTaxAmount() {
        return taxAmount;
    }

    /**
     * Returns the final price of the whole batch including VAT.
     * This is the amount the customer pays for the whole batch.
     *
     * @return final price of the batch in euro
     */
    public double getFinalPrice() {
        return finalPrice;
    }

    /**
     * Returns the final price of a single unit of the batch.
     *
     * @return final price per unit in euro
     */
    public double getFinalPricePerUnit() {
        return finalPricePerUnit;
    }
}
