package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RotateToolButton extends CustomToggleButton {
    public RotateToolButton() {
        try {
            Image moveToolImage = ImageIO.read(new File("assets/rotate-tool.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(moveToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                if (value) {
                    ApplicationToolBar.setTool(ApplicationToolBar.TOOL_ROTATE_TOOL);
                }
            }
        });
    }
}
