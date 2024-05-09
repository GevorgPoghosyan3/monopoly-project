package am.aua.monopoly;

import am.aua.monopoly.cli.MonopolyConsole;
import am.aua.monopoly.ui.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
//       MonopolyConsole monopolyConsole = new MonopolyConsole();
//       monopolyConsole.run();

        MonopolyGUI ui = new MonopolyGUI();
        ui.run();

    }
}
