package gui;

import domain.room.RoomController;

import javax.swing.*;
import java.awt.*;

public class PermanentPanel extends JTabbedPane{
    public RoomController controller;

    public PermanentPanel() {
        controller = new RoomController();
        initComponent();
    }

    private void initComponent() {
        permanentPanel = new JTabbedPane();
        surfaceTabPanel = new JPanel();
        patternTabPanel = new JPanel();
        tileTabPanel = new JPanel();
        groutTabPanel = new JPanel();

        permanentPanel.setPreferredSize(new Dimension(0, 0));

        surfaceTabPanel.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.15), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.75)));

        permanentPanel.addTab("Surface", null, surfaceTabPanel, "");
        permanentPanel.addTab("Motif", null, patternTabPanel, "");
        permanentPanel.addTab("Tuile", null, tileTabPanel, "" );
        permanentPanel.addTab("Coulis", null, groutTabPanel, "");

    }


    private JTabbedPane permanentPanel;
    private JPanel surfaceTabPanel;
    private JPanel patternTabPanel;
    private JPanel tileTabPanel;
    private JPanel groutTabPanel;
}
