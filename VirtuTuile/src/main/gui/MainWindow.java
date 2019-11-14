package gui;

import domain.room.RoomController;
import util.UnitConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

import static gui.MainWindow.ApplicationMode.*;


public class MainWindow extends JFrame {

    public RoomController controller;

    private ApplicationMode currentApplicationMode = SELECT;
    private MeasurementUnitMode currentMeasurementMode = MeasurementUnitMode.METRIC;

    public Point currentMousePoint = new Point();
    public Point initMousePoint = new Point();
    public Point prevMousePoint = new Point();

    public enum ApplicationMode {
        SELECT, ADD_RECTANGULAR, ADD_IRREGULAR
    }

    public enum MeasurementUnitMode {
        METRIC, IMPERIAL
    }


    public MainWindow(){

        controller = new RoomController();
        initComponents();
        setFocusable(true);
    }

    public void setApplicationMode (ApplicationMode newMode) {
        this.currentApplicationMode = newMode;
    }
    public void setMeasurementMode (MeasurementUnitMode newMode) { this.currentMeasurementMode = newMode; }
    public MeasurementUnitMode getCurrentMeasurementMode() { return this.currentMeasurementMode; }

    public DrawingPanel getDrawingPanel(){
        return this.drawingPanel;
    }


    private void initComponents() {

        mainPanel = new JPanel();
        buttonGroup = new ButtonGroup();
        topButtonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectButton = new JToggleButton("Sélection");
        rectangularSurfaceButton = new JToggleButton("Ajouter une surface rectangulaire");
        irregularSurfaceButton = new JToggleButton("Ajouter surface irrégulière");
        zoomInButton = new JButton("+");
        zoomOutButton = new JButton("-");

        measurementUnitComboBox = new javax.swing.JComboBox();
        drawingPanel = new DrawingPanel(this);
        mainScrollPane = new JScrollPane();
        splitPane = new JSplitPane();

        rightPanel = new RightPanel(this);

        menuBar = new JMenuBar();
        fileMenu = new JMenu();
        newProjectItem = new JMenuItem();
        openMenuItem = new JMenuItem();
        closeMenuItem = new JMenuItem();
        saveMenuItem = new JMenuItem();
        saveAsMenuItem = new JMenuItem();

        editionMenu = new JMenu();
        undoMenuItem = new JMenuItem();
        redoMenuItem = new JMenuItem();
        cutMenuItem = new JMenuItem();
        pasteMenuItem = new JMenuItem();
        copyMenuItem = new JMenuItem();

        viewMenu = new JMenu();
        gridMenuItem = new JMenuItem();
        measurementUnitMenuItem = new JMenuItem();

        insertMenu = new JMenu();
        surfaceMenuItem = new JMenuItem();
        patternMenuItem = new JMenuItem();
        tileMenuItem = new JMenuItem();
        groutMenuItem = new JMenuItem();

        windowMenu = new JMenu();
        minimizeMenuItem = new JMenuItem();
        zoomMenuItem = new JMenuItem();

        statusBar = new JLabel(" ");
        statusBar.setBackground(Color.GRAY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VirtuTuile");

        mainPanel.setLayout(new BorderLayout());

        topButtonBar.setPreferredSize(new Dimension(400, 35));

        selectButton.setSelected(true);

        this.setApplicationMode(ApplicationMode.SELECT);

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectButtonActionPerformed(actionEvent);
            }
        });

        rectangularSurfaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                rectangularSurfaceButtonPerformed(actionEvent);
            }
        });

        irregularSurfaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                irregularSurfaceButtonPerformed(actionEvent);
            }
        });
