package editor.logic.stage.parts.scenes;

/**
 * Created by Trent on 11/29/2017.
 */
public class StaticEntityScene extends Scene {
    private static final String EXTENSION = "sse";

    public StaticEntityScene(String filename) {
        super(filename);
    }

    @Override
    public void close() {

    }

    @Override
    public boolean acceptsImageAssets() {
        return true;
    }

    @Override
    public boolean acceptsStaticEntityAssets() {
        return false;
    }

    @Override
    public String getExtension() {
        return EXTENSION;
    }
}