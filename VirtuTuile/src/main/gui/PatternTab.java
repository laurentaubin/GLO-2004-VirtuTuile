package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatternTab extends JPanel{

    private MainWindow mainWindow;
    private JPanel mainPatternPanel;
    private JToggleButton straightPatternButton;
    private JToggleButton brickPatternButton;
    private JToggleButton verticalBrickShapeButton;
    private JToggleButton whatPatternButton;
    private JToggleButton chevronPatternButton;
    private JToggleButton verticalPatternButton;
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
    private JFormattedTextField groutWidthField;
    private JButton modifierLaLargeurDuButton;
    private Color selectedColor;


    public PatternTab(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.groutWidthField.setValue(0);
        this.selectedColor = Color.WHITE;
        patternButtonGroup = new ButtonGroup();
        patternButtonGroup.add(straightPatternButton);
        patternButtonGroup.add(brickPatternButton);
        patternButtonGroup.add(verticalBrickShapeButton);
        patternButtonGroup.add(verticalPatternButton);
        patternButtonGroup.add(chevronPatternButton);
        patternButtonGroup.add(whatPatternButton);

        //groutColorButton.setPreferredSize(new Dimension(60, 40));

        straightPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/droite_horizontale.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        straightPatternButton.setMargin(new Insets(10, 0, 10, 0));

        brickPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/brique_horizontale.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        brickPatternButton.setMargin(new Insets(10, 0, 10, 0));

        verticalPatternButton.setIcon(new ImageIcon(new ImageIcon("src/image/droite_verticale.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        verticalPatternButton.setMargin(new Insets(10, 0, 10, 0));

        verticalBrickShapeButton.setIcon(new ImageIcon(new ImageIcon("src/image/brique_verticale.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT)));
        verticalBrickShapeButton.setMargin(new Insets(10, 0, 10, 0));

        //Ajouter les autres motifs

        this.add(mainPatternPanel);

        groutColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", selectedColor);
                setButtonColor(color);
            }
        });

        straightPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                straightPatternButtonActionPerformed();
            }
        });

        brickPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                horizontalBrickButtonActionPerformed();
            }
        });

        verticalPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalPatternButtonActionPerformed();
            }
        });

        verticalBrickShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalBrickButtonActionPerformed();
            }
        });

        groutWidthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredGroutWidth();
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

    private void straightPatternButtonActionPerformed() {
        mainWindow.setStraightPatternToSelectedSurface();
    }

    private void horizontalBrickButtonActionPerformed() {
        mainWindow.setHorizontalPatternToSelectedSurface();
    }

    private void verticalPatternButtonActionPerformed() {
        mainWindow.setVerticalPatternToSelectedSurface();
    }

    private void verticalBrickButtonActionPerformed() {
        mainWindow.setVerticalBrickPatternToSelectedSurface();
    }

    private void setEnteredGroutWidth() {
        double width = ((Number)groutWidthField.getValue()).doubleValue();
        mainWindow.setEnteredGroutWidtht(width);
    }

    public void setGroutWidth(double width, int number) {
        if (number == 1) {
            this.groutWidthField.setEnabled(true);
            this.groutWidthField.setValue(width);
        }
        else {
            this.groutWidthField.setValue(0);
            this.groutWidthField.setEnabled(false);
        }
    }
}
