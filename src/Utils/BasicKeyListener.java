package Utils;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public abstract class BasicKeyListener implements NativeKeyListener {

    public void nativeKeyPressed(NativeKeyEvent e) {

        Thread t = new Thread(() -> {
            try {
                run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        if (e.getKeyCode() == NativeKeyEvent.VC_F11) {
            t.start();
        }
        if (e.getKeyCode() == NativeKeyEvent.VC_F10) {
            try {
                GlobalScreen.unregisterNativeHook();
                System.exit(0);
            } catch (NativeHookException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected abstract void run() throws Exception;
}
