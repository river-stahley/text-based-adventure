import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.*;

public class GameGUI {

    JFrame frame;
    JPanel title, startGame, textPanel, optionPanel, playerPanel, cluePanel;
    Container container;
    JLabel titleLabel, locationLabel, playerLabel;
    Font titleFont = new Font("Arial", Font.PLAIN, 50);
    Font startFont = new Font("Arial", Font.PLAIN, 30);
    JButton startButton, option1, option2, option3, option4;
    JTextArea textArea, footer;

    private Player player; // Assuming you have a Player class to manage player details

    //StartButtonListener startButtonListener = new StartButtonListener();

    public void startScreen(){
        // Initialize the frame
        initializeFrame(1920, 1080);

        // Prompt the user to enter their name
        String playerName = JOptionPane.showInputDialog(frame, "Enter your name: ", 
        "Player Name", JOptionPane.PLAIN_MESSAGE);

        // If the user cancels or leaves the input blank, assign a default name
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Detective";
        }

        // Initialize the player
        player = new Player(playerName); // Use the entered or default player name
        
        // Set up the title panel
        title = createPanel(675, 200, 600, 150, Color.black);
        titleLabel = createLabel("Along Came A Killer!", titleFont, Color.white);
        title.add(titleLabel);

        // Set up the start game panel
        startGame = createPanel(850, 500, 200, 100, Color.black);
        startButton = createButton("Start", startFont, Color.black, 
        Color.white, (e) -> gameScreen());
        startGame.add(startButton);

        // Add components to the frame
        container.add(title);
        container.add(startGame);

        // Make the frame visible
        frame.setVisible(true);    
    } 
    
    public void gameScreen(){
        title.setVisible(false);
        startGame.setVisible(false);

        textPanel = createPanel(100, 100, 1700, 250, Color.black);
        container.add(textPanel);

        textArea = createTextArea(100, 100, 1700, 500,
            """
            Welcome to the game! This is where the game text will go.
            You can examine the body, question the witness, search for clues, or leave the crime scene.
            """,
            startFont, Color.red, Color.white);
        textPanel.add(textArea);

        optionPanel = createPanel(650, 500, 600, 300, Color.black);
        optionPanel.setLayout(new GridLayout(4, 1));
        container.add(optionPanel);

        option1 = createButton("Examine the body", startFont, Color.black,
        Color.white, (e) -> {
            // Action for option1 button
            textArea.setText("You examine the body and find a clue.");
            option1.setVisible(false); // Hide the button after use
        });

        option2 = createButton("Question Witness", startFont, Color.black,
        Color.white, (e) -> {
            // Action for option2 button
            textArea.setText("You question the witness and gather more information.");
            option2.setVisible(false); // Hide the button after use
        });

        option3 = createButton("Search For Clues", startFont, Color.black,
        Color.white, (e) -> {
            // Action for option3 button
            textArea.setText("You search for clues and find a hidden item.");
            option3.setVisible(false); // Hide the button after use
        });

        option4 = createButton("Leave Crime Scene", startFont, Color.black,
        Color.white, (e) -> {
            // Action for option4 button
            textArea.setText("You leave the crime scene and head to the next location.");
            player.setLocation("Next Location"); // Update player location
            option4.setVisible(false); // Hide the button after use
        });

        JButton saveButton = createButton("Save Progress", startFont, Color.black,
        Color.white, (e) -> {
            try {
                player.saveProgress("save.dat");
                textArea.setText("Progress saved successfully!");
            } catch (IOException ex) {
                textArea.setText("Failed to save progress: " + ex.getMessage());
            }
        });

        JButton loadButton = createButton("Load Progress", startFont, Color.black,
        Color.white, (e) -> {
            try {
                player = Player.loadProgress("save.dat");
                textArea.setText("Progress loaded successfully!\n" + player.displayPlayerDetails());
            } catch (IOException | ClassNotFoundException ex) {
                textArea.setText("Failed to load progress: " + ex.getMessage());
            }
        });

        // Add buttons to the option panel
        optionPanel.add(option1);
        optionPanel.add(option2);
        optionPanel.add(option3);
        optionPanel.add(option4);
        optionPanel.add(saveButton);
        optionPanel.add(loadButton);

        // Add KeyBinding to handle 'C' key for displaying clues
        InputMap inputMap = frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = frame.getRootPane().getActionMap();

        // Boolean flag to track whether clues are visible
        final boolean[] cluesVisible = {false};

        inputMap.put(KeyStroke.getKeyStroke('C'), "displayClues");
        inputMap.put(KeyStroke.getKeyStroke('c'), "displayClues");
        actionMap.put("displayClues", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cluesVisible[0]) {
                    footer.setText("Press 'C' to view clues");
                } else {
                    footer.setText(player.displayClues());
                }
                cluesVisible[0] = !cluesVisible[0]; // Toggle visibility
            }
        });

        playerPanel = createPanel(100, 15, 1700, 50, Color.blue);
        playerPanel.setLayout(new BorderLayout());

        locationLabel = createLabel("Location: " + player.getLocation(), startFont, Color.white);
        playerLabel = createLabel("Player: " + player.getName(), startFont, Color.white);
        playerPanel.add(locationLabel, BorderLayout.WEST);
        playerPanel.add(playerLabel, BorderLayout.EAST);
        container.add(playerPanel);

        cluePanel = createPanel(800, 800, 300, 200, Color.black);
        cluePanel.setLayout(new BorderLayout());
        footer = createTextArea(800, 800, 300, 200,
            """
            Press 'C' to view clues
            """, startFont, Color.black, Color.white);
        cluePanel.add(footer, BorderLayout.CENTER);
        container.add(cluePanel);

        // Revalidate and repaint the frame to reflect changes
        frame.revalidate();
        frame.repaint();
    }

    // Helper method to initialize the frame
    private void initializeFrame(int width, int height) {
        if (frame == null){
            frame = new JFrame("Along Came A Killer");
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        container = frame.getContentPane();
    }

    // Helper method to create a panel
    private JPanel createPanel(int x, int y, int width, int height, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setBounds(x, y, width, height);
        panel.setBackground(backgroundColor);
        return panel;
    }

    // Helper method to create a label
    private JLabel createLabel(String text, Font font, Color foregroundColor) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(foregroundColor);
        return label;
    }

    // Helper method to create a button
    private JButton createButton(String text, Font font, Color backgroundColor, Color foregroundColor, java.awt.event.ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.addActionListener(actionListener);
        return button;
    }

    // Helper method to create a text area
    private JTextArea createTextArea(int x, int y, int width, int height, 
        String text, Font font, Color backgroundColor, Color foregroundColor) {
        JTextArea textBlock = new JTextArea(text);
        textBlock.setBounds(x, y, width, height);
        textBlock.setFont(font);
        textBlock.setBackground(backgroundColor);
        textBlock.setForeground(foregroundColor);
        textBlock.setLineWrap(true);
        textBlock.setEditable(false);
        return textBlock;
    }
}