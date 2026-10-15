package se.lexicon.converter;


import se.lexicon.ConverterApp;

public class Converter_Bmi extends Converter {
    
    public Converter_Bmi(ConverterApp converterApp) {
        super(converterApp);
        this.converterName = "BMI Calculator";
    }

    @Override
    public void runConverter() {
        super.runConverter();
        
        double weight = getNumberFromUser("Enter your weight in kg: ");
        double height = getNumberFromUser("Enter your height in metres: ");
        
        BmiResult result = getBmiResult(weight, height);
        IO.println(result.getPrintResult());
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


    private double getNumberFromUser(String message){
        
        double number = 0;
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message);

            try {
               number = Double.parseDouble(userInput);
               return number;

            } catch (NumberFormatException e) {
                IO.println("Invalid input. Please enter a valid number.");
            }
        }
        return  0;
    }

}

