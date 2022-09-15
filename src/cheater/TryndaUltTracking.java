package cheater;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Timer;
import java.util.TimerTask;

import picture.Picture;

public class TryndaUltTracking {

    public static void main(String[] args) throws AWTException, InterruptedException, IOException {

        /**
         * X955.0 Y1009.0
         * Couleur avant ult = R232 G58 B24
         * Couleur après ult = R29 G79 B121 puis R11 G65 B110
         */
        Robot bot = new Robot();
        Point coord = new Point(955, 1009);
        // System.out.println("X" + coord.getX() + "Y" + coord.getY());
        Color color;

        boolean colorChanged = true;

        while(colorChanged) {
            color = bot.getPixelColor((int)coord.getX(), (int)coord.getY());
            //System.out.printf("Couleur = R%s G%s B%s%n", color.getRed(), color.getGreen(), color.getBlue());
            bot.delay(50);
            if(color.getRed() == 29 && color.getGreen() == 79 && color.getBlue() == 121 && colorChanged) {
                System.out.println("Undying Raaage");
                new TryndaUltTracking().countdown();
                colorChanged = false;
            }
        }

    }
    public void countdown() throws InterruptedException, IOException {

        try {
            Files.copy(Paths.get("src/picture/Undying_Rage.gif"), Paths.get("src/picture/Undying_Rage_Copy.gif"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Picture picture = new Picture();
        picture.setImage();
        picture.create(true);
        Thread.sleep(5000);
        picture.remove();
        picture.stop();

        Files.delete(Paths.get("src/picture/Undying_Rage_Copy.gif"));
    }
}