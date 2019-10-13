package gui;

import domain.room.RoomController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;


public class MainWindow extends JFrame {

    public RoomController controller;

    private ApplicationMode actualMode;
    private MeasurementUnitMode actualMeasurementUnit;

    public Point actualMousePoint = new Point();
    public Point delta = new Point();


    public enum ApplicationMode {
        SELECT, ADD
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

        topButtonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectButton = new JToggleButton("Sélection");
        rectangularSurfaceButton = new JButton("Ajouter une surface rectangulaire");
        irregularSurfaceButton = new JButton("Ajouter surface irrégulière");
        zoomInButton = new JButton("+");
        zoomOutButton = new JButton("-");

        measurementUnitComboBox = new javax.swing.JComboBox();

        drawingPanel = new DrawingPanel(this);
        mainScrollPane = new JScrollPane();
        splitPane = new JSplitPane();

        permanentRightPanel = new JTabbedPane();
        surfaceTabPanel = new JPanel();
        patternTabPanel = new JPanel();
        tileTabPanel = new JPanel();
        groutTabPanel = new JPanel();

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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VirtuTuile");

        mainPanel.setLayout(new BorderLayout());

        topButtonBar.setPreferredSize(new Dimension(400, 35));

        selectButton.setSelected(false);
        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectButtonActionPerformed(actionEvent);
            }
        });

        zoomInButton.addActionListener(new ActionListener() {
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
        });

        measurementUnitComboBox.setModel(new DefaultComboBoxModel(new String[] { "MÉTRIQUE", "IMPÉRIALE" }));
        measurementUnitComboBox.setPreferredSize(new Dimension(120, 23));

        zoomOutButton.setPreferredSize(new Dimension(30, 23));
        zoomInButton.setPreferredSize(new Dimension(30, 23));

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


        //le layout à l'intérieur du drawing panel est en GroupLayout
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

        permanentRightPanel.setPreferredSize(new Dimension(0, 0));

        surfaceTabPanel.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.15), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.75)));

        GroupLayout jPanel1Layout = new GroupLayout(surfaceTabPanel);
        surfaceTabPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 975, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 512, Short.MAX_VALUE)
        );

        permanentRightPanel.addTab("Surface", null, surfaceTabPanel, "");
        permanentRightPanel.addTab("Motif", null, patternTabPanel, "");
        permanentRightPanel.addTab("Tuile", null, tileTabPanel, "" );
        permanentRightPanel.addTab("Coulis", null, groutTabPanel, "");


        splitPane.setRightComponent(permanentRightPanel);

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

    private void selectButtonActionPerformed(ActionEvent actionEvent){
        this.setMode(ApplicationMode.SELECT);
    }

    private void drawingPanelMousePressed(MouseEvent mouseEvent){
        Point mousePoint = mouseEvent.getPoint();
        this.actualMousePoint = mousePoint;

        if (this.actualMode == ApplicationMode.SELECT && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter a conversion des unités de mesure ici!

            System.out.println(mousePoint);

        }

    }

    private JPanel mainPanel;
    private JPanel topButtonBar;
    private JToggleButton selectButton;
    private JButton rectangularSurfaceButton;
    private JButton irregularSurfaceButton;
    private JButton zoomOutButton;
    private JButton zoomInButton;
    private JComboBox measurementUnitComboBox;
    private DrawingPanel drawingPanel;
    private JScrollPane mainScrollPane;
    private JSplitPane splitPane;

    private JTabbedPane permanentRightPanel;
    private JPanel surfaceTabPanel;
    private JPanel patternTabPanel;
    private JPanel tileTabPanel;
    private JPanel groutTabPanel;

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
