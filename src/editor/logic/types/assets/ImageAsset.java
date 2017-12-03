package editor.logic.types.assets;

import com.google.gson.Gson;
import editor.logic.stage.parts.instances.AssetInstance;
import editor.logic.stage.parts.instances.ImageAssetInstance;
import editor.logic.stage.parts.scenes.gson.GSONSceneModel;
import editor.ui.parts.content.library.ApplicationLibrary;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageAsset extends Asset {
    private BufferedImage bufferedImage;

    public ImageAsset(String name, String checksum, File file) {
        super(name, checksum, file);
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(bufferedImage, 0, 0, null);
    }

    @Override
    public AssetInstance getAssetInstance() {
        return new ImageAssetInstance(getChecksum());
    }

    @Override
    protected void generateBufferedImage() {
        try {
            bufferedImage = ImageIO.read(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
