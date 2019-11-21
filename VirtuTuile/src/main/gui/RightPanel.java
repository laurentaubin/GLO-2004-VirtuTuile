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
        this.setBackground(Color.GRAY);
        initRightPanel();
    }

    private void initRightPanel(){
        surfaceTabPanel = new SurfaceTab(mainWindow);
        patternTabPanel = new PatternTab(mainWindow);
        tileTabPanel = new TileTab(mainWindow);
        //groutTabPanel = new GroutTabPanel(this);

        //this.setPreferredSize(new Dimension(10, 10));

        this.addTab("Surface", null, surfaceTabPanel, "");
        this.addTab("Motif", null, patternTabPanel, "");
        this.addTab("Tuile", null, tileTabPanel, "" );
        //this.addTab("Coulis", null, groutTabPanel, "");
        this.setSelectedIndex(0);
    }

    public void updateSurfaceTabDimensions(Dimension dimension) {
        this.surfaceTabPanel.setSurfaceDimensionField(dimension);
    }

    public void updateSurfaceTabColor(Color color) {
        this.surfaceTabPanel.setButtonColor(color);
    }

    public void updateIfSelectedSurfaceIsAHole(boolean isSelectedSurfaceAHole, int number) {
        this.surfaceTabPanel.setHoleCheckBox(isSelectedSurfaceAHole, number);
    }

    public MainWindow getMainWindow() {
        return this.mainWindow;
    }

    private SurfaceTab surfaceTabPanel;
    private PatternTab patternTabPanel;
    private TileTab tileTabPanel;
    private JButton button;
}
