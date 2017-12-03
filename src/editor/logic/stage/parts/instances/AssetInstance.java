package editor.logic.stage.parts.instances;

import editor.logic.Settings;
import editor.ui.parts.content.stageproperties.stage.RenderingStage;

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

    private Color tint;

    public AssetInstance() {
        position = new Point();
        scale = new Point2D.Double(2.0, 2.0);
        anchor = new Point2D.Double(0.5, 0.5);
        tint = Color.WHITE;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(int x, int y) {
        position.x = x;
        position.y = y;
    }

    public Point getScreenPosition() {
        Point2D.Double transformedPosition = RenderingStage.transform(position.x, position.y);

        return new Point((int) Math.round(transformedPosition.x), (int) Math.round(transformedPosition.y));
    }

    public Point2D.Double getScale() {
        return scale;
    }

    public void setScale(double x, double y) {
        this.scale.x = x;
        this.scale.y = y;
    }

    public Point2D.Double getAnchor() {
        return anchor;
    }

    public Point getAnchorPosition() {
        return new Point((int) Math.round(getWidth() * getAnchor().x), (int) Math.round(getHeight() * getAnchor().y));
    }

    public void setAnchor(double x, double y) {
        this.anchor.x = x;
        this.anchor.y = y;
    }

    public int getScreenWidth() {
        return (int) Math.round(RenderingStage.getScale() * getWidth());
    }

    public int getScreenHeight() {
        return (int) Math.round(RenderingStage.getScale() * getWidth());
    }

    public Color getTint() {
        return tint;
    }

    /**
     * Sets the tint for a 1 time use that is reset back tom white after render.
     */
    public void tint(Color tint) {
        this.tint = tint;
    }

    public abstract void render(Graphics2D g);

    /**
     * @return The width of the asset instance scaled by the asset instance scale x value.
     */
    public abstract int getWidth();

    /**
     * @return The height of the asset instance scaled by the asset instance scale y value.
     */
    public abstract int getHeight();
}
