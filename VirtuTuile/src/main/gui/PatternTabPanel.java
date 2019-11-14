package gui;

import domain.room.Cover;
import domain.room.pattern.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PatternTabPanel extends JPanel {
    private RightPanel rightPanel;

    private Dimension dimensionButton;

    private ImageIcon iconL;
    private JButton dispositionL;

    private ImageIcon iconAngle;
    private JButton dispositionAngle;

    private ImageIcon iconBrique;
    private JButton dispositionBrique;

    private ImageIcon iconChevron;
    private JButton dispositionChevron;

    private ImageIcon iconDroit;
    private JButton dispositionDroit;

    private ImageIcon iconPaquet;
    private JButton dispositionPaquet;

    private JPanel dispositions;

    public PatternTabPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
        initComponents();
    }

        private void initComponents() {

            this.iconL = new ImageIcon(new ImageIcon("src/image/L.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionL = new JButton("L", iconL);

            this.iconAngle = new ImageIcon(new ImageIcon("src/image/Angle.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionAngle = new JButton("L", iconAngle);

            this.iconBrique = new ImageIcon(new ImageIcon("src/image/Brique.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionBrique = new JButton("L", iconBrique);

            this.iconChevron = new ImageIcon(new ImageIcon("src/image/Chevron.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionChevron = new JButton("L", iconChevron);

            this.iconDroit = new ImageIcon(new ImageIcon("src/image/Droit.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionDroit = new JButton("L", iconDroit);

            this.iconPaquet = new ImageIcon(new ImageIcon("src/image/Paquet.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
            this.dispositionPaquet = new JButton("L", iconPaquet);

            this.dispositions = new JPanel(new GridLayout(2, 3, 20, 20));


            this.dimensionButton = new Dimension(100, 100);

            this.dispositionL.setPreferredSize(dimensionButton);
            this.dispositionAngle.setPreferredSize(dimensionButton);
            this.dispositionBrique.setPreferredSize(dimensionButton);
            this.dispositionChevron.setPreferredSize(dimensionButton);
            this.dispositionDroit.setPreferredSize(dimensionButton);
            this.dispositionPaquet.setPreferredSize(dimensionButton);


            this.dispositions.add(dispositionBrique);
            this.dispositions.add(dispositionChevron);
            this.dispositions.add(dispositionDroit);
            this.dispositions.add(dispositionPaquet);
            this.dispositions.add(dispositionL);
            this.dispositions.add(dispositionAngle);

            this.add(dispositions);

            dispositionBrique.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new BrickPattern());
                }
            });

            dispositionChevron.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new ChevronPattern());
                }
            });

            dispositionDroit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new StraightPattern());
                }
            });

            dispositionPaquet.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new ParquetPattern());
                }
            });

            dispositionL.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new LShapePattern());
                }
            });

            dispositionAngle.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    updatePattern(new AnglePattern());
                }
            });
        }

        public void updatePattern(Pattern pattern) {
            this.rightPanel.getMainWindow().controller.setPatternToSelectedSurfaces(pattern);
            this.rightPanel.getMainWindow().getDrawingPanel().repaint();
        }

    }
