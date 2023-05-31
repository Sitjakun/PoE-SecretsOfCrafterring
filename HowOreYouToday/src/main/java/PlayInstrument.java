import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PlayInstrument {

    private static final String song = SongsName.HONEY_N_BISCUITS.name();
    private static final int yZ = 801;
    private static final int yQ = 832;
    private static final int yS = 864;
    private static final int yD = 897;
    private static final int ySp = 924;
    private static Integer note;
    private static Long time;

    public static void main(String[] args) throws Exception {

        // https://stackoverflow.com/questions/59373280/is-it-possible-to-color-java-output-in-terminal-using-rgb-or-hex-colors

        Map<String, List<Integer>> songs = new Songs().getSongs();
        int greatFromTimeToTime = 0;
        while (true) {
            Robot bot = new Robot();
            playSequence(bot, songs.get(song), greatFromTimeToTime);
            TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 500) + 1000);
            greatFromTimeToTime++;
        }
    }

    private static void playSequence(Robot bot, List<Integer> song, int greatFromTimeToTime) throws Exception {

        bot.keyPress(KeyEvent.VK_E);
        bot.keyRelease(KeyEvent.VK_E);

        Thread.sleep(1000);

        // largeur bulle = 53
        // largeur dbl click = 67
        long start = System.currentTimeMillis();
        note = 0;
        do {
            setTime(System.currentTimeMillis());
            if (scanBlackPixels(580, 15, bot, 5)) {
                generateNoiseAndMistakes(song, greatFromTimeToTime, note);
                System.out.println("Pressing " + note);
                pressButton(bot, note, 75);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } while (System.currentTimeMillis() - start < 60000);
    }

    private static boolean scanBlackPixels(int x, int width, Robot bot, int threshold) {
        Rectangle area = new Rectangle(x, yZ, width, ySp);
        BufferedImage screenshot = bot.createScreenCapture(area);

        boolean z = loopY(0, screenshot, threshold, width);
        boolean q = loopY(yQ - yZ, screenshot, threshold, width);
        boolean s = loopY(yS - yZ, screenshot, threshold, width);
        boolean d = loopY(yD - yZ, screenshot, threshold, width);
        boolean space = loopY(ySp - yZ, screenshot, threshold, width);
        boolean doubleClick = loopY(yQ - yZ, screenshot, 1, width) && loopY(yS - yZ, screenshot, 2, width) && loopY(yD - yZ, screenshot, 1, width);

        if(z) {
            note = KeyEvent.VK_Z;
            return true;
        } else if (space) {
            note = KeyEvent.VK_SPACE;
            return true;
        }
        if(doubleClick) {
            note = KeyEvent.VK_C;
            return true;
        } else if (q) {
            note = KeyEvent.VK_Q;
            return true;
        } else if (s) {
            note = KeyEvent.VK_S;
            return true;
        } else if (d) {
            note = KeyEvent.VK_D;
            return true;
        }

        return false;

    }

    private static boolean loopY(int y, BufferedImage screenshot, int threshold, int width) {
        int countBlackPixel = 0;
        for (int abs = 0; abs < width; abs++) {
            Color pixelColor = new Color(screenshot.getRGB(abs, y));
            if (pixelColor.getRed() < 15 && pixelColor.getGreen() < 15 && pixelColor.getBlue() < 15) {
                countBlackPixel++;
                if (countBlackPixel > threshold) {
//                    log("Many BP", note, screenshot);
//                    displayPixels(width, note, screenshot);
                    return true;
                }
            }
        }
//        log("No BP", note, screenshot);
//        displayPixels(width, note, screenshot);
        return false;
    }

    private static void displayPixels(int width, int note, BufferedImage screenshot) {
        if (note == 70 || note == 71) {
            for (int i = 0; i < width; i++) {
                Color color = new Color(screenshot.getRGB(i, 0));
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                System.out.print("\033[48;2;" + red + ";" + green + ";" + blue + "m" + " " + "\033[0m");
            }
            System.out.println("");
        }
    }

    private static void log(String bp, int note, BufferedImage screenshot) {
        if (note == 70 || note == 71) {
            setTime(System.currentTimeMillis() - getTime());
            System.out.println(bp + " - Note = " + note + " - Time = " + getTime() + "ms" + " Width = " + screenshot.getWidth());
        }
    }

    private static void generateNoiseAndMistakes(List<Integer> song, int greatFromTimeToTime, int note) throws InterruptedException {
        int randomNumber = (int) (Math.random() * 100);
        if (randomNumber > 85 && note != song.size() - 1) {
            TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 35) + 50);
        }
    }

    private static int getY(Integer key) {
        int y = yS;
        switch (key) {
            case KeyEvent.VK_Z:
                y = yZ;
                break;
            case KeyEvent.VK_Q:
                y = yQ;
                break;
            case KeyEvent.VK_D:
                y = yD;
                break;
            case KeyEvent.VK_SPACE:
                y = ySp;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_C:
            default:
                break;
        }
        return y;
    }

    private static void pressButton(Robot bot, Integer key, int delay) throws InterruptedException {
        if (!key.equals(KeyEvent.VK_C)) {
            pressKeyboard(bot, key, delay);
        } else {
            pressMouse(bot, delay);
        }
//        TimeUnit.MILLISECONDS.sleep(100);
    }

    private static void pressMouse(Robot bot, int delay) throws InterruptedException {
        int split = 10;
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 5) + split / 2);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 35) + delay - split);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 5) + split / 2);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    private static void pressKeyboard(Robot bot, Integer key, int delay) throws InterruptedException {
        bot.keyPress(key);
        TimeUnit.MILLISECONDS.sleep((int) (Math.random() * 35) + delay);
        bot.keyRelease(key);
    }

    public static Long getTime() {
        return time;
    }

    public static void setTime(Long time) {
        PlayInstrument.time = time;
    }
}
