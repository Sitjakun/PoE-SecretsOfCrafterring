package picture;

import java.awt.*;

import javax.swing.*;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class ShowPicture {

    private static Window window;

    public static void main(String[] args) {
        window = new Window(null);
        window.add(new JComponent() {
            /**
             * This will draw a black cross on screen.
             */
            protected void paintComponent(Graphics g) {
                g.drawImage(new ImageIcon("src/picture/Undying_Rage.jpg").getImage(), 0, 0, this);
            }

            public Dimension getPreferredSize() {
                return new Dimension(209, 212);
            }
        });
        window.pack();
        window.setLocation(1209, 865);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        setTransparent(window);
    }
    public static void clear(){
        window.setVisible(false);
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
