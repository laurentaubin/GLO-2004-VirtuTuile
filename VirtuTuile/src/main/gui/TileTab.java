package gui;

import javax.swing.*;

public class TileTab extends JPanel {
    private JPanel mainTileTab;
    private JPanel caracPanel;
    private JLabel tileWidthLabel;
    private JLabel tileHeightLabel;
    private JLabel tileNameLabel;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JTextField textField1;
    private JButton tileColorButton;
    private JLabel tileColorLabel;
    private JLabel numberOfTilesLabel;
    private JFormattedTextField formattedTextField3;
    private JButton créerUneNouvelleTuileButton;
    private JButton modifierLaTuileSélectionnéeButton;

    public TileTab() {
        this.add(mainTileTab);
    }
}
