package cheater;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class SpamClick {

    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot bot = new Robot();
//        bot.keyPress(KeyEvent.VK_SHIFT);
        for (int i = 0; i < 671; i++) {
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            Thread.sleep((int) (Math.random() * 35) + 45);
        }
        bot.keyRelease(KeyEvent.VK_SHIFT);
    }
}
