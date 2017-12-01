package editor.logic.stage.parts.instances;

import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.stageproperties.stage.RenderingStage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

/**
 * Created by Trent on 11/27/2017.
 */
public class ImageAssetInstance extends AssetInstance {
    private String checksum;

    private Point2D.Double imageScale;
    private BufferedImage image;

    public ImageAssetInstance(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public void render(Graphics2D g) {
        if (!getScale().equals(imageScale)) {
            generateImage();
        }

        Point position = getPosition();
        Point anchorPosition = getAnchorPosition();
        g.drawImage(image, position.x - anchorPosition.x, position.y - anchorPosition.y, null);
    }

    @Override
    public int getWidth() {
        return (int) Math.round(ApplicationLibrary.getImage(checksum).getWidth() * getScale().x);
    }

    @Override
    public int getHeight() {
        return (int) Math.round(ApplicationLibrary.getImage(checksum).getHeight() * getScale().y);
    }

    private BufferedImage getImage(String checksum) {
        return ApplicationLibrary.getImage(checksum);
    }

    private void generateImage() {
        image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        imageScale = getScale();

        BufferedImage actualImage = getImage(checksum);
        Image scaledImage = actualImage.getScaledInstance(getWidth(), getHeight(), BufferedImage.SCALE_FAST);

        Graphics g = image.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);
    }
}
