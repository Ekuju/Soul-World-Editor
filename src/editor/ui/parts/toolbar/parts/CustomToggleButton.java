package editor.ui.parts.toolbar.parts;

import editor.logic.interfaces.ToggleListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ButtonUI;
import java.awt.*;
import java.util.ArrayList;

public class CustomToggleButton extends JToggleButton implements ChangeListener {
    private Color normalColor = Color.LIGHT_GRAY;
    private Color toggledColor = Color.GRAY;

    private ArrayList<ToggleListener> toggleListenerList = new ArrayList<ToggleListener>();

    public CustomToggleButton() {
        super(null, null, false);

        setBorderPainted(false);
        setHorizontalAlignment(LEADING);

        setOpaque(true);
        setBackground(normalColor);

        setFocusable(false);

        addChangeListener(this);
    }

    public void addToggleListener(ToggleListener toggleListener) {
        toggleListenerList.add(toggleListener);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        boolean selected = isSelected();

        if (selected) {
            setBackground(toggledColor);
        } else {
            setBackground(normalColor);
        }

        for (ToggleListener toggleListener : toggleListenerList) {
            toggleListener.togglePerformed(selected);
        }
    }

    /**
     * Resets the UI property to a value from the current look and feel.
     * (Of a RadioButton.)
     *
     * @see JComponent#updateUI
     */
    @Override
    public void updateUI() {
        setUI((ButtonUI)UIManager.getUI(new JRadioButton()));
    }
}
