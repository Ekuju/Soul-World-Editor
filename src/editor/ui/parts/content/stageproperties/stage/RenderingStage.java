package editor.ui.parts.content.stageproperties.stage;

import editor.logic.Settings;
import editor.logic.stage.Scene;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;
import utils.Focusable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class RenderingStage extends JComponent implements Runnable {
    private static final Color BACKGROUND = new Color(0xf0f0f0);
    private static final Point absoluteMousePosition = new Point();
    private static final Point relativeMousePosition = new Point();
    
    // this is the position of the top left corner of the rendering area
    private static Point2D.Double realPosition;
    private static Point position;
    
    public RenderingStage() {
        setBackground(BACKGROUND);
        setFocusable(true);
        
        realPosition = new Point2D.Double();
        position = new Point();
        
        Focusable.enable(this);
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                absoluteMousePosition.x = e.getPoint().x;
                absoluteMousePosition.y = e.getPoint().y;

                relativeMousePosition.x = e.getPoint().x + position.x;
                relativeMousePosition.y = e.getPoint().y + position.y;
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                absoluteMousePosition.x = e.getPoint().x;
                absoluteMousePosition.y = e.getPoint().y;
                
                relativeMousePosition.x = e.getPoint().x + position.x;
                relativeMousePosition.y = e.getPoint().y + position.y;
            }
        });
        
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
        int gridSize = Settings.getGridRenderSize();
        Point start = new Point(-position.x % gridSize, -position.y % gridSize);
        for (int y = start.y; y < height; y += gridSize) {
            g.fillRect(0, y, width, 1);
        }
        for (int x = start.x; x < width; x += gridSize) {
            g.fillRect(x, 0, 1, height);
        }
        
        // shift the rendering area and pass it into the real rendering method
        g.translate(-position.x, -position.y);
        render((Graphics2D) g);
    }
    
    private void render(Graphics2D g) {
        g.setColor(Color.RED);
        
        DraggingManager.renderDrag(g);
    }
    
    private void update() {
        
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
    
    public static void setScene(Scene scene) {
        
    }
    
    public static Point getAbsoluteMousePosition() {
        return absoluteMousePosition;
    }
    
    public static Point getRelativeMousePosition() {
        return relativeMousePosition;
    }
    
    public static Point getRelativeMousePositionFromScreenMousePosition(Point screenMousePosition) {
        Point stagePosition = StageCombinedPanel.renderingStage.getLocationOnScreen();
        Point relativeMousePosition = new Point(screenMousePosition.x - stagePosition.x, screenMousePosition.y - stagePosition.y);
        
        return new Point(relativeMousePosition.x + position.x, relativeMousePosition.y + position.y);
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
    
    private static Point2D.Double getStagePosition() {
        return new Point2D.Double(realPosition.x, realPosition.y);
    }

    private static void setStagePosition(double x, double y) {
        realPosition.x = x;
        realPosition.y = y;
        position.x = (int) Math.round(x);
        position.y = (int) Math.round(y);
    }
}
