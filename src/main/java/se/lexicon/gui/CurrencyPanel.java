package se.lexicon.gui;

import se.lexicon.converter.ConversionResult;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Locale;

public class CurrencyPanel extends JPanel {

    private static final double EXCHANGE_RATE_SEK_TO_USD = 0.10;
    private static final double EXCHANGE_RATE_SEK_TO_EUR = 0.089;

    public CurrencyPanel(Navigator navigator, List<ConversionResult> history) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GuiTheme.BG);
        setBorder(new EmptyBorder(28, 36, 28, 36));

        add(GuiTheme.titleLabel("Currency Converter"));
        add(Box.createVerticalStrut(24));

        JComboBox<String> directionBox = new JComboBox<>(new String[]{"SEK to USD", "SEK to EUR"});
        directionBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        directionBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JTextField amountField = GuiTheme.textField();

        add(GuiTheme.fieldLabel("Convert:"));
        add(Box.createVerticalStrut(4));
        add(directionBox);
        add(Box.createVerticalStrut(16));
        add(GuiTheme.fieldLabel("Amount in SEK:"));
        add(Box.createVerticalStrut(4));
        add(amountField);
        add(Box.createVerticalStrut(20));

        JLabel result = GuiTheme.resultLabel();

        RoundedButton convertButton = GuiTheme.fullWidthButton("Convert", GuiTheme.PURPLE, GuiTheme.PURPLE_HOVER);
        convertButton.addActionListener(e -> {
            Double sek = GuiTheme.readPositiveDouble(this, amountField, "amount");
            if (sek == null) return;

            boolean toUsd = directionBox.getSelectedIndex() == 0;
            double converted = toUsd
                    ? Math.round(sek * EXCHANGE_RATE_SEK_TO_USD * 100.0) / 100.0
                    : Math.round(sek * EXCHANGE_RATE_SEK_TO_EUR * 100.0) / 100.0;
            String currency = toUsd ? "USD" : "EUR";

            result.setText(GuiTheme.withStamp(String.format(Locale.ENGLISH, "%.2f SEK = %.2f %s", sek, converted, currency)));
            history.add(new ConversionResult(sek + " SEK", converted + " " + currency, "="));
        });

        add(convertButton);
        add(Box.createVerticalStrut(18));
        add(result);
        add(Box.createVerticalStrut(24));
        add(GuiTheme.backButton(navigator));
    }
}
