package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.concurrent.Flow;

public class SurfaceTabPanel extends JPanel{

    public SurfaceTabPanel(RightPanel rightPanel){
        this.rightPanel = rightPanel;
        initComponent();
    }

    public void initComponent(){
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
    }

    private void setWidthValue(float width){
        System.out.println("DANS setVALUE");
        this.widthValue = width;
        this.updateUI();
    }

    public void updateInformations(float width){
        setWidthValue(width);
    }

    private RightPanel rightPanel;

    private JPanel surfaceDimensionPanel;

    private float width;
    private float height;

    private JLabel widthLabel;
    private JLabel heightLabel;

    private static String widthString = "Width: ";
    private static String heightString = "Height: ";

    private JFormattedTextField widthField;
    private JFormattedTextField heightField;

    private float widthValue = 0f;
    private float heightValue = 0f;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;
}

