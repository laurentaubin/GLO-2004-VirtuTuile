package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private ImageIcon selectIcon;
    private Color initColor;
    private Border oldBorder;


    public TopButtonBar(MainWindow mainWindow) {
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

        this.selectButton.setPreferredSize(new Dimension(50, 50));
        this.createRecSurfaceButton.setPreferredSize(new Dimension(50, 50));
        this.createIrrSurfaceButton.setPreferredSize(new Dimension(50, 50));

        // Hand Up icon by Icons8
        this.selectButton.setIcon(new ImageIcon(new ImageIcon("src/image/mouseClicked.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        // Rectangle icon by Icons8
        this.createRecSurfaceButton.setIcon(new ImageIcon(new ImageIcon("src/image/addRec.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        //Polygon icon by Icons8
        this.createIrrSurfaceButton.setIcon(new ImageIcon(new ImageIcon("src/image/addIrr.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));

        selectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("Entered");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("Exited");
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
    }
}
