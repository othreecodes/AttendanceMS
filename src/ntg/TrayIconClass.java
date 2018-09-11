/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ntg;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
 

/**
 *
 * @author OTHREE
 */
public class TrayIconClass {

    static Toolkit mainToolkit;

    public static Toolkit getMainToolkit() {
        return mainToolkit;
    }

    public static void setMainToolkit(Toolkit mainToolkit) {
        TrayIconClass.mainToolkit = mainToolkit;
    }

    public static Image getTrayIconImage() {
        return trayIconImage;
    }

    public static void setTrayIconImage(Image trayIconImage) {
        TrayIconClass.trayIconImage = trayIconImage;
    }

    public static SystemTray getMainTray() {
        return mainTray;
    }

    public static void setMainTray(SystemTray mainTray) {
        TrayIconClass.mainTray = mainTray;
    }

    public static TrayIcon getMainTrayIcon() {
        return mainTrayIcon;
    }

    public static void setMainTrayIcon(TrayIcon mainTrayIcon) {
        TrayIconClass.mainTrayIcon = mainTrayIcon;
    }
    static Image trayIconImage;
    static SystemTray mainTray;
    static TrayIcon mainTrayIcon;

    public TrayIconClass() throws AWTException, IOException {
        mainToolkit = Toolkit.getDefaultToolkit();
        mainTray = SystemTray.getSystemTray();
        trayIconImage = ImageIO.read(getClass().getResource("image.png"));
        //trayIconImage = new ImageIcon("image.png").getImage();
        mainTrayIcon = new TrayIcon(trayIconImage);
        mainTrayIcon.setImage(trayIconImage);
        mainTrayIcon.setToolTip("NTG");
        mainTrayIcon.setImageAutoSize(true);
        mainTray.add(mainTrayIcon);

    }

}
