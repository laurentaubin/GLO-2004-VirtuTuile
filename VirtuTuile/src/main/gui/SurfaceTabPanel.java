package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

/*
Code inspiré de: Java Tutorials Code Sample – FormattedTextFieldDemo.java
 */

public class SurfaceTabPanel extends JPanel{

    public SurfaceTabPanel(){
        super(new BorderLayout());

        widthLabel = new JLabel(widthString);
        heightLabel = new JLabel(heightString);

        widthField = new JFormattedTextField(widthFormat);
        widthField.setColumns(10);
        widthField.setValue(100d);

        heightField = new JFormattedTextField(widthFormat);
        heightField.setValue(100d);
        widthField.setColumns(10);

        widthLabel.setLabelFor(widthField);
        heightLabel.setLabelFor(heightField);

        JPanel labelPane = new JPanel(new GridLayout(0,1));
        labelPane.add(widthLabel);
        labelPane.add(heightLabel);

        JPanel fieldPane = new JPanel(new GridLayout(0, 1));
        fieldPane.add(widthField);
        fieldPane.add(heightField);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
    }

    private float width;
    private float height;

    private JLabel widthLabel;
    private JLabel heightLabel;

    private static String widthString = "Width: ";
    private static String heightString = "Height: ";

    private JFormattedTextField widthField;
    private JFormattedTextField heightField;

    private NumberFormat widthFormat;
    private NumberFormat heightFormat;
}

