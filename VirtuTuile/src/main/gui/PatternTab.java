package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PatternTab extends JPanel{

    private MainWindow mainWindow;
    private JPanel mainPatternPanel;
    private JToggleButton straightPatternButton;
    private JToggleButton brickPatternButton;
    private JToggleButton verticalBrickShapeButton;
    private JToggleButton anglePatternButton;
    private JToggleButton chevronPatternButton;
    private JToggleButton verticalPatternButton;
    private ButtonGroup patternButtonGroup;
    private JScrollPane patternScrollPane;
    private JPanel panelInsideScrollPane;
    private JPanel straightPatternPanel;
    private JPanel brickPatternPanel;
    private JPanel verticalPatternPanel;
    private JPanel verticalBrickPatternPanel;
    private JPanel groutPanel;
    private JLabel groutColorLabel;
    private JButton groutColorButton;
    private JFormattedTextField groutWidthField;
    private JLabel horizontalLabel;
    private JButton chromaticButton;
    private JPanel anglePanel;
    private JPanel mismatchPanel;
    private JLabel mismatchLabel;
    private JComboBox percentComboBox;
    private JButton percentButton;
    private JPanel squarePanel;
    private JToggleButton squarePatternButton;
    private JPanel chevronPattern;
    private JToggleButton chevronButton;
    private Color selectedColor;


    public PatternTab(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        this.groutWidthField.setValue(0);
        this.selectedColor = Color.WHITE;
        this.mismatchPanel.setVisible(false);
        patternButtonGroup = new ButtonGroup();
        patternButtonGroup.add(straightPatternButton);
        patternButtonGroup.add(brickPatternButton);
        patternButtonGroup.add(verticalBrickShapeButton);
        patternButtonGroup.add(verticalPatternButton);
        patternButtonGroup.add(chevronPatternButton);
        patternButtonGroup.add(anglePatternButton);

        percentComboBox.addItem(25);
        percentComboBox.addItem(50);
        percentComboBox.addItem(75);
        percentComboBox.setSelectedIndex(1);

        percentButton.setBackground(new Color(0,112,245));
        percentButton.setForeground(new Color(235, 235, 240));

        BufferedImage straightImage = ImageIO.read(this.getClass().getResourceAsStream("/image/droite_horizontale.png"));
        Icon straightIcon = new ImageIcon(straightImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        straightPatternButton.setIcon(straightIcon);
        straightPatternButton.setMargin(new Insets(10, 0, 10, 0));

        BufferedImage vertImage = ImageIO.read(this.getClass().getResourceAsStream("/image/droite_verticale.png"));
        Icon vertIcon = new ImageIcon(vertImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        verticalPatternButton.setIcon(vertIcon);
        verticalPatternButton.setMargin(new Insets(10, 0, 10, 0));

        BufferedImage brickImage = ImageIO.read(this.getClass().getResourceAsStream("/image/brique_horizontale.png"));
        Icon brickIcon = new ImageIcon(brickImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        this.brickPatternButton.setIcon(brickIcon);
        brickPatternButton.setMargin(new Insets(10, 0, 10, 0));

        BufferedImage vertBrickImage = ImageIO.read(this.getClass().getResourceAsStream("/image/brique_verticale.png"));
        Icon vertBrickIcon = new ImageIcon(vertBrickImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        this.verticalBrickShapeButton.setIcon(vertBrickIcon);
        verticalBrickShapeButton.setMargin(new Insets(10, 0, 10, 0));


        BufferedImage angleImage = ImageIO.read(this.getClass().getResourceAsStream("/image/incline.png"));
        Icon angleIcon = new ImageIcon(angleImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        this.anglePatternButton.setIcon(angleIcon);
        anglePatternButton.setMargin(new Insets(10, 0, 10, 0));

        BufferedImage squareImage = ImageIO.read(this.getClass().getResourceAsStream("/image/square.png"));
        Icon squareIcon = new ImageIcon(squareImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        this.squarePatternButton.setIcon(squareIcon);
        squarePatternButton.setMargin(new Insets(10, 0, 10, 0));

        BufferedImage chevronImage = ImageIO.read(this.getClass().getResourceAsStream("/image/Chevron.png"));
        Icon chevronIcon = new ImageIcon(chevronImage.getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        this.chevronButton.setIcon(chevronIcon);
        chevronButton.setMargin(new Insets(10, 0, 10, 0));





        groutColorButton.setPreferredSize(new Dimension(50, 20));

        chromaticButton.setPreferredSize(new Dimension(20,20));
        BufferedImage chromImage = ImageIO.read(this.getClass().getResourceAsStream("/image/chromatic.png"));
        Icon chromIcon = new ImageIcon(chromImage.getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        chromaticButton.setIcon(chromIcon);

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
                mismatchPanel.setVisible(false);
            }
        });

        brickPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                horizontalBrickButtonActionPerformed();
                mismatchPanel.setVisible(true);
            }
        });

        verticalPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalPatternButtonActionPerformed();
                mismatchPanel.setVisible(false);
            }
        });

        verticalBrickShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalBrickButtonActionPerformed();
                mismatchPanel.setVisible(true);
            }
        });

        groutWidthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredGroutWidth();
            }
        });

        chromaticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", selectedColor);
                setButtonColor(color);
            }
        });

        anglePatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                anglePatternButtonActionPerformed();
            }
        });

        squarePatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                squarePatternButtonActionPerformed();
            }
        });

        percentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                applyMismatchActionPerformed();

            }
        });

        chevronButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.setChevronPattern();
            }
        });
    }

    private void setButtonColor(Color color) {
        this.selectedColor = color;
        groutColorButton.setBackground(color);
        groutColorButton.setOpaque(true);
        groutColorButton.setBorderPainted(false);
        this.mainWindow.setGroutColor(color);
    }

    private Color getSelectedColor() {
        return this.selectedColor;
    }

    private void straightPatternButtonActionPerformed() { mainWindow.setStraightPatternToSelectedSurface(); }

    private void horizontalBrickButtonActionPerformed() {
        mainWindow.setHorizontalPatternToSelectedSurface();
    }

    private void verticalPatternButtonActionPerformed() {
        mainWindow.setVerticalPatternToSelectedSurface();
    }

    private void verticalBrickButtonActionPerformed() {
        mainWindow.setVerticalBrickPatternToSelectedSurface();
    }

    private void anglePatternButtonActionPerformed() {
        mainWindow.setAnglePattern();;
    }

    private void squarePatternButtonActionPerformed() {
        mainWindow.setSquarePattern();
    }


    private void setEnteredGroutWidth() {
        double width = Double.parseDouble(groutWidthField.getValue().toString());
        this.mainWindow.setEnteredGroutWidtht(width);
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

    public void applyMismatchActionPerformed() {
        double mismatch = (int)percentComboBox.getSelectedItem();
        if (mismatch >= 0 && mismatch <= 100) {
            mismatch = mismatch/100;
            mainWindow.setMismatch(mismatch);
        }
    }
}
