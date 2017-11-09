package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;
import editor.ui.parts.toolbar.parts.CustomRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScaleToolButton extends CustomRadioButton {
    public ScaleToolButton() {
        try {
            Image scaleToolImage = ImageIO.read(new File("assets/scale-tool.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaleToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                if (value) {
                    ApplicationToolBar.setTool(ApplicationToolBar.TOOL_SCALE_TOOL);
                }
            }
        });
    }
}
