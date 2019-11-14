package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.concurrent.Flow;

public class TileTabPanel extends JPanel{

    public TileTabPanel(RightPanel rightPanel){
        this.rightPanel = rightPanel;
        initComponent();
    }

    public void initComponent(){
        modifyButton = new JButton("Modifier la tuile");

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyButtonPressed(actionEvent);
            }
        });

        widthLabel = new JLabel(widthString);
        heightLabel = new JLabel(heightString);
        colorLabel = new JLabel(colorString);
        nameLabel = new JLabel(nameString);
        nbrTilesPerBoxLabel = new JLabel(nbrTilesPerBoxString);

        widthField = new JFormattedTextField(widthFormat);
        widthField.setColumns(10);
        widthField.setValue(this.widthValue);

        heightField = new JFormattedTextField(heightFormat);
        heightField.setColumns(10);
        heightField.setValue(this.heightValue);

        colorField = new JFormattedTextField(colorFormat);
        colorField.setColumns(10);
        colorField.setValue(this.colorValue);

        nameField = new JFormattedTextField(nameFormat);
        nameField.setColumns(10);
        nameField.setValue(this.nameValue);

        nbrTilesPerBoxField = new JFormattedTextField(nbrTilesPerBoxFormat);
        nbrTilesPerBoxField.setColumns(10);
        nbrTilesPerBoxField.setValue(this.nbrTilesPerBoxValue);

        widthLabel.setLabelFor(widthField);
        heightLabel.setLabelFor(heightField);
        colorLabel.setLabelFor(colorField);
        nameLabel.setLabelFor(nameField);
        nbrTilesPerBoxLabel.setLabelFor(nbrTilesPerBoxField);

        JPanel tileCharacteristicsPanel = new JPanel();
        JPanel widthPanel = new JPanel(new FlowLayout());
        JPanel heightPanel = new JPanel(new FlowLayout());
        JPanel colorPanel = new JPanel(new FlowLayout());
        JPanel namePanel = new JPanel(new FlowLayout());
        JPanel nbrTilesPerBoxPanel = new JPanel(new FlowLayout());

        tileCharacteristicsPanel.setBorder(BorderFactory.createTitledBorder("Caractéristiques"));

        BorderLayout layout = new BorderLayout();
        tileCharacteristicsPanel.setLayout(new GridLayout(3, 2));

        widthPanel.add(widthLabel);
        widthPanel.add(widthField);

        heightPanel.add(heightLabel);
        heightPanel.add(heightField);

        colorPanel.add(colorLabel);
        colorPanel.add(colorField);

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        nbrTilesPerBoxPanel.add(nbrTilesPerBoxLabel);
        nbrTilesPerBoxPanel.add(nbrTilesPerBoxField);

        tileCharacteristicsPanel.add(widthPanel);
        tileCharacteristicsPanel.add(heightPanel);
        tileCharacteristicsPanel.add(colorPanel);
        tileCharacteristicsPanel.add(namePanel);
        tileCharacteristicsPanel.add(nbrTilesPerBoxPanel, BorderLayout.CENTER);

        this.add(tileCharacteristicsPanel, BorderLayout.NORTH);
        this.add(modifyButton, BorderLayout.EAST);
    }

    private void modifyButtonPressed(ActionEvent evt) {
        float width = (float) widthField.getValue();
        float height = (float) heightField.getValue();
        Color color = (Color) colorField.getValue();
        String name = (String) nameField.getValue();
        int nbrTilesPerBox = (int) nbrTilesPerBoxField.getValue();

    }


    private RightPanel rightPanel;

    private JPanel tileCharacteristicsPanel;
    private JPanel modifyAndErasePanel;

    private float width;
    private float height;
    private Color color;
    private String name;
    private int nbrTilesPerBox;

    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel colorLabel;
    private JLabel nameLabel;
    private JLabel nbrTilesPerBoxLabel;

    private static String widthString = "Largeur: ";
    private static String heightString = "Hauteur: ";
    private static String colorString = "Couleur: ";
    private static String nameString = "Nom: ";
    private static String nbrTilesPerBoxString = "Nombre de tuiles par boite: ";

    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JFormattedTextField colorField;
    private JFormattedTextField nameField;
    private JFormattedTextField nbrTilesPerBoxField;

    private float widthValue;
    private float heightValue;
    private Color colorValue;
    private String nameValue;
    private int nbrTilesPerBoxValue;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;
    private NumberFormat colorFormat;
    private NumberFormat nameFormat;
    private NumberFormat nbrTilesPerBoxFormat;

    private JButton modifyButton;
    private JButton EraseButton;
}


