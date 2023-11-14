package cheater;

import Utils.BasicKeyListener;
import Utils.Coordinates;
import Utils.PoeInventory;
import Utils.PoeStash;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static cheater.CraftMagicItem.onPaste;

public class SexRoll extends BasicKeyListener {

    private Robot bot;
    private int sextantCounter = 50;
    private int compassCounter = 50;

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new SexRoll());
    }

    public void run() throws Exception {
        bot = new Robot();
        int time = 200;
        openAtlas();
        Thread.sleep(time);
        openInventory();
        Thread.sleep(time);
        for (int i = 1; i <= 50; i++) {
            moveToSextant();
            Thread.sleep(time);
            rightClick();
            Thread.sleep(time);
            moveToVoidStone();
            Thread.sleep(time);
            click();
            Thread.sleep(time);
            if (isCompassValuable(copyItem())) {
                moveToCompass();
                Thread.sleep(time);
                rightClick();
                Thread.sleep(time);
                moveToVoidStone();
                Thread.sleep(time);
                click();
                Thread.sleep(time);
                moveBottomRight();
                Thread.sleep(time);
                click();
                Thread.sleep(time);
                escape();
                Thread.sleep(time);
                moveToStash();
                Thread.sleep(time);
                click();
                Thread.sleep(time);
                moveToCompassStash();
                Thread.sleep(time);
                click();
                Thread.sleep(time);
                moveBottomRight();
                Thread.sleep(time);
                ctrlClick();
                Thread.sleep(time);
                openAtlas();
                Thread.sleep(time);
                openInventory();
                Thread.sleep(time);
            }
        }
    }

    private void ctrlClick() {
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        bot.keyRelease(KeyEvent.VK_CONTROL);
    }

    private void moveToCompassStash() {
        bot.mouseMove(PoeStash.getCompassStash().getX(), PoeStash.getCompassStash().getY());
    }

    private void moveToStash() {
        bot.mouseMove(PoeStash.getStash().getX(), PoeStash.getStash().getY());
    }

    private void escape() {
        bot.keyPress(KeyEvent.VK_ESCAPE);
        bot.keyRelease(KeyEvent.VK_ESCAPE);
    }

    private String copyItem() throws InterruptedException {
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_C);
        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_C);
        Thread.sleep((int) (Math.random() * 35) + 100);
        String item = onPaste().toLowerCase();
        Thread.sleep((int) (Math.random() * 35) + 75);
        System.out.println(item);
        return item;
    }
    private void moveBottomRight() {
        bot.mouseMove(PoeInventory.getLastCase().getX(), PoeInventory.getLastCase().getY());
    }

    private void moveToSextant() {
        bot.mouseMove(PoeInventory.getFirstCase().getX(), PoeInventory.getFirstCase().getY() + ((sextantCounter - 1) / 10) * PoeInventory.getSizeInBetween());
        sextantCounter--;
    }

    public void moveToCompass() {
        bot.mouseMove(PoeInventory.getFirstCase().getX() + PoeInventory.getSizeInBetween(), PoeInventory.getFirstCase().getY() + ((compassCounter - 1) / 10) * PoeInventory.getSizeInBetween());
        compassCounter--;
    }

    private void click() {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

    private void rightClick() {
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    private void openInventory() {
        bot.keyPress(KeyEvent.VK_I);
        bot.keyRelease(KeyEvent.VK_I);
    }

    private void openAtlas() {
        bot.keyPress(KeyEvent.VK_G);
        bot.keyRelease(KeyEvent.VK_G);
    }

    public void moveToVoidStone() {
        Coordinates sextantCoordinates = new Coordinates(2610, 1256);
        bot.mouseMove(sextantCoordinates.getX(), sextantCoordinates.getY());
    }

    public boolean isCompassValuable(String compass) {
        List<String> valuableCompasses = new ArrayList<>();
        valuableCompasses.add("harbinger");
        valuableCompasses.add("additional legion");
        valuableCompasses.add("additional abyss");
        valuableCompasses.add("duplicated");
        valuableCompasses.add("blight");
        valuableCompasses.add("oils");
        valuableCompasses.add("mirror of Delirium");
        valuableCompasses.add("Delirium Reward");
        valuableCompasses.add("smuggler");
        valuableCompasses.add("heist");
        valuableCompasses.add("additional essence");
        valuableCompasses.add("runic monster");
        valuableCompasses.add("can contain breaches");
        valuableCompasses.add("chayula");
        valuableCompasses.add("sacred grove");
        valuableCompasses.add("lifeforce");
        valuableCompasses.add("alva");
        valuableCompasses.add("copy of beasts");
        valuableCompasses.add("jun");
        valuableCompasses.add("immortal syndicate");
        valuableCompasses.add("niko");
        valuableCompasses.add("strongbox");
        valuableCompasses.add("gilded scarab");
        valuableCompasses.add("polished scarab");
        valuableCompasses.add("8 modifier");
        valuableCompasses.add("shaper guardian");
        valuableCompasses.add("elder guardian");
        valuableCompasses.add("conqueror map");
        valuableCompasses.add("maps have 20%");
        valuableCompasses.add("rarity of item");
        valuableCompasses.add("hunted");
        valuableCompasses.add("beyond");
        valuableCompasses.add("pack size");
        valuableCompasses.add("rare monster packs");
        return valuableCompasses.stream().map(String::toLowerCase).anyMatch(compass::contains);
    }

}
