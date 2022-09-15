package cheater;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import picture.Picture;

public class TryndaUltTracking implements NativeKeyListener {
    public void nativeKeyPressed(NativeKeyEvent e) {

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException ex) {
                throw new RuntimeException(ex);
            }
        }

        if (Objects.equals(NativeKeyEvent.getKeyText(e.getKeyCode()), "R")) {
            System.out.println("R pressed");

//          X962.0 Y998.0
//          Color before ult = R232 G58 B24
//          Color after ult = R14 G86 B130 for 2.5 seconds then changes
            Robot bot;
            try {
                bot = new Robot();
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            Point coord = new Point(962, 998);
            Color color;
            while (true) {
                color = bot.getPixelColor((int) coord.getX(), (int) coord.getY());
                // System.out.printf("Couleur = R%s G%s B%s%n", color.getRed(), color.getGreen(), color.getBlue());
                bot.delay(20);
                if (color.getRed() == 14
                        && color.getGreen() == 86
                        && color.getBlue() == 130){
                    // System.out.println("Undying Raaage");
                    try {
                        new TryndaUltTracking().countdown();
                        System.exit(0);
                    } catch (InterruptedException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }

    }

    public void nativeKeyReleased(NativeKeyEvent e) {
        // System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public void nativeKeyTyped(NativeKeyEvent e) {
        // System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    public static void main(String[] args) throws AWTException {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new TryndaUltTracking());

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