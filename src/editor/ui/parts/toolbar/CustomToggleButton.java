package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomToggleButton extends JButton implements ActionListener, ChangeListener {
    private Color normalColor = Color.LIGHT_GRAY;
    private Color toggledColor = Color.GRAY;
    private Color pressedColor = Color.DARK_GRAY;

    private boolean value;

    private ArrayList<ToggleListener> toggleListenerList = new ArrayList<ToggleListener>();

    public CustomToggleButton() {
        setOpaque(true);
        setBorderPainted(false);
        setValue(false);

        super.addActionListener(this);
        addChangeListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setValue(!value);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(pressedColor);
        }
    }

    public boolean isToggled() {
        return value;
    }

    public void setValue(boolean toggled) {
        value = toggled;

        Color color = value ? toggledColor : normalColor;
        setBackground(color);

        for (ToggleListener toggleListener : toggleListenerList) {
            toggleListener.togglePerformed(value);
        }
    }

    public void addToggleListener(ToggleListener toggleListener) {
        toggleListenerList.add(toggleListener);
    }
}
