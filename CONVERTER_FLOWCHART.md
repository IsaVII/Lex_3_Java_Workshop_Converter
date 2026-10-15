# Flowchart: ConverterApp (combined architecture)

This chart shows the combined control flow of `ConverterApp`, its menu loop, shared input/validation helpers, the `Printer` utility, and how it dispatches to each `Converter` subclass (`Converter_Bmi`, `Converter_Currency`, `Converter_DataStorage`) through the shared `Converter` base class, including the `BmiResult` and `ConversionResult` objects produced along the way.

## Main Flow: ConverterApp.main() and menu loop

```mermaid
flowchart TD
  start(["Start: ConverterApp.main()"]):::term
  init["Create Converter_Bmi, Converter_Currency, Converter_DataStorage; init conversionResults list"]:::proc
  loop{"runConverterChoicePart(): show menu, loop while true"}:::dec
  showMenu["Build menu text via Printer.getTitle / Printer.getPrintLine"]:::proc
  printMenu[/"Print menu to console"/]:::io
  readChoice[/"Read menu choice (1-4) via getMenuNumberFromUserInRange"/]:::io
  switchChoice{"Switch on choice"}:::dec

  runBmi[["Converter_Bmi.runConverter()"]]:::pre
  runCurrency[["Converter_Currency.runConverter()"]]:::pre
  runData[["Converter_DataStorage.runConverter()"]]:::pre

  exitChoice["Choice 4: Exit"]:::proc
  invalidChoice["Invalid/default choice"]:::proc
  invalidMsg[/"Print 'Invalid choice. Please try again.'"/]:::io

  printResultsExit[["printConversionResults()"]]:::pre
  goodbyeExit[/"Print 'Goodbye!'"/]:::io
  returnFalseExit(["Return false from runConverterChoicePart"]):::term

  returnTrue(["Return true from runConverterChoicePart"]):::term

  loopContinue{"main(): while runConverterChoicePart() is true"}:::dec
  askStop{"userWantToStop(): ask yes/no 'Continue?'"}:::dec
  readYesNo[/"Read yes/no via getYesNoChoiceFromUser"/]:::io
  stopYes["User said no, want to stop"]:::proc
  printResultsStop[["printConversionResults()"]]:::pre
  goodbyeStop[/"Print 'Goodbye!'"/]:::io
  finishStop(["Return from main()"]):::term
  blankLine[/"Print blank line"/]:::io

  start --> init --> loop
  loop --> showMenu --> printMenu --> readChoice --> switchChoice

  switchChoice -->|"1"| runBmi --> returnTrue
  switchChoice -->|"2"| runCurrency --> returnTrue
  switchChoice -->|"3"| runData --> returnTrue
  switchChoice -->|"4"| exitChoice --> printResultsExit --> goodbyeExit --> returnFalseExit
  switchChoice -->|"other/default"| invalidChoice --> invalidMsg --> returnFalseExit

  returnTrue --> loopContinue
  loopContinue -->|"false (choice was 4 or invalid)"| finishStop
  loopContinue -->|"true"| askStop
  askStop --> readYesNo --> stopYes
  stopYes -->|"answer is 'no'"| printResultsStop --> goodbyeStop --> finishStop
  stopYes -->|"answer is 'yes'"| blankLine --> loop

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Flow: Converter base class and shared helpers

```mermaid
flowchart TD
  entryBase(["Entry: Converter.runConverter() called via super.runConverter()"]):::term
  printTitle[/"Print converter title ('--- name ---')"/]:::io
  returnToSub(["Return control to subclass's runConverter()"]):::term

  entryTimestamp(["Entry: Converter.printTimeStamp()"]):::term
  getNow["Get current LocalDateTime"]:::proc
  formatNow["Format as dd/MM/yyyy HH:mm"]:::proc
  printTs[/"Print 'Converted at: ...'"/]:::io

  entryAddResult(["Entry: Converter.addConversionResult(input, output, sign)"]):::term
  buildResult["Create new ConversionResult(input, output, sign)"]:::proc
  callApp[["ConverterApp.addToConversionResults(conversionResult)"]]:::pre
  storeResult["Append ConversionResult to conversionResults list"]:::proc

  entryBase --> printTitle --> returnToSub
  entryTimestamp --> getNow --> formatNow --> printTs
  entryAddResult --> buildResult --> callApp --> storeResult

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Flow: Converter_Bmi.runConverter()

