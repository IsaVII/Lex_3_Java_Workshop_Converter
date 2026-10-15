package se.lexicon.converter;


import java.util.Locale;

public class BmiResult{
    double bmi;
    String category;
    
    public BmiResult() {
        this.bmi = 0.0;
        this.category = "";
    }
    
    public String getPrintResult( ) {
        
        return String.format(Locale.ENGLISH, "BMI: %.2f, Category: %s%n", bmi, category);
    }
    
    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
   
}
