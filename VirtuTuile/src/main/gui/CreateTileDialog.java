package gui;

import com.sun.tools.javac.Main;
import javafx.beans.binding.DoubleExpression;
import util.UnitConverter;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

// Code inspiré de: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java

public class CreateTileDialog extends JDialog {
    private TileTab tileTab;
    private MainWindow.MeasurementUnitMode measurementUnitMode;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JFormattedTextField heightField;
    private JFormattedTextField widthField;
    private JFormattedTextField nameField;
    private JButton colorButton;
    private JFormattedTextField numberTilesField;
    private JLabel nameLabel;
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel colorLabel;
    private JLabel numberLabel;

    private String name;
    private String width;
    private String height;
    private Color color;
    private int numberOfTilePerBox;

    public CreateTileDialog(TileTab tileTab, MainWindow.MeasurementUnitMode measurementUnitMode) {
        this.tileTab = tileTab;
        this.measurementUnitMode = measurementUnitMode;
        this.name = "";
        this.color = Color.WHITE;
        this.numberTilesField.setValue(0);

        buttonOK.setBackground(new Color(10,132,255));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        colorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setTileColor();
            }
        });
    }

    private void setTileName() {
        if (nameField.getText().length() == 0) {
            this.name = "";
        }
        else {
            this.name += nameField.getText();
        }
    }

    private void setTileWidth() {
        if (widthField.getText().length() == 0) {
            this.width = "";
        }
        else {
            this.width = widthField.getText();
        }
    }

    private void setTileHeight() {
        if (heightField.getText().length() == 0) {
            this.height = "";
        }
        else {
            this.height = heightField.getText();
        }
    }

    private void setTileColor() {
        this.color = JColorChooser.showDialog(null, "Choose a color", this.color);
        colorButton.setBackground(color);
        colorButton.setOpaque(true);
        colorButton.setBorderPainted(false);
    }

    private void setNumberOfTilePerBox() {
        this.numberOfTilePerBox = (int)numberTilesField.getValue();
    }

    private void onOK() {
        setTileName();
        setTileWidth();
        setTileHeight();
        setNumberOfTilePerBox();
        if (validateInputs()) {
            double width = 0d;
            double height = 0d;
            if (measurementUnitMode == MainWindow.MeasurementUnitMode.IMPERIAL) {
                String[] widthArray = getImperialArray(this.width);
                double inchTotal = UnitConverter.stringToInch(widthArray);
                width = UnitConverter.convertSelectedUnitToPixel(inchTotal, MainWindow.MeasurementUnitMode.IMPERIAL);

                String[] heightArray = getImperialArray(this.height);
                inchTotal = UnitConverter.stringToInch(heightArray);
                height = UnitConverter.convertSelectedUnitToPixel(inchTotal, MainWindow.MeasurementUnitMode.IMPERIAL);
            }

            else if (measurementUnitMode == MainWindow.MeasurementUnitMode.METRIC) {
                width = UnitConverter.convertSelectedUnitToPixel(Double.parseDouble(this.width), MainWindow.MeasurementUnitMode.METRIC);
                height = UnitConverter.convertSelectedUnitToPixel(Double.parseDouble(this.height), MainWindow.MeasurementUnitMode.METRIC);
            }

            tileTab.createTileFromUserInput(this.color, width, height, this.name, this.numberOfTilePerBox);
            dispose();
        }
        else {
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private boolean validateInputs() {
        boolean areValid = true;
        if (this.name.isEmpty()) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "Identifiant de la tuile ne peut être vide");
        }
        else if (this.width.isEmpty()) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "La largeur ne peut pas être nulle");
        }

        else if (!this.width.isEmpty()) {
            if (measurementUnitMode == MainWindow.MeasurementUnitMode.IMPERIAL) {
                String[] inchArray = getImperialArray(this.width);
                if (inchArray[0] == "format invalide") {
                    JOptionPane.showMessageDialog(null, "Le format doit être 0\"0/0");
                    areValid = false;
                }
            }
            else if (measurementUnitMode == MainWindow.MeasurementUnitMode.METRIC) {
                try {
                    double width = Double.parseDouble(this.width);
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Format de la largeur invalide");
                    areValid = false;
                }
            }
        }

        else if (this.height.isEmpty()) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "La hauteur ne peut pas être nulle");
        }

        else if (!this.height.isEmpty()) {
            if (measurementUnitMode == MainWindow.MeasurementUnitMode.IMPERIAL) {
                String[] inchArray = getImperialArray(this.height);
                if (inchArray[0] == "format invalide") {
                    JOptionPane.showMessageDialog(null, "Le format doit être 0\"0/0");
                    areValid = false;
                }
            }
            else if (measurementUnitMode == MainWindow.MeasurementUnitMode.METRIC) {
                try {
                    double height = Double.parseDouble(this.height);
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Format de la hauteur invalide");
                    areValid = false;
                }
            }
        }

        else if (numberOfTilePerBox == 0) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "Le nombre de tuiles par boîte ne peut pas être nulle");
        }
        return areValid;
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
}
