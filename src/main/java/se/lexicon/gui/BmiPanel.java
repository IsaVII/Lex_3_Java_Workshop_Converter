package se.lexicon.gui;

import se.lexicon.converter.ConversionResult;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.Locale;

public class BmiPanel extends JPanel {

    public BmiPanel(Navigator navigator, List<ConversionResult> history) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(GuiTheme.BG);
        setBorder(new EmptyBorder(28, 36, 28, 36));

        add(GuiTheme.titleLabel("BMI Calculator"));
        add(Box.createVerticalStrut(24));

        JTextField weightField = GuiTheme.textField();
        JTextField heightField = GuiTheme.textField();

        add(GuiTheme.fieldLabel("Weight in kg (30-400):"));
        add(Box.createVerticalStrut(4));
        add(weightField);
        add(Box.createVerticalStrut(16));
        add(GuiTheme.fieldLabel("Height in metres (0.5-2.5):"));
        add(Box.createVerticalStrut(4));
        add(heightField);
        add(Box.createVerticalStrut(20));

        JLabel result = GuiTheme.resultLabel();

        RoundedButton calcButton = GuiTheme.fullWidthButton("Calculate", GuiTheme.GREEN, GuiTheme.GREEN_HOVER);
        calcButton.addActionListener(e -> {
            Double weight = GuiTheme.readPositiveDouble(this, weightField, "weight");
            if (weight == null) return;
            Double height = GuiTheme.readPositiveDouble(this, heightField, "height");
            if (height == null) return;

            if (weight < 30 || weight > 400) {
                JOptionPane.showMessageDialog(this, "Weight must be between 30 and 400 kg.", "Out of range", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (height < 0.5 || height > 2.5) {
                JOptionPane.showMessageDialog(this, "Height must be between 0.5 and 2.5 m.", "Out of range", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double bmi = Math.round(weight / (height * height) * 100.0) / 100.0;
            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi < 24.9) {
                category = "Normal weight";
            } else if (bmi < 29.9) {
                category = "Overweight";
            } else {
                category = "Obese";
            }

            result.setText(GuiTheme.withStamp(String.format(Locale.ENGLISH, "BMI: %.2f — %s", bmi, category)));
            history.add(new ConversionResult("BMI " + bmi, category, "->"));
        });

        add(calcButton);
        add(Box.createVerticalStrut(18));
        add(result);
        add(Box.createVerticalStrut(24));
        add(GuiTheme.backButton(navigator));
    }
}
