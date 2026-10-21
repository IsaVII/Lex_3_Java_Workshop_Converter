package se.lexicon.converter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.lexicon.ConverterApp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConverterTest {

    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void redirectStdOut() {
        originalOut = System.out;
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void restoreStdOut() {
        System.setOut(originalOut);
    }

    /**
     * {@code ConverterApp.conversionResults} is only initialized inside the instance
     * {@code main()} method, which blocks on console input. To exercise
     * {@link Converter#addConversionResult(String, String, String)} without going through
     * that blocking entry point, the private list field is initialized via reflection here.
     */
    private static ConverterApp newConverterAppWithInitializedResultsList() throws Exception {
        ConverterApp app = new ConverterApp();
        Field field = ConverterApp.class.getDeclaredField("conversionResults");
        field.setAccessible(true);
        field.set(app, new java.util.ArrayList<ConversionResult>());
        return app;
    }

    @SuppressWarnings("unchecked")
    private static List<ConversionResult> readResultsList(ConverterApp app) throws Exception {
        Field field = ConverterApp.class.getDeclaredField("conversionResults");
        field.setAccessible(true);
        return (List<ConversionResult>) field.get(app);
    }

    @Nested
    @DisplayName("getConverterTitle")
    class GetConverterTitle {

        @Test
        @DisplayName("Wraps the converter name in dashes when a name is set")
        void getConverterTitle_withNameSet_wrapsNameInDashes() {
            Converter converter = new Converter(null);
            converter.converterName = "Test Converter";

            assertEquals("--- Test Converter ---", converter.getConverterTitle());
        }

        @Test
        @DisplayName("Renders the literal text null when the converter name is null")
        void getConverterTitle_withNullName_rendersLiteralNull() {
            // TODO: confirm intended behavior - a Converter created without a name
            // produces the title "--- null ---" instead of throwing or using a default.
            Converter converter = new Converter(null);

            assertEquals("--- null ---", converter.getConverterTitle());
        }
    }

    @Nested
    @DisplayName("getConverterName")
    class GetConverterName {

        @Test
        @DisplayName("Returns the name when it has been set")
        void getConverterName_withNameSet_returnsName() {
            Converter converter = new Converter(null);
            converter.converterName = "Test Converter";

            assertEquals("Test Converter", converter.getConverterName());
        }

        @Test
        @DisplayName("Returns null when no name has been set")
        void getConverterName_withNoNameSet_returnsNull() {
            Converter converter = new Converter(null);

            assertEquals(null, converter.getConverterName());
        }
    }

    @Nested
    @DisplayName("runConverter")
    class RunConverter {

        @Test
        @DisplayName("Prints the converter title to standard out")
        void runConverter_printsTitleToStandardOut() {
            Converter converter = new Converter(null);
            converter.converterName = "Test Converter";

            converter.runConverter();

            assertTrue(capturedOut.toString().contains("--- Test Converter ---"),
                    "Expected output to contain the converter title");
        }
    }

    @Nested
    @DisplayName("printTimeStamp")
    class PrintTimeStamp {

        @Test
        @DisplayName("Prints a timestamp line in the expected date format")
        void printTimeStamp_printsTimestampLineInExpectedFormat() {
            Converter converter = new Converter(null);

            converter.printTimeStamp();

            String output = capturedOut.toString();
            assertTrue(output.matches("(?s)Converted at: \\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}\\s*\\n\\s*"),
                    "Expected output to match 'Converted at: dd/MM/yyyy HH:mm' but was: [" + output + "]");
        }
    }

    @Nested
    @DisplayName("addConversionResult")
    class AddConversionResult {

        @Test
        @DisplayName("Adds a single result with the given fields")
        void addConversionResult_withInitializedConverterApp_addsSingleResultWithGivenFields() throws Exception {
            ConverterApp app = newConverterAppWithInitializedResultsList();
            Converter converter = new Converter(app);

            converter.addConversionResult("100 SEK", "10 USD", "=");

            List<ConversionResult> results = readResultsList(app);
            assertEquals(1, results.size());
            String printed = results.get(0).getPrintResult();
            assertAll(
                    () -> assertTrue(printed.contains("100 SEK")),
                    () -> assertTrue(printed.contains("10 USD")),
                    () -> assertTrue(printed.contains("="))
            );
        }

        @Test
        @DisplayName("Appends both results in order when called twice")
        void addConversionResult_calledTwice_appendsBothResultsInOrder() throws Exception {
            ConverterApp app = newConverterAppWithInitializedResultsList();
            Converter converter = new Converter(app);

            converter.addConversionResult("100 SEK", "10 USD", "=");
            converter.addConversionResult("200 SEK", "20 USD", "=");

            List<ConversionResult> results = readResultsList(app);
            assertEquals(2, results.size());
            assertTrue(results.get(0).getPrintResult().contains("100 SEK"));
            assertTrue(results.get(1).getPrintResult().contains("200 SEK"));
        }

        @Test
        @DisplayName("Throws NullPointerException when the results list was never initialized")
        void addConversionResult_whenConverterAppResultsListNotInitialized_throwsNullPointerException() {
            // Possible bug: ConverterApp.conversionResults (ConverterApp.java) is only
            // initialized inside the instance main() method. Any Converter built around a
            // freshly constructed ConverterApp that never ran main() will NPE here.
            ConverterApp app = new ConverterApp();
            Converter converter = new Converter(app);

            assertThrows(NullPointerException.class,
                    () -> converter.addConversionResult("100 SEK", "10 USD", "="));
        }
    }
}
