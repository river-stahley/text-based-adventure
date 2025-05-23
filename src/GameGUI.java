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

    // Create list of suspects
    Suspect suspect1 = new Suspect("Dr. William Patrick", "Psychiatrist");
    Suspect suspect2 = new Suspect("Mr. Paul Todd", "Business Partner");
    Suspect suspect3 = new Suspect("Mrs. Annie West", "Wife");
    Suspect suspect4 = new Suspect("Ms. Karen Turner", "Lover");
    Suspect suspect5 = new Suspect("Mr. Wayne Cady", "Rival");
    Suspect suspect6 = new Suspect("Mr. Jack Shipman", "Neighbor");
    Suspect suspect7 = new Suspect("Mr. Eugene West", "Son");

    private GameStory gameStory;
    private final Suspect[] suspects = {
        suspect1, suspect2, suspect3, suspect4, suspect5, suspect6, suspect7
    };
    private Suspect killer;

    public void startScreen(){
        // Initialize the frame
        initializeFrame(1920, 1080);
        
        killer = suspects[(int)(Math.random() * suspects.length)];
        gameStory = new GameStory(killer); // Initialize the game story

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

        // Create option buttons
        option1 = createButton("", startFont, Color.black, Color.white, e -> {});
        option2 = createButton("", startFont, Color.black, Color.white, e -> {});
        option3 = createButton("", startFont, Color.black, Color.white, e -> {});
        option4 = createButton("", startFont, Color.black, Color.white, e -> {});

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

        updateOptionButtonsForStory(); // Initialize option buttons

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
        playerLabel = createLabel("Player: Detective " + player.getName(), startFont, Color.white);
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

    private void updateOptionButtonsForStory() {
        int segment = gameStory.getCurrentSegmentIndex(); // Add this getter to GameStory if needed
        String killerName = killer.getName();

        //display killer name
        textArea.setText("Current segment: " + segment + "\nKiller: " + killerName);

        // Remove all previous listeners before adding new ones
        for (JButton btn : new JButton[]{option1, option2, option3, option4}) {
            for (var al : btn.getActionListeners()) {
                btn.removeActionListener(al);
            }
        }

        // Example: Change button text and actions based on the segment
        switch (segment) {
            case 0 -> {
                option1.setVisible(true);
                option2.setVisible(true);
                option3.setVisible(true);
                option4.setVisible(true);

                option1.setText("Examine the body");
                option1.setVisible(true);
                option1.addActionListener(e -> {
                    textArea.setText("Examining naked body...");
                    option1.setVisible(false);
                });

                option2.setText("Question witness");
                option2.setVisible(true);
                option2.addActionListener(e -> {
                    textArea.setText("You look around and see a nervous witness.");
                    option2.setVisible(false);
                });

                option3.setText("Search for clues");
                option3.setVisible(true);
                option3.addActionListener(e -> {
                    textArea.setText("The police share some details with you.");
                    option3.setVisible(false);
                });

                option4.setText("Leave crime scene");
                option4.setVisible(true);
                option4.addActionListener(e -> {
                    textArea.setText(gameStory.nextSegment());
                    //player.setLocation("Next Location"); // Update player location
                    //locationLabel.setText("Location: " + player.getLocation());
                    updateOptionButtonsForStory();
                });
            }
            case 1 -> {
                option1.setVisible(true);
                option2.setVisible(true);
                option3.setVisible(true);
                option4.setVisible(true);    
                // Example: Custom text based on killer
            switch (killerName) {
                case "Dr. William Patrick" -> {
                    option1.setText("Confront Dr. Patrick");
                    option1.addActionListener(e -> {
                        textArea.setText("Dr. Patrick seems nervous under questioning.");
                        player.setLocation("Dr. Patrick's Office");
                        locationLabel.setText("Location: " + player.getLocation());
                        option1.setVisible(false);
                    });
                    option2.setText("Check psychiatric notes");
                    option2.addActionListener(e -> {
                        textArea.setText("The notes reveal late-night visits.");
                        player.setLocation("Records Room");
                        locationLabel.setText("Location: " + player.getLocation());
                        option2.setVisible(false);
                    });
                    option3.setText("Talk to the nurse");
                    option3.addActionListener(e -> {
                        textArea.setText("The nurse mentions a secret affair.");
                        player.setLocation("Nurse's Station");
                        locationLabel.setText("Location: " + player.getLocation());
                        option3.setVisible(false);
                    });
                    option4.setText("Search the office");
                    option4.addActionListener(e -> {
                        textArea.setText("You find a hidden letter.");
                        player.setLocation("Dr. Patrick's Office");
                        locationLabel.setText("Location: " + player.getLocation());
                        option4.setVisible(false);
                    });

                }
                case "Mr. Paul Todd" -> {
                    option1.setText("Review business records");
                    option1.addActionListener(e -> {
                        textArea.setText("You find evidence of arguments.");
                        player.setLocation("Mr. Todd's Office");
                        locationLabel.setText("Location: " + player.getLocation());
                        option1.setVisible(false);
                    });
                    option2.setText("Talk to secretary");
                    option2.addActionListener(e -> {
                        textArea.setText("The secretary mentions threats.");
                        player.setLocation("Secretary's Desk");
                        locationLabel.setText("Location: " + player.getLocation());
                        option2.setVisible(false);
                    });
                    option3.setText("Inspect the trash");
                    option3.addActionListener(e -> {
                        textArea.setText("You find a torn contract.");
                        player.setLocation("Office Trash Area");
                        locationLabel.setText("Location: " + player.getLocation());
                        option3.setVisible(false);
                    });
                    option4.setText("Confront Paul Todd");
                    option4.addActionListener(e -> {
                        textArea.setText("Mr. Todd tries to bribe you.");
                        player.setLocation("Mr. Todd's Office");
                        locationLabel.setText("Location: " + player.getLocation());
                        option4.setVisible(false);
                    });
                }
                case "Mrs. Annie West" -> {
                    option1.setText("Talk to neighbors");
                    option1.addActionListener(e -> {
                        textArea.setText("Neighbors mention loud arguments.");
                        player.setLocation("West's Neighborhood");
                        locationLabel.setText("Location: " + player.getLocation());
                        option1.setVisible(false);
                    });
                    option2.setText("Search the house");
                    option2.addActionListener(e -> {
                        textArea.setText("You find a hidden letter.");
                        player.setLocation("Annie's House");
                        locationLabel.setText("Location: " + player.getLocation());
                        option2.setVisible(false);
                    });
                    option3.setText("Examine the evidence");
                    option3.addActionListener(e -> {
                        textArea.setText("Annie's fingerprints are on the weapon.");
                        player.setLocation("Police Lab");
                        locationLabel.setText("Location: " + player.getLocation());
                        option3.setVisible(false);
                    });
                    option4.setText("Confront Annie");
                    option4.addActionListener(e -> {
                        textArea.setText("Annie tries to blame the lover.");
                        player.setLocation("Annie's House");
                        locationLabel.setText("Location: " + player.getLocation());
                        option4.setVisible(false);
                    });
                }
                case "Ms. Karen Turner" -> {
                    option1.setText("Examine love letters");
                    option1.addActionListener(e -> {
                        textArea.setText("The letters reveal a secret affair.");
                        option1.setVisible(false);
                    });
                    option2.setText("Talk to friends");
                    option2.addActionListener(e -> {
                        textArea.setText("Friends mention jealousy.");
                        option2.setVisible(false);
                    });
                    option3.setText("Inspect the scene");
                    option3.addActionListener(e -> {
                        textArea.setText("You find Karen's scarf.");
                        option3.setVisible(false);
                    });
                    option4.setText("Confront Karen");
                    option4.addActionListener(e -> {
                        textArea.setText("Karen tries to flee the city.");
                        option4.setVisible(false);
                    });
                }
                case "Mr. Wayne Cady" -> {
                    option1.setText("Check emails");
                    option1.addActionListener(e -> {
                        textArea.setText("You find threatening emails.");
                        option1.setVisible(false);
                    });
                    option2.setText("Talk to witnesses");
                    option2.addActionListener(e -> {
                        textArea.setText("Witnesses mention a rivalry.");
                        option2.setVisible(false);
                    });
                    option3.setText("Inspect the car");
                    option3.addActionListener(e -> {
                        textArea.setText("Cady's car was seen near the scene.");
                        option3.setVisible(false);
                    });
                    option4.setText("Confront Cady");
                    option4.addActionListener(e -> {
                        textArea.setText("You find Cady's glove in the garden.");
                        option4.setVisible(false);
                    });
                }
                case "Mr. Jack Shipman" -> {
                    option1.setText("Talk to neighbors");
                    option1.addActionListener(e -> {
                        textArea.setText("Neighbors mention Jack's odd behavior.");
                        option1.setVisible(false);
                    });
                    option2.setText("Inspect the boots");
                    option2.addActionListener(e -> {
                        textArea.setText("You find muddy boots in Jack's house.");
                        option2.setVisible(false);
                    });
                    option3.setText("Check the timeline");
                    option3.addActionListener(e -> {
                        textArea.setText("Jack's timeline doesn't match up.");
                        option3.setVisible(false);
                    });
                    option4.setText("Confront Jack");
                    option4.addActionListener(e -> {
                        textArea.setText("Jack tries to mislead your investigation.");
                        option4.setVisible(false);
                    });
                }
                default -> {
                    // Default for other suspects
                    option1.setText("Talk to the suspect");
                }
            }

            option4.setText("Advance story");
            option4.addActionListener(e -> {
                textArea.setText(gameStory.nextSegment());
                updateOptionButtonsForStory();
            });        
            }
            // Add more cases for other segments
            default -> {
                // Hide all options if story is over
                option1.setVisible(false);
                option2.setVisible(false);
                option3.setVisible(false);
                option4.setVisible(false);
            }
        }
    }
}
