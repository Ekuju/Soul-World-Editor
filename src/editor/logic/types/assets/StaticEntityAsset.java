package editor.logic.types.assets;

import com.google.gson.Gson;
import editor.logic.stage.parts.instances.AssetInstance;
import editor.logic.stage.parts.instances.ImageAssetInstance;
import editor.logic.stage.parts.scenes.gson.GSONSceneModel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class StaticEntityAsset extends Asset {
    private static final Gson GSON = new Gson();

    private BufferedImage bufferedImage;

    public StaticEntityAsset(String name, String checksum, File file) {
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
        Point topLeft = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point bottomRight = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

        ArrayList<ImageAssetInstance> imageAssetInstances = new ArrayList<ImageAssetInstance>();

        try {
            String content = new String(Files.readAllBytes(getFile().toPath()));
            GSONSceneModel sceneModel = GSON.fromJson(content, GSONSceneModel.class);

            imageAssetInstances.addAll(sceneModel.getImageAssetInstances());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (ImageAssetInstance imageAssetInstance : imageAssetInstances) {
            Point position = imageAssetInstance.getPosition();
            Point anchor = imageAssetInstance.getAnchorPosition();

            topLeft.x = Math.min(topLeft.x, position.x - anchor.x);
            topLeft.y = Math.min(topLeft.y, position.y - anchor.y);

            bottomRight.x = Math.max(topLeft.x, position.x + anchor.x);
            bottomRight.y = Math.max(topLeft.y, position.y + anchor.y);
        }

        bufferedImage = new BufferedImage(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

        g.translate(-topLeft.x, -topLeft.y);
        for (ImageAssetInstance imageAssetInstance : imageAssetInstances) {
            imageAssetInstance.render(g);
        }
    }

    @Override
    protected BufferedImage getBufferedImage() {
        return bufferedImage;
    }
}
