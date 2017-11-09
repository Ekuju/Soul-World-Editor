package editor.ui.parts.toolbar;

import editor.logic.tools.*;

import javax.swing.*;
import java.awt.*;

public class ApplicationToolBar extends JToolBar {
    public static final int TOOL_SELECT_MOVE_TOOL = 0;
    public static final int TOOL_SCALE_TOOL = 1;
    public static final int TOOL_ROTATE_TOOL = 2;
    public static final int TOOL_POLYGON_TOOL = 3;

    private static final Tool SELECT_MOVE_TOOL = new SelectMoveTool();
    private static final Tool SCALE_TOOL = new ScaleTool();
    private static final Tool ROTATE_TOOL = new RotateTool();
    private static final Tool POLYGON_TOOL = new PolygonTool();

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

        SelectMoveToolButton selectMoveToolButton = new SelectMoveToolButton();
        rootPanel.add(selectMoveToolButton);

        ScaleToolButton scaleToolButton = new ScaleToolButton();
        rootPanel.add(scaleToolButton);

        RotateToolButton rotateToolButton = new RotateToolButton();
        rootPanel.add(rotateToolButton);

        PolygonToolButton polygonToolButton = new PolygonToolButton();
        rootPanel.add(polygonToolButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(selectMoveToolButton);
        buttonGroup.add(scaleToolButton);
        buttonGroup.add(rotateToolButton);
        buttonGroup.add(polygonToolButton);

        selectMoveToolButton.setSelected(true);
    }

    public static Tool getTool() {
        return currentTool;
    }

    public static void setTool(int tool) {
        switch (tool) {
            case TOOL_SELECT_MOVE_TOOL:
                currentTool = SELECT_MOVE_TOOL;
                break;

            case TOOL_SCALE_TOOL:
                currentTool = SCALE_TOOL;
                break;

            case TOOL_ROTATE_TOOL:
                currentTool = ROTATE_TOOL;
                break;

            case TOOL_POLYGON_TOOL:
                currentTool = POLYGON_TOOL;
                break;
        }
    }
}
