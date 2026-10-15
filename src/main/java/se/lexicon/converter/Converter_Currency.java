package se.lexicon.converter;

import se.lexicon.ConverterApp;

public class Converter_Currency extends  Converter {
    static final double EXCHANGE_RATE_SEK_TO_USD = 0.10;
    static final double EXCHANGE_RATE_SEK_TO_EUR = 0.089;
    
    public Converter_Currency(ConverterApp converterApp) {
        super(converterApp);
        this.converterName = "Currency Converter";
    }

    @Override
    public void runConverter() {
        super.runConverter();

        StringBuilder menu = new StringBuilder();
        menu.append("Convert:\n");
        menu.append("1. SEK to USD\n");
        menu.append("2. SEK to EUR");
        IO.println(menu.toString());
        
        int choice = ConverterApp.getMenuNumberFromUserInRange("Your choice:", 1, 2);
        switch (choice) {
            case 1:
                double sekToUsd = ConverterApp.getNumberFromUser("Enter amount in SEK: ", false);
                
                double usd = convertSekToUsd(sekToUsd);
                IO.println("Result: " + sekToUsd + " SEK = " + usd + " USD.");;

                addConversionResult(sekToUsd +" SEK " , usd + " USD" , "=");
                
                break;
            case 2:
                double sekToEur = ConverterApp.getNumberFromUser("Enter amount in SEK: ", false);
                double eur = convertSekToEur(sekToEur);
                IO.println("Result: " + sekToEur + " SEK = " + eur + " EUR.");
                
                addConversionResult(sekToEur +" SEK " , eur + " EUR" , "=");
                break;
            default:
                IO.println("Invalid choice.");
                break;
        }
        IO.println("");

    }

    private static double convertSekToUsd(double sek) {
        return Math.round(sek * EXCHANGE_RATE_SEK_TO_USD * 100.0) / 100.0;
    }

    private static double convertSekToEur(double sek) {
        return Math.round(sek * EXCHANGE_RATE_SEK_TO_EUR * 100.0) / 100.0;
    }

}
