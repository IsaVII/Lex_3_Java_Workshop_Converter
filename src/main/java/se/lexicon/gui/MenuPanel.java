package se.lexicon.gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

public class MenuPanel extends JPanel {

    public MenuPanel(Navigator navigator) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GuiTheme.BG);
        setBorder(new EmptyBorder(28, 36, 28, 36));

        JLabel title = GuiTheme.titleLabel("LEXICON CONVERTER APP");
        add(title);
        add(Box.createVerticalStrut(8));

        JLabel subtitle = GuiTheme.fieldLabel("Choose a converter to get started");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(new Color(0x6B, 0x72, 0x80));
        add(subtitle);
        add(Box.createVerticalStrut(28));

        RoundedButton bmiButton = GuiTheme.fullWidthButton("BMI Calculator", GuiTheme.GREEN, GuiTheme.GREEN_HOVER);
        bmiButton.addActionListener(e -> navigator.goTo("bmi"));

        RoundedButton currencyButton = GuiTheme.fullWidthButton("Currency Converter", GuiTheme.PURPLE, GuiTheme.PURPLE_HOVER);
        currencyButton.addActionListener(e -> navigator.goTo("currency"));

        RoundedButton dataButton = GuiTheme.fullWidthButton("Data Storage Converter", GuiTheme.TEAL, GuiTheme.TEAL_HOVER);
        dataButton.addActionListener(e -> navigator.goTo("data"));

        RoundedButton historyButton = GuiTheme.fullWidthButton("Conversion History", GuiTheme.BLUE, GuiTheme.BLUE_HOVER);
        historyButton.addActionListener(e -> navigator.goTo("history"));

        RoundedButton exitButton = GuiTheme.fullWidthButton("Exit", GuiTheme.RED, GuiTheme.RED_HOVER);
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Goodbye! Close the app?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        for (RoundedButton b : List.of(bmiButton, currencyButton, dataButton, historyButton, exitButton)) {
            add(b);
            add(Box.createVerticalStrut(14));
        }
    }
}
