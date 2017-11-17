package editor.ui.parts.content.library.buttons.parts;

import editor.logic.interfaces.ToggleListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ButtonUI;
import java.awt.*;

public class CustomButton extends JButton implements ChangeListener {
    private Color normalColor = Color.LIGHT_GRAY;
    private Color toggledColor = Color.GRAY;

    public CustomButton() {
        setBorderPainted(false);
        setHorizontalAlignment(LEADING);

        setOpaque(true);
        setBackground(normalColor);
        
        setFocusable(false);

        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (getModel().isPressed()) {
            setBackground(toggledColor);
        } else if (!getModel().isPressed()) {
            setBackground(normalColor);
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
