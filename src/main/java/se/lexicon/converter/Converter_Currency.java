package se.lexicon.converter;

public class Converter_Currency {
    static final double EXCHANGE_RATE_SEK_TO_USD = 0.10;
    static final double EXCHANGE_RATE_SEK_TO_EUR = 0.089;
    
    public static double convertSekToUsd(double sek) {
        return sek * EXCHANGE_RATE_SEK_TO_USD;
    }
    
    public static double convertSekToEur(double sek) {
        return sek * EXCHANGE_RATE_SEK_TO_EUR;
    }

}
