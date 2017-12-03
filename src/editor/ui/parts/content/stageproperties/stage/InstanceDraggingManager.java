package editor.ui.parts.content.stageproperties.stage;

import editor.logic.stage.parts.instances.AssetInstance;
import editor.logic.stage.parts.instances.ImageAssetInstance;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by Trent on 11/27/2017.
 */
public class InstanceDraggingManager {
    private static final Color VALID_COLOR = new Color(0xd9e3ff);
    private static final Color INVALID_COLOR = new Color(0xd31f0e);

    private static AssetInstance instance;
    private static Point2D.Double dragRelativePosition;

    public static synchronized void beginDrag(AssetInstance instance, Point2D.Double dragRelativePosition) {
        InstanceDraggingManager.instance = instance;
        InstanceDraggingManager.dragRelativePosition = dragRelativePosition;

        if (InstanceDraggingManager.dragRelativePosition == null) {
            InstanceDraggingManager.dragRelativePosition = new Point2D.Double();
        }
    }

    public static synchronized void updateDrag() {
        if (instance == null) {
            return;
        }

        Point relativeMousePosition = RenderingStage.getRelativeMousePosition();

        Point2D.Double offset = new Point2D.Double(instance.getWidth() * dragRelativePosition.x, instance.getHeight() * dragRelativePosition.y);
        Point2D.Double anchor = new Point2D.Double(instance.getWidth() * instance.getAnchor().x, instance.getHeight() * instance.getAnchor().y);

        Point position = RenderingStage.getValidPosition(anchor.x - offset.x + relativeMousePosition.x, anchor.y - offset.y + relativeMousePosition.y);

        instance.setPosition(position.x, position.y);
    }

    public static synchronized void renderDrag(Graphics2D g) {
        if (instance == null) {
            return;
        }

        if (instance instanceof ImageAssetInstance) {
            if (RenderingStage.getScene().acceptsImageAssets()) {
                instance.tint(VALID_COLOR);
            } else {
                instance.tint(INVALID_COLOR);
            }
        }
        instance.render(g);
    }

    public static synchronized void endDrag(Point screenPosition) {
        Point renderingStagePosition = StageCombinedPanel.renderingStage.getLocationOnScreen();
        Dimension renderingStageDimensions = StageCombinedPanel.renderingStage.getSize();

        boolean horizontal = screenPosition.x >= renderingStagePosition.x && screenPosition.x < renderingStagePosition.x + renderingStageDimensions.width;
        boolean vertical = screenPosition.y >= renderingStagePosition.y && screenPosition.y < renderingStagePosition.y + renderingStageDimensions.height;
        if (!horizontal || !vertical) {
            cancelDrag();

            return;
        }

        if (instance instanceof ImageAssetInstance) {
            if (RenderingStage.getScene().acceptsImageAssets()) {
                RenderingStage.getScene().addAssetInstance((ImageAssetInstance) instance);
            }
        } else {
            System.err.println("Tried to end the drag on an invalid asset instance type.");
        }

        cancelDrag();
    }

    private static synchronized void cancelDrag() {
        instance = null;
        dragRelativePosition = null;
    }
}
