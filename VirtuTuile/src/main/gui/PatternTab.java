package gui;

import util.UnitConverter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
    private JButton centerTile;
    private JToggleButton centerToggleButton;
    private JButton startFullTileButton;
    private JTextField angleInputField;
    private JPanel angleJPanel;
    private JLabel Angle;
    private Color selectedColor;


    public PatternTab(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        this.groutWidthField.setValue(0);
        this.angleInputField.setText("30");
        this.selectedColor = Color.WHITE;
        this.mismatchPanel.setVisible(false);
        this.angleJPanel.setVisible(false);
        patternButtonGroup = new ButtonGroup();
        patternButtonGroup.add(straightPatternButton);
        patternButtonGroup.add(brickPatternButton);
        patternButtonGroup.add(verticalBrickShapeButton);
        patternButtonGroup.add(verticalPatternButton);
        patternButtonGroup.add(chevronButton);
        patternButtonGroup.add(anglePatternButton);
        patternButtonGroup.add(squarePatternButton);

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
                angleJPanel.setVisible(false);

            }
        });

        brickPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                horizontalBrickButtonActionPerformed();
                mismatchPanel.setVisible(true);
                angleJPanel.setVisible(false);
            }
        });

        verticalPatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalPatternButtonActionPerformed();
                mismatchPanel.setVisible(false);
                angleJPanel.setVisible(false);
            }
        });

        verticalBrickShapeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                verticalBrickButtonActionPerformed();
                mismatchPanel.setVisible(true);
                angleJPanel.setVisible(false);
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
                mismatchPanel.setVisible(false);
                angleJPanel.setVisible(true);
            }
        });

        squarePatternButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                squarePatternButtonActionPerformed();
                mismatchLabel.setVisible(false);
                angleJPanel.setVisible(false);

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
                mismatchLabel.setVisible(false);
                angleJPanel.setVisible(false);
            }
        });

        centerToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                centerTiles();
            }
        });

        startFullTileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startPatternWithFullTile();

            }
        });

        angleInputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setAngle();
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
        mainWindow.setAnglePattern(30);;
    }

    private void squarePatternButtonActionPerformed() {
        mainWindow.setSquarePattern();
    }


    private void setEnteredGroutWidth() {
        if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
            String inchGrout = groutWidthField.getText();
            String[] inchArray = getImperialArray(inchGrout);
            if (inchArray[0] == "format invalide") {
                JOptionPane.showMessageDialog(null, "Le format doit être 0\"0/0");
                return;
            }
            double inchTotal = UnitConverter.stringToInch(getImperialArray(inchGrout));
            double enteredGrout = UnitConverter.convertSelectedUnitToPixel(inchTotal, mainWindow.getCurrentMeasurementMode());
            this.mainWindow.setEnteredGroutWidtht(enteredGrout);
        }
        else if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.METRIC) {
            String enteredWidthString = groutWidthField.getText();
            double enteredGrout;
            try {
                enteredGrout = Double.parseDouble(enteredWidthString);
            }
           catch (Exception e) {
               JOptionPane.showMessageDialog(null, "La valeur entrée n'est pas dans le bon format. Veuillez recommencer.");
               return;
           }
            enteredGrout = UnitConverter.convertSelectedUnitToPixel(enteredGrout, MainWindow.MeasurementUnitMode.METRIC);
            this.mainWindow.setEnteredGroutWidtht(enteredGrout);
        }
    }

    public void setGroutWidth(double widthGrout, int number) {
        double width = UnitConverter.convertPixelToSelectedUnit(widthGrout, mainWindow.getCurrentMeasurementMode());
        if (number == 1) {
            this.groutWidthField.setEnabled(true);
            if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
                String groutWidth = getImperialFormat(width);
                this.groutWidthField.setText(groutWidth);
            }
           else if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.METRIC) {
                BigDecimal bdWidth = BigDecimal.valueOf(width);
                bdWidth = bdWidth.setScale(2, RoundingMode.HALF_UP);
                String widthString = Double.toString(bdWidth.doubleValue());
                this.groutWidthField.setText(widthString + "m");
            }
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

    public void setAngle(){
        int angle = Integer.parseInt(angleInputField.getText());
        if(angle >= 10 && angle <= 80){
            mainWindow.setAnglePattern(angle);
        }
    }

    public void centerTiles(){
        if (centerToggleButton.isSelected()) {
            mainWindow.centerTiles(true);
        }
        else {
            mainWindow.centerTiles(false);
        }
    }

    public void setCenterTileButtonState(boolean bool) {
        if (bool) {
            centerToggleButton.setSelected(true);
        }
        else {
            centerToggleButton.setSelected(false);
        }
    }

    public void startPatternWithFullTile() {
        mainWindow.startWithFullTile();
    }

    private String getImperialFormat(double value) {
        int inch = (int)(value);
        double fraction = value - inch;
        BigDecimal bd = BigDecimal.valueOf(fraction);
        bd = bd.setScale(1, RoundingMode.HALF_DOWN);
        String imperialString = Integer.toString(inch) + "\"" + Double.toString(bd.doubleValue());
        return imperialString;
    }


    private String[] getImperialArray(String value) {
        String[] stringArray = new String[2];
        try {
            int inchIndex = value.indexOf("\"");
            String inch = value.substring(0, inchIndex);
            String fraction = value.substring(inchIndex + 1);
            stringArray[0] = inch;
            stringArray[1] = fraction;
            return stringArray;
        } catch (StringIndexOutOfBoundsException e) {
            stringArray[0] = "format invalide";
        }
        return stringArray;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
