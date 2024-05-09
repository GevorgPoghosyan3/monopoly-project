package am.aua.monopoly.ui;

import am.aua.monopoly.core.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MonopolyGUI class represents the graphical user interface for the Monopoly game.
 */
public class MonopolyGUI extends JFrame {
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
    private static final int BOARD_SIZE = 600;
    private static final int NUM_SPACES_PER_SIDE = 10;
    private static final int SPACE_SIZE = BOARD_SIZE / (NUM_SPACES_PER_SIDE);

    private static final ArrayList<int[]> POSITIONS = initializePositions();

    /**
     * Initializes the user interface.
     */
    private void initializeUI() {
        frame = new JFrame("Monopoly Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(layeredPane, BorderLayout.CENTER);
        setupControlPanel();
        setupInfoPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(infoPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    /**
     * Initializes the game.
     */
    private void initializeGame() {
        String input = JOptionPane.showInputDialog(frame, "Welcome to Monopoly: AUA Edition! \n Enter the Number of Players:");
        int numberOfPlayers = 0;
        try {
            numberOfPlayers = Integer.parseInt(input);
            monopoly = new Monopoly(numberOfPlayers);
        } catch (NumberFormatException | InvalidNumberOfPlayersException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String[] playerNames = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            playerNames[i] = JOptionPane.showInputDialog(frame, "Enter player " + (i + 1) + " name:");
            if (playerNames[i] == null || playerNames[i].trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Player name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                i--;
            }
        }
        monopoly.setPlayers(playerNames);
        currentPlayer = monopoly.getTurn();
    }

    /**
     * Runs the game.
     */
    public void run() {
        initializeGame();
        setTitle("Monopoly Game");
        loadBoard();
        initializeUI();
        updatePlayerInfo();
        frame.setSize(new Dimension(1100, 800));
        frame.setResizable(false);
    }

    /**
     * Initializes the positions of the tiles on the board.
     *
     * @return ArrayList of positions.
     */
    private static ArrayList<int[]> initializePositions() {
        ArrayList<int[]> positions = new ArrayList<>();
        int boardSize = 600;
        int numSpacesPerSide = 10;
        int cornerSize = boardSize / (numSpacesPerSide + 1);
        int tileSize = (boardSize - 2 * cornerSize) / numSpacesPerSide;
        int startX = boardSize - cornerSize;
        int startY = boardSize - cornerSize;
        int bottomRowAdjustment = -5;
        int topRowAdjustment = 5;
        positions.add(new int[]{startX, startY});
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{startX - i * tileSize + bottomRowAdjustment, startY});
        }
        positions.add(new int[]{cornerSize + bottomRowAdjustment, startY});
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{0, startY - i * tileSize});
        }
        positions.add(new int[]{0, cornerSize});
        positions.add(new int[]{cornerSize + topRowAdjustment, 0});
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{i * tileSize + cornerSize + topRowAdjustment, 0});
        }
        positions.add(new int[]{startX + topRowAdjustment, 0});
        for (int i = 1; i < numSpacesPerSide; i++) {
            positions.add(new int[]{startX, i * tileSize + cornerSize});
        }
        positions.add(new int[]{startX, startY});
        return positions;
    }

    /**
     * Loads the game board.
     */
    private void loadBoard() {
        ImageIcon originalIcon = new ImageIcon("resources/board1.jpg");
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        monopolyImage = new ImageIcon(scaledImage);
        imageLabel = new JLabel(monopolyImage);
        imageLabel.setBounds(0, 0, 600, 600);
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        layeredPane.add(imageLabel, Integer.valueOf(1));
        boardPanel = new JPanel(new GridLayout(10, 10));
        boardPanel.setBounds(0, 0, 600, 600);
        boardPanel.setOpaque(false);
        for (int i = 0; i < 100; i++) {
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.setOpaque(false);
            boardPanel.add(panel);
        }
        String[] playerFiles = {"player1.png", "player2.png", "player3.png", "player4.png", "player5.png"};
        int[] playerPositions = POSITIONS.get(0);
        for (int i = 0; i < monopoly.getPlayers().size(); i++) {
            ImageIcon playerIcon = new ImageIcon("resources/" + playerFiles[i]);
            Image playerImage = playerIcon.getImage();
            Image scaledPlayerImage = playerImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JLabel playerLabel = new JLabel(new ImageIcon(scaledPlayerImage));
            playerLabel.setBounds(playerPositions[0], playerPositions[1], 30, 30);
            layeredPane.add(playerLabel, Integer.valueOf(2));
            playerLabels.add(playerLabel);
        }
        layeredPane.add(boardPanel, Integer.valueOf(0));
    }

    /**
     * Moves the player label to the specified position on the game board.
     *
     * @param playerId The ID of the player whose label needs to be moved.
     * @param newX     The new X-coordinate of the player label.
     * @param newY     The new Y-coordinate of the player label.
     */
    private void movePlayer(int playerId, int newX, int newY) {
        JLabel playerLabel = playerLabels.get(playerId);
        playerLabel.setBounds(newX, newY, 50, 50);
        playerLabel.revalidate();
        playerLabel.repaint();
    }

    /**
     * Sets up the information panel displaying the current player and balance.
     */
    private void setupInfoPanel() {
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 100, 220));
        infoPanel.setBackground(new Color(249, 255, 205));

        currentPlayerLabel = new JLabel("Current Player: " + currentPlayer.getName());
        currentPlayerLabel.setFont(new Font("Serif", Font.BOLD, 20));
        currentPlayerLabel.setForeground(new Color(153, 153, 172));
        currentPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        currentBalanceLabel = new JLabel("Balance: $" + currentPlayer.getMoney());
        currentBalanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        currentBalanceLabel.setForeground(new Color(36, 108, 21));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        infoPanel.add(Box.createVerticalStrut(30));
        infoPanel.add(currentPlayerLabel);
        infoPanel.add(Box.createVerticalStrut(15));
        infoPanel.add(currentBalanceLabel);
        infoPanel.add(Box.createVerticalStrut(30));
    }

    /**
     * Creates a styled JButton with custom font and background color.
     *
     * @param text The text to be displayed on the button.
     * @return The styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(new Color(240, 240, 240));
        return button;
    }

    /**
     * Handles the action when the roll dice button is clicked.
     */
    private void handleRollDice() {
        if (!monopoly.gameOver()) {
            monopoly.bankrupt(currentPlayer);

            if (!monopoly.getHasRolled() || monopoly.getHasRolledDouble()) {
                try {
                    String cardContent = monopoly.move(currentPlayer, Dice.roll());
                    movePlayer(monopoly.getPlayers().indexOf(currentPlayer), POSITIONS.get(currentPlayer.getPosition())[0], POSITIONS.get(currentPlayer.getPosition())[1]);
                    if (cardContent != null) {
                        JOptionPane.showMessageDialog(frame, cardContent, "Your Card", JOptionPane.INFORMATION_MESSAGE);
                    }
                    updatePlayerInfo();
                    JOptionPane.showMessageDialog(frame, Dice.toStringDice() + "\n" +
                                    currentPlayer.getName() + " moves to " + Board.tileAt(currentPlayer.getPosition()).getName(),
                            "Dice Roll", JOptionPane.INFORMATION_MESSAGE);
                } catch (OutOfMoneyException e) {
                    JOptionPane.showMessageDialog(frame, "Out of money: " + e.getMessage(), "Financial Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "You have already rolled the dice", "Roll Dice", JOptionPane.WARNING_MESSAGE);
            }
        } else
            JOptionPane.showMessageDialog(frame, "Game Over " + currentPlayer.getName() + " Won!", "Over", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Handles the action when the buy property button is clicked.
     */
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

    /**
     * Handles the action when the sell property button is clicked.
     */
    private void handleSellProperty() {
        java.util.List<Property> properties = Monopoly.showProperties(currentPlayer);

        if (properties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "You don't have any properties to sell.", "No Properties", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] propertyNames = properties.stream().map(Property::getName).toArray(String[]::new);

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

    /**
     * Handles the action when teleport button is clicked.
     */
    private void handleTeleport() {
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
                        movePlayer(monopoly.getPlayers().indexOf(currentPlayer), POSITIONS.get(currentPlayer.getPosition())[0], POSITIONS.get(currentPlayer.getPosition())[1]);
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

    /**
     * Handles the action when the build button is clicked.
     */
    private void handleBuild() {
        java.util.List<Property> buildableProperties = Monopoly.checkSameColorProps(currentPlayer);

        if (buildableProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No properties available for building.", "Build Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] propertyNames = buildableProperties.stream().map(Property::getName).toArray(String[]::new);

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

    /**
     * Handles the action when the mortgage button is clicked.
     */
    private void handleMortgage() {
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

    /**
     * Handles the action when the de-mortgage button is clicked.
     */
    private void handleDemortgage() {
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

    /**
     * Handles the action when the next turn button is clicked.
     */
    private void handleNextTurn() {
        if (monopoly.getHasRolled() && !monopoly.getHasRolledDouble()) {
            currentPlayer = monopoly.getTurn();
            updatePlayerInfo();
            JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "'s turn.");
        } else
            JOptionPane.showMessageDialog(frame, "You should roll the dice.", "Dice Error", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Handles the action when the quit button is clicked.
     */
    private void handleQuit() {
        int response = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit the game?", "Confirm Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            try {
                monopoly.leaveTheGame(currentPlayer);
                currentPlayer = monopoly.getTurn();
                JOptionPane.showMessageDialog(frame, currentPlayer.getName() + "'s turn with type " + currentPlayer.getType(), "Next Player", JOptionPane.INFORMATION_MESSAGE);
                updatePlayerInfo();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error quitting game: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Handles the action when the show properties button is clicked.
     */
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

    /**
     * Updates the player information displayed in the GUI.
     */
    private void updatePlayerInfo() {
        currentPlayerLabel.setText("Current Player: " + currentPlayer.getName());
        currentBalanceLabel.setText("Balance: $" + currentPlayer.getMoney());
    }

    /**
     * Sets up the control panel of the GUI.
     * This method initializes buttons for various game actions and adds them to the control panel with appropriate action listeners.
     */
    private void setupControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 5, 10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        controlPanel.setBackground(new Color(195, 194, 194));

        rollDiceButton = createStyledButton("Roll Dice");
        buyButton = createStyledButton("Buy");
        sellButton = createStyledButton("Sell");
        teleportButton = createStyledButton("Teleport");
        buildButton = createStyledButton("Build");
        mortgageButton = createStyledButton("Mortgage");
        demortgageButton = createStyledButton("De-Mortgage");
        showPropertiesButton = createStyledButton("My Properties");
        nextTurnButton = createStyledButton("Next Turn");
        quitButton = createStyledButton("Quit");

        controlPanel.add(rollDiceButton);
        controlPanel.add(nextTurnButton);
        controlPanel.add(buyButton);
        controlPanel.add(sellButton);
        controlPanel.add(teleportButton);
        controlPanel.add(buildButton);
        controlPanel.add(mortgageButton);
        controlPanel.add(demortgageButton);
        controlPanel.add(showPropertiesButton);
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

}
