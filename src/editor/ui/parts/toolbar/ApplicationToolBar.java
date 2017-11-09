package editor.ui.parts.toolbar;

import editor.logic.tools.SelectMoveTool;
import editor.logic.tools.Tool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ApplicationToolBar extends JToolBar {
    private static final Tool SELECT_MOVE_TOOL = new SelectMoveTool();

    private static Tool currentTool;

    public ApplicationToolBar() {
        setRollover(false);
        setFloatable(false);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

        JPanel rootPanel = new JPanel();
        add(rootPanel);

        rootPanel.setBackground(new Color(0xe2e2e2));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.LEADING);
        rootPanel.setLayout(flowLayout);

        MoveToolButton moveToolButton = new MoveToolButton();
        rootPanel.add(moveToolButton);
    }

    public static Tool getTool() {
        return currentTool;
    }

    public static void setTool(Class<? extends Tool> tool) {
        if (tool == SelectMoveTool.class) {
            currentTool = SELECT_MOVE_TOOL;
        }
    }
}