```mermaid
flowchart TD
  startBmi(["Entry: Converter_Bmi.runConverter()"]):::term
  baseBmi[["Converter.runConverter() - print title"]]:::pre
  readWeight[/"Read weight in kg (30-400) via getNumberInRangeFromUser"/]:::io
  readHeight[/"Read height in metres (0.5-2.5) via getNumberInRangeFromUser"/]:::io
  calcBmi["calculateBMI(weight, height)"]:::proc
  categorize{"Categorize BMI value"}:::dec
  underweight["Category: Underweight (bmi < 18.5)"]:::proc
  normal["Category: Normal weight (bmi < 24.9)"]:::proc
  overweight["Category: Overweight (bmi < 29.9)"]:::proc
  obese["Category: Obese (otherwise)"]:::proc
  buildBmiResult["Build BmiResult (bmi, category)"]:::proc
  bmiResultNode(("BmiResult object")):::conn
  printBmiResult[/"Print 'Result: ' + BmiResult.getPrintResult()"/]:::io
  tsBmi[["Converter.printTimeStamp()"]]:::pre
  addResultBmi[["Converter.addConversionResult(bmi info, category, '->')"]]:::pre
  endBmi(["Return to ConverterApp menu loop"]):::term

  startBmi --> baseBmi --> readWeight --> readHeight --> calcBmi --> categorize
  categorize -->|"< 18.5"| underweight --> buildBmiResult
  categorize -->|"18.5-24.9"| normal --> buildBmiResult
  categorize -->|"24.9-29.9"| overweight --> buildBmiResult
  categorize -->|">= 29.9"| obese --> buildBmiResult
  buildBmiResult --> bmiResultNode --> printBmiResult --> tsBmi --> addResultBmi --> endBmi

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Flow: Converter_Currency.runConverter()

```mermaid
flowchart TD
  startCur(["Entry: Converter_Currency.runConverter()"]):::term
  baseCur[["Converter.runConverter() - print title"]]:::pre
  printCurMenu[/"Print submenu: 1. SEK to USD, 2. SEK to EUR"/]:::io
  readCurChoice[/"Read choice (1-2) via getMenuNumberFromUserInRange"/]:::io
  curSwitch{"Switch on choice"}:::dec

  readSekUsd[/"Read amount in SEK"/]:::io
  convUsd["convertSekToUsd(sek)"]:::proc
  printUsd[/"Print 'Result: ... SEK = ... USD.'"/]:::io
  tsUsd[["Converter.printTimeStamp()"]]:::pre
  addUsd[["Converter.addConversionResult(sek, usd, '=')"]]:::pre

  readSekEur[/"Read amount in SEK"/]:::io
  convEur["convertSekToEur(sek)"]:::proc
  printEur[/"Print 'Result: ... SEK = ... EUR.'"/]:::io
  tsEur[["Converter.printTimeStamp()"]]:::pre
  addEur[["Converter.addConversionResult(sek, eur, '=')"]]:::pre

  invalidCur[/"Print 'Invalid choice.'"/]:::io
  endCur(["Return to ConverterApp menu loop"]):::term

  startCur --> baseCur --> printCurMenu --> readCurChoice --> curSwitch
  curSwitch -->|"1"| readSekUsd --> convUsd --> printUsd --> tsUsd --> addUsd --> endCur
  curSwitch -->|"2"| readSekEur --> convEur --> printEur --> tsEur --> addEur --> endCur
  curSwitch -->|"default"| invalidCur --> endCur

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Flow: Converter_DataStorage.runConverter()

