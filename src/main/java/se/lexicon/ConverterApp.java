package se.lexicon;

import se.lexicon.converter.Converter_Bmi;
import se.lexicon.converter.Converter_Currency;
import se.lexicon.converter.Converter_DataStorage;

public class ConverterApp {

    Converter_Bmi converter_Bmi;
    Converter_Currency converter_Currency;
    Converter_DataStorage converter_DataStorage;
    
    
    void main() {
        converter_Bmi = new Converter_Bmi(this);
        converter_Currency = new Converter_Currency(this);
        converter_DataStorage = new Converter_DataStorage(this);
        
        while (runConverterChoicePart()) {
            String userChoice = getYesNoChoiceFromUser("Continue?");
            switch (userChoice) {
                case "yes":
                    IO.println();
                    break;
                case "no":
                    IO.println("Goodbye!");
                    return;
                    
                default:
                    IO.println("Invalid choice. Please try again.");
            }
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
                IO.println("Goodbye!");
                return false;
            default:
                IO.println("Invalid choice. Please try again.");
                return false;
        }
        
        
        
        return true;
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
                IO.println("Invalid input. Please enter a valid number.");
            }
        }
        return  menuNumber;
    }


    public static double getNumberFromUser(String message){

        double number = 0;
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message);

            try {
                number = Double.parseDouble(userInput);
                return number;

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number.");
            }
        }
        return  0;
    }

}
