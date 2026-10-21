package se.lexicon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Converter_Currency#convertSekToUsd(double)} and
 * {@link Converter_Currency#convertSekToEur(double)} hold the actual conversion math, but
 * both are private static methods only reachable through {@code runConverter()}, which
 * blocks on console input via the JDK's {@code IO} class. {@code IO.readln} lazily caches a
 * single reader over {@code System.in} for the whole JVM lifetime, so redirecting
 * {@code System.in} per test is not reliable across multiple independent tests/classes.
 * Reflection is used instead to invoke the private static methods directly.
 */
class Converter_CurrencyTest {

    private static double convertSekToUsd(double sek) throws Exception {
        Method method = Converter_Currency.class.getDeclaredMethod("convertSekToUsd", double.class);
        method.setAccessible(true);
        return (double) method.invoke(null, sek);
    }

    private static double convertSekToEur(double sek) throws Exception {
        Method method = Converter_Currency.class.getDeclaredMethod("convertSekToEur", double.class);
        method.setAccessible(true);
        return (double) method.invoke(null, sek);
    }

    @Nested
    @DisplayName("constructor")
    class Constructor {

        @Test
        @DisplayName("Sets the converter name to Currency Converter")
        void constructor_setsConverterNameToCurrencyConverter() {
            Converter_Currency converter = new Converter_Currency(null);

            assertEquals("Currency Converter", converter.getConverterName());
        }

        @Test
        @DisplayName("Converter title wraps the name in dashes")
        void constructor_converterTitle_wrapsNameInDashes() {
            Converter_Currency converter = new Converter_Currency(null);

            assertEquals("--- Currency Converter ---", converter.getConverterTitle());
        }
    }

    @Nested
    @DisplayName("convertSekToUsd")
    class ConvertSekToUsd {

        @ParameterizedTest(name = "{0} SEK -> {1} USD")
        @DisplayName("Converts SEK to USD, applying the exchange rate and rounding")
        @CsvSource({
                "100, 10.0",
                "0,   0.0",
                "1,   0.1",
                "-50, -5.0"
        })
        void convertSekToUsd_variousAmounts_appliesExchangeRateAndRoundsToTwoDecimals(
                double sek, double expectedUsd) throws Exception {

            assertEquals(expectedUsd, convertSekToUsd(sek), 1e-9);
        }
    }

    @Nested
    @DisplayName("convertSekToEur")
    class ConvertSekToEur {

        @ParameterizedTest(name = "{0} SEK -> {1} EUR")
        @DisplayName("Converts SEK to EUR, applying the exchange rate and rounding")
        @CsvSource({
                "1000, 89.0",
                "100,  8.9",
                "1,    0.09",
                "0,    0.0"
        })
        void convertSekToEur_variousAmounts_appliesExchangeRateAndRoundsToTwoDecimals(
                double sek, double expectedEur) throws Exception {

            assertEquals(expectedEur, convertSekToEur(sek), 1e-9);
        }
    }
}
