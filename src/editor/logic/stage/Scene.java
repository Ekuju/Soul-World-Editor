package editor.logic.stage;

import editor.logic.stage.parts.ImageAssetInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trent on 11/27/2017.
 */
public class Scene {
    private List<ImageAssetInstance> imageInstances;
    
    public Scene() {
        imageInstances = new ArrayList<ImageAssetInstance>();
    }
}
