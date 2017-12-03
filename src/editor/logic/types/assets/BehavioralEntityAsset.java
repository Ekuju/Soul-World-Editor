package editor.logic.types.assets;

import editor.logic.stage.parts.instances.AssetInstance;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BehavioralEntityAsset extends Asset {
    public BehavioralEntityAsset(String name, String checksum, File file) {
        super(name, checksum, file);
    }

    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public AssetInstance getAssetInstance() {
        return null;
    }

    @Override
    protected void generateBufferedImage() {

    }

    @Override
    protected BufferedImage getBufferedImage() {
        return null;
    }
}
