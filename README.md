# Insait Invoice Calc

> **Training project.** This is my training project, created as part of a
> professional education program. It is built for practice and to demonstrate
> development skills, not for commercial use.

## About

Insait Invoice Calc is a JavaFX desktop application for calculating the final
price of a batch including discount and VAT.

**Features:**

- Net subtotal of a batch: unit price × quantity
- Customer discount in percent, applied to the whole batch (optional)
- VAT in percent, applied to the amount after the discount
- Invoice summary with subtotal, discount amount, tax amount, batch total and price per unit
- Calculation runs on the `Calculate` button or the `Enter` key
- Amounts in cents; both comma and dot are accepted as the decimal separator

## Tech stack

- Language: Java 26
- UI: JavaFX 25
- Build tool: Maven
- Packaging: `jpackage` (self-contained Windows app)

## Build and run

See [`RabattRechnerCode/demo/README.md`](RabattRechnerCode/demo/README.md) for full instructions.
In short:

```powershell
mvn clean package -DskipTests
```

## Structure

- `RabattRechnerCode/demo/` — application source code
