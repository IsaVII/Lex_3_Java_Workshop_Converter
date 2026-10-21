package se.lexicon.converter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link Converter_Bmi#calculateBMI(double, double)} and
 * {@link Converter_Bmi#getBmiResult(double, double)} hold all of the BMI calculation and
 * categorization logic, but both are private and are otherwise only reachable through
 * {@code runConverter()}, which blocks on console input read through the JDK's {@code IO}
 * class. Because {@code IO.readln} lazily caches a single {@code BufferedReader} over
 * {@code System.in} for the lifetime of the JVM, redirecting {@code System.in} per test
 * only works reliably for the first test that ever calls it in the whole test run, which
 * would make such tests order-dependent and non-isolated. Reflection is used instead to
 * invoke the private calculation methods directly, which keeps the tests deterministic and
 * independent while still covering the real business logic.
 */
class Converter_BmiTest {

    private static double calculateBMI(double weight, double height) throws Exception {
        Converter_Bmi converter = new Converter_Bmi(null);
        Method method = Converter_Bmi.class.getDeclaredMethod("calculateBMI", double.class, double.class);
        method.setAccessible(true);
        return (double) method.invoke(converter, weight, height);
    }

    private static BmiResult getBmiResult(double weight, double height) throws Exception {
        Converter_Bmi converter = new Converter_Bmi(null);
        Method method = Converter_Bmi.class.getDeclaredMethod("getBmiResult", double.class, double.class);
        method.setAccessible(true);
        return (BmiResult) method.invoke(converter, weight, height);
    }

    @Nested
    @DisplayName("constructor")
    class Constructor {

        @Test
        @DisplayName("Sets the converter name to BMI Calculator")
        void constructor_setsConverterNameToBmiCalculator() {
            Converter_Bmi converter = new Converter_Bmi(null);

            assertEquals("BMI Calculator", converter.getConverterName());
        }

        @Test
        @DisplayName("Converter title wraps the name in dashes")
        void constructor_converterTitle_wrapsNameInDashes() {
            Converter_Bmi converter = new Converter_Bmi(null);

            assertEquals("--- BMI Calculator ---", converter.getConverterTitle());
        }
    }

    @Nested
    @DisplayName("calculateBMI")
    class CalculateBmi {

        @Test
        @DisplayName("Rounds a typical weight and height to two decimals")
        void calculateBMI_typicalWeightAndHeight_roundsToTwoDecimals() throws Exception {
            assertEquals(22.86, calculateBMI(70, 1.75), 1e-9);
        }

        @Test
        @DisplayName("Zero weight produces a BMI of zero")
        void calculateBMI_zeroWeight_isZero() throws Exception {
            assertEquals(0.0, calculateBMI(0, 1.0), 1e-9);
        }

        @Test
        @DisplayName("Negative weight produces a negative BMI")
        void calculateBMI_negativeWeight_isNegative() throws Exception {
            assertEquals(-70.0, calculateBMI(-70, 1.0), 1e-9);
        }

        @Test
        @DisplayName("Zero height causes division by zero that rounds to a huge value")
        void calculateBMI_zeroHeight_divisionByZeroYieldsRoundedInfinity() throws Exception {
            // TODO: confirm intended behavior - a height of 0 is never guarded against, so
            // this divides by zero. Math.round(Double.POSITIVE_INFINITY) returns Long.MAX_VALUE,
            // which is then converted back to a very large double rather than staying infinite.
            assertEquals(9.223372036854776E16, calculateBMI(70, 0), 1.0);
        }

        @Test
        @DisplayName("Zero weight and height produce a NaN result that rounds to zero")
        void calculateBMI_zeroWeightAndZeroHeight_zeroDividedByZeroRoundsToZero() throws Exception {
            // TODO: confirm intended behavior - 0/0 is NaN, and Math.round(NaN) is
            // documented to return 0, so this silently resolves to a BMI of 0.0 instead of
            // signalling an invalid calculation.
            assertEquals(0.0, calculateBMI(0, 0), 1e-9);
        }
    }

    @Nested
    @DisplayName("getBmiResult")
    class GetBmiResult {

        @ParameterizedTest(name = "weight={0}, height={1} -> bmi={2}, category={3}")
        @DisplayName("Produces the expected BMI and category across the category boundaries")
        @CsvSource({
                "18.49, 1.0, 18.49, Underweight",
                "18.5,  1.0, 18.5,  Normal weight",
                "24.89, 1.0, 24.89, Normal weight",
                "24.9,  1.0, 24.9,  Overweight",
                "29.89, 1.0, 29.89, Overweight",
                "29.9,  1.0, 29.9,  Obese",
                "70,    1.75, 22.86, Normal weight"
        })
        void getBmiResult_boundaryAndTypicalInputs_producesExpectedBmiAndCategory(
                double weight, double height, double expectedBmi, String expectedCategory) throws Exception {

            BmiResult result = getBmiResult(weight, height);

            assertAll(
                    () -> assertEquals(expectedBmi, result.getBmi(), 1e-9),
                    () -> assertEquals(expectedCategory, result.getCategory())
            );
        }

        @Test
        @DisplayName("Combines BMI and category into the printed result")
        void getBmiResult_printResult_combinesBmiAndCategory() throws Exception {
            BmiResult result = getBmiResult(70, 1.75);

            assertEquals("BMI: 22.86, Category: Normal weight", result.getPrintResult());
        }
    }
}
