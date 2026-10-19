package se.lexicon.gui;

import se.lexicon.converter.ConversionResult;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class ConverterGUI extends JFrame implements Navigator {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final List<ConversionResult> history = new ArrayList<>();

    public ConverterGUI() {
        setTitle("Lexicon Converter App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 520);
        setMinimumSize(new Dimension(560, 460));
        setLocationRelativeTo(null);

        cards.setBackground(GuiTheme.BG);
        cards.add(new MenuPanel(this), "menu");
        cards.add(new BmiPanel(this, history), "bmi");
        cards.add(new CurrencyPanel(this, history), "currency");
        cards.add(new DataStoragePanel(this, history), "data");
        cards.add(new HistoryPanel(this, history), "history");

        setContentPane(cards);
        goTo("menu");
    }

    @Override
    public void goTo(String cardName) {
        cardLayout.show(cards, cardName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConverterGUI().setVisible(true));
    }
}
