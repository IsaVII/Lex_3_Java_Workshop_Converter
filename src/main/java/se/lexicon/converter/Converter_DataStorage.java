package se.lexicon.converter;

import se.lexicon.ConverterApp;

public class Converter_DataStorage extends  Converter {
    static final int DATA_SIZE = 1024;
    
    public Converter_DataStorage(ConverterApp converterApp) {
        super(converterApp);
        this.converterName = "Data Storage Converter";
    }
    
    @Override
    public void runConverter() {
        super.runConverter();

        StringBuilder menu = new StringBuilder();
        menu.append("Convert:\n");
        menu.append("1. MB to GB\n");
        menu.append("2. KB to MB");
        IO.println(menu.toString());

        int choice = ConverterApp.getMenuNumberFromUserInRange("Your choice:", 1, 2);
        switch (choice) {
            case 1:
                double mbToGb = ConverterApp.getNumberFromUser("Enter amount in MB: ", false);
                double gb = convertMbToGb(mbToGb);
                IO.println("Result: " + mbToGb + " MB = " + gb   + " GB.");;

                printTimeStamp();
                
                addConversionResult(mbToGb +" MB " , gb + " GB" , "=");
                break;
            case 2:
                double kbToMb = ConverterApp.getNumberFromUser("Enter amount in KB: ", false);
                double mb = convertKbToMb(kbToMb);
                IO.println("Result: " + kbToMb + " KB = " + mb + " MB.");

                printTimeStamp();
                
                addConversionResult(kbToMb +" KB " , mb + " MB" , "=");
                break;
            default:
                IO.println("Invalid choice.");
                break;
        }
    }
    
    private static double convertMbToGb(double megabytes) {    
        return Math.round(megabytes / DATA_SIZE * 100.0) / 100.0;
    }
    
    private static double convertKbToMb(double kilobytes) {
        return Math.round(kilobytes / DATA_SIZE * 100.0) / 100.0;
    }
}
