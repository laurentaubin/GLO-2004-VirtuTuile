import gui.*;
import javax.swing.*;


public class Main {

    public static void main(String[] args)
    {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setVisible(true);


    }
}
