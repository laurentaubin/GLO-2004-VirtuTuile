package gui;

import domain.drawing.SurfaceDrawer;
import domain.room.RoomController;
import domain.room.surface.Surface;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    private double zoom = 1d;


    public DrawingPanel() {
    }

    public DrawingPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, 1));
        setVisible(true);
        int height = (int) (width * 0.5);
        initialDimension = new Dimension(800, 600);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (mainWindow != null) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension);

            mainDrawer.draw(g2d);
        }
    }

    public MainWindow getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public Dimension getInitialDimension() {
        return initialDimension;
    }

    public void zoomActionPerformed(int wheelAmount, int mouseX, int mouseY) {
        if (wheelAmount == 1) {
            zoom = 1.1;
        } else {
            zoom = .9;
        }

    }

}
    /*
        for(Surface surface: RoomController.getSurfaceList()){
            if(surface.getType() == "RECTANGULAR"){
                int[] x = surface.getxCoord();
                int[] y = surface.getyCoord();
                int deltaX = (int)(x[0]*zoom - x[0]);
                int deltaY = (int)(y[0]*zoom - y[0]);
                if(deltaX > 5 && deltaY > 5 && zoom > 1) {
                    for (int i = 0; i < x.length; i++) {
                        surface.setxCoord((int) ((x[i] * zoom) - deltaX), i);
                        surface.setyCoord((int) ((y[i] * zoom) - deltaY), i);
                    }
                }
                else if(deltaX < -5 && deltaY < -5 && zoom < 1){
                    for (int i = 0; i < x.length; i++) {
                        surface.setxCoord((int) ((x[i] * zoom) - deltaX), i);
                        surface.setyCoord((int) ((y[i] * zoom) - deltaY), i);
                    }
                }
            }else {
                //TODO après surface irregulière
            }
            surface.updateSurface();
            this.repaint();
        }
    }*/
