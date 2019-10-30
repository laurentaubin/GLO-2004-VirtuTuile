package gui;

import domain.room.RoomController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static gui.MainWindow.ApplicationMode.ADD_IRREGULAR;


public class MainWindow extends JFrame {

    public RoomController controller;

    private ApplicationMode actualMode;
    private MeasurementUnitMode actualMeasurementUnit;

    public Point actualMousePoint = new Point();
    public Point initMousePoint = new Point();



    public enum ApplicationMode {
        SELECT, ADD_RECTANGULAR, ADD_IRREGULAR
    }

    public enum MeasurementUnitMode {
        METRIC, IMPERIAL
    }


    public MainWindow(){
        controller = new RoomController();
        initComponents();
    }

    public void setMode (ApplicationMode newMode) {
        this.actualMode = newMode;
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

        this.setVisible(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VirtuTuile");

        mainPanel.setLayout(new BorderLayout());

        topButtonBar.setPreferredSize(new Dimension(400, 35));

        selectButton.setSelected(true);
        this.setMode(ApplicationMode.SELECT);
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

        /*zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawingPanel.zoomInActionPerformed();
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawingPanel.zoomOutActionPerformed();
            }
        });*/

        /*drawingPanel.addMouseWheelListener(new java.awt.event.MouseAdapter() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                int wheel = evt.getWheelRotation();
                if (wheel < 0) {
                    drawingPanel.zoomInActionPerformed(evt.getScrollAmount());
                } else {
                    drawingPanel.zoomOutActionPerformed(evt.getScrollAmount());
                }
            }
        });*/

        drawingPanel.addMouseWheelListener(new java.awt.event.MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent evt){
                drawingPanel.zoomActionPerformed(evt.getWheelRotation(), evt.getX(), evt.getY());

            }

        });




        measurementUnitComboBox.setModel(new DefaultComboBoxModel(new String[] { "MÉTRIQUE", "IMPÉRIALE" }));
        measurementUnitComboBox.setPreferredSize(new Dimension(120, 23));

        zoomOutButton.setPreferredSize(new Dimension(30, 23));
        zoomInButton.setPreferredSize(new Dimension(30, 23));

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

        //Change la grandeur du panel de gauche min
        mainScrollPane.setMinimumSize(new Dimension(0, 202));
        mainScrollPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));




        drawingPanel.setPreferredSize(new Dimension(0, 540));
        
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

        splitPane.setResizeWeight(0.8);

        mainPanel.add(splitPane, BorderLayout.CENTER);

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

    private void selectButtonActionPerformed(ActionEvent actionEvent){this.setMode(ApplicationMode.SELECT);}

    private void rectangularSurfaceButtonPerformed(ActionEvent actionEvent){this.setMode(ApplicationMode.ADD_RECTANGULAR);}

    private void irregularSurfaceButtonPerformed(ActionEvent actionEvent){this.setMode(ADD_IRREGULAR);}

    private void drawingPanelMousePressed(MouseEvent mouseEvent){
        Point mousePoint = mouseEvent.getPoint();
        this.actualMousePoint = mousePoint;
        boolean isShiftDown = false;

        if (this.actualMode == ApplicationMode.SELECT && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            if (mouseEvent.isShiftDown()){
                isShiftDown = true;
            }
            this.controller.switchSelectionStatus(mousePoint.getX(), mousePoint.getY(), isShiftDown);
            drawingPanel.repaint();
            System.out.println(mousePoint);
        }

        if (this.actualMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            this.initMousePoint = mousePoint;
        }

        if (this.actualMode == ApplicationMode.ADD_IRREGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            System.out.println(mousePoint);
        }

    }

    private void drawingPanelMouseReleased(MouseEvent mouseEvent){
        Point mousePointReleased = mouseEvent.getPoint();

        if (this.actualMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            int[] xPoints = new int[4];
            int[] yPoints = new int[4];

            xPoints[0] = (int)this.initMousePoint.x;
            xPoints[1] = (int)mousePointReleased.getX();
            xPoints[2] = (int)mousePointReleased.getX();
            xPoints[3] = (int)initMousePoint.getX();

            yPoints[0] = (int)initMousePoint.getY();
            yPoints[1] = (int)initMousePoint.getY();
            yPoints[2] = (int)mousePointReleased.getY();
            yPoints[3] = (int)mousePointReleased.getY();

            int n  = 4;

            controller.addSurface(xPoints, yPoints, n);
        }
        drawingPanel.repaint();
    }

    private void drawingPanelMouseDragged(MouseEvent mouseEvent){
        if (SwingUtilities.isRightMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!

            this.controller.updateSelectedSurfacesPositions(mouseEvent.getX() - this.actualMousePoint.getX(), mouseEvent.getY() - this.actualMousePoint.getY());
            this.actualMousePoint = mouseEvent.getPoint();
            drawingPanel.repaint();

        }

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
