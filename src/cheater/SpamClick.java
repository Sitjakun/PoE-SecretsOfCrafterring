package cheater;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SpamClick {

    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_CONTROL);
        for (int i = 0; i < 60; i++) {
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep((int) (Math.random() * 35) + 75);
        }
        bot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
