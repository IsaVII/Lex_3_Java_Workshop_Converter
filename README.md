# Workshop Converter

A console-based unit converter written in Java, built as a Lexicon coding workshop exercise. The user picks a converter from a menu, enters a value, and gets a formatted result with a timestamp. Conversions are recorded during the session and printed as a summary when the user exits.

## Features

- Menu-driven CLI with three converters:
  - **BMI Calculator** — weight (kg) and height (m) in, BMI value and category (Underweight / Normal weight / Overweight / Obese) out
  - **Currency Converter** — SEK to USD or SEK to EUR
  - **Data Storage Converter** — MB to GB or KB to MB
- Input validation with re-prompting (non-numeric input, negative values, out-of-range BMI inputs, menu choices out of range)
- Timestamp (`dd/MM/yyyy HH:mm`) printed after each conversion
- Conversion history: every result is recorded and printed as a numbered summary, with a total count, when the user exits

## Tech stack

- Java (compiled with `maven.compiler.source`/`target` 27)
- Maven (no plugins configured beyond the default build)
- Uses the `IO` class and an unnamed-class-style instance `main()` method (JDK preview feature for simplified launch), so it must be run with a JDK that supports it (see Prerequisites)

## Getting started

### Prerequisites

- JDK 27 (or a JDK new enough to support the implicit/unnamed classes and instance `main` method feature used by `ConverterApp`, run with `--enable-preview` if required by your JDK version)
- Maven 3.8+

### Installation

```bash
git clone https://github.com/IsaVII/Lex_3_Java_Workshop_Converter.git
cd Lex_3_Java_Workshop_Converter
mvn compile
```

### Running

There is no configured `exec` or `jar` plugin in `pom.xml`, so run the app directly from your IDE (IntelliJ project files are included), or compile and run from the command line, enabling preview features if your JDK requires it for instance `main` methods:

```bash
mvn compile
java --enable-preview --class-path target/classes se.lexicon.ConverterApp
```

## Usage

Example session:

```text
==================================
      LEXICON CONVERTER APP
==================================
1. BMI Calculator
2. Currency Converter
3. Data Storage Converter
4. Exit
==================================
Choose an option (1-4): 1

--- BMI Calculator ---
Enter your weight in kg: 75
Enter your height in metres: 1.80
Result: BMI: 23.15, Category: Normal weight
Converted at: 11/10/2026 09:14

Continue? (yes/no): no

=== Conversion History ===
1. BMI 23.15        ->  Normal weight
==================================
Total conversions: 1
Goodbye!
```

Each converter with more than one direction (Currency, Data Storage) shows its own sub-menu after being selected.

## Project structure

```
src/main/java/se/lexicon/
├── ConverterApp.java              # Entry point, main menu loop, shared input-validation helpers
├── Printer.java                   # Static helpers for the title banner and separator line
└── converter/
    ├── Converter.java             # Base class: converter name/title, timestamp printing, result recording
    ├── ConversionResult.java      # A single recorded conversion (input, output, sign) for the history
    ├── BmiResult.java             # BMI value + category holder used by the BMI converter
    ├── Converter_Bmi.java         # BMI Calculator
    ├── Converter_Currency.java    # SEK -> USD / SEK -> EUR
    └── Converter_DataStorage.java # MB -> GB / KB -> MB
src/main/resources/
└── Java2_Workshop_Converter.md    # Original workshop assignment and requirements
```

<!-- TODO: add a Running tests section if/when automated tests are added to the project -->

## Author

Isa (IsaVII)
