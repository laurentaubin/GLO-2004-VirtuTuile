package gui;

import domain.room.RoomController;
import domain.room.TileType;
import util.UnitConverter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;




public class MainWindow extends JFrame {

    public RoomController controller;

    private ApplicationMode currentApplicationMode = ApplicationMode.SELECT;
    private MeasurementUnitMode currentMeasurementMode = MeasurementUnitMode.METRIC;

    public Point currentMousePoint = new Point();
    public Point initMousePoint = new Point();

    public void updateTile(TileType selectedTileType, float tileWidth, float tileHeight, String tileName, int nbrTilesPerBox, Color tileColor) {
        controller.updateTile(selectedTileType, tileWidth, tileHeight, tileName, nbrTilesPerBox, tileColor);
        drawingPanel.repaint();
    }

    public void updateTileWidth(TileType selectedTileType, float tileWidth) {
        controller.updateTileWidth(selectedTileType, tileWidth);
        drawingPanel.repaint();
    }

    public void updateTileHeight(TileType tileType, float height) {
        controller.updateTileHeight(tileType, height);
        drawingPanel.repaint();
    }

    public void updateTileColor(TileType tileType, Color color) {
        controller.updateTileColor(tileType, color);
        drawingPanel.repaint();
    }

    public void updateTileName(TileType tileType, String name) {
        controller.updateTileName(tileType, name);
    }

    public void updateNumberPerBox(TileType tileType, int numberPerBox) {
        controller.updateNumberPerBox(tileType, numberPerBox);
    }


    public enum ApplicationMode {
        SELECT, ADD_RECTANGULAR, ADD_IRREGULAR, MOVE_PATTERN
    }

    public enum MeasurementUnitMode {
        METRIC, IMPERIAL
    }

    private boolean surfaceReadyToMove;
    private boolean redoApplied;
    private boolean mouseWasDragged;
    private int numberOfSelectedSurfaces;


    public MainWindow() throws IOException {

        controller = new RoomController();
        initComponents();
        setFocusable(true);
    }

    public void setApplicationMode (ApplicationMode newMode) {
        this.currentApplicationMode = newMode;
    }
    public ApplicationMode getApplicationMode() {
        return this.currentApplicationMode;
    }

    public void setMeasurementMode (MeasurementUnitMode newMode) { this.currentMeasurementMode = newMode; }
    public MeasurementUnitMode getCurrentMeasurementMode() { return this.currentMeasurementMode; }

    public DrawingPanel getDrawingPanel(){
        return this.drawingPanel;
    }


