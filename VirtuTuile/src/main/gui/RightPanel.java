package gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.io.Serializable;

public class RightPanel extends JTabbedPane implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    public RightPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int height = (int)(width*0.5);
        initRightPanel();
    }

    private void initRightPanel(){
        surfaceTabPanel = new SurfaceTabPanel(this);
        patternTabPanel = new PatternTabPanel(this);
        tileTabPanel = new TileTabPanel(this);
        groutTabPanel = new GroutTabPanel(this);




        //this.setPreferredSize(new Dimension(10, 10));

            this.addTab("Surface", null, surfaceTabPanel, "");

            this.addTab("Motif", null, patternTabPanel, "");
            this.addTab("Tuile", null, tileTabPanel, "" );
            this.addTab("Coulis", null, groutTabPanel, "");
            this.setSelectedIndex(0);

    }





    private JPanel surfaceTabPanel;
    private JPanel patternTabPanel;
    private JPanel tileTabPanel;
    private JPanel groutTabPanel;
    private JButton button;
}