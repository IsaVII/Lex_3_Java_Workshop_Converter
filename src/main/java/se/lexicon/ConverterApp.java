package se.lexicon;

import se.lexicon.converter.ConversionResult;
import se.lexicon.converter.Converter_Bmi;
import se.lexicon.converter.Converter_Currency;
import se.lexicon.converter.Converter_DataStorage;

import java.util.ArrayList;
import java.util.List;

public class ConverterApp {

    Converter_Bmi converter_Bmi;
    Converter_Currency converter_Currency;
    Converter_DataStorage converter_DataStorage;
    
    List<ConversionResult> conversionResults;
    
    void main() {
        converter_Bmi = new Converter_Bmi(this);
        converter_Currency = new Converter_Currency(this);
        converter_DataStorage = new Converter_DataStorage(this);
        conversionResults = new ArrayList<>();
        
        while (runConverterChoicePart()) {
            if (userWantToStop()) {
                printConversionResults();
                IO.println("Goodbye!");
                return;
            }
            
            IO.println();
            
        }
    }


    public boolean runConverterChoicePart() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Printer.getTitle());

        //Converters 
        stringBuilder.append("1. " + converter_Bmi.getConverterName() + "\n");
        stringBuilder.append("2. " + converter_Currency.getConverterName() + "\n");
        stringBuilder.append("3. " + converter_DataStorage.getConverterName() + "\n");
        stringBuilder.append("4. Exit\n");
        stringBuilder.append(Printer.getPrintLine());

        IO.println(stringBuilder.toString());

        //choice handling
        int choice = getMenuNumberFromUserInRange("Choose an option", 1, 4);
        switch (choice){
            case 1:
                converter_Bmi.runConverter();
                break;
            case 2:
                converter_Currency.runConverter();
                break;
            case 3:
                converter_DataStorage.runConverter();
                break;
            case 4:
                printConversionResults();
                IO.println("Goodbye!");
                return false;
            default:
                IO.println("Invalid choice. Please try again.");
                return false;
        }
        return true;
    }
    

    private boolean userWantToStop() {
        String userChoice = getYesNoChoiceFromUser("Continue?");
        return userChoice.equals("no");
    }

    private void printConversionResults() {

        if (conversionResults.isEmpty()) {
            IO.println("No conversion results to display.");
            return;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=== Conversion History ===\n");

        int index = 1;
        for (ConversionResult result : conversionResults) {
            stringBuilder.append(index).append(". ").append(result.getPrintResult());
            index++;
        }

        stringBuilder.append(Printer.getPrintLine()).append("\n");
        stringBuilder.append("Total conversions: ").append(conversionResults.size());
        IO.println(stringBuilder.toString());
    }
    
    public static String getYesNoChoiceFromUser(String message){
        String choice = "";
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message + " (yes/no): ");

            if (userInput.equalsIgnoreCase("yes") || userInput.equalsIgnoreCase("no")) {
                choice = userInput.toLowerCase();
                validInput = true;
            } else {
                IO.println("Invalid input. Please enter 'yes' or 'no'.");
            }
           
        }
        return  choice;
    }
    

    public static int getMenuNumberFromUserInRange(String message, int minIncl, int maxIncl){
        int menuNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message + " (" + minIncl + "-" + maxIncl + "): ");

            try {
                menuNumber = Integer.parseInt(userInput);
                validInput = menuNumber >= minIncl && menuNumber <= maxIncl;
                
            } catch (NumberFormatException e) {
            }
            
            if (!validInput) {
                IO.println("Invalid choice. Please enter a number between " + minIncl + " and " + maxIncl + ".");
            }
        }
        return  menuNumber;
    }


    public static double getNumberFromUser(String message, boolean allowNegative){

        double number = 0;
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message);

            try {
                number = Double.parseDouble(userInput);
                if (allowNegative && number < 0) {
                    validInput = true;
                    
                } else if (!allowNegative && number < 0) {
                    validInput = false;
                    IO.println("Invalid input. The number cannot be negative.");
                }
                else 
                {       
                    validInput = true;
                }

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number.");
                
            }
        }
        return  number;
    }
    
    public void addToConversionResults(ConversionResult conversionResult){
        conversionResults.add(conversionResult);
    }

}
