package gui;

import domain.room.surface.Surface;

import javax.swing.*;

public class SurfaceTab extends JPanel{
    private JPanel surfaceTab;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JButton modifierLesDimensionsDeButton;

    public SurfaceTab() {
        this.add(surfaceTab);
        formattedTextField1.setValue(0d);
        formattedTextField2.setValue(0d);
    }
}