```mermaid
flowchart TD
  startDs(["Entry: Converter_DataStorage.runConverter()"]):::term
  baseDs[["Converter.runConverter() - print title"]]:::pre
  printDsMenu[/"Print submenu: 1. MB to GB, 2. KB to MB"/]:::io
  readDsChoice[/"Read choice (1-2) via getMenuNumberFromUserInRange"/]:::io
  dsSwitch{"Switch on choice"}:::dec

  readMb[/"Read amount in MB"/]:::io
  convGb["convertMbToGb(mb) using DATA_SIZE=1024"]:::proc
  printGb[/"Print 'Result: ... MB = ... GB.'"/]:::io
  tsGb[["Converter.printTimeStamp()"]]:::pre
  addGb[["Converter.addConversionResult(mb, gb, '=')"]]:::pre

  readKb[/"Read amount in KB"/]:::io
  convMb["convertKbToMb(kb) using DATA_SIZE=1024"]:::proc
  printMb[/"Print 'Result: ... KB = ... MB.'"/]:::io
  tsMb[["Converter.printTimeStamp()"]]:::pre
  addMb[["Converter.addConversionResult(kb, mb, '=')"]]:::pre

  invalidDs[/"Print 'Invalid choice.'"/]:::io
  endDs(["Return to ConverterApp menu loop"]):::term

  startDs --> baseDs --> printDsMenu --> readDsChoice --> dsSwitch
  dsSwitch -->|"1"| readMb --> convGb --> printGb --> tsGb --> addGb --> endDs
  dsSwitch -->|"2"| readKb --> convMb --> printMb --> tsMb --> addMb --> endDs
  dsSwitch -->|"default"| invalidDs --> endDs

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Flow: printConversionResults() - shared output of history

```mermaid
flowchart TD
  entryPrint(["Entry: ConverterApp.printConversionResults()"]):::term
  checkEmpty{"Is conversionResults list empty?"}:::dec
  printNone[/"Print 'No conversion results to display.'"/]:::io
  returnEmpty(["Return"]):::term

  buildHeader["Start building history text: '=== Conversion History ==='"]:::proc
  loopResults{"For each ConversionResult in list"}:::dec
  getPrintResult["ConversionResult.getPrintResult(): format input/sign/output"]:::proc
  appendLine["Append formatted line with index"]:::proc
  appendFooter["Append print line and total conversions count"]:::proc
  printHistory[/"Print full conversion history text"/]:::io
  doneHistory(["Return"]):::term

  entryPrint --> checkEmpty
  checkEmpty -->|"Yes"| printNone --> returnEmpty
  checkEmpty -->|"No"| buildHeader --> loopResults
  loopResults -->|"Next result"| getPrintResult --> appendLine --> loopResults
  loopResults -->|"Done"| appendFooter --> printHistory --> doneHistory

  classDef term fill:#e3f2fd,stroke:#90caf9,color:#000000,stroke-width:1px;
  classDef io fill:#fff3e0,stroke:#ffcc80,color:#000000,stroke-width:1px;
  classDef proc fill:#e8f5e9,stroke:#a5d6a7,color:#000000,stroke-width:1px;
  classDef dec fill:#fde0dc,stroke:#f8bbd0,color:#000000,stroke-width:1px;
  classDef pre fill:#ede7f6,stroke:#b39ddb,color:#000000,stroke-width:1px;
  classDef conn fill:#f5f5f5,stroke:#bdbdbd,color:#000000,stroke-width:1px;
  classDef hide fill:none,stroke:none,color:none;
  classDef label fill:none,stroke:none,color:#000000,text-align:center;
```

## Legend

| Shape | Class | Meaning |
|---|---|---|
| Stadium `([ ])` | `term` | Start / end / return |
| Parallelogram `[/ /]` | `io` | Console input/output (read/print) |
| Rectangle `[ ]` | `proc` | Processing: calculation, assignment, formatting |
| Rhombus `{ }` | `dec` | Decision: if/else, switch, loop condition |
| Subroutine `[[ ]]` | `pre` | Call to another charted method (e.g. a converter's `runConverter()`, `Converter.printTimeStamp()`) |
| Circle `(( ))` | `conn` | Connector / object marker (e.g. the `BmiResult` instance) |
| (none shown) | `hide` | Invisible spacer, unused here |
| (none shown) | `label` | Free text annotation, unused here |
