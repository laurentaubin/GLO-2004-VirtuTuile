package gui;

import domain.drawing.SurfaceDrawer;

import java.awt.*;
import java.io.Serializable;
import javax.swing.*;

public class DrawingPanel extends JPanel implements Serializable {

    public Dimension initialDimension;
    private MainWindow mainWindow;

    private float prevZoom = 1f;
    private int zoom = 1;
    private double xOffset = 0;
    private double yOffset = 0;
    private Point zoomPoint;

    private int gridGap = 10;
    private boolean isGridActivated = false;

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


    /*
    Le code pour l'implémentation de la grille est inspiré de "https://www.buildingjavaprograms.com/DrawingPanel.java"
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (mainWindow != null) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            SurfaceDrawer mainDrawer = new SurfaceDrawer(mainWindow.controller, initialDimension, mainWindow.getCurrentMeasurementMode());

            mainDrawer.draw(g2d, zoom, prevZoom, zoomPoint, xOffset, yOffset);
            if (isGridActivated) {
                g2d.scale(zoom, zoom);

                g2d.setPaint(Color.GRAY);
                g2d.setStroke(new BasicStroke(0.25f));
                for (int row = 1; row <= initialDimension.height / this.gridGap; row++) {
                    g2d.drawLine(0, row * this.gridGap, initialDimension.width, row * this.gridGap);
                }
                for (int col = 1; col <= initialDimension.getWidth() / this.gridGap; col++) {
                    g2d.drawLine(col * this.gridGap, 0, col * this.gridGap, (int) initialDimension.getHeight());
                }
            }
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

    public boolean getGridlines() {
        return this.isGridActivated;
    }

    public void setGridLines() {
        initialDimension = mainWindow.getMainScrollPaneDimension();
        this.isGridActivated = !isGridActivated;
        this.repaint();
    }

    public double getZoom() {
        return this.zoom;
    }

    public void setZoom(int zoom) {
        this.zoom = zoom;
    }

    public void zoomInActionPerformed(Point point) {
        zoom += Math.max(zoom += 1, 5);
        this.zoomPoint = point;
        repaint();
    }

    public void zoomOutActionPerformed(Point point) {
        zoom = Math.max(1, zoom -= 1);
        this.zoomPoint = point;
        repaint();
    }


    public void zoomActionPerformed(int wheelAmount, int mouseX, int mouseY) {
        Point pos = mainWindow.getMainScrollPane().getViewport().getViewPosition();

        Dimension size = new Dimension(this.initialDimension.width * zoom, this.initialDimension.height * zoom);
        this.setPreferredSize(size);
        //mainWindow.setMainScrollPaneDimension(size);
        //this.setSize(size);

        this.revalidate();
        this.repaint();
        mainWindow.setStatusBarText(" ");

        /*

        for (Surface surface : RoomController.getSurfaceList()) {
            if (surface.getType() == "RECTANGULAR") {
                int[] x = surface.getxCoord();
                int[] y = surface.getyCoord();
                int deltaX = (int) (x[0] * zoom - x[0]);
                int deltaY = (int) (y[0] * zoom - y[0]);
                if (deltaX > 5 && deltaY > 5 && zoom > 1) {
                    for (int i = 0; i < x.length; i++) {
                        surface.setxCoord((int) ((x[i] * zoom) - deltaX), i);
                        surface.setyCoord((int) ((y[i] * zoom) - deltaY), i);
                    }
                } else if (deltaX < -5 && deltaY < -5 && zoom < 1) {
                    for (int i = 0; i < x.length; i++) {
                        surface.setxCoord((int) ((x[i] * zoom) - deltaX), i);
                        surface.setyCoord((int) ((y[i] * zoom) - deltaY), i);
                    }
                }
            } else {
                //TODO après surface irregulière
            }
            surface.updateSurface();
            this.repaint();
        }
         */
    }
}

