/*
File: AISummarizerUI.java
- Contains all GUI elements (buttons, text areas, layout, and color scheme).
- Handles user interactions (button clicks).
- On clicking “Summarize,” it calls SummaryGenerator.generateSummary(inputText)
  and displays the returned result in the output box.
*/

import javax.swing.*;
import java.awt.*;

public class AISummarizer extends JFrame {
    private JTextArea inputBox, outputBox;

    public AISummarizer() {
        setTitle("AI Summarizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel gridPanel = new GridPanel();
        gridPanel.setLayout(null);
        setContentPane(gridPanel);

        Font font = new Font("Times New Roman", Font.PLAIN, 14);

        JLabel title = new JLabel("AI Summarizer");
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBounds(40, 15, 200, 30);
        gridPanel.add(title);

        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(font);
        homeBtn.setBounds(250, 20, 80, 25);
        gridPanel.add(homeBtn);

        JButton historyBtn = new JButton("History");
        historyBtn.setFont(font);
        historyBtn.setBounds(340, 20, 80, 25);
        gridPanel.add(historyBtn);

        JButton contactBtn = new JButton("Contact");
        contactBtn.setFont(font);
        contactBtn.setBounds(430, 20, 80, 25);
        gridPanel.add(contactBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(font);
        logoutBtn.setBounds(530, 20, 80, 25);
        gridPanel.add(logoutBtn);

        JButton accountBtn = new JButton("My Account");
        accountBtn.setFont(font);
        accountBtn.setBounds(630, 15, 130, 35);
        gridPanel.add(accountBtn);

        inputBox = new JTextArea("paste medical record as text here");
        inputBox.setFont(font);
        inputBox.setLineWrap(true);
        JScrollPane inputScroll = new JScrollPane(inputBox);
        inputScroll.setBounds(140, 120, 300, 270);
        gridPanel.add(inputScroll);

        outputBox = new JTextArea("generated AI summary will be shown here");
        outputBox.setFont(font);
        outputBox.setLineWrap(true);
        outputBox.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputBox);
        outputScroll.setBounds(460, 120, 300, 270);
        gridPanel.add(outputScroll);

        JButton summarizeBtn = new JButton("Summarize");
        summarizeBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        summarizeBtn.setBackground(Color.BLACK);
        summarizeBtn.setForeground(Color.WHITE);
        summarizeBtn.setBounds(350, 420, 180, 40);
        summarizeBtn.addActionListener(e -> {
            String inputText = inputBox.getText();
            String result = SummaryGenerator.generateSummary(inputText);
            outputBox.setText(result);
        });
        gridPanel.add(summarizeBtn);

        JLabel riskNote = new JLabel("Risk Note: Do not take AI summary as medical advise.");
        riskNote.setFont(new Font("Times New Roman", Font.ITALIC, 12));
        riskNote.setBounds(270, 480, 400, 30);
        gridPanel.add(riskNote);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AISummarizer::new);
    }
}

class GridPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(183, 214, 126));
        g.setColor(new Color(150, 190, 110));
        int gridSize = 25;
        for (int x = 0; x < getWidth(); x += gridSize) g.drawLine(x, 0, x, getHeight());
        for (int y = 0; y < getHeight(); y += gridSize) g.drawLine(0, y, getWidth(), y);
    }
}