package gui;

import domain.drawing.SurfaceDrawer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

//Code du zoom inspiré de https://stackoverflow.com/questions/13155382/jscrollpane-zoom-relative-to-mouse-position

public class DrawingPanel extends JPanel implements Serializable {
    final double ZOOM_FACTOR = 1.05;

    public Dimension initialDimension;
    private MainWindow mainWindow;

    private float prevZoom = 1f;
    private double zoom = 1d;
    private double xOffset = 0;
    private double yOffset = 0;
    private Point zoomPoint;

    private double gridGap = 100d;
    private boolean isGridActivated = false;

    public DrawingPanel() {
    }

    public DrawingPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        int width = mainWindow.getMainScrollPaneDimension().width;
        int height = mainWindow.getMainScrollPaneDimension().height;

        //int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().width);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
        //int height = (int) (width * 0.5);
        this.initialDimension = new Dimension(800, 600);
        setBackground(Color.WHITE);
    }



    //Le code pour l'implémentation de la grille est inspiré de "https://www.buildingjavaprograms.com/DrawingPanel.java"
    @Override
    protected void paintComponent(Graphics g) {
        if (mainWindow != null) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            if (isGridActivated) {
                g2d.scale(zoom, zoom);
                g2d.setPaint(Color.LIGHT_GRAY);
                g2d.setStroke(new BasicStroke(0.25f / (float)getZoom()));
                Dimension ajustingDimension = new Dimension(this.getWidth(), this.getHeight());
                if (zoom >= 1) {
                    for (int row = 1; row <= ajustingDimension.getHeight() / this.gridGap; row++) {
                        g2d.drawLine(0, (int) (row * this.gridGap), (int)ajustingDimension.getWidth(), (int) (row * this.gridGap));
                    }
                    for (int col = 1; col <= (int)ajustingDimension.getWidth() / this.gridGap; col++) {
                        g2d.drawLine((int) (col * this.gridGap), 0, (int) (col * this.gridGap), (int)ajustingDimension.getHeight());
                    }
                }
                else if (zoom < 1) {
                    for (int row = 1; row <= (ajustingDimension.getHeight()/ (this.gridGap)) / (zoom) ; row++) {
                        g2d.drawLine(0, (int) (row * this.gridGap), (int)(ajustingDimension.getWidth() / zoom), (int) (row * this.gridGap));
                    }
                    for (int col = 1; col <= ((int)ajustingDimension.getWidth() / this.gridGap) / (zoom); col++) {
                        g2d.drawLine((int) ((col) * this.gridGap), 0, (int) ((col) * this.gridGap), (int)(ajustingDimension.getHeight() / zoom));
                    }
                }
            }
            mainWindow.draw(g2d, this, zoom);
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

    public double getGridGap() {
        return this.gridGap;
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

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void zoomInActionPerformed(Point point) {
        this.setZoom(getZoom() * ZOOM_FACTOR);
        Point pos = mainWindow.getMainScrollPane().getViewport().getViewPosition();

        int newX = (int)(point.x*(1.1f - 1f) + ZOOM_FACTOR * pos.x);
        int newY = (int)(point.y*(1.1f - 1f) + ZOOM_FACTOR * pos.y);
        Point newPoint = new Point(newX, newY);
        mainWindow.setMainScrollPanePosition(newPoint);

        setDrawingPanelDimensions();

        revalidate();
        repaint();
    }

    public void zoomOutActionPerformed(Point point) {
        this.setZoom(getZoom() / ZOOM_FACTOR);
        Point pos = mainWindow.getMainScrollPane().getViewport().getViewPosition();

        int newX = (int)(point.x*(0.9f - 1f) + pos.x / ZOOM_FACTOR);
        int newY = (int)(point.y*(0.9f - 1f) + pos.y / ZOOM_FACTOR);
        Point newPoint = new Point(newX, newY);
        mainWindow.setMainScrollPanePosition(newPoint);

        setDrawingPanelDimensions();

        revalidate();
        repaint();
    }

    public void setDrawingPanelDimensions() {
        Dimension dimension = new Dimension((int)initialDimension.getWidth(), (int)initialDimension.getHeight());
        this.setPreferredSize(new Dimension((int)(dimension.getWidth() * zoom), (int)(dimension.getHeight() * zoom)));
        revalidate();
    }

    public void setDrawingPanelDimensionsOut() {
        Dimension dimension = new Dimension((int)this.getWidth(), (int)this.getHeight());
        //Dimension dimension = new Dimension((int)initialDimension.getWidth(), (int)initialDimension.getHeight());
        this.setPreferredSize(new Dimension((int)(dimension.getWidth() / ZOOM_FACTOR), (int)(dimension.getHeight() / ZOOM_FACTOR)));
        //this.setPreferredSize(new Dimension((int)(this.getWidth() / ZOOM_FACTOR), (int)(this.getHeight() / ZOOM_FACTOR)));
        revalidate();
    }



    public void setDrawingPanelInitialDimension() {
        this.setPreferredSize(mainWindow.getMainScrollPaneDimension());
        validate();
        revalidate();
    }

    public void setGridGap(double newGridGap) {
        this.gridGap = newGridGap;
    }

    public void setWidth() {
        Dimension dimension = new Dimension(this.getWidth() + 20, this.getHeight());
        this.setPreferredSize(dimension);
        this.initialDimension = dimension;
        validate();
        revalidate();
    }

    public void setHeight() {
        Dimension dimension = new Dimension(this.getWidth(), this.getHeight() + 20);
        this.setPreferredSize(dimension);
        this.initialDimension = dimension;
        validate();
        revalidate();
    }
}

