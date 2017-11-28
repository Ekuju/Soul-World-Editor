package editor.ui.listeners;

import java.awt.event.*;

public class Listener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        StaticListener.keyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        StaticListener.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        StaticListener.keyReleased(e);
    }
}
