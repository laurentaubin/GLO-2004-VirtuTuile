package gui;

import javax.swing.*;

public class MainPanelTest  extends JFrame{
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JTabbedPane rightPanel;
    private JScrollPane mainScrollPane;
    private JPanel surfaceTab;
    private JPanel patternTab;
    private JPanel tileTab;
    private JPanel topButtonBar;
    private JPanel statusBar;
    private JPanel drawingPanel;
    private JSplitPane splitPane;
    private JButton selectionButton;
    private JButton createRecSurfaceButton;
    private JLabel mouseInformations;
    private JButton createIrrSurfaceButton;
    private SurfaceTab surfaceTabbedPane;
    private PatternTab patternTab1;

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

    public MainPanelTest() {
        initComponent();
        initListener();
    }

    public void initComponent() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("VirtuTuile");
        this.setContentPane(mainPanel);

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
    }

    public void initListener() {

    }
}
