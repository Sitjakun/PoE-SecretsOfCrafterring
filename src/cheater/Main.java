package cheater;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) throws AWTException, InterruptedException {

        // To check pixels coordinate of each monitor
//        GraphicsDevice[] screens = GraphicsEnvironment
//                .getLocalGraphicsEnvironment()
//                .getScreenDevices();
//
//        for (GraphicsDevice screen:screens) {
//            System.out.println(screen.getDefaultConfiguration().getBounds());
//        }

        // To inspect mouse coordinates
//        Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
//        System.out.println(mouseLocation);

// Alteration = [x=1876,y=827]
// Item = [x=1881,y=772]

        String wantedMod = "increased intel";
        boolean keepGoing = true;

        Robot bot = new Robot();

        bot.keyPress(KeyEvent.VK_SHIFT);
        Thread.sleep((int)(Math.random() * 35) + 75);

        bot.mouseMove(1876, 827);
        Thread.sleep((int)(Math.random() * 35) + 75);

        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep((int)(Math.random() * 35) + 75);

        bot.mouseMove(1881, 772);
        Thread.sleep((int)(Math.random() * 35) + 75);

        while (keepGoing) {

            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep((int)(Math.random() * 35) + 75);

            bot.keyPress(KeyEvent.VK_CONTROL);
            bot.keyPress(KeyEvent.VK_C);
            bot.keyRelease(KeyEvent.VK_CONTROL);
            bot.keyRelease(KeyEvent.VK_C);
            Thread.sleep((int)(Math.random() * 35) + 75);

            String clipboard = onPaste();
            Thread.sleep((int)(Math.random() * 35) + 75);

            if (clipboard.toLowerCase().contains(wantedMod)) {
                keepGoing = false;
            }

        }
        bot.keyRelease(KeyEvent.VK_SHIFT);
        Thread.sleep((int)(Math.random() * 35) + 75);
    }

    private static String onPaste() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(null);
        if (t == null)
            return "";
        try {
            return (String) t.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            System.out.println("Error message =" + e.getMessage() + "\n Error cause=" + e.getCause());
            return "";
        }
    }
}
