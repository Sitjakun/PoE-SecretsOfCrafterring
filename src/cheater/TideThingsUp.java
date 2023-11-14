package cheater;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class TideThingsUp {
    public static void main(String[] args) throws AWTException, InterruptedException {
        // First box = [x=1295,y=613] --> 50 px to move

        int firstBoxX = 4290;
        int firstBoxY = 820;

        Robot bot = new Robot();
        bot.keyPress(KeyEvent.VK_CONTROL);
        for (int pixelX = 0; pixelX < 12; pixelX++) {
            for (int pixelY = 0; pixelY < 5; pixelY++) {
                bot.mouseMove(firstBoxX + pixelX * 70, firstBoxY + pixelY * 70);
                Thread.sleep((int) (Math.random() * 35) + 25);

                bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                Thread.sleep((int) (Math.random() * 35) + 25);
            }
        }
        bot.keyRelease(KeyEvent.VK_CONTROL);
    }
}
