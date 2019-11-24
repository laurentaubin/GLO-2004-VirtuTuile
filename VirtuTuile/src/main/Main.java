import gui.*;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import java.awt.*;


public class Main {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        /*
        MainPanelTest mainPanelTest = new MainPanelTest();
        mainPanelTest.setExtendedState(mainPanelTest.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainPanelTest.pack();
        mainPanelTest.setVisible(true);

         */
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        Main object = new Main();
        object.setFont(new FontUIResource((new Font("Helvetica Neue", Font.PLAIN, 13))));

        UIManager.put("Menu.selectionBackground", new Color(0,112,245));
        UIManager.put("Menu.selectionForeground", new Color(235, 235, 240));
        UIManager.put("MenuItem.selectionForeground", new Color(235, 235, 240));
        UIManager.put("MenuItem.selectionBackground", new Color(20,142,255));

        UIManager.put("ToggleButton.highlight", new Color(0,112,245));
        UIManager.put("ToggleButton.focus", new Color(216, 216, 220));
        UIManager.put("ToggleButton.select", new Color(216, 216, 220));
        UIManager.put("ToggleButton.background", Color.WHITE);

        UIManager.put("Button.background", Color.WHITE);

        UIManager.put("CheckBox.select", new Color(0,112,245));
        //UIManager.put("CheckBox.interiorBackground", new Color(0,112,245));

        //UIManager.put("scrollbar", Color.BLACK);
        //UIManager.put("Scrollbar.track", Color.RED);
        //UIManager.put("ScrollBar.thumbHighlight", Color.BLACK);
        //UIManager.put("ScrollBar.thumbShadow", Color.BLACK);
        //UIManager.put("ScrollBar.thumbDarkShadow", Color.BLACK);

        UIManager.put("ComboBox.selectionBackground", new Color(0,112,245) );
        UIManager.put("ComboBox.selectionForeground", new Color(235, 235, 240));
        UIManager.put("ComboBox.buttonHighlight", Color.LIGHT_GRAY);
        UIManager.put("ComboBox.buttonBackground", Color.BLACK);

        UIManager.put("TabbedPane.unselectedTabForeground", new Color(108,108,112));
        UIManager.put("TabbedPane.selected", new Color(0,122,255));
        //UIManager.put("TabbedPane.selectedForeground", new Color(216, 216, 220));
        //UIManager.put("TabbedPane.contentAreaColor", Color.LIGHT_GRAY);
        //UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        //UIManager.put("TabbedPane.selectedTabPadInsets", new Insets(0, 20, 20, 0));
        //UIManager.put("TabbedPane.borderHightlightColor", Color.BLACK);

        UIManager.put("Panel.border", Color.BLACK);


        MainWindow mainWindow = new MainWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);
    }

    // Code provenant de: https://stackoverflow.com/questions/7434845/setting-the-default-font-of-swing-program
    private void setFont(FontUIResource myFont) {
        UIManager.put("CheckBoxMenuItem.acceleratorFont", myFont);
        UIManager.put("Button.font", myFont);
        UIManager.put("ToggleButton.font", myFont);
        UIManager.put("RadioButton.font", myFont);
        UIManager.put("CheckBox.font", myFont);
        UIManager.put("ColorChooser.font", myFont);
        UIManager.put("ComboBox.font", myFont);
        UIManager.put("Label.font", myFont);
        UIManager.put("List.font", myFont);
        UIManager.put("MenuBar.font", myFont);
        UIManager.put("Menu.acceleratorFont", myFont);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.font", myFont);
        UIManager.put("RadioButtonMenuItem.font", myFont);
        UIManager.put("CheckBoxMenuItem.font", myFont);
        UIManager.put("OptionPane.buttonFont", myFont);
        UIManager.put("OptionPane.messageFont", myFont);
        UIManager.put("Menu.font", myFont);
        UIManager.put("PopupMenu.font", myFont);
        UIManager.put("OptionPane.font", myFont);
        UIManager.put("Panel.font", myFont);
        UIManager.put("ProgressBar.font", myFont);
        UIManager.put("ScrollPane.font", myFont);
        UIManager.put("Viewport.font", myFont);
        UIManager.put("TabbedPane.font", myFont);
        UIManager.put("Slider.font", myFont);
        UIManager.put("Table.font", myFont);
        UIManager.put("TableHeader.font", myFont);
        UIManager.put("TextField.font", myFont);
        UIManager.put("Spinner.font", myFont);
        UIManager.put("PasswordField.font", myFont);
        UIManager.put("TextArea.font", myFont);
        UIManager.put("TextPane.font", myFont);
        UIManager.put("EditorPane.font", myFont);
        UIManager.put("TabbedPane.smallFont", myFont);
        UIManager.put("TitledBorder.font", myFont);
        UIManager.put("ToolBar.font", myFont);
        UIManager.put("ToolTip.font", myFont);
        UIManager.put("Tree.font", myFont);
        UIManager.put("FormattedTextField.font", myFont);
        UIManager.put("IconButton.font", myFont);
        UIManager.put("InternalFrame.optionDialogTitleFont", myFont);
        UIManager.put("InternalFrame.paletteTitleFont", myFont);
        UIManager.put("InternalFrame.titleFont", myFont);
    }
}
