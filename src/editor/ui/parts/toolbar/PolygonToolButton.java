package editor.ui.parts.toolbar;

import editor.logic.interfaces.ToggleListener;
import editor.ui.parts.toolbar.parts.CustomRadioButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PolygonToolButton extends CustomRadioButton {
    public PolygonToolButton() {
        try {
            Image polygonToolImage = ImageIO.read(new File("assets/polygon-tool.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(polygonToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Polygon Tool");

        addToggleListener(new ToggleListener() {
            @Override
            public void togglePerformed(boolean value) {
                if (value) {
                    ApplicationToolBar.setTool(ApplicationToolBar.TOOL_POLYGON_TOOL);
                }
            }
        });
    }
}
