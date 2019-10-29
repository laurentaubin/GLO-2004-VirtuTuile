package gui;

import domain.drawing.SurfaceDrawer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends JPanel implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    private double zoom = 1d;

    public DrawingPanel(){}

    public DrawingPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;
        setBorder(new BevelBorder(BevelBorder.LOWERED));
        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int height = (int)(width*0.5);
        setBackground(Color.BLACK);
        initialDimension = new Dimension(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g){
        if (mainWindow != null){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform at = new AffineTransform();
            at.scale(zoom, zoom);


            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension);

            mainDrawer.draw(g);
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

    public void zoomInActionPerformed(){
        zoom += 0.1d;
        System.out.println(zoom);
    }

    public void zoomOutActionPerformed() {
        if (zoom > 0) {
            if (zoom - 0.1d < 0) {
                zoom = 0;
            } else {
                zoom -= 0.1d;
            }
        }
        System.out.println(zoom);
    }
}
