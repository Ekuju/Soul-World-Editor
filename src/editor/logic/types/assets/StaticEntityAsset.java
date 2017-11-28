package editor.logic.types.assets;

import editor.logic.stage.parts.AssetInstance;

import java.awt.image.BufferedImage;
import java.io.File;

public class StaticEntityAsset implements Asset {
    @Override
    public String getAssetName() {
        return null;
    }

    @Override
    public void setAssetName(String name) {

    }

    @Override
    public BufferedImage getPreviewImage(int maxWidth, int maxHeight) {
        return null;
    }

    @Override
    public String getChecksum() {
        return null;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void setFile(File file) {

    }

    @Override
    public AssetInstance getAssetInstance() {
        return null;
    }
}
