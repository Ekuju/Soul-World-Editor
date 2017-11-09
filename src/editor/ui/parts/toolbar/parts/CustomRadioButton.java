package editor.ui.parts.toolbar.parts;

import editor.logic.interfaces.ToggleListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomRadioButton extends JRadioButton implements ChangeListener {
    private Color normalColor = Color.LIGHT_GRAY;
    private Color toggledColor = Color.GRAY;

    private ArrayList<ToggleListener> toggleListenerList = new ArrayList<ToggleListener>();

    public CustomRadioButton() {
        setOpaque(true);
        setBackground(normalColor);

        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (isSelected()) {
            setBackground(toggledColor);
        } else if (!isSelected()) {
            setBackground(normalColor);
        }

        for (ToggleListener toggleListener : toggleListenerList) {
            toggleListener.togglePerformed(isSelected());
        }
    }

    public void addToggleListener(ToggleListener toggleListener) {
        toggleListenerList.add(toggleListener);
    }
}
