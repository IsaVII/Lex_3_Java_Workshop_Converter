package se.lexicon.converter;


public class Converter_Bmi{
    
    private double calculateBMI(double weight, double height) {
        return weight / (Math.pow(height, 2));
    }

    public BmiResult getBmiResult(double weight, double height) {
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

