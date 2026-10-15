package se.lexicon.converter;


public class Converter_Bmi extends Converter {
    
    public Converter_Bmi() {
        this.converterName = "BMI Converter";
    }

    @Override
    public void runConverter() {
        super.runConverter();
    }

    private double calculateBMI(double weight, double height) {
        return weight / (Math.pow(height, 2));
    }

    private BmiResult getBmiResult(double weight, double height) {
        BmiResult result = new BmiResult();
        
        double bmi =  calculateBMI(weight, height);
        result.setBmi(bmi);
        
        if (bmi < 18.5) {
            result.setCategory("Underweight");
        } else if (bmi < 24.9) {
            result.setCategory("Normal weight");
        } else if (bmi < 29.9) {
            result.setCategory("Overweight");
        } else {
            result.setCategory("Obese");
        }
        return result;
    }



}

