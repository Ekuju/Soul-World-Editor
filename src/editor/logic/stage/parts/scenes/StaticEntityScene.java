package editor.logic.stage.parts.scenes;

import editor.ui.parts.content.library.content.LibraryContent;

import java.io.File;

/**
 * Created by Trent on 11/29/2017.
 */
public class StaticEntityScene extends Scene {
    public static final String EXTENSION = "sse";

    public StaticEntityScene(String filename) {
        super(filename);
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
        return new File(LibraryContent.getStaticEntityFolderLocation(), getFileName() + "." + getExtension());
    }
}