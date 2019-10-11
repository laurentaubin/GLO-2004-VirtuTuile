package gui;

import domain.drawing.SurfaceDrawer;

import java.awt.*;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends JPanel implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    public DrawingPanel(){}

    public DrawingPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int heigth = (int)(width*0.5);
        initialDimension = new Dimension(width, heigth);
    }

    @Override
    protected void paintComponent(Graphics g){
        if (mainWindow != null){
            super.paintComponent(g);
            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension);
            //mainDrawer.draw(g);
        }
    }

    public MainWindow getMainWindow(){
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow){
        this.mainWindow = mainWindow;
    }

    public Dimension getInitialDimension(){
        return initialDimension;
    }

    public void setInitialDimension(){
        this.mainWindow = mainWindow;
    }
}
