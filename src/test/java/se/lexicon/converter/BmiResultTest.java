package se.lexicon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BmiResultTest {

    @Nested
    @DisplayName("Default constructor")
    class DefaultConstructor {

        @Test
        @DisplayName("New instance starts with zero BMI and empty category")
        void newInstance_hasZeroBmiAndEmptyCategory() {
            BmiResult result = new BmiResult();

            assertAll(
                    () -> assertEquals(0.0, result.getBmi(), 1e-9),
                    () -> assertEquals("", result.getCategory())
            );
        }
    }

    @Nested
    @DisplayName("setBmi / getBmi")
    class BmiAccessors {

        @Test
        @DisplayName("Setting a positive BMI value is stored and returned unchanged")
        void setBmi_positiveValue_isStoredAndReturned() {
            BmiResult result = new BmiResult();

            result.setBmi(22.86);

            assertEquals(22.86, result.getBmi(), 1e-9);
        }

        @Test
        @DisplayName("Setting a negative BMI value is stored and returned unchanged")
        void setBmi_negativeValue_isStoredAndReturned() {
            BmiResult result = new BmiResult();

            result.setBmi(-5.0);

            assertEquals(-5.0, result.getBmi(), 1e-9);
        }

        @Test
        @DisplayName("Setting BMI to zero is stored and returned unchanged")
        void setBmi_zero_isStoredAndReturned() {
            BmiResult result = new BmiResult();

            result.setBmi(0.0);

            assertEquals(0.0, result.getBmi(), 1e-9);
        }

        @Test
        @DisplayName("Setting BMI a second time overwrites the previous value")
        void setBmi_calledTwice_overwritesPreviousValue() {
            BmiResult result = new BmiResult();

            result.setBmi(10.0);
            result.setBmi(20.0);

            assertEquals(20.0, result.getBmi(), 1e-9);
        }
    }

    @Nested
    @DisplayName("setCategory / getCategory")
    class CategoryAccessors {

        @Test
        @DisplayName("Setting a valid category is stored and returned unchanged")
        void setCategory_validValue_isStoredAndReturned() {
            BmiResult result = new BmiResult();

            result.setCategory("Normal weight");

            assertEquals("Normal weight", result.getCategory());
        }

        @Test
        @DisplayName("Setting an empty string category is stored and returned unchanged")
        void setCategory_emptyString_isStoredAndReturned() {
            BmiResult result = new BmiResult();

            result.setCategory("");

            assertEquals("", result.getCategory());
        }

        @Test
        @DisplayName("Setting category to null is stored and returned as null")
        void setCategory_null_isStoredAsNull() {
            BmiResult result = new BmiResult();

            result.setCategory(null);

            assertEquals(null, result.getCategory());
        }
    }

    @Nested
    @DisplayName("getPrintResult")
    class GetPrintResult {

        @Test
        @DisplayName("Formats typical BMI and category with two decimal places")
        void getPrintResult_typicalValues_formatsBmiWithTwoDecimalsAndCategory() {
            BmiResult result = new BmiResult();
            result.setBmi(22.86);
            result.setCategory("Normal weight");

            String printed = result.getPrintResult();

            assertEquals("BMI: 22.86, Category: Normal weight", printed);
        }

        @Test
        @DisplayName("Pads a BMI value with fewer than two decimals with zeros")
        void getPrintResult_bmiWithFewerThanTwoDecimals_padsWithZeros() {
            BmiResult result = new BmiResult();
            result.setBmi(18.5);
            result.setCategory("Normal weight");

            String printed = result.getPrintResult();

            assertEquals("BMI: 18.50, Category: Normal weight", printed);
        }

        @Test
        @DisplayName("Formats a zero BMI as 0.00")
        void getPrintResult_zeroBmi_formatsAsZeroPointZeroZero() {
            BmiResult result = new BmiResult();
            result.setBmi(0.0);
            result.setCategory("Underweight");

            String printed = result.getPrintResult();

            assertEquals("BMI: 0.00, Category: Underweight", printed);
        }

        @Test
        @DisplayName("Formats a negative BMI with a minus sign")
        void getPrintResult_negativeBmi_formatsWithMinusSign() {
            BmiResult result = new BmiResult();
            result.setBmi(-5.0);
            result.setCategory("Underweight");

            String printed = result.getPrintResult();

            assertEquals("BMI: -5.00, Category: Underweight", printed);
        }

        @Test
        @DisplayName("Renders a null category as the literal text null")
        void getPrintResult_nullCategory_rendersLiteralNullWithoutThrowing() {
            // TODO: confirm intended behavior - BmiResult allows a null category to be
            // printed as the literal text "null" instead of being rejected or defaulted.
            BmiResult result = new BmiResult();
            result.setBmi(22.86);
            result.setCategory(null);

            String printed = result.getPrintResult();

            assertEquals("BMI: 22.86, Category: null", printed);
        }

        @Test
        @DisplayName("Renders an empty category as an empty segment")
        void getPrintResult_emptyCategory_rendersEmptyCategorySegment() {
            BmiResult result = new BmiResult();
            result.setBmi(22.86);
            result.setCategory("");

            String printed = result.getPrintResult();

            assertEquals("BMI: 22.86, Category: ", printed);
        }
    }
}
