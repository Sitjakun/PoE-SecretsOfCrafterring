package cheater;

import Utils.BasicKeyListener;
import Utils.Modifiers;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftMagicItem extends BasicKeyListener {
    private static final int xItem = 5061;
    private static final int yItem = 1098;
    private static final int xAltStash = 146;
    private static final int yAltStash = 413;
    private static final int xAugStash = 298;
    private static final int yAugStash = 486;

    private static final List<String> prefixes = new ArrayList<>();
    private static final List<String> suffixes = new ArrayList<>();
    private static String itemType;
    private static boolean emptyPrefix;
    private static boolean emptySuffix;

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        GlobalScreen.addNativeKeyListener(new CraftMagicItem());
    }

    private static void setup() {
        itemType = "flask";
        prefixes.add("experiment");
        prefixes.add("clini");
        prefixes.add("exami");
        suffixes.add("movement speed during effect");
//        suffixes.add("life when you block");
//        suffixes.add("increased evasion rating during");
//        prefixes.add("empowering");
//        prefixes.add("unleashed");
//        prefixes.add("glaciated");
//        prefixes.add("polar");
//        prefixes.add("entombing");


        emptyPrefix = false;
        emptySuffix = false;
    }

    public void run() throws Exception {

        setup();

        Robot bot = new Robot();
        moveToItem(xItem, yItem, bot);

        String previousDecision = "";
        String previousItem = "";

        while (true) {
            try {
                // Copy item
                String item = copyItem(bot);

                // Safety to stop running
                if (!item.contains(itemType)) {
                    System.exit(0);
                }

                Map<String, Boolean> altOrAug = altOrAug(item);
                boolean aug = altOrAug.get("aug");
                boolean alt = altOrAug.get("alt");

                if (aug && alt) {
                    System.out.println("Error aug and alt at the same time.");
                    System.exit(0);
                } else if (!aug && !alt) {
                    System.out.println("Job done !");
                    break;
                } else if (alt && (!"alt".equals(previousDecision) || item.equals(previousItem))) {
                    pickCurrency(xAltStash, yAltStash, bot);
                    moveToItem(xItem, yItem, bot);
                    previousDecision = "alt";
                } else if (aug && (!"aug".equals(previousDecision) || item.equals(previousItem))) {
                    pickCurrency(xAugStash, yAugStash, bot);
                    moveToItem(xItem, yItem, bot);
                    previousDecision = "aug";
                }
                previousItem = item;

                // Roll
                rollCurrency(bot);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        bot.keyRelease(KeyEvent.VK_SHIFT);
        Thread.sleep((int) (Math.random() * 35) + 75);
    }


    private static Map<String, Boolean> altOrAug(String item) {

        Map<String, List<String>> wantedMods = new HashMap<>();
        wantedMods.put(Modifiers.Prefix.name(), prefixes);
        wantedMods.put(Modifiers.Suffix.name(), suffixes);

        Map<String, Boolean> altOrAug = new HashMap<>();
        altOrAug.put("alt", false);
        altOrAug.put("aug", false);

        switch (chooseScenario()) {
            case PrefixOnly -> {
                return altOrAugPrefixOnly(item, wantedMods, altOrAug);
            }
            case SuffixOnly -> {
                return altOrAugSuffixOnly(item, wantedMods, altOrAug);
            }
            case Both -> {
                return altOrAugBoth(item, wantedMods, altOrAug);
            }
            default -> {
                System.out.println("Error decision prefix only vs suffix only vs both.");
                return altOrAug;
            }
        }
    }

    private static Map<String, Boolean> altOrAugPrefixOnly(String item, Map<String, List<String>> wantedMods, Map<String, Boolean> altOrAug) {
        if ((hasPrefix(item) && wantedMods.get(Modifiers.Prefix.name()).stream().noneMatch(item::contains))) { // has prefix but no match
            altOrAug.put("alt", true);
        } else if (hasSuffix(item) && !emptySuffix) { // has suffix
            altOrAug.put("aug", true);
        } else if (hasSuffix(item)) {
            altOrAug.put("alt", true);
        }
        return altOrAug;
    }

    private static Map<String, Boolean> altOrAugSuffixOnly(String item, Map<String, List<String>> wantedMods, Map<String, Boolean> altOrAug) {
        if ((hasSuffix(item) && wantedMods.get(Modifiers.Suffix.name()).stream().noneMatch(item::contains))) { // has suffix but no match
            altOrAug.put("alt", true);
        } else if (hasPrefix(item) && !emptyPrefix) { // has prefix
            altOrAug.put("aug", true);
        } else if (hasPrefix(item)) {
            altOrAug.put("alt", true);
        }
        return altOrAug;
    }

    private static Map<String, Boolean> altOrAugBoth(String item, Map<String, List<String>> wantedMods, Map<String, Boolean> altOrAug) {

        // No prefix no suffix matched = alteration
        if ((hasPrefix(item) && wantedMods.get(Modifiers.Prefix.name()).stream().noneMatch(item::contains)) // has prefix but no match
                || (hasSuffix(item) && wantedMods.get(Modifiers.Suffix.name()).stream().noneMatch(item::contains)) // has suffix but no match
        ) {
            altOrAug.put("alt", true);
        }

        // Prefix matched
        else if (wantedMods.get(Modifiers.Prefix.name()).stream().anyMatch(item::contains)) {
            if (wantedMods.get(Modifiers.Suffix.name()).stream().anyMatch(item::contains)) { // + Suffix match = STOP
                return altOrAug;
            } else if (hasSuffix(item)) { // Bad suffix -> alteration
                altOrAug.put("alt", true);
            } else { // Missing suffix -> augmentation
                altOrAug.put("aug", true);
            }
        }

        // Suffix matched
        else if (wantedMods.get(Modifiers.Suffix.name()).stream().anyMatch(item::contains)) {
            if (hasPrefix(item)) { // Bad prefix  -> alteration
                altOrAug.put("alt", true);
            } else { // Missing prefix -> augmentation
                altOrAug.put("aug", true);
            }
        }

        return altOrAug;
    }

    private static Modifiers chooseScenario() {
        if (prefixes.isEmpty() && suffixes.isEmpty()) {
            System.out.println("Error no mods specified");
            System.exit(0);
        }
        if (prefixes.isEmpty()) {
            return Modifiers.SuffixOnly;
        } else if (suffixes.isEmpty()) {
            return Modifiers.PrefixOnly;
        }
        return Modifiers.Both;
    }

    private static String copyItem(Robot bot) throws InterruptedException {
        bot.keyPress(KeyEvent.VK_CONTROL);
        bot.keyPress(KeyEvent.VK_ALT);
        bot.keyPress(KeyEvent.VK_C);
        bot.keyRelease(KeyEvent.VK_CONTROL);
        bot.keyRelease(KeyEvent.VK_ALT);
        bot.keyRelease(KeyEvent.VK_C);
        Thread.sleep((int) (Math.random() * 35) + 100);

        String item = onPaste().toLowerCase();
        Thread.sleep((int) (Math.random() * 35) + 75);
        System.out.println(item);
        return item;
    }

    private static void rollCurrency(Robot bot) throws InterruptedException {
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep((int) (Math.random() * 35) + 100);
    }

    @SuppressWarnings("SameParameterValue")
    private static void moveToItem(int xItem, int yItem, Robot bot) throws InterruptedException {
        bot.mouseMove(xItem, yItem);
        Thread.sleep((int) (Math.random() * 35) + 15);
    }

    private static void shift(Robot bot) throws InterruptedException {
        bot.keyPress(KeyEvent.VK_SHIFT);
        Thread.sleep((int) (Math.random() * 35) + 15);
    }

    private static void pickCurrency(int xCurrency, int yCurrency, Robot bot) throws InterruptedException {
        shift(bot);
        bot.mouseMove(xCurrency, yCurrency);
        Thread.sleep((int) (Math.random() * 35) + 15);
        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep((int) (Math.random() * 35) + 15);
        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        Thread.sleep((int) (Math.random() * 35) + 15);
    }

    public static String onPaste() {
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

    private static boolean hasPrefix(String item) {
        return item.contains(Modifiers.Prefix.name().toLowerCase());
    }

    private static boolean hasSuffix(String item) {
        return item.contains(Modifiers.Suffix.name().toLowerCase());
    }

    private static void checkMonitorsCoordinates() {
        // To check pixels coordinate of each monitor
        GraphicsDevice[] screens = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getScreenDevices();

        for (GraphicsDevice screen : screens) {
            System.out.println(screen.getDefaultConfiguration().getBounds());
        }
    }
}
