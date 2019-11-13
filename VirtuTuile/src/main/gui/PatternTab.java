package gui;

import javax.swing.*;
import java.awt.*;

public class PatternTab extends JPanel{
    private JPanel mainPatternPanel;
    private JButton straightPatternButton;
    private JButton brickPatternButton;
    private JButton LShapeButton;
    private JButton whatPatternButton;
    private JButton chevronPatternButton;
    private JButton anglePatternButton;
    private JScrollPane patternScrollPane;
    private JPanel panelInsideScrollPane;
    private JPanel straightPatternPanel;
    private JPanel brickPatternPanel;
    private JPanel anglePatternPanel;
    private JPanel lPatternPanel;


    public PatternTab() {

        straightPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Droit.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        straightPatternButton.setMargin(new Insets(10, 0, 10, 0));

        brickPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Brique.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        brickPatternButton.setMargin(new Insets(10, 0, 10, 0));

        anglePatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Angle.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        anglePatternButton.setMargin(new Insets(10, 0, 10, 0));

        LShapeButton.setIcon(new ImageIcon(new ImageIcon("src/image/L.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        LShapeButton.setMargin(new Insets(10, 0, 10, 0));

        //Ajouter les autres motifs
    }
}
