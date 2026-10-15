package se.lexicon.converter;


import se.lexicon.ConverterApp;

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
        
    }
}
