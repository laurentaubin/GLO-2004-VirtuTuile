package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SurfaceTab extends JPanel{
    private MainWindow mainWindow;
    private JPanel surfaceTab;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JButton combineButton;
    private JButton surfaceColorButton;
    private JPanel dimensionPanel;
    private JCheckBox holeCheckBox;
    private JLabel surfaceTitle;
    private JLabel dimensionLabel;
    private JPanel optionsPanel;
    private JLabel dispositionLabel;
    private JButton chromaticButton;
    private Color surfaceColor;

    public SurfaceTab(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.add(surfaceTab);
        widthField.setValue(0d);
        heightField.setValue(0d);
        surfaceTitle.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        holeCheckBox.setFocusPainted(true);


        surfaceColorButton.setPreferredSize(new Dimension(50, 20));
        chromaticButton.setPreferredSize(new Dimension(20, 20));
        chromaticButton.setIcon(new ImageIcon(new ImageIcon("src/image/chromatic.png").getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT)));

        surfaceColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", surfaceColor);
                setButtonColor(color);
                setSurfaceColor(color);
            }
        });

        widthField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredWidthSurfaceDimensions();
            }
        });

        heightField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setEnteredHeightSurfaceDimensions();
            }
        });

        combineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                combineSelectedSurface();

            }
        });

        //TODO Peut-être à changer pour ActionListener
        holeCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    setSelectedSurfaceAsHole();
                }
                else if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                    setSelectedSurfaceAsWhole();
                }
            }
        });

        chromaticButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", surfaceColor);
                setButtonColor(color);
                setSurfaceColor(color);
            }
        });
    }

    public void setEnteredWidthSurfaceDimensions(){
        double enteredWidth = (double)widthField.getValue();
        this.mainWindow.setSelectedSurfaceWidth(enteredWidth);
    }

    public void setEnteredHeightSurfaceDimensions() {
        double enteredHeight = (double)heightField.getValue();
        this.mainWindow.setSelectedSurfaceHeight(enteredHeight);
    }

    public void combineSelectedSurface() {
        this.mainWindow.combineSelectedSurfaces();
    }

    public void setButtonColor(Color color) {
        this.surfaceColor = color;
        surfaceColorButton.setBackground(color);
        surfaceColorButton.setOpaque(true);

    }

    public void setSurfaceColor(Color color){
        mainWindow.setSelectedSurfaceColor(color);
    }

    private Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public void setSurfaceDimensionField(Dimension dimension) {
        this.widthField.setValue(dimension.getWidth());
        this.heightField.setValue(dimension.getHeight());
    }


    public void setSelectedSurfaceAsWhole() {
        mainWindow.setSelectedSurfaceAsWhole();
    }

    public void setSelectedSurfaceAsHole() {
        mainWindow.setSelectedSurfaceAsHole();
    }

    public void setHoleCheckBox(boolean isSelectedSurfaceAHole, int numberOfSelectedSurfaces) {
        if (numberOfSelectedSurfaces == 1) {
            holeCheckBox.setEnabled(true);
            if (isSelectedSurfaceAHole) {
                holeCheckBox.setSelected(true);
            }
            else{
                holeCheckBox.setSelected(false);
            }
        }
        else  {
            holeCheckBox.setEnabled(false);
        }
    }
}
