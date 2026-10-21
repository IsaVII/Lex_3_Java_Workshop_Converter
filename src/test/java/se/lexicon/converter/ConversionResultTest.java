package se.lexicon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConversionResultTest {

    @Nested
    @DisplayName("getPrintResult")
    class GetPrintResult {

        @Test
        @DisplayName("Formats input, sign and output with padding and a trailing newline")
        void getPrintResult_typicalValues_formatsInputSignOutputWithPaddingAndNewline() {
            ConversionResult result = new ConversionResult("100.0 SEK", "10.0 USD", "=");

            String printed = result.getPrintResult();

            assertEquals(String.format("%-15s %-3s %s%n", "100.0 SEK", "=", "10.0 USD"), printed);
            assertEquals(String.format("100.0 SEK       =   10.0 USD%n"), printed);
        }

        @Test
        @DisplayName("Left-pads a short input to fifteen characters")
        void getPrintResult_shortInput_isLeftPaddedToFifteenCharacters() {
            ConversionResult result = new ConversionResult("a", "b", "->");

            String printed = result.getPrintResult();

            assertEquals(String.format("a               ->  b%n"), printed);
        }

        @Test
        @DisplayName("Does not truncate input longer than the field width")
        void getPrintResult_inputLongerThanFieldWidth_isNotTruncated() {
            String longInput = "this input is longer than fifteen characters";
            ConversionResult result = new ConversionResult(longInput, "out", "=");

            String printed = result.getPrintResult();

            assertEquals(String.format(longInput + " =   out%n"), printed);
        }

        @Test
        @DisplayName("Formats empty strings with only padding and a newline")
        void getPrintResult_emptyStrings_formatsWithOnlyPaddingAndNewline() {
            ConversionResult result = new ConversionResult("", "", "");

            String printed = result.getPrintResult();

            assertEquals(String.format("                    %n"), printed);
        }

        @Test
        @DisplayName("Renders a null input as the literal text null")
        void getPrintResult_nullInput_rendersLiteralNullWithoutThrowing() {
            // TODO: confirm intended behavior - ConversionResult allows null fields to be
            // printed as the literal text "null" instead of being rejected.
            ConversionResult result = new ConversionResult(null, "10.0 USD", "=");

            String printed = result.getPrintResult();

            assertEquals(String.format("null            =   10.0 USD%n"), printed);
        }

        @Test
        @DisplayName("Renders a null output as the literal text null")
        void getPrintResult_nullOutput_rendersLiteralNullWithoutThrowing() {
            ConversionResult result = new ConversionResult("100.0 SEK", null, "=");

            String printed = result.getPrintResult();

            assertEquals(String.format("100.0 SEK       =   null%n"), printed);
        }

        @Test
        @DisplayName("Renders a null converter sign as the literal text null")
        void getPrintResult_nullConverterSign_rendersLiteralNullWithoutThrowing() {
            ConversionResult result = new ConversionResult("100.0 SEK", "10.0 USD", null);

            String printed = result.getPrintResult();

            assertEquals(String.format("100.0 SEK       null 10.0 USD%n"), printed);
        }
    }
}
