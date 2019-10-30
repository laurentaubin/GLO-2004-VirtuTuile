package gui;

import domain.drawing.SurfaceDrawer;
import domain.room.Surface;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class DrawingPanel extends JPanel implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    private double zoom = 1d;
    private double translateX = 0d;
    private double translateY = 0d;


    public DrawingPanel(){}

    public DrawingPanel(MainWindow mainWindow){
        this.mainWindow = mainWindow;

        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int height = (int)(width*0.5);
        initialDimension = new Dimension(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g){
        if (mainWindow != null){
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(zoom, zoom);
            g2d.translate(translateX, translateY);
            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension);

            mainDrawer.draw(g2d);
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

    public void zoomActionPerformed(int wheelAmount, int mouseX, int mouseY){
        if (wheelAmount == 1){
            zoom = zoom * 1.1;
        }
        else{
            zoom = zoom / 1.1;
        }
        System.out.println(wheelAmount + ", " + mouseX + ", " + mouseY);
        translateX -= 0.5 * wheelAmount * (mouseX - (this.getHeight()/2));//Proportion à travailler
        translateY -= 0.5 * wheelAmount * (mouseY - (this.getWidth()/2));    //Proportion à travailler
        this.repaint();

    }

    /*public void zoomOutActionPerformed(int wheelAmount) {
        zoom = zoom * wheelAmount;
        this.repaint();
    }*/
}
