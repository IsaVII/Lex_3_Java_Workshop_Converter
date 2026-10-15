package se.lexicon.converter;


import se.lexicon.ConverterApp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converter {

    private ConverterApp converterApp;
    protected String converterName;
    
    public Converter(ConverterApp converterApp) {
        this.converterApp = converterApp;
    }
    
    public String getConverterTitle(){
        return "--- " + converterName + " ---";
    }
    
    public String getConverterName(){
        return converterName;
    }

    public void runConverter(){
        
        IO.println("\n" + getConverterTitle());
    }
    
    public void addConversionResult(String input, String output, String converterSign){
        ConversionResult conversionResult = new ConversionResult(input, output, converterSign);
        converterApp.addToConversionResults(conversionResult);
    }
    
    protected void printTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = now.format(formatter);
        
        IO.println("Converted at: "  + formattedDateTime + "\n");
    }
}
