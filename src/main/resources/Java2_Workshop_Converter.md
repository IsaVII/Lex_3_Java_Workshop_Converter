![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop – Converter App

## The Scenario

You are building a console-based unit converter.
The user opens the app, sees a menu of converters, picks one, enters a value, and gets a clean formatted result.
After each conversion the app asks whether to continue or quit.

You get to **choose 3 converter types** from the list below — pick the ones that interest you most and implement all three in a single program.

**Create `ConverterApp.java`** inside `src/main/java/se/lexicon/`.  
Commit regularly as you make progress.

---

## Requirements

Your application must:
- Display a numbered menu showing your 3 chosen converters and an Exit option
- For converters with more than one direction (e.g. °C → °F *or* °F → °C), show a sub-menu and let the user choose
- Ask the user to enter a value, calculate the result, and display it in a formatted line
- After each conversion, ask: continue or exit?
- Keep running until the user chooses to exit
- Define **all conversion rates and fixed values as `final` constants** at the top of your class
- Organise the logic into **methods** — each converter should have its own method that takes the input value and returns the converted result

---

## Choose Your 3 Converters

Pick any 3. All formulas and rates are provided below — use them as `final` constants in your code.

---

**1. Currency Converter**
- SEK → USD: `USD = SEK / 10.87`
- SEK → EUR: `EUR = SEK / 11.32`

> Currency rates change daily. Treat the values above as fixed constants for this workshop.

---

**2. Temperature Converter**
- Celsius → Fahrenheit: `F = C * 9.0 / 5.0 + 32`
- Fahrenheit → Celsius: `C = (F - 32) * 5.0 / 9.0`

---

**3. Length Converter**
- Centimeters → Meters: `m = cm / 100`
- Meters → Kilometers: `km = m / 1000`

---

**4. Weight Converter**
- Kilograms → Grams: `g = kg * 1000`
- Kilograms → Pounds: `lb = kg * 2.20462`

---

**5. Time Converter**
- Hours → Minutes: `min = h * 60`
- Minutes → Seconds: `sec = min * 60`

---

**6. Speed Converter**
- km/h → m/s: `m_per_s = km_per_h / 3.6`
- m/s → km/h: `km_per_h = m_per_s * 3.6`

---

**7. Data Storage Converter**
- Kilobytes → Megabytes: `MB = KB / 1024`
- Megabytes → Gigabytes: `GB = MB / 1024`

---

**8. BMI Calculator**

Takes two inputs: weight in kg and height in metres.

```
BMI = weight / (height * height)
```

Use `Math.pow(height, 2)` for the height-squared step.


| BMI range    | Category       |
|:-------------|:---------------|
| Below 18.5   | Underweight    |
| 18.5 – 24.9  | Normal weight  |
| 25.0 – 29.9  | Overweight     |
| 30.0 and up  | Obese          |

---

**9. Fuel Consumption Converter**
- Litres per 100 km → km per litre: `kmPerL = 100.0 / litresPer100km`
- km per litre → Litres per 100 km: `litresPer100km = 100.0 / kmPerL`

---

**10. Grade Converter**

Takes a score (0–100) as input. If the score is a decimal, use `Math.round()` before categorising.

| Score range | Grade | Label         |
|:------------|:------|:--------------|
| 90 – 100    | A     | Excellent     |
| 80 – 89     | B     | Good          |
| 70 – 79     | C     | Pass          |
| 60 – 69     | D     | Below average |
| 0 – 59      | F     | Fail          |

---

## Sample Session

Below is a complete run of the finished application using **Temperature**, **Currency**, and **BMI** as the three chosen converters.  
Use it as your target — your output should match this format for the same inputs.

**Test case: three conversions then exit**

```text
==================================
      LEXICON CONVERTER APP
==================================
1. Temperature Converter
2. Currency Converter
3. BMI Calculator
4. Exit
==================================
Choose an option: 1

--- Temperature Converter ---
Convert:
  1. Celsius to Fahrenheit
  2. Fahrenheit to Celsius
Your choice: 1
Enter temperature in Celsius: 100
Result: 100.00 C = 212.00 F

Continue? (yes/no): yes

==================================
      LEXICON CONVERTER APP
==================================
1. Temperature Converter
2. Currency Converter
3. BMI Calculator
4. Exit
==================================
Choose an option: 2

--- Currency Converter ---
Convert:
  1. SEK to USD
  2. SEK to EUR
Your choice: 2
Enter amount in SEK: 1000
Result: 1000.00 SEK = 88.34 EUR

Continue? (yes/no): yes

==================================
      LEXICON CONVERTER APP
==================================
1. Temperature Converter
2. Currency Converter
3. BMI Calculator
4. Exit
==================================
Choose an option: 3

--- BMI Calculator ---
Enter weight in kg: 75
Enter height in metres: 1.80
BMI: 23.15  ->  Normal weight

Continue? (yes/no): no
Goodbye!
```

**Test case: exit immediately**

```text
==================================
      LEXICON CONVERTER APP
==================================
1. Temperature Converter
2. Currency Converter
3. BMI Calculator
4. Exit
==================================
Choose an option: 4
Goodbye!
```

---

## Optional Challenges

Once the core app works, try extending it:
- create a new branch for each challenge
- commit your work regularly
- merge your branch into the main branch when you're done

---

### Challenge 1 – Input Validation

Guard your app against bad input. Re-prompt instead of crashing.

- Reject non-numeric values for amounts and menu choices
- Reject invalid menu numbers (e.g. typing `7` when you only have options 1–4)
- Reject negative values where they don't make sense (weight, height, currency amounts, data sizes)

```text
Choose an option: 9
Invalid choice. Please enter a number between 1 and 4.
Choose an option: abc
Invalid input. Please enter a number.
Choose an option: 1

Enter temperature in Celsius: hot
Invalid input. Please enter a number.
Enter temperature in Celsius: -500
Invalid value. Temperature cannot be below absolute zero (-273.15 C).
Enter temperature in Celsius: 37
Result: 37.00 C = 98.60 F
```

---

### Challenge 2 – Session History

After each conversion, record it. When the user exits, print a full summary of everything converted in the session.

```text
Continue? (yes/no): no

=== Conversion History ===
1.  100.00 C          =  212.00 F
2.  1000.00 SEK       =   88.34 EUR
3.  BMI 23.15         -> Normal weight
==========================
Total conversions: 3
Goodbye!
```

---

### Challenge 3 – Add Timestamps

Show the current date and time on each result line.

```text
Result: 100.00 C = 212.00 F
Converted at: 2026-06-11 09:14
```

---

## Submission

Push your project to GitHub and share the repository link with your instructor.
