package editor.logic.stage.parts.instances;

import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.stageproperties.stage.RenderingStage;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

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

        Color tint = getTint();
        float[] tintData = new float[]{tint.getRed() / 255f, tint.getGreen() / 255f, tint.getBlue() / 255f, 1f};

//        g.setComposite(new Composite() {
//            @Override
//            public CompositeContext createContext(ColorModel srcColorModel, ColorModel dstColorModel, RenderingHints hints) {
//                return new CompositeContext() {
//                    @Override
//                    public void dispose() {
//
//                    }
//
//                    @Override
//                    public void compose(Raster src, Raster dstIn, WritableRaster dstOut) {
//                        for (int y = 0; y < src.getHeight(); y++) {
//                            for (int x = 0; x < src.getWidth(); x++) {
//                                float[] srcData = new float[4];
//                                src.getPixel(x, y, srcData);
//
////                                float[] dstData = new float[]{
////                                        (srcData[0] + tintData[0]) / 2f,
////                                        (srcData[1] + tintData[1]) / 2f,
////                                        (srcData[2] + tintData[2]) / 2f,
////                                        srcData[3] * tintData[3]
////                                };
//                                float[] dstData = new float[]{
//                                        srcData[0] * tintData[0],
//                                        srcData[1] * tintData[1],
//                                        srcData[2] * tintData[2],
//                                        srcData[3] * tintData[3]
//                                };
//
//                                dstOut.setPixel(x, y, dstData);
//                            }
//                        }
//                    }
//                };
//            }
//        });

        // reset the tint color after each render
        tint(Color.WHITE);

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

    public String getChecksum() {
        return checksum;
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
