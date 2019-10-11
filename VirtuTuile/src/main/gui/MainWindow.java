package gui;

import domain.room.RoomController;

import javax.swing.*;
import java.awt.*;


public class MainWindow extends JFrame {

    public RoomController controller;


    public MainWindow(){
        controller = new RoomController();
        initComponents();
    }

    private void initComponents() {
        mainPanel = new JPanel();

        topMenuButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rectangularSurface = new JButton("Ajouter une surface rectangulaire");
        irregularSurface = new JButton("Ajouter surface irrégulière");

        drawingPanel = new DrawingPanel(this);
        mainScrollPane = new JScrollPane();
        jSplitPane1 = new JSplitPane();
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

        topMenuButton.setPreferredSize(new Dimension(400, 35));
        topMenuButton.add(rectangularSurface);
        topMenuButton.add(irregularSurface);

        mainPanel.add(topMenuButton, BorderLayout.NORTH);

        jSplitPane1.setMinimumSize(new Dimension(0, 202));
        jSplitPane1.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));

        mainScrollPane.setMinimumSize(new Dimension(0, 0));
        mainScrollPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));

        drawingPanel.setPreferredSize(new Dimension(0, 540));
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

        jSplitPane1.setLeftComponent(mainScrollPane);

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


        jSplitPane1.setRightComponent(permanentRightPanel);

        jSplitPane1.setResizeWeight(0.8);

        mainPanel.add(jSplitPane1, BorderLayout.CENTER);

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

    private JPanel mainPanel;
    private JPanel topMenuButton;
    private JButton rectangularSurface;
    private JButton irregularSurface;
    private DrawingPanel drawingPanel;
    private JScrollPane mainScrollPane;
    private JSplitPane jSplitPane1;
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
