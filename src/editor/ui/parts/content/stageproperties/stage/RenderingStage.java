package editor.ui.parts.content.stageproperties.stage;

import editor.logic.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class RenderingStage extends JComponent implements Runnable {
    private static final Color BACKGROUND = new Color(0xf0f0f0);
    
    private static Point2D.Double realPosition;
    private static Point position;
    
    public RenderingStage() {
        setBackground(BACKGROUND);
        setFocusable(false);
        
        realPosition = new Point2D.Double();
        position = new Point();
        
        new Thread(this).start();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, width, height);
        
        g.setColor(Color.GRAY);
        int gridSize = Settings.getGridRenderSize();
        Point start = new Point(position.x % gridSize, position.y % gridSize);
        for (int y = start.y; y < height; y += gridSize) {
            g.fillRect(0, y, width, 1);
        }
        for (int x = start.x; x < width; x += gridSize) {
            g.fillRect(x, 0, 1, height);
        }
        
        render((Graphics2D) g);
    }

    @Override
    public void run() {
        while (true) {
            repaint();

            try {
                Thread.sleep(16L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void render(Graphics2D g) {
        
    }
    
    public static void setPosition(double x, double y) {
        realPosition.x = x;
        realPosition.y = y;
        position.x = (int) Math.round(x);
        position.y = (int) Math.round(y);
    }
}
