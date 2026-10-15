package se.lexicon.converter;


public class BmiResult{
    double bmi;
    String category;
    
    public BmiResult() {
        this.bmi = 0.0;
        this.category = "";
    }
    
    public BmiResult(double bmi, String category) {
        this.bmi = bmi;
        this.category = category;
    }

    public String getPrintResult( ) {
        
        return String.format("BMI: %.2f, Category: %s%n", bmi, category);
    }
    
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
   
}
