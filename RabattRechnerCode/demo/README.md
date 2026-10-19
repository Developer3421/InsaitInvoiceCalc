# Insait Invoice Calc

A JavaFX desktop application for calculating the final price of a whole batch including discount and VAT.

## Features

- **Final price of a batch**: `unit price x quantity` as the net subtotal
- **Customer discount in percent** applied to the whole batch, optional
- **VAT in percent** applied to the subtotal after the discount
- **Invoice summary** with subtotal, discount amount, tax amount, final price of the batch and final price per unit
- **Calculate on demand**: the calculation starts with the `Calculate` button or with `Enter` in an input field, a change of the input alone does not calculate
- **Quantity** can be typed directly or adjusted with the `−` and `+` buttons
- **Amounts in cents** in the display, for example `4,759.05 EUR`
- Comma and dot are both accepted as decimal separator
- **Terms of use** available from the icon in the title bar

## User interface

The design follows the visual language of the VCalc project: cream window background, grey window chrome, cards on a light cream surface and the purple/orange accents.

- Theme: `src/main/resources/styles/Styles.css`
- Main window: 940 x 800, minimum 780 x 600
- Terms window: 780 x 680, modal

## Project overview

- Language: Java 26
- UI framework: JavaFX 25
- Build tool: Maven
- Packaging: Java `jpackage` for a self-contained Windows app

## Requirements

- Java 26 JDK installed
- Maven installed
- Windows 10/11 for the native EXE version

## Build the app locally

Run this in the project folder:

```powershell
mvn clean package -DskipTests
```

This creates the JAR file:

```text
target/insait-invoice-calc-1.0.0.jar
```

## Create a self-contained app

The whole app can also be built without Maven using the JDK tools `javac`, `jar`, `jlink` and `jpackage`. `$jdk` is the path to the JDK, for example `C:\Program Files\Java\jdk-26.0.2\bin`.

```powershell
# 1. Compile classes and resources into a module folder
javac --module-path $modulePath -d target/selfcontained/modules `
  src/main/java/module-info.java src/main/java/com/example/*.java
Copy-Item -Recurse -Force src/main/resources/* target/selfcontained/modules

# 2. Build the module JAR
jar --create --file target/selfcontained/insait-invoice-calc.jar `
  --main-class com.example.MainApp -C target/selfcontained/modules .

# 3. Custom runtime image with only the required modules
jlink --module-path target/selfcontained/insait-invoice-calc.jar;$modulePath `
  --add-modules com.example,javafx.controls,javafx.fxml,javafx.media `
  --strip-debug --no-header-files --no-man-pages `
  --output target/selfcontained/runtime

# 4. Windows folder with the EXE
jpackage --type app-image --name InsaitInvoiceCalc --app-version 1.0.0 `
  --runtime-image target/selfcontained/runtime `
  --module com.example/com.example.MainApp `
  --module-path target/selfcontained/insait-invoice-calc.jar `
  --icon src/main/resources/image/appicon.ico `
  --java-options "--enable-native-access=javafx.graphics" `
  --dest target/selfcontained/app

# 5. Pack it up as a ZIP for the customer
Compress-Archive -Path target/selfcontained/app/InsaitInvoiceCalc `
  -DestinationPath target/selfcontained/InsaitInvoiceCalc-1.0.0-win-x64.zip
```

The option `--win-console` is deliberately not set. Without it the EXE starts without a black console window in the background.
`$modulePath` points to the JavaFX JARs, here JavaFX 25 from the local Maven repository.

The result then lives here:

```text
target/selfcontained/
├── modules/           intermediate result of the compilation
├── insait-invoice-calc.jar  the application as a single JAR
├── runtime/           own Java runtime image
├── app/InsaitInvoiceCalc/
│   ├── app/           configuration and modules
│   ├── runtime/       bundled Java runtime
│   └── InsaitInvoiceCalc.exe
└── InsaitInvoiceCalc-1.0.0-win-x64.zip
```

## Publish as a self-contained app

1. Hand the ZIP `InsaitInvoiceCalc-1.0.0-win-x64.zip` to a customer.
2. The customer unpacks it and starts only this file:

```text
InsaitInvoiceCalc.exe
```

For a real installer (`InsaitInvoiceCalcSetup.exe`) `jpackage` additionally needs the WiX tools, see <https://wixtoolset.org>. Without WiX it stays at the ZIP version, which serves the same purpose.

## Note

The app already contains the Java runtime and needs no separate Java installation on the target machine.

## Project files

- `src/main/java/com/example/MainApp.java`
- `src/main/java/com/example/FXMLController.java`
- `src/main/java/com/example/RabattRechner.java`
- `src/main/java/com/example/RechnungsErgebnis.java`
- `src/main/java/com/example/TermsController.java`
- `src/main/resources/fxml/primary.fxml`
- `src/main/resources/fxml/terms.fxml`
- `src/main/resources/styles/Styles.css`
- `src/main/resources/image/`
- `pom.xml`
