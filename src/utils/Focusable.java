package utils;

import editor.Application;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Trent on 11/17/2017.
 */
public class Focusable {
    private static Set<JComponent> components = new HashSet<JComponent>();

    public static void enable(JComponent component) {
        enable(component, true);
    }
    
    public static void enable(JComponent component, boolean escapeKey) {
        if (components.contains(component)) {
            System.err.println("Tried to enable focusing on the same component twice. " + component);
            
            return;
        }
        components.add(component);
        
        component.setFocusable(true);
        
        component.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                component.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        
        component.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE && escapeKey) {
                    Application.applicationFrame.requestFocus();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
