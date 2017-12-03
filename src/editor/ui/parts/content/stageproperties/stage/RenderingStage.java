package editor.ui.parts.content.stageproperties.stage;

import editor.logic.Settings;
import editor.logic.stage.parts.scenes.GlobalScene;
import editor.logic.stage.parts.scenes.Scene;
import editor.ui.listeners.StaticListener;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;
import utils.Focusable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

public class RenderingStage extends JComponent implements Runnable {
    private static final Color BACKGROUND = new Color(0xf0f0f0);

    // this is the position of the top left corner of the rendering area
    private static Point2D.Double realPosition;
    private static Point position;

    private static LinkedList<Scene> sceneStack;

    private static double scale = 1.0;

    public RenderingStage() {
        setBackground(BACKGROUND);
        setFocusable(true);

        realPosition = new Point2D.Double();
        position = new Point();

        Focusable.enable(this, false);

        StageListener stageListener = new StageListener();
        addKeyListener(stageListener);
        addMouseWheelListener(stageListener);

        sceneStack = new LinkedList<Scene>();
        pushScene(new GlobalScene());

        new Thread(this).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        // render the background
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, width, height);

        // render the grid lines
        g.setColor(Color.LIGHT_GRAY);
        double gridSize = Settings.getGridRenderSize();
        Point2D.Double start = new Point2D.Double(-position.x % gridSize, -position.y % gridSize);
        for (double y = start.y * scale; y < height; y += gridSize * scale) {
            g.fillRect(0, (int) Math.round(y), width, 1);
        }
        for (double x = start.x * scale; x < width; x += gridSize * scale) {
            g.fillRect((int) Math.round(x), 0, 1, height);
        }

        // shift the rendering area and pass it into the real rendering method
        ((Graphics2D) g).scale(scale, scale);
        g.translate(-position.x, -position.y);
        render((Graphics2D) g);
    }

    private void render(Graphics2D g) {
        getScene().render(g);

        InstanceDraggingManager.renderDrag(g);
    }

    private void update() {
        StageDraggingManager.updateDrag();
    }

    @Override
    public void run() {
        while (true) {
            update();
            repaint();

            try {
                Thread.sleep(16L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Scene getScene() {
        return RenderingStage.sceneStack.getLast();
    }

    public static void pushScene(Scene scene) {
        RenderingStage.sceneStack.add(scene);
    }

    public static void popScene() {
        if (sceneStack.size() > 1) {
            sceneStack.removeLast().save();
        }
    }

    public static Point getRelativeMousePosition() {
        Point screenMousePosition = StaticListener.getMousePosition();

        Point stagePosition = StageCombinedPanel.renderingStage.getLocationOnScreen();
        Point relativeMousePosition = new Point(screenMousePosition.x - stagePosition.x, screenMousePosition.y - stagePosition.y);

        return new Point((int) Math.round(relativeMousePosition.x / scale + realPosition.x), (int) Math.round(relativeMousePosition.y / scale + realPosition.y));
    }

    /**
     * This method must be used to place everything in the rendering stage.
     * @param x The x coordinate that the object would like to be placed at.
     * @param y The y coordinate that the object would like to be placed at.
     * @return The valid point that the object should be placed at.
     */
    public static Point getValidPosition(double x, double y) {
        double snap = Settings.isSnapToGrid() ? Settings.getGridRenderSize() : 2.0;
        return new Point((int) (Math.round(x / snap) * snap), (int) (Math.round(y / snap) * snap));
    }

    public static Point2D.Double getStagePosition() {
        return new Point2D.Double(realPosition.x, realPosition.y);
    }

    public static void setStagePosition(double x, double y) {
        realPosition.x = x;
        realPosition.y = y;
        position.x = (int) Math.round(x);
        position.y = (int) Math.round(y);
    }

    public static Point2D.Double transform(double x, double y) {
        Point2D.Double returnPoint = new Point2D.Double(x, y);
        returnPoint.x -= realPosition.x;
        returnPoint.y -= realPosition.y;
        returnPoint.x *= scale;
        returnPoint.y *= scale;

        return returnPoint;
    }

    public static double getScale() {
        return scale;
    }

    static void zoom(double amount) {
        Point screenMousePosition = StaticListener.getMousePosition();
        Point stagePosition = StageCombinedPanel.renderingStage.getLocationOnScreen();
        Point relativeMousePosition = new Point(screenMousePosition.x - stagePosition.x, screenMousePosition.y - stagePosition.y);
        Point2D.Double mousePosition = new Point2D.Double(relativeMousePosition.x / scale + realPosition.x, relativeMousePosition.y / scale + realPosition.y);

        Dimension stageDimensions = StageCombinedPanel.renderingStage.getSize();
        Point2D.Double mousePercent = new Point2D.Double((double) relativeMousePosition.x / stageDimensions.width, (double) relativeMousePosition.y / stageDimensions.height);

        if (amount < 0) {
            scale *= 1.2 * Math.abs(amount);
        } else {
            scale /= 1.2 * amount;
        }

        Point2D.Double stageScaledDimensions = new Point2D.Double(stageDimensions.width / scale, stageDimensions.height / scale);
        Point2D.Double newPosition = new Point2D.Double(
                mousePosition.x - stageScaledDimensions.x * mousePercent.x,
                mousePosition.y - stageScaledDimensions.y * mousePercent.y
        );
        setStagePosition(newPosition.x, newPosition.y);
    }
}
