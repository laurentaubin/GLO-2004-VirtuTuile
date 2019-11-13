package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatternTab extends JPanel{

    private JPanel mainPatternPanel;
    private JToggleButton straightPatternButton;
    private JToggleButton brickPatternButton;
    private JToggleButton LShapeButton;
    private JToggleButton whatPatternButton;
    private JToggleButton chevronPatternButton;
    private JToggleButton anglePatternButton;
    private ButtonGroup patternButtonGroup;
    private JScrollPane patternScrollPane;
    private JPanel panelInsideScrollPane;
    private JPanel straightPatternPanel;
    private JPanel brickPatternPanel;
    private JPanel anglePatternPanel;
    private JPanel lPatternPanel;
    private JPanel groutPanel;
    private JLabel groutColorLabel;
    private JButton groutColorButton;
    private JFormattedTextField formattedTextField1;
    private JButton modifierLaLargeurDuButton;
    private Color selectedColor;


    public PatternTab() {
        this.selectedColor = Color.WHITE;
        patternButtonGroup = new ButtonGroup();
        patternButtonGroup.add(straightPatternButton);
        patternButtonGroup.add(brickPatternButton);
        patternButtonGroup.add(LShapeButton);
        patternButtonGroup.add(anglePatternButton);
        patternButtonGroup.add(chevronPatternButton);
        patternButtonGroup.add(whatPatternButton);

        //groutColorButton.setPreferredSize(new Dimension(60, 40));

        straightPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Droit.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        straightPatternButton.setMargin(new Insets(10, 0, 10, 0));

        brickPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Brique.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        brickPatternButton.setMargin(new Insets(10, 0, 10, 0));

        anglePatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/Angle.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        anglePatternButton.setMargin(new Insets(10, 0, 10, 0));

        LShapeButton.setIcon(new ImageIcon(new ImageIcon("src/image/L.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        LShapeButton.setMargin(new Insets(10, 0, 10, 0));

        //Ajouter les autres motifs

        this.add(mainPatternPanel);

        groutColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", selectedColor);

                //Set l'attribut de la couleur du coulis
                setButtonColor(color);
            }
        });
    }

    private void setButtonColor(Color color) {
        this.selectedColor = color;
        groutColorButton.setBackground(color);
        groutColorButton.setOpaque(true);
        groutColorButton.setBorderPainted(false);
    }

    private Color getSelectedColor() {
        return this.selectedColor;
    }
}
