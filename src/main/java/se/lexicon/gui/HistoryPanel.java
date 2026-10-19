package se.lexicon.gui;

import se.lexicon.converter.ConversionResult;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

public class HistoryPanel extends JPanel {

    private final JTextArea historyArea = new JTextArea();
    private final List<ConversionResult> history;

    public HistoryPanel(Navigator navigator, List<ConversionResult> history) {
        this.history = history;

        setLayout(new BorderLayout(0, 16));
        setBackground(GuiTheme.BG);
        setBorder(new EmptyBorder(28, 36, 28, 36));

        add(GuiTheme.titleLabel("Conversion History"), BorderLayout.NORTH);

        historyArea.setEditable(false);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        historyArea.setForeground(GuiTheme.TEXT_DARK);
        historyArea.setBackground(GuiTheme.CARD);
        historyArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0xD0, 0xD5, 0xDD), 1, true));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setBackground(GuiTheme.BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 8, 0, 8);
        bottom.add(GuiTheme.backButton(navigator), gbc);
        add(bottom, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refreshHistoryText();
            }
        });
    }

    private void refreshHistoryText() {
        if (history.isEmpty()) {
            historyArea.setText("No conversion results to display.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (ConversionResult result : history) {
            sb.append(index++).append(". ").append(result.getPrintResult());
        }
        sb.append("\nTotal conversions: ").append(history.size());
        historyArea.setText(sb.toString());
    }
}
