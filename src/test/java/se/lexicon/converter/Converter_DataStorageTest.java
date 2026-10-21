package se.lexicon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Converter_DataStorage#convertMbToGb(double)} and
 * {@link Converter_DataStorage#convertKbToMb(double)} hold the actual conversion math, but
 * both are private static methods only reachable through {@code runConverter()}, which
 * blocks on console input via the JDK's {@code IO} class. {@code IO.readln} lazily caches a
 * single reader over {@code System.in} for the whole JVM lifetime, so redirecting
 * {@code System.in} per test is not reliable across multiple independent tests/classes.
 * Reflection is used instead to invoke the private static methods directly.
 */
class Converter_DataStorageTest {

    private static double convertMbToGb(double megabytes) throws Exception {
        Method method = Converter_DataStorage.class.getDeclaredMethod("convertMbToGb", double.class);
        method.setAccessible(true);
        return (double) method.invoke(null, megabytes);
    }

    private static double convertKbToMb(double kilobytes) throws Exception {
        Method method = Converter_DataStorage.class.getDeclaredMethod("convertKbToMb", double.class);
        method.setAccessible(true);
        return (double) method.invoke(null, kilobytes);
    }

    @Nested
    @DisplayName("constructor")
    class Constructor {

        @Test
        @DisplayName("Sets the converter name to Data Storage Converter")
        void constructor_setsConverterNameToDataStorageConverter() {
            Converter_DataStorage converter = new Converter_DataStorage(null);

            assertEquals("Data Storage Converter", converter.getConverterName());
        }

        @Test
        @DisplayName("Converter title wraps the name in dashes")
        void constructor_converterTitle_wrapsNameInDashes() {
            Converter_DataStorage converter = new Converter_DataStorage(null);

            assertEquals("--- Data Storage Converter ---", converter.getConverterTitle());
        }
    }

    @Nested
    @DisplayName("convertMbToGb")
    class ConvertMbToGb {

        @ParameterizedTest(name = "{0} MB -> {1} GB")
        @DisplayName("Converts MB to GB, dividing by the data size and rounding")
        @CsvSource({
                "1024, 1.0",
                "2048, 2.0",
                "0,    0.0",
                "1,    0.0",
                "1023, 1.0"
        })
        void convertMbToGb_variousAmounts_dividesByDataSizeAndRoundsToTwoDecimals(
                double megabytes, double expectedGb) throws Exception {

            assertEquals(expectedGb, convertMbToGb(megabytes), 1e-9);
        }
    }

    @Nested
    @DisplayName("convertKbToMb")
    class ConvertKbToMb {

        @ParameterizedTest(name = "{0} KB -> {1} MB")
        @DisplayName("Converts KB to MB, dividing by the data size and rounding")
        @CsvSource({
                "1024, 1.0",
                "512,  0.5",
                "0,    0.0"
        })
        void convertKbToMb_variousAmounts_dividesByDataSizeAndRoundsToTwoDecimals(
                double kilobytes, double expectedMb) throws Exception {

            assertEquals(expectedMb, convertKbToMb(kilobytes), 1e-9);
        }
    }
}
