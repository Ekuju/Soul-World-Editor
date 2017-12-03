package editor.logic.types.assets;

import editor.logic.stage.parts.instances.AssetInstance;

import java.awt.image.BufferedImage;
import java.io.File;

public class BehavioralEntityAsset extends Asset {
    private BufferedImage bufferedImage;

    public BehavioralEntityAsset(String name, String checksum, File file) {
        super(name, checksum, file);
    }

    @Override
    public BufferedImage getBufferedImage() {
        return null;
    }

    @Override
    public AssetInstance getAssetInstance() {
        return null;
    }

    @Override
    protected void generateBufferedImage() {

    }
}
