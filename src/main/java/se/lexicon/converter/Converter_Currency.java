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
    }

    private static double convertSekToUsd(double sek) {
        return sek * EXCHANGE_RATE_SEK_TO_USD;
    }

    private static double convertSekToEur(double sek) {
        return sek * EXCHANGE_RATE_SEK_TO_EUR;
    }

}
