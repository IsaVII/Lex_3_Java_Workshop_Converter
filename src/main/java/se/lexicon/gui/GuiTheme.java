package se.lexicon.gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class GuiTheme {

    public static final Color BG = new Color(0xF4, 0xF6, 0xFA);
    public static final Color CARD = Color.WHITE;
    public static final Color TEXT_DARK = new Color(0x22, 0x27, 0x33);
    public static final Color BLUE = new Color(0x42, 0x6F, 0xE5);
    public static final Color BLUE_HOVER = new Color(0x31, 0x58, 0xC9);
    public static final Color GREEN = new Color(0x43, 0xA0, 0x47);
    public static final Color GREEN_HOVER = new Color(0x2E, 0x7D, 0x32);
    public static final Color PURPLE = new Color(0x7E, 0x57, 0xC2);
    public static final Color PURPLE_HOVER = new Color(0x5E, 0x35, 0xB1);
    public static final Color TEAL = new Color(0x00, 0x89, 0x7B);
    public static final Color TEAL_HOVER = new Color(0x00, 0x6B, 0x5F);
    public static final Color RED = new Color(0xE5, 0x3E, 0x3E);
    public static final Color RED_HOVER = new Color(0xC6, 0x2A, 0x2A);

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private GuiTheme() {
    }

    public static JLabel titleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(TEXT_DARK);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JLabel fieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);
        return label;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0, 0xD5, 0xDD), 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        return field;
    }

    public static JLabel resultLabel() {
        JLabel label = new JLabel(" ");
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(BLUE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static RoundedButton fullWidthButton(String text, Color base, Color hover) {
        RoundedButton button = new RoundedButton(text, base, hover);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        button.setPreferredSize(new Dimension(260, 46));
        return button;
    }

    public static RoundedButton backButton(Navigator navigator) {
        RoundedButton button = new RoundedButton("← Back to menu", TEXT_DARK, Color.BLACK);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(180, 36));
        button.setMaximumSize(new Dimension(180, 36));
        button.addActionListener(e -> navigator.goTo("menu"));
        return button;
    }

    public static String nowStamp() {
        return "Converted at: " + LocalDateTime.now().format(TIME_FORMATTER);
    }

    public static String withStamp(String resultText) {
        return "<html><center>" + resultText + "<br><font size=3 color=#6B7280>" + nowStamp() + "</font></center></html>";
    }

    public static Double readPositiveDouble(Component parent, JTextField field, String fieldName) {
        try {
            double value = Double.parseDouble(field.getText().trim());
            if (value < 0) {
                JOptionPane.showMessageDialog(parent, fieldName + " cannot be negative.", "Invalid input", JOptionPane.WARNING_MESSAGE);
                return null;
            }
            return value;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(parent, "Please enter a valid number for " + fieldName + ".", "Invalid input", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }
}