/*
        // Pas rapport

        /*

=======


        /*
>>>>>>> 941acb977c1c98e79281b777ceaaed51ca52fc40
        drawingPanel.addMouseWheelListener(new java.awt.event.MouseAdapter() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                int wheel = evt.getWheelRotation();
                if (wheel < 0) {
                    drawingPanel.zoomInActionPerformed(evt.getScrollAmount());
                } else {
                    drawingPanel.zoomOutActionPerformed(evt.getScrollAmount());
                }
            }
        });
<<<<<<< HEAD
*/

        measurementUnitComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "MÉTRIQUE", "IMPÉRIALE" }));
        measurementUnitComboBox.setPreferredSize(new Dimension(120, 23));

        measurementUnitComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int mode = measurementUnitComboBox.getSelectedIndex();

                switch(mode) {
                    case 0:
                        metricModeSelected(actionEvent);
                        break;
                    case 1:
                        imperialModeSelected(actionEvent);
                        break;
                }
            }

        });

        buttonGroup.add(selectButton);
        buttonGroup.add(rectangularSurfaceButton);
        buttonGroup.add(irregularSurfaceButton);

        topButtonBar.add(selectButton);
        topButtonBar.add(rectangularSurfaceButton);
        topButtonBar.add(irregularSurfaceButton);
        topButtonBar.add(zoomInButton);
        topButtonBar.add(zoomOutButton);
        topButtonBar.add(measurementUnitComboBox);

        mainPanel.add(topButtonBar, BorderLayout.NORTH);

        splitPane.setMinimumSize(new Dimension(0, 202));
        splitPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));

        mainScrollPane.setMinimumSize(new Dimension(0, 202));
        mainScrollPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));

        System.out.println(mainScrollPane.getWidth());

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                drawingPanelKeyPressed(evt);
            }
            public void keyReleased(KeyEvent evt) {
                //drawingPanelKeyReleased(evt);
            }
            public void keyTyped(KeyEvent evt) {
                //drawingPanelKeyTyped(evt);
            }
        });

        drawingPanel.addMouseWheelListener(new java.awt.event.MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent evt){
                Point point = evt.getPoint();
                if (evt.getPreciseWheelRotation() > 0) {
                    drawingPanel.zoomInActionPerformed(point);
                }
                else {
                    drawingPanel.zoomOutActionPerformed(point);
                }
            }
        });

        drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                drawingPanelMousePressed(evt);
            }
        });
        drawingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                drawingPanelMouseReleased(evt);
            }
        });

        drawingPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                drawingPanelMouseDragged(evt);
            }
            public void mouseMoved(MouseEvent evt) {
                drawingPanelMouseMoved(evt);
            }
        });

        GroupLayout drawingPanelLayout = new GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
                drawingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 1598, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
                drawingPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 538, Short.MAX_VALUE)
        );

        mainScrollPane.setViewportView(drawingPanel);

        splitPane.setLeftComponent(mainScrollPane);

        splitPane.setRightComponent(rightPanel);

        splitPane.setOneTouchExpandable(true);

        splitPane.setResizeWeight(0.90);

        statusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        mainPanel.add(splitPane, BorderLayout.CENTER);
        mainPanel.add(statusBar, "South");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );

        fileMenu.setText("Fichier");
        newProjectItem.setText("Nouveau projet");
        fileMenu.add(newProjectItem);
        openMenuItem.setText("Ouvrir projet");
        fileMenu.add(openMenuItem);
        closeMenuItem.setText("Fermer");
        fileMenu.add(closeMenuItem);
        saveMenuItem.setText("Sauvegarder");
        fileMenu.add(saveMenuItem);
        saveAsMenuItem.setText("Sauvegarder sous...");
        fileMenu.add(saveAsMenuItem);

        editionMenu.setText("Édition");
        undoMenuItem.setText("Undo");
        editionMenu.add(undoMenuItem);
        redoMenuItem.setText("Redo");
        editionMenu.add(redoMenuItem);
        copyMenuItem.setText("Copier");
        editionMenu.add(copyMenuItem);
        pasteMenuItem.setText("Coller");
        editionMenu.add(pasteMenuItem);
        cutMenuItem.setText("Couper");
        editionMenu.add(cutMenuItem);

        viewMenu.setText("Affichage");
        gridMenuItem.setText("Grille");
        gridMenuItem.setSelected(drawingPanel.getGridlines());
        gridMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridMenuItemActionPerformed(actionEvent);
            }
        });
        viewMenu.add(gridMenuItem);
        measurementUnitMenuItem.setText("Unité de mesure");
        viewMenu.add(measurementUnitMenuItem);

        insertMenu.setText("Insérer");
        surfaceMenuItem.setText("Surface");
        insertMenu.add(surfaceMenuItem);
        patternMenuItem.setText("Motif");
        insertMenu.add(patternMenuItem);
        tileMenuItem.setText("Tuile");
        insertMenu.add(tileMenuItem);
        groutMenuItem.setText("Coulis/Joint");
        insertMenu.add(groutMenuItem);

        windowMenu.setText("Fenêtre");
        minimizeMenuItem.setText("Minimiser");
        windowMenu.add(minimizeMenuItem);
        zoomMenuItem.setText("Zoom");
        windowMenu.add(zoomMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editionMenu);
        menuBar.add(viewMenu);
        menuBar.add(insertMenu);
        menuBar.add(windowMenu);

        setJMenuBar(menuBar);

        pack();
    }

    private void selectButtonActionPerformed(ActionEvent actionEvent){this.setApplicationMode(ApplicationMode.SELECT);}

    private void rectangularSurfaceButtonPerformed(ActionEvent actionEvent){this.setApplicationMode(ApplicationMode.ADD_RECTANGULAR);}

    private void irregularSurfaceButtonPerformed(ActionEvent actionEvent){this.setApplicationMode(ApplicationMode.ADD_IRREGULAR);}

    private void metricModeSelected(ActionEvent actionEvent) {
        this.setMeasurementMode(MeasurementUnitMode.METRIC);
        this.controller.setMeasurementMode(MeasurementUnitMode.METRIC);
        this.drawingPanel.repaint();
    }

    private void imperialModeSelected(ActionEvent actionEvent) {
        this.setMeasurementMode(MeasurementUnitMode.IMPERIAL);
        this.controller.setMeasurementMode(MeasurementUnitMode.IMPERIAL);
        this.drawingPanel.repaint();
    }



    private void drawingPanelKeyPressed(java.awt.event.KeyEvent evt) {

        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && RoomController.surfaceSelecte()){
            String[] options = {"Ok", "Cancel"};
            int indexReponse = JOptionPane.showOptionDialog(null, "Voulez-vous vraiment supprimer cette surface?",
                    "Attention!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if(indexReponse == 0){
                RoomController.deleteSurface();
            }
        }
        drawingPanel.repaint();
    }

    private void drawingPanelMousePressed(MouseEvent mouseEvent){
        this.prevMousePoint = mouseEvent.getPoint();
        this.initMousePoint = mouseEvent.getPoint();
        this.currentMousePoint = this.initMousePoint;
        this.requestFocus();
        boolean isShiftDown = false;


        if (this.currentApplicationMode == ApplicationMode.SELECT && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!

            double xPos = UnitConverter.convertPixelToSelectedUnit((int) this.initMousePoint.getX(), this.currentMeasurementMode) * 10000;
            double yPos = UnitConverter.convertPixelToSelectedUnit((int) this.initMousePoint.getY(), this.currentMeasurementMode) * 10000;

            this.controller.switchSelectionStatus(xPos, yPos, mouseEvent.isShiftDown());
            // this.controller.switchSelectionStatus(this.initMousePoint.getX(), this.initMousePoint.getY(), mouseEvent.isShiftDown());
            drawingPanel.repaint();

           // rightPanel.updateInformations(this.controller.getSelectedRectangularSurfaceDimensions());
        }

        if (this.currentApplicationMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
        }

        if (this.currentApplicationMode == ApplicationMode.ADD_IRREGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
        }

    }

    private void drawingPanelMouseReleased(MouseEvent mouseEvent){
        Point mousePointReleased = mouseEvent.getPoint();

        if (this.currentApplicationMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!

            Point position = this.initMousePoint.getLocation();

            int[] xDrawPoints = getXDrawPoints();
            int[] yDrawPoints = getYDrawPoints();

            if (xDrawPoints[0] - yDrawPoints[1] > 0){
                position = mousePointReleased.getLocation();
            }

            // controller.addRectangularProjection(position, xPoints, yPoints);
            drawingPanel.repaint();
            RoomController.clearSurfaceProjectionList();

            controller.addSurface(position ,xDrawPoints, yDrawPoints, 4);
        }

        else {
            if (this.currentApplicationMode == ADD_IRREGULAR) {
                SwingUtilities.isLeftMouseButton((mouseEvent));
            }
            //TODO Code pour surface irrégulière
        }
        drawingPanel.repaint();
    }

    private void drawingPanelMouseDragged(MouseEvent mouseEvent){
        // TODO ça marche pas pcq le init mouse point est pas updaté a bonne palce faique le delta est pas bon
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            this.controller.updateSelectedSurfacesPositions(mouseEvent.getX() - this.currentMousePoint.getX(), mouseEvent.getY() - this.currentMousePoint.getY());
            this.currentMousePoint = mouseEvent.getPoint();

        }

        else if (this.currentApplicationMode == ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            this.currentMousePoint = mouseEvent.getPoint();
            int[] xDrawPoints = getXDrawPoints();
            int[] yDrawPoints = getYDrawPoints();

            controller.addRectangularProjection(initMousePoint, xDrawPoints, yDrawPoints);
        }
        drawingPanel.repaint();
    }

    private int[] getXDrawPoints() {
        int[] drawPoints = new int[4];

        drawPoints[0] = (int)initMousePoint.getX();
        drawPoints[1] = (int)currentMousePoint.getX();
        drawPoints[2] = (int)currentMousePoint.getX();
        drawPoints[3] = (int)initMousePoint.getX();

        return UnitConverter.convertPixelListToSelectedUnit(drawPoints, this.currentMeasurementMode);
    }

    private int[] getYDrawPoints() {
        int[] drawPoints = new int[4];

        drawPoints[0] = (int)initMousePoint.getY();
        drawPoints[1] = (int)initMousePoint.getY();
        drawPoints[2] = (int)currentMousePoint.getY();
        drawPoints[3] = (int)currentMousePoint.getY();

        return UnitConverter.convertPixelListToSelectedUnit(drawPoints, this.currentMeasurementMode);
    }

    private void gridMenuItemActionPerformed(ActionEvent actionEvent) {
        drawingPanel.setGridLines();
    }

    private void drawingPanelMouseMoved(MouseEvent evt) {
        //TODO convertir les unités
        double x = evt.getX() / drawingPanel.getZoom();
        double y = evt.getY() / drawingPanel.getZoom();
        String mousePosition = "x= " + x + ", y= " + y;
        setStatusBarText(mousePosition);
    }

    public void setStatusBarText(String statusBarText) {
        statusBar.setText(statusBarText);
    }

    public Dimension getMainScrollPaneDimension() {
        return mainScrollPane.getSize();
    }

    public void setMainScrollPanePosition(Point point) {
        this.mainScrollPane.getViewport().setViewPosition(point);

    }

    public JScrollPane getMainScrollPane(){
        return this.mainScrollPane;
    }

    private ButtonGroup buttonGroup;

    private JPanel mainPanel;
    private JPanel topButtonBar;
    private JToggleButton selectButton;
    private JToggleButton rectangularSurfaceButton;
    private JToggleButton irregularSurfaceButton;
    private JButton zoomOutButton;
    private JButton zoomInButton;
    private JComboBox measurementUnitComboBox;
    private DrawingPanel drawingPanel;
    private JScrollPane mainScrollPane;
    private JSplitPane splitPane;
    private JLabel statusBar;

    private RightPanel rightPanel;

    private JMenuBar menuBar;

    private JMenu fileMenu;
    private JMenuItem newProjectItem;
    private JMenuItem openMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;

    private JMenu editionMenu;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenuItem cutMenuItem;
    private JMenuItem pasteMenuItem;
    private JMenuItem copyMenuItem;

    private JMenu viewMenu;
    private JMenuItem gridMenuItem;
    private JMenuItem measurementUnitMenuItem;

    private JMenu insertMenu;
    private JMenuItem surfaceMenuItem;
    private JMenuItem patternMenuItem;
    private JMenuItem tileMenuItem;
    private JMenuItem groutMenuItem;

    private JMenu windowMenu;
    private JMenuItem minimizeMenuItem;
    private JMenuItem zoomMenuItem;
}
