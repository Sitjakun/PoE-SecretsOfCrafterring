package cheater;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import picture.ShowPicture;

public class TryndaUltTracking {

    public static void main(String[] args) throws AWTException, InterruptedException {

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

        while(true) {
            color = bot.getPixelColor((int)coord.getX(), (int)coord.getY());
            //System.out.printf("Couleur = R%s G%s B%s%n", color.getRed(), color.getGreen(), color.getBlue());
            bot.delay(50);
            if(color.getRed() == 29 && color.getGreen() == 79 && color.getBlue() == 121 && colorChanged) {
                System.out.println("Undying Raaage");
                countdown();
                colorChanged = false;
            }
            if(color.getRed() == 232 && color.getGreen() == 58 && color.getBlue() == 24 && !colorChanged) {
                colorChanged = true;
            }
        }


    }

    private static void countdown() {

        ShowPicture.main(null);

        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        final Runnable runnable = new Runnable() {
            int countdownStarter = 5;

            public void run() {

                System.out.println(countdownStarter);
                countdownStarter--;

                if (countdownStarter < 0) {
                    System.out.println("Timer Over!");
                    ShowPicture.clear();
                    scheduler.shutdown();
                }
            }
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
    }
}