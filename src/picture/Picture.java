package picture;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class Picture {

    private Window window;
    private JComponent image;

    public void setImage() {
        image = new JComponent() {
            protected void paintComponent(Graphics g) {
                ImageIcon gif = new ImageIcon("src/picture/Undying_Rage_Copy.gif");
                g.drawImage(gif.getImage(), 0, 0, this);
            }
            public Dimension getPreferredSize() {
                return new Dimension(204, 208);
            }};
    }

    public void create(boolean visible) {
        window = new Window(null);
        window.add(image);
        window.pack();
        window.setLocation(1213, 869);
        window.setVisible(visible);
        window.setAlwaysOnTop(true);
        setTransparent(window);
    }

    public void remove(){
        window.remove(image);
    }

    public void stop(){
        window.dispose();
        window = null;
    }

    private static void setTransparent(Component w) {
        WinDef.HWND hwnd = getHWnd(w);
        int wl = User32.INSTANCE.GetWindowLong(hwnd, WinUser.GWL_EXSTYLE);
        wl = wl | WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT;
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, wl);
    }

    /**
     * Get the window handle from the OS
     */
    private static HWND getHWnd(Component w) {
        HWND hwnd = new HWND();
        hwnd.setPointer(Native.getComponentPointer(w));
        return hwnd;
    }
}
