/*
File: AISummarizerUI.java
- Contains all GUI elements (buttons, text areas, layout, and color scheme).
- Handles user interactions (button clicks).
- On clicking “Summarize,” it calls SummaryGenerator.generateSummary(inputText)
  and displays the returned result in the output box.
*/

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class AISummarizer extends JFrame {
    private JTextArea inputBox, outputBox;

    public AISummarizer() {
        //FIXME: Wait, all of this was done using absolute positioning? This seems unsustainable for making changes...
        //Update (11/17): Changed from absolute positioning to relative positioning via layout use and rigid areas.

        setTitle("AI Summarizer Demo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel gridPanel = new GridPanel(); //gridPanel = Main Panel where everything is displayed
        gridPanel.setLayout(new BorderLayout());
        setContentPane(gridPanel);

        JPanel hudPanelWrapper = new JPanel();  //Wrapper panel for hudPanel to streamline formatting
        hudPanelWrapper.setLayout(new BoxLayout(hudPanelWrapper, BoxLayout.Y_AXIS));
        hudPanelWrapper.setOpaque(false);

        JPanel centerPanelWrapper = new JPanel();   //Wrapper panel for centerPanel
        centerPanelWrapper.setLayout(new BoxLayout(centerPanelWrapper, BoxLayout.Y_AXIS));
        centerPanelWrapper.setOpaque(false);

        JPanel southPanelWrapper = new JPanel();    //Wrapper panel for southPanel
        southPanelWrapper.setLayout(new BoxLayout(southPanelWrapper, BoxLayout.Y_AXIS));
        southPanelWrapper.setOpaque(false);

        JPanel hudPanel = new JPanel(); //Buttons and title should go here
        hudPanel.setLayout(new BoxLayout(hudPanel, BoxLayout.X_AXIS));
        hudPanel.setOpaque(false);
        hudPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        hudPanelWrapper.add(hudPanel);
        hudPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel centerPanel = new JPanel();  //Text fields should go here
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setOpaque(false);
        centerPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanelWrapper.add(centerPanel);
        centerPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel southPanel = new JPanel();   //Summarize button and disclaimer should go here
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
        southPanel.setOpaque(false);
        southPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        southPanelWrapper.add(southPanel);
        southPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        Font font = new Font("Times New Roman", Font.PLAIN, 14);

        hudPanel.add(Box.createRigidArea(new Dimension (10, 0)));

        JLabel title = new JLabel("AI Summarizer Demo");
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        hudPanel.add(title);

        hudPanel.add(Box.createRigidArea(new Dimension (40, 0)));

        JButton homeBtn = new JButton("Home");
        homeBtn.setFont(font);
        hudPanel.add(homeBtn);

        hudPanel.add(Box.createRigidArea(new Dimension (20, 0)));

        JButton historyBtn = new JButton("History");
        historyBtn.setFont(font);
        hudPanel.add(historyBtn);

        hudPanel.add(Box.createRigidArea(new Dimension (20, 0)));

        JButton contactBtn = new JButton("Contact");
        contactBtn.setFont(font);
        hudPanel.add(contactBtn);

        hudPanel.add(Box.createRigidArea(new Dimension (20, 0)));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(font);
        hudPanel.add(logoutBtn);

        hudPanel.add(Box.createRigidArea(new Dimension (20, 0)));

        JButton accountBtn = new JButton("My Account");
        accountBtn.setFont(font);
        hudPanel.add(accountBtn);

        hudPanel.add(Box.createRigidArea(new Dimension (10, 0)));

        centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        inputBox = new JTextArea("Paste medical record as text here.");
        inputBox.setFont(font);
        inputBox.setLineWrap(true);
        JScrollPane inputScroll = new JScrollPane(inputBox);
        inputScroll.setPreferredSize(new Dimension(300, 270));
        centerPanel.add(inputScroll);

        centerPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        outputBox = new JTextArea("AI-generated summary will be shown here.");
        outputBox.setFont(font);
        outputBox.setLineWrap(true);
        outputBox.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputBox);
        outputScroll.setPreferredSize(new Dimension(300, 270));
        centerPanel.add(outputScroll);

        Color gray = new Color(128, 128 ,128);
        inputBox.setDisabledTextColor(gray);
        outputBox.setDisabledTextColor(gray);

        centerPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        //Upload file button code
        JPanel uploadFilePanel = new JPanel();
        uploadFilePanel.setLayout(new FlowLayout());
        JLabel chosenFileText = new JLabel("Selected File: None");

        AtomicBoolean fileInsteadOfText = new AtomicBoolean(false);
        JButton uploadFileButton = new JButton("Upload File");
        uploadFilePanel.setOpaque(false);
        final File[] selectedFile = new File[1];

        uploadFileButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Upload File (supports .pdf and .txt)", "pdf", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile[0] = chooser.getSelectedFile();
                inputBox.setEditable(false);
                inputBox.setEnabled(false);
                inputBox.setText("File selected. Text field locked.");   //Clear text from input box upon selecting a file
                chosenFileText.setText("Selected File: " + chooser.getSelectedFile().getName());
                fileInsteadOfText.set(true);
            }
            else if (returnVal == JFileChooser.CANCEL_OPTION) {
                inputBox.setEditable(true);
                inputBox.setEnabled(true);
                fileInsteadOfText.set(false);
                inputBox.setText("Paste medical record as text here.");
                chosenFileText.setText("Selected File: None");
            }
            else {
                System.out.println("Error, invalid file type!");
            }
        });
        JLabel uploadLabel1 = new JLabel("or choose a file:");
        JLabel uploadLabel2 = new JLabel("(supports .pdf and .txt file types)");

        uploadFileButton.setFont(new Font("Times New Roman", Font.BOLD, 12));

        uploadFilePanel.add(uploadLabel1);
        uploadFilePanel.add(uploadFileButton);
        uploadFilePanel.add(uploadLabel2);
        uploadFilePanel.add(Box.createRigidArea(new Dimension(50, 0)));
        uploadFilePanel.add(chosenFileText);
        centerPanelWrapper.add(uploadFilePanel);
        centerPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        //End upload file button code

        JButton summarizeBtn = new JButton("Summarize");
        summarizeBtn.setFont(new Font("Times New Roman", Font.BOLD, 14));
        summarizeBtn.setBackground(Color.BLACK);
        summarizeBtn.setForeground(Color.WHITE);
//        summarizeBtn.setBounds(350, 420, 180, 40);
        summarizeBtn.addActionListener(e -> {
            if (fileInsteadOfText.get()) {
                String result;
                try {
                    result = SummaryGenerator.parseDataFirst(selectedFile[0]);
                }
                catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                outputBox.setText(result);
            }
            else {
                String inputText = inputBox.getText();
                String result = SummaryGenerator.generateSummary(inputText);
                outputBox.setText(result);
            }
        });
        southPanel.add(summarizeBtn);

        southPanel.add(Box.createRigidArea(new Dimension(50 ,0)));

        JLabel riskNote = new JLabel("**Risk Note: Do not take AI summary as medical advise.");
        riskNote.setFont(new Font("Times New Roman", Font.BOLD, 12));
        riskNote.setBounds(270, 480, 400, 30);
        southPanel.add(riskNote);

        southPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));
        southPanelWrapper.add(southPanel);
        southPanelWrapper.add(Box.createRigidArea(new Dimension(0, 20)));

        gridPanel.add(hudPanelWrapper, BorderLayout.NORTH);
        gridPanel.add(centerPanelWrapper, BorderLayout.CENTER);
        gridPanel.add(southPanelWrapper, BorderLayout.SOUTH);

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