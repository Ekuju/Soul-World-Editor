package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;
import editor.logic.tools.SelectMoveTool;
import editor.logic.tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MoveToolButton extends CustomToggleButton {
    public MoveToolButton() {
        try {
            Image moveToolImage = ImageIO.read(new File("assets/move-tool.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(moveToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                if (value) {
                    ApplicationToolBar.setTool(SelectMoveTool.class);
                }

                Tool tool = ApplicationToolBar.getTool();
                if (!value && tool != null && tool.getClass() == SelectMoveTool.class) {
                    ApplicationToolBar.setTool(null);
                }
            }
        });
    }
}
