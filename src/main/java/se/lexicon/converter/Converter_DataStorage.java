package se.lexicon.converter;

public class Converter_DataStorage extends  Converter {
    static final int DATA_SIZE = 1024;
    
    public Converter_DataStorage() {
        this.converterName = "Data Storage Converter";
    }
    
    @Override
    public void runConverter() {
        super.runConverter();
    }
    
    private static float convertMbToGb(float megabytes) {    
        return megabytes / 1024;
    }
    
    private static float convertKbToMb(float kilobytes) {
        return kilobytes / 1024;
    }
}
