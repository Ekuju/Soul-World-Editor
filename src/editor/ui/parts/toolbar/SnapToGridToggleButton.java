package editor.ui.parts.toolbar;

import editor.logic.Settings;
import editor.logic.interfaces.ToggleListener;
import editor.ui.parts.toolbar.parts.CustomToggleButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SnapToGridToggleButton extends CustomToggleButton {
    public SnapToGridToggleButton() {
        try {
            Image gridSnapImage = ImageIO.read(new File("assets/grid-snap.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            Image gridNoSnapImage = ImageIO.read(new File("assets/grid-no-snap.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);

            setIcon(new ImageIcon(gridNoSnapImage));
            // setPressedIcon(new ImageIcon(gridNoSnapImage));
            // setSelectedIcon(new ImageIcon(gridSnapImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Snap to Grid");

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                Settings.setSnapToGrid(value);
            }
        });

        setSelected(Settings.isSnapToGrid());
    }
}
