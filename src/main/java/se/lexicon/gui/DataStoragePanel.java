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

public class DataStoragePanel extends JPanel {

    private static final int DATA_SIZE = 1024;

    public DataStoragePanel(Navigator navigator, List<ConversionResult> history) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GuiTheme.BG);
        setBorder(new EmptyBorder(28, 36, 28, 36));

        add(GuiTheme.titleLabel("Data Storage Converter"));
        add(Box.createVerticalStrut(24));

        JComboBox<String> directionBox = new JComboBox<>(new String[]{"MB to GB", "KB to MB"});
        directionBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        directionBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JTextField amountField = GuiTheme.textField();

        add(GuiTheme.fieldLabel("Convert:"));
        add(Box.createVerticalStrut(4));
        add(directionBox);
        add(Box.createVerticalStrut(16));
        add(GuiTheme.fieldLabel("Amount:"));
        add(Box.createVerticalStrut(4));
        add(amountField);
        add(Box.createVerticalStrut(20));

        JLabel result = GuiTheme.resultLabel();

        RoundedButton convertButton = GuiTheme.fullWidthButton("Convert", GuiTheme.TEAL, GuiTheme.TEAL_HOVER);
        convertButton.addActionListener(e -> {
            Double amount = GuiTheme.readPositiveDouble(this, amountField, "amount");
            if (amount == null) return;

            boolean mbToGb = directionBox.getSelectedIndex() == 0;
            double converted = Math.round(amount / DATA_SIZE * 100.0) / 100.0;
            String fromUnit = mbToGb ? "MB" : "KB";
            String toUnit = mbToGb ? "GB" : "MB";

            result.setText(GuiTheme.withStamp(String.format(Locale.ENGLISH, "%.2f %s = %.2f %s", amount, fromUnit, converted, toUnit)));
            history.add(new ConversionResult(amount + " " + fromUnit, converted + " " + toUnit, "="));
        });

        add(convertButton);
        add(Box.createVerticalStrut(18));
        add(result);
        add(Box.createVerticalStrut(24));
        add(GuiTheme.backButton(navigator));
    }
}
