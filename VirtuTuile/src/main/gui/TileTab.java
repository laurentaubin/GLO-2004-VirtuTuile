package gui;

import com.sun.tools.javac.Main;
import domain.room.Tile;
import domain.room.TileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JButton créerUneNouvelleTuileButton;
    private JPanel tileListPanel;
    private JScrollPane tileListScrollPane;
    private JButton modifyTile;
    private JButton applySelectedTile;
    private JComboBox tileComboBox;

    private Color tileColor;
    private Color updateColor;

    public TileTab(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.add(mainTileTab);



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
        créerUneNouvelleTuileButton.addActionListener(new ActionListener() {
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

    }

    private void modifySelectedTile(){
        TileType selectedTileType = (TileType)tileComboBox.getSelectedItem();
        selectedTileType.setWidth((float)this.tileWidthField.getValue());
        selectedTileType.setHeight((float)this.tileHeightField.getValue());
        selectedTileType.setName(this.tileNameField.getText());
        selectedTileType.setNbrTilesPerBox((int)this.tileNumberPerBoxField.getValue());
        selectedTileType.setColor(this.updateColor);
        mainWindow.updateSelectedTile();


    }

    public void setButtonColor(Color color) {
        this.updateColor = color;
        this.tileColorButton.setBackground(color);
        tileColorButton.setOpaque(true);
        tileColorButton.setBorderPainted(false);
    }



    public void createTileButtonActionPerformed(){

        CreateTileDialog createTileDialog = new CreateTileDialog(this);
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
        this.tileColorButton.setOpaque(true);
        this.tileColorButton.setBorderPainted(false);
        this.tileNumberPerBoxField.setValue(selectedTileType.getNbrTilesPerBox());
    }



    public void createTileFromUserInput(Color color, float width, float height, String name, int nbrTilesPerBox) {
        mainWindow.createTileFromUserInput(color, width, height, name, nbrTilesPerBox);
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
        setTileWidht(width);
        setTileHeight(height);
        setTileName(name);
        setTileColor(color);
        setTileNumberPerBoxField(nbrTilesPerBox);
    }

    public void setTileWidht(float width) {
        this.tileWidthField.setValue(width);
    }

    public void setTileHeight(float height) {
        this.tileHeightField.setValue(height);
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


}
