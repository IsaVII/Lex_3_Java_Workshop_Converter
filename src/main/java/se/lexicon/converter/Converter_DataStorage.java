package se.lexicon.converter;

public class Converter_DataStorage {
    static final int DATA_SIZE = 1024;
    
    public static float convertMbToGb(float megabytes) {    
        return megabytes / 1024;
    }
    
    public static float convertKbToMb(float kilobytes) {
        return kilobytes / 1024;
    }
}
