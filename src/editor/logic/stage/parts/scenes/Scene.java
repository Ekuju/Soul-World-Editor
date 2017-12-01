package editor.logic.stage.parts.scenes;

import editor.logic.stage.parts.instances.AssetInstance;
import editor.logic.stage.parts.instances.ImageAssetInstance;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trent on 11/27/2017.
 */
public abstract class Scene {
    private AssetInstance selectedInstance;
    private List<ImageAssetInstance> imageInstances;
    private String filename;

    public Scene(String filename) {
        this.filename = filename;

        selectedInstance = null;
        imageInstances = new ArrayList<ImageAssetInstance>();
    }

    public void render(Graphics2D g) {
        for (ImageAssetInstance imageAssetInstance : imageInstances) {
            imageAssetInstance.render(g);
        }
    }

    public void mouseDown(Point point) {

    }

    public void mouseUp(Point point) {

    }

    public void mouseMove(Point point) {

    }

    /**
     * Save to the given scene.
     */
    public void close() {

    }

    public abstract boolean acceptsImageAssets();

    public abstract boolean acceptsStaticEntityAssets();

    public abstract String getExtension();
}
