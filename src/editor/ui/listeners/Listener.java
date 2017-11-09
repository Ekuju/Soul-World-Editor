package editor.ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listener implements KeyListener, MouseListener {
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

    @Override
    public void mouseClicked(MouseEvent e) {
        StaticListener.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        StaticListener.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        StaticListener.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        StaticListener.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        StaticListener.mouseExited(e);
    }
}
