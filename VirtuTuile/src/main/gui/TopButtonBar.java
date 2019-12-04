package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TopButtonBar extends JPanel{
    private MainWindow mainWindow;
    private JPanel buttonBarMainPanel;
    private ButtonGroup buttonGroup;
    private JToggleButton selectButton;
    private JToggleButton createRecSurfaceButton;
    private JToggleButton createIrrSurfaceButton;
    private JPanel buttonsPanel;
    private JComboBox measurementUnitComboBox;
    private JLabel createRecLabel;
    private JButton undoButton;
    private JButton redoButton;
    private JToggleButton movePatternButton;
    private ImageIcon selectIcon;
    private Color initColor;
    private Border oldBorder;


    public TopButtonBar(MainWindow mainWindow) throws IOException {
        this.mainWindow = mainWindow;
        this.add(buttonBarMainPanel);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonGroup = new ButtonGroup();
        initColor = selectButton.getBackground();
        oldBorder = selectButton.getBorder();

        measurementUnitComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "MÉTRIQUE", "IMPÉRIALE" }));

        selectButton.setSelected(true);
        selectButton.setFocusable(true);

        //createRecLabel.setText("<html>Surface<br/>retangulaire</html>");
        //createRecLabel.setHorizontalAlignment(JLabel.CENTER);



        buttonGroup.add(selectButton);
        buttonGroup.add(createIrrSurfaceButton);
        buttonGroup.add(createRecSurfaceButton);
        buttonGroup.add(movePatternButton);

        Dimension mainButtonDimension = new Dimension(50,50);
        this.selectButton.setPreferredSize(mainButtonDimension);
        this.createRecSurfaceButton.setPreferredSize(mainButtonDimension);
        this.createIrrSurfaceButton.setPreferredSize(mainButtonDimension);
        this.movePatternButton.setPreferredSize(mainButtonDimension);

        Dimension smallButtonDimension = new Dimension(25, 25);

        this.undoButton.setPreferredSize(smallButtonDimension);
        this.redoButton.setPreferredSize(smallButtonDimension);

        // Hand Up icon by Icons8
        BufferedImage selectImage = ImageIO.read(this.getClass().getResourceAsStream("/image/mouseClicked.png"));
        Icon selectIcon = new ImageIcon(selectImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        this.selectButton.setIcon(selectIcon);

        // Rectangle icon by Icons8
        BufferedImage createRecImage = ImageIO.read(this.getClass().getResourceAsStream("/image/addRec.png"));
        Icon createRecIcon = new ImageIcon(createRecImage.getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.createRecSurfaceButton.setIcon(createRecIcon);

        BufferedImage createIrrImage = ImageIO.read(this.getClass().getResourceAsStream("/image/addIrr.png"));
        Icon addIrrIcon = new ImageIcon(createIrrImage.getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        this.createIrrSurfaceButton.setIcon(addIrrIcon);

        BufferedImage undoImage = ImageIO.read(this.getClass().getResourceAsStream("/image/undo.png"));
        Icon undoIcon = new ImageIcon(undoImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.undoButton.setIcon(undoIcon);

        BufferedImage redoImage = ImageIO.read(this.getClass().getResourceAsStream("/image/redo.png"));
        Icon redoIcon = new ImageIcon(redoImage.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        this.redoButton.setIcon(redoIcon);

        //Move Grabber icon icon by Icons8
        BufferedImage movePatternImage = ImageIO.read(this.getClass().getResourceAsStream("/image/movePattern.png"));
        Icon movePatternIcon = new ImageIcon(movePatternImage.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        this.movePatternButton.setIcon(movePatternIcon);





        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.selectButtonActionPerformed(actionEvent);
            }
        });

        createRecSurfaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.rectangularSurfaceButtonPerformed(actionEvent);
            }
        });

        createIrrSurfaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.irregularSurfaceButtonPerformed(actionEvent);
            }
        });

        measurementUnitComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int mode = measurementUnitComboBox.getSelectedIndex();

                switch(mode) {
                    case 0:
                        mainWindow.metricModeSelected(actionEvent);
                        break;
                    case 1:
                        mainWindow.imperialModeSelected(actionEvent);
                        break;
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mainWindow.redo();
            }
        });
    }
}
