package editor.logic;

public class Settings {
    private static int gridRenderSize = 10;
    private static int worldSectionSize = 3840;

    private static boolean snapToGrid = false;

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
}
