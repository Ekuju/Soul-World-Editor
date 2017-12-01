package editor.logic.stage.parts.scenes;

/**
 * Created by Trent on 11/29/2017.
 */
public class GlobalScene extends Scene {
    private static final String EXTENSION = "sgs";

    public GlobalScene() {
        super("world");
    }

    @Override
    public boolean acceptsImageAssets() {
        return true;
    }

    @Override
    public boolean acceptsStaticEntityAssets() {
        return true;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}
