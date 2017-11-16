package editor.logic.types.assets;

import java.awt.image.BufferedImage;
import java.io.File;

public class ImageAsset implements Asset {
    private String name;
    private BufferedImage image;
    private String checksum;
    private File file;

    public ImageAsset(String name, BufferedImage image, String checksum, File file) {
        this.name = name;
        this.image = image;
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
    public BufferedImage getPreviewImage() {
        return image;
    }

    @Override
    public void setPreviewImage(BufferedImage image) {
        this.image = image;
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
}
