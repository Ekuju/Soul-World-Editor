package editor.ui.parts.content.stageproperties.stage;

import editor.ui.listeners.StaticListener;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Trent on 11/28/2017.
 */
public class StageDraggingManager {
    private static boolean dragging;
    private static Point2D.Double stageInitialPosition;
    private static Point mouseInitialPosition;

    public static synchronized void beginDrag(Point2D.Double stagePosition) {
        if (dragging) {
            return;
        }

        stageInitialPosition = stagePosition;
        mouseInitialPosition = StaticListener.getMousePosition();
        dragging = true;
    }

    public static synchronized void updateDrag() {
        if (!dragging) {
            return;
        }

        Point mousePosition = StaticListener.getMousePosition();
        Point delta = new Point(mousePosition.x - mouseInitialPosition.x, mousePosition.y - mouseInitialPosition.y);
        RenderingStage.setStagePosition(stageInitialPosition.x - delta.x / RenderingStage.getScale(), stageInitialPosition.y - delta.y / RenderingStage.getScale());
    }

    public static synchronized void endDrag() {
        updateDrag();

        dragging = false;
        stageInitialPosition = null;
        mouseInitialPosition = null;
    }
}
