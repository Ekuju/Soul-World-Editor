package editor.ui.parts.content.stageproperties.stage;

import editor.logic.stage.parts.AssetInstance;
import editor.ui.listeners.StaticListener;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Trent on 11/27/2017.
 */
public class DraggingManager {
    private static AssetInstance instance;
    private static Point2D.Double dragRelativePosition;
    
    public static synchronized void beginDrag(AssetInstance instance, Point2D.Double dragRelativePosition) {
        DraggingManager.instance = instance;
        DraggingManager.dragRelativePosition = dragRelativePosition;
        
        if (DraggingManager.dragRelativePosition == null) {
            DraggingManager.dragRelativePosition = new Point2D.Double();
        }
    }
    
    public static synchronized void updateDrag(Point mousePosition) {
        if (instance == null) {
            return;
        }
        
        Point relativeMousePosition = RenderingStage.getRelativeMousePositionFromScreenMousePosition(mousePosition);
        
        Point offset = new Point((int) Math.round(instance.getWidth() * dragRelativePosition.x), (int) Math.round(instance.getHeight() * dragRelativePosition.y));
        Point anchor = new Point((int) Math.round(instance.getWidth() * instance.getAnchor().x), (int) Math.round(instance.getHeight() * instance.getAnchor().y));

        instance.setPosition(anchor.x - offset.x + relativeMousePosition.x, anchor.y - offset.y + relativeMousePosition.y);
    }
    
    public static synchronized void renderDrag(Graphics2D g) {
        if (instance == null) {
            return;
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
        
        cancelDrag();
    }
    
    private static synchronized void cancelDrag() {
        instance = null;
        dragRelativePosition = null;
    }
}
