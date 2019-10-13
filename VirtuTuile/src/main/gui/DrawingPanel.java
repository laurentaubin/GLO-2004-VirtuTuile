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
        int heigth = (int)(width*0.5);
        initialDimension = new Dimension(width, heigth);
    }

    @Override
    protected void paintComponent(Graphics g){
        if (mainWindow != null){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension);

            double width = getWidth();
            double height = getHeight();

            double zoomWidth = width * zoom;
            double zoomHeight = height * zoom;

            double anchorx = (width - zoomWidth) / 2;
            double anchory = (height - zoomHeight) / 2;

            AffineTransform at = new AffineTransform();
            at.translate(anchorx, anchory);
            at.scale(zoom, zoom);
            at.translate(-100, -100);

            g2d.setTransform(at);

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

    public void zoomInActionPerformed() {
        zoom -= 0.1d;
    }

    public void zoomOutActionPerformed() {
        zoom += 0.1d;
    }
}
