package gui;

import domain.room.TileType;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;


public class RightPanel extends JTabbedPane implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;
    private ImageIcon surfaceTabIcon;
    private ImageIcon patternTabIcon;
    private ImageIcon tileTabIcon;

    public RightPanel(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int height = (int)(width*0.5);
        this.setBackground(Color.GRAY);
        initRightPanel();
    }

    private void initRightPanel() throws IOException {
        surfaceTabScrollpane = new JScrollPane();
        patternTabScrollpane = new JScrollPane();
        tileTabScrollpane = new JScrollPane();

        surfaceTabPanel = new SurfaceTab(mainWindow);
        patternTabPanel = new PatternTab(mainWindow);
        tileTabPanel = new TileTab(mainWindow);

        //Rectangle icon by Icons8
        BufferedImage surfaceImage = ImageIO.read(this.getClass().getResourceAsStream("/image/surfaceTab.png"));
        Icon surfaceTabIcon = new ImageIcon(surfaceImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        surfaceTabScrollpane.setViewportView(surfaceTabPanel);
        this.addTab("", surfaceTabIcon, surfaceTabScrollpane, "");

        BufferedImage patternImage = ImageIO.read(this.getClass().getResourceAsStream("/image/patternTab.png"));
        Icon patternTabIcon = new ImageIcon(patternImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        patternTabScrollpane.setViewportView(patternTabPanel);
        this.addTab("", patternTabIcon, patternTabScrollpane, "");

        BufferedImage tileImage = ImageIO.read(this.getClass().getResourceAsStream("/image/tileIcon.jpg"));
        Icon tileTabIcon = new ImageIcon(tileImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        tileTabScrollpane.setViewportView(tileTabPanel);
        this.addTab("", tileTabIcon, tileTabScrollpane, "" );
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

    public void updatePatternTab(double groutWidth, int number) {
        this.patternTabPanel.setGroutWidth(groutWidth, number);
    }

    public void updateTileTab(float width, float height, String name, Color color, int nbrTilesPerBox) {
        tileTabPanel.setCurrentTileInfo(width, height, name, color, nbrTilesPerBox);
    }

    public void updateSurfaceInformation(int nbTile, int nbBox) {
        surfaceTabPanel.updateSurfaceInformation(nbTile, nbBox);
    }

    public void updateSurfaceTabDistances(Dimension dimension) {
        this.surfaceTabPanel.setSurfacesDistancesField(dimension);
    }

    public MainWindow getMainWindow() {
        return this.mainWindow;
    }


    private SurfaceTab surfaceTabPanel;
    private PatternTab patternTabPanel;
    private TileTab tileTabPanel;
    private JButton button;
    private JScrollPane patternTabScrollpane;
    private JScrollPane surfaceTabScrollpane;
    private JScrollPane tileTabScrollpane;


    public void showSurfaceInformation() {
        this.surfaceTabPanel.showSurfaceInformation();
    }

    public void showProjectInformation() {
        this.surfaceTabPanel.showProjetInformation();
    }
}
