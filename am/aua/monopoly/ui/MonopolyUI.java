package am.aua.monopoly.ui;


//import javax.swing.*;
//import java.awt.*;
//
//public class MonopolyUI extends JFrame {
//    public MonopolyUI() {
//        setTitle("Monopoly Game");
//        setSize(1024, 768);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Setup the board layout
//        setupBoardPanels();
//        setupPlayerInfoPanel();
//
//        setVisible(true);
//    }
//
//    private void setupBoardPanels() {
//        // North panel for the top properties (11 including corners)
//        JPanel northPanel = createPropertyPanel(11);
//        add(northPanel, BorderLayout.NORTH);
//
//        // South panel for the bottom properties (11 including corners)
//        JPanel southPanel = createPropertyPanel(11);
//        add(southPanel, BorderLayout.SOUTH);
//
//        // East panel for the right properties (9 not including corners)
//        JPanel eastPanel = createPropertyPanel(9);
//        add(eastPanel, BorderLayout.EAST);
//
//        // West panel for the left properties (9 not including corners)
//        JPanel westPanel = createPropertyPanel(9);
//        add(westPanel, BorderLayout.WEST);
//
//        // Center panel for the Monopoly logo
//        JPanel centerPanel = new JPanel();
//        centerPanel.setLayout(new BorderLayout());
//        JLabel monopolyLabel = new JLabel("MONOPOLY", SwingConstants.CENTER);
//        monopolyLabel.setFont(new Font("Arial", Font.BOLD, 48));
//        centerPanel.add(monopolyLabel, BorderLayout.CENTER);
//        add(centerPanel, BorderLayout.CENTER);
//    }
//
//    private JPanel createPropertyPanel(int count) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(1, count));
//        for (int i = 0; i < count; i++) {
//            panel.add(new JLabel("Tile " + (i + 1)));
//        }
//        return panel;
//    }
//
//    private void setupPlayerInfoPanel() {
//        JPanel playerInfoPanel = new JPanel();
//        playerInfoPanel.setLayout(new GridLayout(6, 1));
//        playerInfoPanel.add(new JLabel("Player 1 All Wealth"));
//        playerInfoPanel.add(new JLabel("Current Balance: 3200"));
//        playerInfoPanel.add(new JLabel("Title Deeds:"));
//        playerInfoPanel.add(new JButton("Roll Dice"));
//        playerInfoPanel.add(new JButton("Buy"));
//        playerInfoPanel.add(new JButton("Pay Rent"));
//        playerInfoPanel.add(new JButton("Next Turn"));
//        add(playerInfoPanel, BorderLayout.EAST);
//    }




import am.aua.monopoly.core.Dice;
import am.aua.monopoly.core.Monopoly;
import am.aua.monopoly.core.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MonopolyUI extends JFrame {
    private Monopoly monopoly; // Your core game class
    private JTextField inputField;
    private JTextArea gameArea;
    private JButton rollDiceButton, buyPropertyButton, nextTurnButton, quitButton;
    private JLabel statusLabel;

    public MonopolyUI() {
        initUI();
        initGame();
    }

    private void initUI() {
        setTitle("Monopoly Game");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Status label at the top
        statusLabel = new JLabel("Welcome to Monopoly! Enter number of players to start the game.");
        add(statusLabel, BorderLayout.NORTH);

        // Game area in the center
        gameArea = new JTextArea();
        gameArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gameArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel for input and buttons at the bottom
        JPanel controlPanel = new JPanel();
        inputField = new JTextField(20);
        controlPanel.add(inputField);

        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener(this::handleRollDice);
        controlPanel.add(rollDiceButton);

        buyPropertyButton = new JButton("Buy Property");
        //   buyPropertyButton.addActionListener(this::handleBuyProperty);
        controlPanel.add(buyPropertyButton);

        nextTurnButton = new JButton("Next Turn");
        nextTurnButton.addActionListener(this::handleNextTurn);
        controlPanel.add(nextTurnButton);

        quitButton = new JButton("Quit Game");
        quitButton.addActionListener(this::handleQuitGame);
        controlPanel.add(quitButton);

        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void initGame() {
        String input = JOptionPane.showInputDialog(this, "Enter the number of players:");
        try {
            int numberOfPlayers = Integer.parseInt(input);
            monopoly = new Monopoly(numberOfPlayers);
            gameArea.append("Game started with " + numberOfPlayers + " players.\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid number of players.");
            System.exit(1);
        }
    }

    private void handleRollDice(ActionEvent event) {
        try {
            String result = Dice.toStringDice(); // Assuming a method that handles dice rolling
            gameArea.append(result + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error while rolling dice: " + e.getMessage());
        }
    }

//    private void handleBuyProperty(ActionEvent event) {
//        try {
//            String result = monopoly.buyProperty(); // Assuming a method that handles buying properties
//            gameArea.append(result + "\n");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Error while buying property: " + e.getMessage());
//        }
//    }

    private void handleNextTurn(ActionEvent event) {
        Player currentPlayer = monopoly.getTurn(); // Assuming a method to fetch the current player
        gameArea.append("It's now " + currentPlayer.getName() + "'s turn.\n");
    }

    private void handleQuitGame(ActionEvent event) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to quit the game?", "Confirm Quit", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}


