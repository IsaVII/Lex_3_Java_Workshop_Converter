package se.lexicon.converter;

public class ConversionResult {
    
    String input;
    String output;
    String converterSign;
    
    public ConversionResult(String input, String output, String converterSign) {
        this.input = input;
        this.output = output;
        this.converterSign = converterSign;
    }
    
    public String getPrintResult() {
        return String.format("%-20s %-3s %s%n", input,converterSign, output);
    }
    
    
}
