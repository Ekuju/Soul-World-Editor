package editor.ui.listeners;

import editor.Application;

import java.awt.*;
import java.awt.event.KeyEvent;

public class StaticListener {
    public static Point getMousePosition() {
        return MouseInfo.getPointerInfo().getLocation();
    }
}
