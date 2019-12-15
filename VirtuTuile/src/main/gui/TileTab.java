package gui;

import domain.room.Tile;
import domain.room.TileType;
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
import java.util.ArrayList;

public class TileTab extends JPanel {

    private MainWindow mainWindow;
    private JPanel mainTileTab;
    private JPanel caracPanel;
    private JLabel tileWidthLabel;
    private JLabel tileHeightLabel;
    private JLabel tileNameLabel;
    private JFormattedTextField tileWidthField;
    private JFormattedTextField tileHeightField;
    private JTextField tileNameField;
    private JButton tileColorButton;
    private JLabel tileColorLabel;
    private JLabel numberOfTilesLabel;
    private JFormattedTextField tileNumberPerBoxField;
    private JButton createNewTile;
    private JPanel tileListPanel;
    private JScrollPane tileListScrollPane;
    private JButton modifyTile;
    private JButton applySelectedTile;
    private JComboBox tileComboBox;
    private JButton chromaticButton;

    private Color tileColor;
    private Color updateColor;

    public TileTab(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        this.add(mainTileTab);

        createNewTile.setBackground(new Color(30, 195 ,55));
        createNewTile.setForeground(new Color(235, 235, 240));
        //creerUneNouvelleTuileButton.setBackground(new Color(40, 205, 65));

        modifyTile.setBackground(new Color(255, 159, 10));
        modifyTile.setForeground(new Color(235, 235, 240));

        applySelectedTile.setBackground(new Color(0,112,245));
        applySelectedTile.setForeground(new Color(235, 235, 240));

        tileColorButton.setPreferredSize(new Dimension(20,20));
        chromaticButton.setPreferredSize(new Dimension(20, 20));
        BufferedImage chromImage = ImageIO.read(this.getClass().getResourceAsStream("/image/chromatic.png"));
        Icon chromIcon = new ImageIcon(chromImage.getScaledInstance(15, 15, Image.SCALE_DEFAULT));
        chromaticButton.setIcon(chromIcon);

        modifyTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifySelectedTile();
            }
        });

        tileColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", tileColor);
                setButtonColor(color);
            }
        });

        createNewTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createTileButtonActionPerformed();
            }
        });

        applySelectedTile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setSelectedTileToSelectedSurface();
            }
        });


        tileComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setTileInformation();
            }
        });

        chromaticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", tileColor);
                setButtonColor(color);
            }
        });

        tileWidthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateTileWidth();
            }
        });

        tileHeightField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateTileHeight();
            }
        });

        tileNameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateTileName();
            }
        });

        tileNumberPerBoxField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                updateNumberPerBox();
            }
        });
    }

    private void modifySelectedTile(){
        if (tileComboBox.getItemCount() != 0) {
            float tileWidth = 0;
            float tileHeight = 0;
            if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
                String widthValue = tileWidthField.getText();
                String heightValue = tileHeightField.getText();
                String[] widthStringArray = getImperialArray(widthValue);
                String[] heightStringArray = getImperialArray(heightValue);
                double widthInchTotal = UnitConverter.stringToInch(widthStringArray);
                double heightInchTotal = UnitConverter.stringToInch(heightStringArray);
                tileWidth = UnitConverter.convertSelectedUnitToPixel(widthInchTotal, mainWindow.getCurrentMeasurementMode());
                tileHeight = UnitConverter.convertSelectedUnitToPixel(heightInchTotal, mainWindow.getCurrentMeasurementMode());
            }

            else if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.METRIC) {
                tileWidth = UnitConverter.convertSelectedUnitToPixel(((Number) this.tileWidthField.getValue()).floatValue(), mainWindow.getCurrentMeasurementMode());
                tileHeight = UnitConverter.convertSelectedUnitToPixel(((Number) this.tileHeightField.getValue()).floatValue(), mainWindow.getCurrentMeasurementMode());
            }

            TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
            String tileName = this.tileNameField.getText();
            int nbrTilesPerBox = (int)this.tileNumberPerBoxField.getValue();
            Color tileColor = this.updateColor;
            mainWindow.updateTile(selectedTileType, tileWidth, tileHeight, tileName, nbrTilesPerBox, tileColor);
        }
    }

    public void updateTileWidth() {
        TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
        float tileWidth = 0f;
        if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
            String widthValue = tileWidthField.getText();
            String[] widthStringArray = getImperialArray(widthValue);
            double widthInchTotal = UnitConverter.stringToInch(widthStringArray);
            tileWidth = UnitConverter.convertSelectedUnitToPixel(widthInchTotal, mainWindow.getCurrentMeasurementMode());
        }
        else {
            tileWidth = UnitConverter.convertSelectedUnitToPixel(((Number) this.tileWidthField.getValue()).floatValue(), mainWindow.getCurrentMeasurementMode());
        }
        mainWindow.updateTileWidth(selectedTileType, tileWidth);
    }

    public void updateTileHeight() {
        TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
        float tileHeight = 0f;
        if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
            String heightValue = tileWidthField.getText();
            String[] heightStringArray = getImperialArray(heightValue);
            double heightInchTotal = UnitConverter.stringToInch(heightStringArray);
            tileHeight = UnitConverter.convertSelectedUnitToPixel(heightInchTotal, mainWindow.getCurrentMeasurementMode());
        }
        else {
            tileHeight = UnitConverter.convertSelectedUnitToPixel(((Number) this.tileHeightField.getValue()).floatValue(), mainWindow.getCurrentMeasurementMode());
        }
        mainWindow.updateTileHeight(selectedTileType, tileHeight);
    }

    public void setButtonColor(Color color) {
        this.updateColor = color;
        this.tileColorButton.setBackground(color);
        tileColorButton.setOpaque(true);
        tileColorButton.setBorderPainted(false);

        TileType tileType = (TileType)tileComboBox.getSelectedItem();
        mainWindow.updateTileColor(tileType, color);
    }

    public void updateTileName() {
        TileType tileType = (TileType)tileComboBox.getSelectedItem();
        String name = tileNameField.getText();
        mainWindow.updateTileName(tileType, name);

    }

    public void updateNumberPerBox() {
        TileType tileType = (TileType)tileComboBox.getSelectedItem();
        int numberPerBox = (int)tileNumberPerBoxField.getValue();
        mainWindow.updateNumberPerBox(tileType, numberPerBox);
    }



    public void createTileButtonActionPerformed(){

        CreateTileDialog createTileDialog = new CreateTileDialog(this, mainWindow.getCurrentMeasurementMode());
        createTileDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        createTileDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createTileDialog.setLocationRelativeTo(mainWindow);
        createTileDialog.pack();
        createTileDialog.setVisible(true);
    }



    public void setTileInformation() {
        TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
        this.tileWidthField.setValue(selectedTileType.getWidth());
        this.tileHeightField.setValue(selectedTileType.getHeight());
        this.tileNameField.setText(selectedTileType.getName());
        this.tileColorButton.setBackground(selectedTileType.getColor());
        this.updateColor =  selectedTileType.getColor();
        this.tileColorButton.setBackground(selectedTileType.getColor());
        this.tileColorButton.setOpaque(true);
        this.tileColorButton.setBorderPainted(false);
        this.tileNumberPerBoxField.setValue(selectedTileType.getNbrTilesPerBox());
    }



    public void createTileFromUserInput(Color color, double width, double height, String name, int nbrTilesPerBox) {
        mainWindow.createTileFromUserInput(color, (float)width, (float)height, name, nbrTilesPerBox);
        updateTileComboBox();
    }

    public void updateTileComboBox() {
        ArrayList<TileType> tileList = mainWindow.getTileList();
        tileComboBox.addItem(tileList.get(tileList.size() - 1));
    }



    public void setSelectedTileToSelectedSurface() {
        TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
        mainWindow.setSelectedTileToSelectedSurface(selectedTileType);

    }

    public void setCurrentTileInfo(float width, float height, String name, Color color, int nbrTilesPerBox) {
        MainWindow.MeasurementUnitMode measurementUnitMode = mainWindow.getCurrentMeasurementMode();
        setTileWidth(UnitConverter.convertPixelToSelectedUnit(width, measurementUnitMode));
        setTileHeight(UnitConverter.convertPixelToSelectedUnit(height, measurementUnitMode));
        setTileName(name);
        setTileColor(color);
        setTileNumberPerBoxField(nbrTilesPerBox);
    }

    public void setTileWidth(double width) {
        if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
            this.tileWidthField.setText(getImperialFormat(width));
        }
        else {
            this.tileWidthField.setValue(width);
        }
    }

    public void setTileHeight(double height) {
        if (mainWindow.getCurrentMeasurementMode() == MainWindow.MeasurementUnitMode.IMPERIAL) {
            this.tileHeightField.setText(getImperialFormat(height));
        }
        else {


            this.tileHeightField.setValue(height);
        }
    }

    public void setTileName(String name) {
        this.tileNameField.setText(name);
    }

    public void setTileColor(Color color) {
        this.tileColorButton.setBackground(color);
        this.tileColorButton.setOpaque(true);
        this.tileColorButton.setBorderPainted(false);
    }

    public void setTileNumberPerBoxField(int nbrOfTiles) {
        this.tileNumberPerBoxField.setValue(nbrOfTiles);
    }

    private String getImperialFormat(double value) {
        int inch = (int)(value);
        double fraction = value - inch;
        BigDecimal bd = BigDecimal.valueOf(fraction);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
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
            System.out.println("Format invalide");
        }
        return stringArray;
    }
}

