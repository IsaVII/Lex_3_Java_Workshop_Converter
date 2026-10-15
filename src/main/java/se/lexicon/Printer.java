package se.lexicon;

public class Printer {

    
    public static StringBuilder getTitle() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPrintLine()).append("\n");
        builder.append("      LEXICON CONVERTER APP\n");
        builder.append(getPrintLine()).append("\n");
        return builder;
    }

    public static String getPrintLine() {
        return "==================================";
    }


    
}
