package editor.logic.types.assets;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Asset {
    public String getAssetName();
    public void setAssetName(String name);
    public BufferedImage getPreviewImage();
    public void setPreviewImage(BufferedImage image);
    public String getChecksum();
    public File getFile();
    public void setFile(File file);
}
