package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SurfaceTab extends JPanel{
    private MainWindow mainWindow;
    private JPanel surfaceTab;
    private JFormattedTextField widthField;
    private JFormattedTextField heightField;
    private JButton modifierLesDimensionsDeButton;
    private JButton surfaceColorButton;
    private Color surfaceColor;

    public SurfaceTab(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.add(surfaceTab);
        widthField.setValue(0d);
        heightField.setValue(0d);

        surfaceColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Color color = JColorChooser.showDialog(null, "Choose a color", surfaceColor);

                setButtonColor(color);
            }
        });

        modifierLesDimensionsDeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyButtonPressed(actionEvent);
            }
        });
    }

    public void setButtonColor(Color color) {
        this.surfaceColor = color;
        surfaceColorButton.setBackground(color);
        surfaceColorButton.setOpaque(true);
        surfaceColorButton.setBorderPainted(false);
        mainWindow.controller.setSelectedSurfaceColor(color);
    }

    private Color getSurfaceColor() {
        return this.surfaceColor;
    }

    public void setDimensionsValue(double[] dimensions){
        widthField.setValue(dimensions[0]);
        heightField.setValue(dimensions[1]);
        repaint();
    }

    private void modifyButtonPressed(ActionEvent evt) {
        double modify_width = (double)widthField.getValue();
        double modify_height = (double)heightField.getValue();

        double[] dimensionsList = new double[2];
        dimensionsList[0] = modify_width;
        dimensionsList[1] = modify_height;
        this.mainWindow.controller.setSelectedRectangularSurfaceDimensions(dimensionsList);
        this.mainWindow.getDrawingPanel().repaint();
    }

}
