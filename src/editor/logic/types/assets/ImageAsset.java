package editor.logic.types.assets;

import editor.logic.stage.parts.AssetInstance;
import editor.logic.stage.parts.ImageAssetInstance;
import editor.ui.parts.content.library.ApplicationLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageAsset implements Asset {
    private String name;
    private String checksum;
    private File file;

    public ImageAsset(String name, String checksum, File file) {
        this.name = name;
        this.checksum = checksum;
        this.file = file;
    }

    @Override
    public String getAssetName() {
        return name;
    }

    @Override
    public void setAssetName(String name) {

    }

    @Override
    public BufferedImage getPreviewImage(int maxWidth, int maxHeight) {
        BufferedImage image = ApplicationLibrary.getImage(checksum);
        int width = image.getWidth();
        int height = image.getHeight();

        double scaleX = (double) width / maxWidth;
        double scaleY = (double) height / maxHeight;
        double scale = 1.0 / Math.max(scaleX, scaleY);
        
        BufferedImage returnImage = new BufferedImage((int) Math.round(width * scale), (int) Math.round(height * scale), BufferedImage.TYPE_INT_ARGB);
        Image scaledInstance = image.getScaledInstance(returnImage.getWidth(), returnImage.getHeight(), BufferedImage.SCALE_SMOOTH);
        
        Graphics g = returnImage.getGraphics();
        g.drawImage(scaledInstance, 0, 0, null);
        
        return returnImage;
    }

    @Override
    public String getChecksum() {
        return checksum;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public AssetInstance getAssetInstance() {
        return new ImageAssetInstance(checksum);
    }
}
