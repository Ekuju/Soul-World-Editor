package editor.logic;

import javax.swing.*;
import java.awt.*;

public class Settings {
    private static int gridRenderSize = 10;
    private static int worldSectionSize = 3840;

    private static boolean snapToGrid = false;

    private static int contentDividerPosition = 540;

    public static int getGridRenderSize() {
        return gridRenderSize;
    }

    public static void setGridRenderSize(int gridRenderSize) {
        Settings.gridRenderSize = gridRenderSize;
    }

    public static int getWorldSectionSize() {
        return worldSectionSize;
    }

    public static void setWorldSectionSize(int worldSectionSize) {
        Settings.worldSectionSize = worldSectionSize;
    }

    public static boolean isSnapToGrid() {
        return snapToGrid;
    }

    public static void setSnapToGrid(boolean snapToGrid) {
        Settings.snapToGrid = snapToGrid;
    }

    public static int getContentDividerPosition() {
        return contentDividerPosition;
    }

    public static void setContentDividerPosition(int contentDividerPosition) {
        Settings.contentDividerPosition = contentDividerPosition;
    }
}
