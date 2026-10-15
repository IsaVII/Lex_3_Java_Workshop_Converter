package se.lexicon;

public class Printer {

    
    public static StringBuilder getTitle() {
        StringBuilder builder = new StringBuilder();
        builder.append(getPrintLine());
        builder.append("      LEXICON CONVERTER APP\n");
        builder.append(getPrintLine());
        return builder;
    }

    private static String getPrintLine() {
        return "==================================\n";
    }


    
}
