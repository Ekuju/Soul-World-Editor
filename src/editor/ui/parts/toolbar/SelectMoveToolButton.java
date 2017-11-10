package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;
import editor.ui.parts.toolbar.parts.CustomRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SelectMoveToolButton extends CustomRadioButton {
    public SelectMoveToolButton() {
        try {
            Image selectMoveToolImage = ImageIO.read(new File("assets/move-tool.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(selectMoveToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Select Move Tool");

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                if (value) {
                    ApplicationToolBar.setTool(ApplicationToolBar.TOOL_SELECT_MOVE_TOOL);
                }
            }
        });
    }
}
