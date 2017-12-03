package editor.logic.stage.parts.scenes;

import com.google.gson.Gson;
import editor.logic.stage.parts.instances.AssetInstance;
import editor.logic.stage.parts.instances.ImageAssetInstance;
import editor.logic.stage.parts.scenes.gson.GSONSceneModel;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trent on 11/27/2017.
 */
public abstract class Scene {
    private static final Gson GSON = new Gson();

    private AssetInstance selectedInstance;
    private List<ImageAssetInstance> imageInstances;
    private String fileName;

    public Scene(String fileName) {
        this.fileName = fileName;

        selectedInstance = null;
        imageInstances = new ArrayList<ImageAssetInstance>();

        load();
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

    public void addAssetInstance(ImageAssetInstance imageAssetInstance) {
        if (!acceptsImageAssets()) {
            System.err.println("Tried to add an image asset instance but this type is not accepted.");
            return;
        }

        imageInstances.add(imageAssetInstance);
    }

    public void save() {
        GSONSceneModel sceneModel = new GSONSceneModel(imageInstances);
        String json = GSON.toJson(sceneModel);

        File file = getFile();
        try {
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(json.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    private void load() {
        File file = getFile();

        if (!file.exists()) {
            return;
        }

        // load the file
        try {
            String contents = new String(Files.readAllBytes(file.toPath()));
            GSONSceneModel sceneModel = GSON.fromJson(contents, GSONSceneModel.class);
            imageInstances.addAll(sceneModel.getImageAssetInstances());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract boolean acceptsImageAssets();

    public abstract boolean acceptsStaticEntityAssets();

    public abstract String getExtension();

    public abstract File getFile();
}
