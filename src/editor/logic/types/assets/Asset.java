package editor.logic.types.assets;

import editor.logic.stage.parts.AssetInstance;

import java.awt.image.BufferedImage;
import java.io.File;

public interface Asset {
    public String getAssetName();
    public void setAssetName(String name);
    public BufferedImage getPreviewImage(int maxWidth, int maxHeight);
    public String getChecksum();
    public File getFile();
    public void setFile(File file);
    public AssetInstance getAssetInstance();
}
