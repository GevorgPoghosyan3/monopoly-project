package am.aua.monopoly;

import am.aua.monopoly.cli.MonopolyConsole;
import am.aua.monopoly.ui.*;

import javax.swing.*;

/**
 * The Main class is the entry point of the Monopoly game application.
 */
public class Main {
    /**
     * The main method is the entry point of the application.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Alternatively, to run the game in console mode, uncomment the following lines:
//       MonopolyConsole monopolyConsole = new MonopolyConsole();
//       monopolyConsole.run();

        // Create an instance of MonopolyGUI and run the game with the graphical user interface.
        MonopolyGUI ui = new MonopolyGUI();
        ui.run();

    }
}
