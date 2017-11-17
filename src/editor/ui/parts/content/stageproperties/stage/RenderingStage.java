package editor.ui.parts.content.stageproperties.stage;

import editor.logic.Settings;
import utils.Focusable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class RenderingStage extends JComponent implements Runnable {
    private static final Color BACKGROUND = new Color(0xf0f0f0);
    
    // this is the position of the top left corner of the rendering area
    private static Point2D.Double realPosition;
    private static Point position;
    
    public RenderingStage() {
        setBackground(BACKGROUND);
        setFocusable(true);
        
        realPosition = new Point2D.Double();
        position = new Point();
        
        Focusable.enable(this);
        
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
    
    public static Point2D.Double getPosition() {
        return new Point2D.Double(realPosition.x, realPosition.y);
    }
    
    public static void setPosition(double x, double y) {
        realPosition.x = x;
        realPosition.y = y;
        position.x = (int) Math.round(x);
        position.y = (int) Math.round(y);
    }
}
