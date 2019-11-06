package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.concurrent.Flow;

public class SurfaceTabPanel extends JPanel{

    public SurfaceTabPanel(RightPanel rightPanel){
        this.rightPanel = rightPanel;
        initComponent();
    }

    public void initComponent(){
        modifyButton = new JButton("Modifier le dimensions");

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                modifyButtonPressed(actionEvent);
            }
        });

        widthLabel = new JLabel(widthString);
        heightLabel = new JLabel(heightString);

        widthField = new JFormattedTextField(widthFormat);
        widthField.setColumns(10);
        widthField.setValue(this.widthValue);

        heightField = new JFormattedTextField(heightFormat);
        heightField.setColumns(10);
        heightField.setValue(this.heightValue);

        widthLabel.setLabelFor(widthField);
        heightLabel.setLabelFor(heightField);

        JPanel surfaceDimensionPanel = new JPanel();
        JPanel widthPanel = new JPanel(new FlowLayout());
        JPanel heightPanel = new JPanel(new FlowLayout());

        surfaceDimensionPanel.setBorder(BorderFactory.createTitledBorder("Dimensions"));

        BorderLayout layout = new BorderLayout();
        surfaceDimensionPanel.setLayout(layout);

        widthPanel.add(widthLabel);
        widthPanel.add(widthField);

        heightPanel.add(heightLabel);
        heightPanel.add(heightField);

        surfaceDimensionPanel.add(widthPanel, BorderLayout.WEST);
        surfaceDimensionPanel.add(heightPanel, BorderLayout.CENTER);

        this.add(surfaceDimensionPanel, BorderLayout.NORTH);
        this.add(modifyButton, BorderLayout.EAST);
    }

    private void setDimensionsValue(double[] dimensions){
        widthField.setValue(dimensions[0]);
        heightField.setValue(dimensions[1]);
        repaint();
    }

    public void updateInformations(double[] dimensions){
        setDimensionsValue(dimensions);
    }

    private void modifyButtonPressed(ActionEvent evt) {
        double modify_width = (double)widthField.getValue();
        double modify_height = (double)heightField.getValue();

        double[] dimensionsList = new double[2];
        dimensionsList[0] = modify_width;
        dimensionsList[1] = modify_height;

        this.rightPanel.getMainWindow().controller.setSelectedRectangularSurfaceDimensions(dimensionsList);
        this.rightPanel.getMainWindow().getDrawingPanel().repaint();
    }

    private RightPanel rightPanel;

    private JPanel surfaceDimensionPanel;
    private JPanel modifyAndErasePanel;

    private float width;
    private float height;

    private JLabel widthLabel;
    private JLabel heightLabel;

    private static String widthString = "Width: ";
    private static String heightString = "Height: ";

    private JFormattedTextField widthField;
    private JFormattedTextField heightField;

    private float widthValue = 0f;
    private float heightValue;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;

    private JButton modifyButton;
    private JButton EraseButton;
}

