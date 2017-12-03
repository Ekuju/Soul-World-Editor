package editor.logic.stage.parts.scenes;

import editor.ui.parts.content.library.ApplicationLibrary;

import java.io.File;

/**
 * Created by Trent on 11/29/2017.
 */
public class GlobalScene extends Scene {
    public static final String EXTENSION = "sgs";

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

    @Override
    public File getFile() {
        return new File(ApplicationLibrary.projectFolder, getFileName() + getExtension());
    }
}
