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
                double mbToGb = ConverterApp.getNumberFromUser("Enter amount in MB: ");
                double gb = convertMbToGb(mbToGb);
                IO.println("Result: " + mbToGb + " MB = " + Math.round(gb * 100.0) / 100.0 + " GB.");;
                break;
            case 2:
                double kbToMb = ConverterApp.getNumberFromUser("Enter amount in KB: ");
                double mb = convertKbToMb(kbToMb);
                IO.println("Result: " + kbToMb + " KB = " + Math.round(mb * 100.0) / 100.0 + " MB.");
                break;
            default:
                IO.println("Invalid choice.");
                break;
        }
        IO.println("");
    }
    
    private static double convertMbToGb(double megabytes) {    
        return megabytes / 1024;
    }
    
    private static double convertKbToMb(double kilobytes) {
        return kilobytes / 1024;
    }
}
