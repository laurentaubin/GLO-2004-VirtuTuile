package gui;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;

// Code inspiré de: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/FormattedTextFieldDemoProject/src/components/FormattedTextFieldDemo.java

public class CreateTileDialog extends JDialog {
    private TileTab tileTab;
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
    private double width;
    private double height;
    private Color color;
    private int numberOfTilePerBox;

    public CreateTileDialog(TileTab tileTab) {
        this.tileTab = tileTab;
        this.name = "";
        this.widthField.setValue(0d);
        this.heightField.setValue(0d);
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
            System.out.println("Allo");
            this.name = "";
        }
        else {

            this.name += nameField.getText();
        }
    }

    private void setTileWidth() {
        this.width = ((Number)widthField.getValue()).doubleValue();
    }

    private void setTileHeight() {
        this.height = (double)heightField.getValue();
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
            tileTab.createTileFromUserInput(this.color, (float)this.width, (float)this.height, this.name, this.numberOfTilePerBox);
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
        else if (this.width == 0d) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "La largeur ne peut pas être nulle");
        }
        else if (this.height == 0d) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "La hauteur ne peut pas être nulle");
        }

        else if (numberOfTilePerBox == 0) {
            areValid = false;
            JOptionPane.showMessageDialog(this, "Le nombre de tuiles par boîte ne peut pas être nulle");
        }
        return areValid;
    }
}
