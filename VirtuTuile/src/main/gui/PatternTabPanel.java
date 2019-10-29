package gui;

import javax.swing.*;
import java.awt.*;

public class PatternTabPanel extends JPanel {
    public PatternTabPanel(RightPanel rightPanel){
        iconL = new ImageIcon(new ImageIcon("src/image/L.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionL = new JButton("L", iconL);

        iconAngle = new ImageIcon(new ImageIcon("src/image/Angle.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionAngle = new JButton("L", iconAngle);

        iconBrique = new ImageIcon(new ImageIcon("src/image/Brique.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionBrique = new JButton("L", iconBrique);

        iconChevron = new ImageIcon(new ImageIcon("src/image/Chevron.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionChevron = new JButton("L", iconChevron);

        iconDroit = new ImageIcon(new ImageIcon("src/image/Droit.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionDroit = new JButton("L", iconDroit);

        iconPaquet = new ImageIcon(new ImageIcon("src/image/Paquet.png").getImage().getScaledInstance(120, 100, Image.SCALE_DEFAULT));
        dispositionPaquet = new JButton("L", iconPaquet);

        dispositions = new JPanel(new GridLayout(2,3,20,20));


        dimentionButton = new Dimension(100,100);

        dispositionL.setPreferredSize(dimentionButton);
        dispositionAngle.setPreferredSize(dimentionButton);
        dispositionBrique.setPreferredSize(dimentionButton);
        dispositionChevron.setPreferredSize(dimentionButton);
        dispositionDroit.setPreferredSize(dimentionButton);
        dispositionPaquet.setPreferredSize(dimentionButton);


        dispositions.add(dispositionBrique);
        dispositions.add(dispositionChevron);
        dispositions.add(dispositionDroit);
        dispositions.add(dispositionPaquet);
        dispositions.add(dispositionL);
        dispositions.add(dispositionAngle);

        this.add(dispositions);

    }
    private Dimension dimentionButton;
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

}


