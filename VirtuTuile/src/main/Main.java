import gui.*;
import javax.swing.*;


public class Main {

    public static void main(String[] args)
    {
        MainPanelTest mainPanelTest = new MainPanelTest();
        mainPanelTest.setExtendedState(mainPanelTest.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainPanelTest.pack();
        mainPanelTest.setVisible(true);



        //MainWindow mainWindow = new MainWindow();
        //mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        //mainWindow.setVisible(true);
    }
}