    private void initComponents() throws IOException {

        mainPanel = new JPanel();
        buttonGroup = new ButtonGroup();
        topButtonBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topButtonBarTest = new TopButtonBar(this);
        selectButton = new JToggleButton("Sélection");
        rectangularSurfaceButton = new JToggleButton("Ajouter une surface rectangulaire");
        irregularSurfaceButton = new JToggleButton("Ajouter surface irrégulière");

        measurementUnitComboBox = new javax.swing.JComboBox();
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
        gridOptionItem = new JMenuItem();
        measurementUnitMenuItem = new JMenuItem();

        insertMenu = new JMenu();
        surfaceMenuItem = new JMenuItem();
        patternMenuItem = new JMenuItem();
        tileMenuItem = new JMenuItem();
        groutMenuItem = new JMenuItem();

        windowMenu = new JMenu();
        minimizeMenuItem = new JMenuItem();
        zoomMenuItem = new JMenuItem();
        cancelZoomMenuItem = new JMenuItem();

        topButtonScrollPane = new JScrollPane();


        statusBar = new JLabel(" ");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VirtuTuile");

        mainPanel.setLayout(new BorderLayout());

        topButtonBar.setPreferredSize(new Dimension(400, 35));

        selectButton.setSelected(true);

        this.setApplicationMode(ApplicationMode.SELECT);

        rightPanel.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent actionEvent) {
                rightPanelTabChanged(actionEvent);
            }
        });

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

        topButtonScrollPane.setViewportView(topButtonBarTest);
        mainPanel.add(topButtonScrollPane, BorderLayout.NORTH);

        splitPane.setMinimumSize(new Dimension(0, 202));
        splitPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));

        mainScrollPane.setMinimumSize(new Dimension(0, 202));
        mainScrollPane.setPreferredSize(new Dimension((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.85), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.5)));
        drawingPanel = new DrawingPanel(this);

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

        mainScrollPane.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent evt) {
                mouseWheelMovedEventPerformed(evt);
            }
        });


        /*
        drawingPanel.addMouseWheelListener(new java.awt.event.MouseAdapter() {
            public void mouseWheelMoved(MouseWheelEvent evt){
                mouseWheelMovedEventPerformed(evt);
            }
        });
        */


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

        splitPane.setResizeWeight(0.83);

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
        viewMenu.add(gridMenuItem);
        gridMenuItem.setText("Afficher/cacher la grille");
        gridMenuItem.setSelected(drawingPanel.getGridlines());

        gridMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridMenuItemActionPerformed(actionEvent);
            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openMenuItemActionPerform();
            }
        });
        saveAsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveAsMenuItemActionPerformed();
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveMenuItemActionPerformed();
            }
        });
        newProjectItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                newProjectItemActionPerformed();
            }
        });

        gridOptionItem.setText("Modifier la grille");
        viewMenu.add(gridOptionItem);
        gridOptionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gridOptionItemActionPerformed(actionEvent);
            }
        });

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
        cancelZoomMenuItem.setText("Cancel Zoom");
        cancelZoomMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancelZoomActionPerformed();
            }
        });
        windowMenu.add(cancelZoomMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editionMenu);
        menuBar.add(viewMenu);
        menuBar.add(insertMenu);
        menuBar.add(windowMenu);

        setJMenuBar(menuBar);

        pack();
    }

    private void rightPanelTabChanged(ChangeEvent actionEvent) {
        if (this.controller.getNumberOfSelectedSurfaces() == 1) {
            rightPanel.updateSurfaceInformation(this.controller.getSelectedSurfaceNbTile(), this.controller.getSelectedSurfaceNbBox());
        }
        else {
            rightPanel.updateSurfaceInformation(this.controller.getAllSurfaceNbTile(), this.controller.getAllSurfaceNbBox());
        }
    }

    public void selectButtonActionPerformed(ActionEvent actionEvent){
        this.setApplicationMode(ApplicationMode.SELECT);
    }

    public void rectangularSurfaceButtonPerformed(ActionEvent actionEvent){
        this.setApplicationMode(ApplicationMode.ADD_RECTANGULAR);
        this.controller.unselectAllSurfaces();
    }

    public void irregularSurfaceButtonPerformed(ActionEvent actionEvent){
        this.setApplicationMode(ApplicationMode.ADD_IRREGULAR);
        this.controller.unselectAllSurfaces();

    }

    public void movePatternButtonActionPerformed(ActionEvent actionEvent) {
        this.setApplicationMode(ApplicationMode.MOVE_PATTERN);
        this.controller.unselectAllSurfaces();
    }

    public void metricModeSelected(ActionEvent actionEvent) {
        this.setMeasurementMode(MeasurementUnitMode.METRIC);
        // this.controller.setMeasurementMode(MeasurementUnitMode.METRIC);
        this.drawingPanel.repaint();
    }

    public void imperialModeSelected(ActionEvent actionEvent) {
        this.setMeasurementMode(MeasurementUnitMode.IMPERIAL);
        // this.controller.setMeasurementMode(MeasurementUnitMode.IMPERIAL);
        this.drawingPanel.repaint();
    }



    public void drawingPanelKeyPressed(java.awt.event.KeyEvent evt) {
        if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE && controller.surfaceSelecte()){
            String[] options = {"Ok", "Cancel"};
            int indexReponse = JOptionPane.showOptionDialog(null, "Voulez-vous vraiment supprimer cette surface?",
                    "Attention!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if(indexReponse == 0){
                controller.deleteSurface();
            }
        }
        drawingPanel.repaint();
    }

    public void drawingPanelMousePressed(MouseEvent mouseEvent){
        this.initMousePoint = new Point(
                (int)(mouseEvent.getPoint().getX() / drawingPanel.getZoom()),
                (int)(mouseEvent.getPoint().getY() / drawingPanel.getZoom()));
        this.currentMousePoint = new Point(this.initMousePoint);

        this.requestFocus();
        boolean isShiftDown = false;
        this.mouseWasDragged = false;


        if (this.currentApplicationMode == ApplicationMode.SELECT && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            double xPos = this.initMousePoint.getX();
            double yPos = this.initMousePoint.getY();


            //double xPos = UnitConverter.convertPixelToSelectedUnit((int) this.initMousePoint.getX(), this.currentMeasurementMode);
            //double yPos = UnitConverter.convertPixelToSelectedUnit((int) this.initMousePoint.getY(), this.currentMeasurementMode);

            this.controller.switchSelectionStatus(xPos, yPos, mouseEvent.isShiftDown());

            if (controller.numberOfSelectedSurfaces() > 0) {
                this.surfaceReadyToMove = true;
            }

            rightPanel.updateSurfaceTabDimensions(this.controller.getSelectedSurfaceDimensions());
            rightPanel.updateSurfaceInformation(this.controller.getSelectedSurfaceNbTile(), this.controller.getSelectedSurfaceNbBox());
            rightPanel.showSurfaceInformation();
            rightPanel.updateSurfaceTabColor(this.controller.getSelectedSurfaceColor());
            rightPanel.updateIfSelectedSurfaceIsAHole(this.controller.getIfSelectedSurfaceIsAHole(), this.controller.getNumberOfSelectedSurfaces());
            rightPanel.updatePatternTab(this.controller.getSelectedSurfaceGroutWidth(), this.controller.getNumberOfSelectedSurfaces());

            rightPanel.updateTileTab(   controller.getCurrentTileWidth(),
                                        controller.getCurrentTileHeight(),
                                        controller.getCurrentNameTile(),
                                        controller.getSelectedSurfaceColor(),
                                        controller.getCurrentTilePerBox());
            changeInformationPanelState();
            rightPanel.updateSurfaceTabDistances(this.controller.getSelectedSurfacesDistances());
        }

        if (this.currentApplicationMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
        }

        if (this.currentApplicationMode == ApplicationMode.ADD_IRREGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            //TODO Ajouter la conversion des unités de mesure ici!
            Point point = new Point((int)this.initMousePoint.getX(),(int) this.initMousePoint.getY());
            controller.addPoint(point, drawingPanel.getZoom());
            drawingPanel.repaint();
        }

        if (this.controller.getNumberOfSelectedSurfaces() != 1) {
            rightPanel.updateSurfaceInformation(this.controller.getAllSurfaceNbTile(), this.controller.getAllSurfaceNbBox());
            rightPanel.showProjectInformation();;
        }

        drawingPanel.repaint();
    }

    private void changeInformationPanelState() {
        if (this.controller.getNumberOfSelectedSurfaces() == 1) {
            rightPanel.showSurfaceInformation();
        }
        else {
            rightPanel.showProjectInformation();
        }
    }

    public void drawingPanelMouseReleased(MouseEvent mouseEvent){
        Point mousePointReleased = new Point(
                (int)(mouseEvent.getX() / drawingPanel.getZoom()),
                (int)(mouseEvent.getY() / drawingPanel.getZoom())
        );

        if (this.currentApplicationMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent) && mouseWasDragged) {
            //TODO Ajouter la conversion des unités de mesure ici!

            Point position = this.initMousePoint.getLocation();
            position.setLocation(position.getX(), position.getY());
            //Point position = UnitConverter.convertPointToSelectedUnit(this.initMousePoint.getLocation(), this.currentMeasurementMode);
            double[] xDrawPoints = getXDrawPoints();
            double[] yDrawPoints = getYDrawPoints();

            if (xDrawPoints[0] - yDrawPoints[1] > 0){
                position = mousePointReleased.getLocation();
                //position = UnitConverter.convertPointToSelectedUnit(mousePointReleased.getLocation(), this.currentMeasurementMode);
            }
            controller.clearSurfaceProjectionList();
            if (drawingPanel.getGridlines()) {
                controller.addSurfaceOnGrid(position, xDrawPoints, yDrawPoints, 4, drawingPanel.getGridGap());
            }
            else {
                controller.addSurface(position, xDrawPoints, yDrawPoints, 4);
            }
            this.mouseWasDragged = false;
        }

        else if (this.currentApplicationMode == ApplicationMode.ADD_IRREGULAR) {
            SwingUtilities.isLeftMouseButton((mouseEvent));
        }
            //TODO Code pour surface irrégulière

        else if (this.currentApplicationMode == ApplicationMode.SELECT && mouseWasDragged) {
            if (drawingPanel.getGridlines()) {
                controller.snapSelectedSurfaceToGrid(drawingPanel.getGridGap());
            }
            if (controller.numberOfSelectedSurfaces() > 0) {
                controller.addRoom();
                this.surfaceReadyToMove = true;
            }
        }
        this.mouseWasDragged = false;
        drawingPanel.repaint();
    }

    public void drawingPanelMouseDragged(MouseEvent mouseEvent){
        // TODO ça marche pas pcq le init mouse point est pas updaté a bonne palce faique le delta est pas bon
        if (SwingUtilities.isRightMouseButton(mouseEvent) && !mouseEvent.isShiftDown()) {
            double deltaX = (
                    (int)(mouseEvent.getX() / drawingPanel.getZoom()) - (int)(this.currentMousePoint.getX()));
            double deltaY = (
                    (int)(mouseEvent.getY() / drawingPanel.getZoom()) - (int)(this.currentMousePoint.getY()));
            /*
            double pixelX = (int) (mouseEvent.getX() - this.currentMousePoint.getX());
            double pixelY = (int) (mouseEvent.getY() - this.currentMousePoint.getY());
            double deltaX = UnitConverter.convertPixelToSelectedUnit(pixelX, this.currentMeasurementMode);
            double deltaY = UnitConverter.convertPixelToSelectedUnit(pixelY, this.currentMeasurementMode);

             */
            if (controller.numberOfSelectedSurfaces() > 0 && surfaceReadyToMove) {
                controller.deletePreviousStatesIfRequired();
                this.surfaceReadyToMove = false;
                if(redoApplied) {
                    controller.replaceCurrentState();
                    this.redoApplied = false;
                }
            }

            this.controller.updateSelectedSurfacesPositions(deltaX, deltaY);
            //this.controller.updateSelectedSurfacesPositions(deltaX, deltaY, pixelX, pixelY);
            //this.currentMousePoint.setLocation(mouseEvent.getX() / drawingPanel.getZoom(), mouseEvent.getY() / drawingPanel.getZoom());
            this.currentMousePoint = new Point(
                    (int)(mouseEvent.getX() / drawingPanel.getZoom()),
                    (int)(mouseEvent.getY() / drawingPanel.getZoom())
                    );
        }

        else if (SwingUtilities.isRightMouseButton(mouseEvent) && mouseEvent.isShiftDown()) {
            double deltaX = (
                    (int)(mouseEvent.getX() / drawingPanel.getZoom()) - (int)(this.currentMousePoint.getX()));
            double deltaY = (
                    (int)(mouseEvent.getY() / drawingPanel.getZoom()) - (int)(this.currentMousePoint.getY()));

            this.controller.updateSelectedSurfacesPatternPosition(deltaX, deltaY);
            this.currentMousePoint = new Point(
                    (int)(mouseEvent.getX() / drawingPanel.getZoom()),
                    (int)(mouseEvent.getY() / drawingPanel.getZoom())
            );
        }

        else if (this.currentApplicationMode == ApplicationMode.ADD_RECTANGULAR && SwingUtilities.isLeftMouseButton(mouseEvent)) {
            this.currentMousePoint = new Point(
                    (int)(mouseEvent.getX() / drawingPanel.getZoom()),
                    (int)(mouseEvent.getY() / drawingPanel.getZoom())
            );


            double[] xDrawPoints = getXDrawPoints();
            double[] yDrawPoints = getYDrawPoints();

            /*
            if (currentMousePoint.getX() >= drawingPanel.getWidth() - 20) {
                drawingPanel.setWidth();
                Point translateScrollPane  = new Point (
                        (int)mainScrollPane.getViewport().getViewPosition().getX() + 20,
                        (int) mainScrollPane.getViewport().getViewPosition().getY());
                setMainScrollPanePosition(translateScrollPane);
            }

            if (currentMousePoint.getY() >= drawingPanel.getHeight() - 20) {
                drawingPanel.setHeight();
                Point translateScrollPane  = new Point (
                        (int)mainScrollPane.getViewport().getViewPosition().getX(),
                        (int) mainScrollPane.getViewport().getViewPosition().getY() + 20);
                setMainScrollPanePosition(translateScrollPane);
            }
            
             */

            controller.addRectangularProjection(this.initMousePoint, xDrawPoints, yDrawPoints);
        }
        this.mouseWasDragged = true;
        drawingPanel.repaint();
    }

    public double[] getXDrawPoints() {
        double[] drawPoints = new double[4];
        //int[] drawPoints = new int[4];

        drawPoints[0] = initMousePoint.getX();
        drawPoints[1] = currentMousePoint.getX();
        drawPoints[2] = currentMousePoint.getX();
        drawPoints[3] = initMousePoint.getX();
/*
        drawPoints[0] = (int)initMousePoint.getX();
        drawPoints[1] = (int)currentMousePoint.getX();
        drawPoints[2] = (int)currentMousePoint.getX();
        drawPoints[3] = (int)initMousePoint.getX();

 */

        //return UnitConverter.convertPixelListToSelectedUnit(drawPoints, this.currentMeasurementMode);
        return drawPoints;
    }

    public double[] getYDrawPoints() {
        double[] drawPoints = new double[4];

        drawPoints[0] = initMousePoint.getY();
        drawPoints[1] = initMousePoint.getY();
        drawPoints[2] = currentMousePoint.getY();
        drawPoints[3] = currentMousePoint.getY();
        /*
        int[] drawPoints = new int[4];

        drawPoints[0] = (int)initMousePoint.getY();
        drawPoints[1] = (int)initMousePoint.getY();
        drawPoints[2] = (int)currentMousePoint.getY();
        drawPoints[3] = (int)currentMousePoint.getY();

         */

        //return UnitConverter.convertPixelListToSelectedUnit(drawPoints, this.currentMeasurementMode);
        return drawPoints;
    }

    public void openMenuItemActionPerform(){
        this.controller.openMenuSelected();
        controller.getSelectedSurfaceGroutWidth();
        drawingPanel.repaint();

    }

    public void saveMenuItemActionPerformed(){
        this.controller.saveSelected();
    }

    public void desactivateInspectorMode(){
        this.controller.desactivateInspectorMode();
        drawingPanel.repaint();
    }
    public void activateInspectorMode(){
        this.controller.activateInspectorMode();
        drawingPanel.repaint();
    }


    public void saveAsMenuItemActionPerformed(){
        this.controller.saveAsSelected();
    }

    public void newProjectItemActionPerformed(){
        this.controller.newProjectItemActionPerformed();
        drawingPanel.repaint();
    }


    public void gridMenuItemActionPerformed(ActionEvent actionEvent) {
        drawingPanel.setGridLines();
    }


    public void gridOptionItemActionPerformed(ActionEvent actionEvent) {

        JPanel fields = new JPanel(new GridLayout(1, 2));
        JTextField field = new JTextField(15);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"mètres", "pouces"});

        fields.add(field);
        fields.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, fields, "Entrer les dimensions d'un carré de la grille", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            double newGridGap;
            try {
                newGridGap = Double.parseDouble(field.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "La valeur entrée n'est pas un nombre. Veuillez recommencer.");
                return;
            }

            if (newGridGap <= 0) {
                JOptionPane.showMessageDialog(null, "La valeur entrée est plus petite ou égale à zéro. Veuillez recommencer.");
                throw new ArithmeticException("Negative grid gap.");
            }
            switch(comboBox.getSelectedIndex()) {
                case 0:
                    // TODO ajouter la conversion d'unité Mètre -> Pixel
                    this.drawingPanel.setGridGap(newGridGap);
                    break;
                case 1:
                    // TODO ajouter la conversion d'unité Pouce -> Pixel
                    this.drawingPanel.setGridGap(newGridGap);
                    break;
            }
            this.drawingPanel.repaint();
        }
        // String inputDimensions = JOptionPane.showInputDialog("Entrer les dimensions de la grille");
        // String inputDimensions = (String) JOptionPane.showInputDialog(null, "Entrer les dimensions de la grille", "Configuration de la grille", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public void drawingPanelMouseMoved(MouseEvent evt) {
        //TODO convertir les unités
        int x = (int) (evt.getX());
        int y = (int) (evt.getY());

        String mousePosition = " ";


        double xPos = evt.getX() / drawingPanel.getZoom();
        double yPos = evt.getY() / drawingPanel.getZoom();
        //double xPos = UnitConverter.convertPixelToSelectedUnit( evt.getX(), this.currentMeasurementMode);
        //double yPos = UnitConverter.convertPixelToSelectedUnit(evt.getY(), this.currentMeasurementMode);

        /*
        if (this.currentMeasurementMode == MeasurementUnitMode.METRIC) {
            mousePosition += ("x= " + (double)(Math.round(UnitConverter.pixelToMeter(xPos) * 100d) / 100d) + "m " + ", y= " + Math.round(UnitConverter.pixelToMeter(yPos) * 100d) / 100d + "m ");
        }

         */

        mousePosition += ("x= " + xPos + ", y= " + yPos);


        if (this.controller.checkIfMouseAboveTile(evt.getX(), evt.getY())) {
            ArrayList<Double> tileDimension = this.controller.getTileDimensions(evt.getX(), evt.getY());
            mousePosition += " | Largeur de la tuile = " + tileDimension.get(0) + ", hauteur de la tuile = " + tileDimension.get(1) ;
            setStatusBarText(mousePosition);
        }

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


    public void draw(Graphics2D g, DrawingPanel drawingPanel, double zoom) {
        controller.draw(g, getCurrentMeasurementMode(), drawingPanel, zoom, currentMousePoint);
    }

    public void setSelectedSurfaceWidth(double enteredWidth) {
        controller.setSelectedSurfaceWidth(enteredWidth);
        drawingPanel.repaint();
    }

    public void setSelectedSurfaceHeight(double height) {
        controller.setSelectedSurfaceHeight(height);
        drawingPanel.repaint();
    }

    public void setSelectedSurfacesWidthDistance(double enteredWidth) {
        controller.setSelectedSurfacesWidthDistance(enteredWidth);
        drawingPanel.repaint();
    }

    public void setSelectedSurfacesHeightDistance(double enteredHeight) {
        controller.setSelectedSurfacesHeightDistance(enteredHeight);
        drawingPanel.repaint();
    }

    public void combineSelectedSurfaces() {
        if(controller.getNumberOfSurfaces() < 2){
            String[] options = {"Ok"};
            int indexReponse = JOptionPane.showOptionDialog(null, "Vous devez sélectionner un minimum de deux surfaces à combiner",
                    "Attention!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
        else if(controller.doesSelectedSurfacesIntersect()) {
            controller.combineSelectedSurfaces();
        }
        else {
            String[] options = {"Ok"};
            int indexReponse = JOptionPane.showOptionDialog(null, "Les surfaces à combiner doivent être en contact!",
                    "Attention!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void createTileFromUserInput(Color color, float width, float height, String name, int nbrTilesPerBox) {
        controller.createTileFromUserInput(color, width, height, name, nbrTilesPerBox);
    }

    public ArrayList<TileType> getTileList() {
        return controller.getTileList();
    }

    public void setSelectedTileToSelectedSurface(TileType selectedTileType) {
        controller.setSelectedTileToSelectedSurface(selectedTileType);
        drawingPanel.repaint();
    }

    public void setStraightPatternToSelectedSurface() {
        controller.setStraightPatternToSelectedSurface();
        drawingPanel.repaint();
    }

    public void setHorizontalPatternToSelectedSurface() {
        controller.setHorizontalPatternToSelectedSurface();
        drawingPanel.repaint();
    }

    public void setVerticalPatternToSelectedSurface() {
        controller.setVerticalPatternToSelectedSurface();
        drawingPanel.repaint();
    }

    public void setVerticalBrickPatternToSelectedSurface() {
        controller.setVerticalBrickPatternToSelectedSurface();
        drawingPanel.repaint();
    }

    public void setAnglePattern() {
        controller.setAnglePattern();
        drawingPanel.repaint();
    }

    public void setSquarePattern() {
        controller.setSquarePatternToSelectedSurface();
        drawingPanel.repaint();
    }

    public void setChevronPattern() {
        controller.setChevronPattern();
        drawingPanel.repaint();
    }

    //Fait Juste repaint la couleur de la surface de la couleur choisit pour grout
    public void setGroutColor(Color color){
        controller.setGroutColor(color);
        drawingPanel.repaint();
    }

    public void setSelectedSurfaceColor(Color color) {
        controller.setSelectedSurfaceColor(color);
        drawingPanel.repaint();
    }

    public void setSelectedSurfaceAsHole() {
        controller.setSelectedSurfaceAsHole();
    }

    public void cancelZoomActionPerformed() {
        drawingPanel.setZoom(1d);
        drawingPanel.setDrawingPanelInitialDimension();
        drawingPanel.repaint();
    }

    public void mouseWheelMovedEventPerformed(MouseWheelEvent evt) {
        Point point = evt.getPoint();
        this.currentMousePoint = evt.getPoint();
        if (evt.getPreciseWheelRotation() > 0) {
            drawingPanel.zoomInActionPerformed(point);
        }
        else {
            // drawingPanel.zoomOut(point);
            drawingPanel.zoomOutActionPerformed(point);
        }
    }

    public void setEnteredGroutWidtht(double width) {
        this.controller.setSelectedSurfaceGroutWidth(width);
        drawingPanel.repaint();
    }

    public void setSelectedSurfaceAsWhole() {
        this.controller.setSelectedSurfaceAsWhole();
    }

    public void separateSelectedSurface() {
        this.controller.separateSelectedSurface();
        drawingPanel.repaint();
    }

    public void horizontallyAlignSelectedSurfaces() {
        controller.horizontallyAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void verticallyAlignSelectedSurfaces() {
        controller.verticallyAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void horizontallyCenterSelectedSurfaces() {
        controller.horizontallyCenterSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void verticallyCenterSelectedSurfaces() {
        controller.verticallyCenterSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void leftAlignSelectedSurfaces() {
        controller.leftAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void rightAlignSelectedSurfaces() {
        controller.rightAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void topAlignSelectedSurfaces() {
        controller.topAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void bottomAlignSelectedSurfaces() {
        controller.bottomAlignSelectedSurfaces();
        rightPanel.updateSurfaceTabDistances(controller.getSelectedSurfacesDistances());
        drawingPanel.repaint();
    }

    public void undo() {
        this.controller.undo();
        drawingPanel.repaint();
    }

    public void redo() {
        this.controller.redo();
        this.redoApplied = true;
        drawingPanel.repaint();
    }

    public void setMismatch(double mismatch) {
        controller.setMismatch(mismatch);
        drawingPanel.repaint();
    }

    public void centerTiles(){
        controller.centerTiles();
        drawingPanel.repaint();
    }

    private ButtonGroup buttonGroup;

    private JPanel mainPanel;

    private JPanel topButtonBar;
    private TopButtonBar topButtonBarTest;

    private JToggleButton selectButton;
    private JToggleButton rectangularSurfaceButton;
    private JToggleButton irregularSurfaceButton;
    private JComboBox measurementUnitComboBox;
    private DrawingPanel drawingPanel;
    private JScrollPane mainScrollPane;
    private JScrollPane topButtonScrollPane;
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
    private JMenuItem gridOptionItem;
    private JMenuItem measurementUnitMenuItem;

    private JMenu insertMenu;
    private JMenuItem surfaceMenuItem;
    private JMenuItem patternMenuItem;
    private JMenuItem tileMenuItem;
    private JMenuItem groutMenuItem;

    private JMenu windowMenu;
    private JMenuItem minimizeMenuItem;
    private JMenuItem zoomMenuItem;
    private JMenuItem cancelZoomMenuItem;
}
