package editor.logic.stage.parts;

import editor.logic.Settings;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Trent on 11/27/2017.
 */
public abstract class AssetInstance {
    private Point position;
    private Point2D.Double scale;
    private Point2D.Double anchor;
    
    public AssetInstance() {
        position = new Point();
        scale = new Point2D.Double(2.0, 2.0);
        anchor = new Point2D.Double(0.5, 0.5);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public Point2D.Double getScale() {
        return scale;
    }

    public void setScale(Point2D.Double scale) {
        this.scale = scale;
    }
    
    public Point getAnchorPosition() {
        return new Point((int) Math.round(getWidth() * getAnchor().x), (int) Math.round(getHeight() * getAnchor().y));
    }

    public Point2D.Double getAnchor() {
        return anchor;
    }
    
    public void setAnchor(Point2D.Double anchor) {
        this.anchor = anchor;
    }
    
    public abstract void render(Graphics2D g);
    
    public abstract int getWidth();
    
    public abstract int getHeight();
}
