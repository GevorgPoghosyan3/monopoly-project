package am.aua.monopoly.ui;

import am.aua.monopoly.core.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonopolyGUI extends JFrame {
    private static final int NUM_SPACES = Board.BOARD_SIZE;  // e.g., 40 for a standard Monopoly board
    private JFrame frame;
    private JLayeredPane layeredPane;
    private JPanel boardPanel;
    private JLabel imageLabel;
    private JPanel controlPanel, infoPanel;
    private JLabel currentPlayerLabel, currentBalanceLabel;
    private JButton rollDiceButton, buyButton, sellButton, nextTurnButton, quitButton;
    private JButton teleportButton, buildButton, mortgageButton, demortgageButton, showPropertiesButton;
    private Monopoly monopoly;
    private Player currentPlayer;
    private ImageIcon monopolyImage;
    private ArrayList<JLabel> playerLabels = new ArrayList<>();
    private static final int BOARD_SIZE = 600; // Size of the board in pixels
    private static final int NUM_SPACES_PER_SIDE = 10; // Including the corner as part of two sides
    private static final int SPACE_SIZE = BOARD_SIZE / (NUM_SPACES_PER_SIDE); // Adjusted for even distribution

    private static final ArrayList<int[]> POSITIONS = initializePositions();

    public void run(){
        setTitle("Monopoly Game");
        loadBoard();
        initializeGame();
        initializeUI();
        updatePlayerInfo();
        frame.setSize(new Dimension(1000, 800));
        frame.setResizable(false);

    }

    private static ArrayList<int[]> initializePositions() {
        ArrayList<int[]> positions = new ArrayList<>();

        int boardSize = 600; // Size of the board
        int numSpacesPerSide = 10; // Number of spaces per side, excluding corners

        // Define corner and regular tile sizes
        int cornerSize = boardSize / (numSpacesPerSide + 1); // Larger corner tiles
        int tileSize = (boardSize - 2 * cornerSize) / numSpacesPerSide; // Remaining space divided by the number of tiles

        // Start at the bottom right corner (Go)
        int startX = boardSize - cornerSize;
        int startY = boardSize - cornerSize;

        // Adjustments
        int bottomRowAdjustment = -5; // Move left to align properly on the bottom row
        int topRowAdjustment = 5;     // Move right to align properly on the top row

        // Bottom row, right to left
        positions.add(new int[]{startX, startY}); // Go (corner)
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{startX - i * tileSize + bottomRowAdjustment, startY});
        }
        positions.add(new int[]{cornerSize + bottomRowAdjustment, startY}); // Jail (corner)

        // Left column, bottom to top
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{0, startY - i * tileSize});
        }
        positions.add(new int[]{0, cornerSize}); // Free Parking (corner)

        // Top row, left to right
        positions.add(new int[]{cornerSize + topRowAdjustment, 0}); // Free Parking (corner)
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{i * tileSize + cornerSize + topRowAdjustment, 0});
        }
        positions.add(new int[]{startX + topRowAdjustment, 0}); // Go to Jail (corner)

        // Right column, top to bottom
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{startX, i * tileSize + cornerSize});
        }

        // Closing the loop back to the start position (Go)
        positions.add(new int[]{startX, startY}); // Go (corner, repeated to indicate full loop)

        return positions;
    }







    private void loadBoard() {
        // Load the original image icon
        ImageIcon originalIcon = new ImageIcon("resources/board1.jpg");  // Ensure path is correct
        Image originalImage = originalIcon.getImage();

        // Scale the image to match the grid size (e.g., 800x800 pixels)
        Image scaledImage = originalImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        monopolyImage = new ImageIcon(scaledImage);

        // Create the label with the scaled image
        imageLabel = new JLabel(monopolyImage);
        imageLabel.setBounds(0, 0, 600, 600);  // Ensure these dimensions match the grid size

        // Setup the layered pane
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        layeredPane.add(imageLabel, Integer.valueOf(1));  // Image on top

        // Setup the transparent grid panel
        boardPanel = new JPanel(new GridLayout(10, 10));  // Adjust grid layout as needed
        boardPanel.setBounds(0, 0, 600, 600);  // Ensure these dimensions match the image size
        boardPanel.setOpaque(false);

        // Add empty panels to the grid to show the grid structure
        for (int i = 0; i < 100; i++) {  // Adjust count based on grid size
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setOpaque(false);
            boardPanel.add(panel);
        }


        String[] playerFiles = {"player1.png", "player2.png", "player3.png"};
        int[] playerPositions = POSITIONS.getFirst();

        for (int i = 0; i < playerFiles.length; i++) {
            ImageIcon playerIcon = new ImageIcon(playerFiles[i]);
            Image playerImage = playerIcon.getImage();
            Image scaledPlayerImage = playerImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel playerLabel = new JLabel(new ImageIcon(scaledPlayerImage));
            playerLabel.setBounds(playerPositions[0], playerPositions[1], 30, 30);
            layeredPane.add(playerLabel, Integer.valueOf(2));
            playerLabels.add(playerLabel);
        }

        // Add the grid panel to the layered pane behind the image
        layeredPane.add(boardPanel, Integer.valueOf(0));  // Grid at the bottom
    }

    public void movePlayer(int playerId, int newX, int newY) {
        // Retrieve the player's label from a stored reference
        JLabel playerLabel = playerLabels.get(playerId);
        playerLabel.setBounds(newX, newY, 50, 50);

        // Refresh the display
        playerLabel.revalidate();
        playerLabel.repaint();
    }


    private void setupInfoPanel() {
        infoPanel = new JPanel(new GridLayout(3, 1));
        currentPlayerLabel = new JLabel("Current Player: ");
        currentBalanceLabel = new JLabel("Balance: ");

        infoPanel.add(currentPlayerLabel);
        infoPanel.add(currentBalanceLabel);
    }


    private void handleRollDice() {
        if (!monopoly.gameOver()) {
            monopoly.bankrupt(currentPlayer);

            // Check if the player has already rolled or if they rolled doubles and can roll again
            if (!monopoly.getHasRolled() || monopoly.getHasRolledDouble()) {
                try {
                    String cardContent = monopoly.move(currentPlayer, Dice.roll());
                    movePlayer(monopoly.getPlayers().indexOf(currentPlayer), POSITIONS.get(currentPlayer.getPosition())[0], POSITIONS.get(currentPlayer.getPosition())[1]);
                    if (cardContent != null) {
                        JOptionPane.showMessageDialog(frame, cardContent, "Your Card", JOptionPane.INFORMATION_MESSAGE);
                    }
                    updatePlayerInfo(); // Update the player's GUI information
                    // Displaying the dice result in a dialog
                    JOptionPane.showMessageDialog(frame, Dice.toStringDice() + "\n" +
                                    currentPlayer.getName() + " moves to " + Board.tileAt(currentPlayer.getPosition()).getName(),
                            "Dice Roll", JOptionPane.INFORMATION_MESSAGE);
                } catch (OutOfMoneyException e) {
                    // Handle case where player cannot pay for an action triggered by the dice roll
                    JOptionPane.showMessageDialog(frame, "Out of money: " + e.getMessage(), "Financial Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Inform the player that they cannot roll the dice again
                JOptionPane.showMessageDialog(frame, "You have already rolled the dice", "Roll Dice", JOptionPane.WARNING_MESSAGE);
            }
        }else  JOptionPane.showMessageDialog(frame, "Game Over" + currentPlayer.getName() + " Won!", "Over", JOptionPane.WARNING_MESSAGE);
    }


    private void handleBuyProperty() {
        if (monopoly.getHasRolled()) {
            try {
                Monopoly.buyProperty(currentPlayer, currentPlayer.getPosition());
                JOptionPane.showMessageDialog(frame, "Property bought at position " + Board.tileAt(currentPlayer.getPosition()).getName());
                updatePlayerInfo();
            } catch (InvalidPurchaseException | OutOfMoneyException e) {
                JOptionPane.showMessageDialog(frame, "Cannot buy property: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(frame, "You should roll the dice.", "Dice Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleSellProperty() {
        // Retrieve the properties list
        java.util.List<Property> properties = Monopoly.showProperties(currentPlayer);

        // Check if the player has properties to sell
        if (properties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You don't have any properties to sell.", "No Properties", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Convert properties list to a readable format for display in a JComboBox
        String[] propertyNames = properties.stream().map(Property::getName).toArray(String[]::new);

        // Create a JComboBox to let the user select a property
        JComboBox<String> propertyCombo = new JComboBox<>(propertyNames);
        JOptionPane.showMessageDialog(null, propertyCombo, "Select a property to sell", JOptionPane.QUESTION_MESSAGE);

        int selectedPropertyIndex = propertyCombo.getSelectedIndex();
        if (selectedPropertyIndex >= 0) {
            try {
                JOptionPane.showMessageDialog(frame, "Property sold: " + properties.get(selectedPropertyIndex).getName(), "Property Sold", JOptionPane.INFORMATION_MESSAGE);
                Monopoly.sellProperty(currentPlayer, properties.get(selectedPropertyIndex));
            } catch (OutOfMoneyException e) {
                JOptionPane.showMessageDialog(frame, "Error selling property: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        updatePlayerInfo();
    }

    private void handleTeleport() {
        // Retrieve railroad properties the player can teleport to
        List<Property> railroadProperties = Monopoly.ElevatorChecker(currentPlayer);

        if (railroadProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No elevators available to teleport.", "Teleport Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (railroadProperties.size() > 1) {
            String[] propertyNames = railroadProperties.stream().map(Property::getName).toArray(String[]::new);
            JComboBox<String> propertyCombo = new JComboBox<>(propertyNames);
            int result = JOptionPane.showConfirmDialog(null, propertyCombo, "Select a railroad to teleport to", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                int selectedPropertyIndex = propertyCombo.getSelectedIndex();
                if (selectedPropertyIndex >= 0) {
                    try {
                        monopoly.teleport(currentPlayer, railroadProperties.get(selectedPropertyIndex));
                        JOptionPane.showMessageDialog(frame, "Teleported to " + railroadProperties.get(selectedPropertyIndex).getName(), "Teleport Successful", JOptionPane.INFORMATION_MESSAGE);
                    } catch (InvalidTeleportLocationException e) {
                        JOptionPane.showMessageDialog(frame, "Error teleporting: " + e.getMessage(), "Teleport Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "No elevators available to teleport.", "Teleport Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        updatePlayerInfo();
    }


    private void handleBuild() {
        // Retrieve properties of the same color
        java.util.List<Property> buildableProperties = Monopoly.checkSameColorProps(currentPlayer);

        // Check if there are properties to build on
        if (buildableProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No properties available for building.", "Build Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Convert properties list to a readable format for JComboBox
        String[] propertyNames = buildableProperties.stream().map(Property::getName).toArray(String[]::new);

        // Create JComboBox to let the user select a property
        JComboBox<String> propertyCombo = new JComboBox<>(propertyNames);
        int result = JOptionPane.showConfirmDialog(null, propertyCombo, "Select a property to build on", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedPropertyIndex = propertyCombo.getSelectedIndex();
            if (selectedPropertyIndex >= 0) {
                Property buildProperty = buildableProperties.get(selectedPropertyIndex);
                try {
                    Monopoly.build(currentPlayer, buildProperty);
                    JOptionPane.showMessageDialog(frame, "Built on " + buildProperty.getName() + ". Now has " + buildProperty.getNumberOfHouses() + " houses.", "Build Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (InvalidNumberOfHousesException | OutOfMoneyException e) {
                    JOptionPane.showMessageDialog(frame, "Error in building: " + e.getMessage(), "Build Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updatePlayerInfo();
    }

    private void handleMortgage() {
        // Retrieve properties not under mortgage
        java.util.List<Property> mortgageableProperties = Monopoly.checkNotUnderMortgage(currentPlayer);

        if (mortgageableProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No properties available to mortgage.", "Mortgage Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] propertyNames = mortgageableProperties.stream().map(Property::getName).toArray(String[]::new);
        JComboBox<String> propertyCombo = new JComboBox<>(propertyNames);
        int result = JOptionPane.showConfirmDialog(null, propertyCombo, "Select a property to mortgage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedPropertyIndex = propertyCombo.getSelectedIndex();
            if (selectedPropertyIndex >= 0) {
                Property mortgageProperty = mortgageableProperties.get(selectedPropertyIndex);
                try {
                    Monopoly.putUnderMortgage(currentPlayer, mortgageProperty);
                    JOptionPane.showMessageDialog(frame, "Mortgaged " + mortgageProperty.getName() + ". You got $" + mortgageProperty.getPrice(), "Mortgage Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (OutOfMoneyException e) {
                    JOptionPane.showMessageDialog(frame, "Error mortgaging property: " + e.getMessage(), "Mortgage Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updatePlayerInfo();
    }

    private void handleDemortgage() {
        // Retrieve properties currently under mortgage
        java.util.List<Property> demortgageableProperties = Monopoly.checkUnderMortgage(currentPlayer);

        if (demortgageableProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No properties available to demortgage.", "Demortgage Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] propertyNames = demortgageableProperties.stream().map(Property::getName).toArray(String[]::new);
        JComboBox<String> propertyCombo = new JComboBox<>(propertyNames);
        int result = JOptionPane.showConfirmDialog(null, propertyCombo, "Select a property to demortgage", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int selectedPropertyIndex = propertyCombo.getSelectedIndex();
            if (selectedPropertyIndex >= 0) {
                Property mortgageProperty = demortgageableProperties.get(selectedPropertyIndex);
                try {
                    Monopoly.deMortgage(currentPlayer, mortgageProperty);
                    JOptionPane.showMessageDialog(frame, "Demortgaged " + mortgageProperty.getName() + ". You paid $" + mortgageProperty.getPrice(), "Demortgage Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (OutOfMoneyException e) {
                    JOptionPane.showMessageDialog(frame, "Error demortgaging property: " + e.getMessage(), "Demortgage Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updatePlayerInfo();
    }

    private void handleNextTurn() {
        if (monopoly.getHasRolled() && !monopoly.getHasRolledDouble()) {
            currentPlayer = monopoly.getTurn();
            updatePlayerInfo();
            JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "'s turn.");
        } else
            JOptionPane.showMessageDialog(frame, "You should roll the dice.", "Dice Error", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleQuit() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit the game?", "Confirm Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try {
                monopoly.leaveTheGame(currentPlayer);  // Assuming leaveTheGame handles player removal and game state
                currentPlayer = monopoly.getTurn();  // Update to the next player
                JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "'s turn with type " + currentPlayer.getType(), "Next Player", JOptionPane.INFORMATION_MESSAGE);
                updatePlayerInfo();
            } catch (Exception e) {  // Catch any potential exceptions thrown by the game logic
                JOptionPane.showMessageDialog(frame, "Error quitting game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void handleShowProperties() {
        java.util.List<Property> properties = Monopoly.showProperties(currentPlayer);

        if (properties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You don't have properties yet.", "No Properties", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder propertyList = new StringBuilder("Your properties:\n");
            for (Property property : properties) {
                propertyList.append(property.getName())
                        .append(" - $")
                        .append(property.getPrice())
                        .append(" - Houses: ")
                        .append(property.getNumberOfHouses())
                        .append("\n");
            }

            JTextArea textArea = new JTextArea(propertyList.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(350, 200));

            JOptionPane.showMessageDialog(frame, scrollPane, "Properties", JOptionPane.INFORMATION_MESSAGE);
        }
    }





    private void updatePlayerInfo() {
        currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());
        currentBalanceLabel.setText("Balance: $" + currentPlayer.getMoney());
    }



    private void setupControlPanel() {
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        rollDiceButton = new JButton("Roll Dice");
        buyButton = new JButton("Buy");
        sellButton = new JButton("Sell");
        teleportButton = new JButton("Teleport");
        buildButton = new JButton("Build");
        mortgageButton = new JButton("Mortgage");
        demortgageButton = new JButton("De-Mortgage");
        showPropertiesButton = new JButton("My Properties");
        nextTurnButton = new JButton("Next Turn");
        quitButton = new JButton("Quit Game");

        controlPanel.add(rollDiceButton);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);
        controlPanel.add(teleportButton);
        controlPanel.add(buildButton);
        controlPanel.add(mortgageButton);
        controlPanel.add(demortgageButton);
        controlPanel.add(showPropertiesButton);
        controlPanel.add(nextTurnButton);
        controlPanel.add(quitButton);

        rollDiceButton.addActionListener(e -> handleRollDice());
        buyButton.addActionListener(e -> handleBuyProperty());
        sellButton.addActionListener(e -> handleSellProperty());
        teleportButton.addActionListener(e -> handleTeleport());
        buildButton.addActionListener(e -> handleBuild());
        mortgageButton.addActionListener(e -> handleMortgage());
        demortgageButton.addActionListener(e -> handleDemortgage());
        showPropertiesButton.addActionListener(e -> handleShowProperties());
        nextTurnButton.addActionListener(e -> handleNextTurn());
        quitButton.addActionListener(e -> handleQuit());
    }

    private void initializeUI() {
        frame = new JFrame("Monopoly Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());
        frame.add(layeredPane, BorderLayout.CENTER);
        setupControlPanel();
        setupInfoPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }


    private void initializeGame() {
        // Ask for the number of players
        String input = JOptionPane.showInputDialog(frame, "Welcome to Monopoly: AUA Edition! \n Enter the Number of Players:");
        int numberOfPlayers = 0;
        try {
            numberOfPlayers = Integer.parseInt(input);
            monopoly = new Monopoly(numberOfPlayers);
        } catch (NumberFormatException | InvalidNumberOfPlayersException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Collect player names
        String[] playerNames = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            playerNames[i] = JOptionPane.showInputDialog(frame, "Enter player " + (i + 1) + " name:");
            if (playerNames[i] == null || playerNames[i].trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Player name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                i--;  // Decrement i to redo the input for this player
            }
        }
        monopoly.setPlayers(playerNames);
        currentPlayer = monopoly.getTurn();  // Start the game with the first player's turn
    }
}
