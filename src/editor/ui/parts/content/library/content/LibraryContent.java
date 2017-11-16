package editor.ui.parts.content.library.content;

import editor.logic.types.assets.BehavioralEntityAsset;
import editor.logic.types.assets.ImageAsset;
import editor.logic.types.assets.StaticEntityAsset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class LibraryContent extends JTabbedPane {
    private static LibraryContentFolders<ImageAsset> imagePane;
    private static LibraryContentFolders<StaticEntityAsset> staticEntityPane;
    private static LibraryContentFolders<BehavioralEntityAsset> behavioralEntityPane;

    public static File imagesFolder;
    public static File staticEntitiesFolder;
    public static File behavioralEntitiesFolder;

    public LibraryContent() {
        setBorder(null);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        imagesFolder = new File(ApplicationLibrary.projectFolder, "images");
        if (!imagesFolder.exists()) {
            imagesFolder = ApplicationLibrary.createFolder(imagesFolder);
        }

        imagePane = new LibraryContentFolders<ImageAsset>(imagesFolder, "png");
        addTab("Images", imagePane);

        staticEntitiesFolder = new File(ApplicationLibrary.projectFolder, "static_entities");
        if (!staticEntitiesFolder.exists()) {
            staticEntitiesFolder = ApplicationLibrary.createFolder(staticEntitiesFolder);
        }

        staticEntityPane = new LibraryContentFolders<StaticEntityAsset>(staticEntitiesFolder, "sse");
        addTab("Static Entities", staticEntityPane);

        behavioralEntitiesFolder = new File(ApplicationLibrary.projectFolder, "behavioral_entities");
        if (!behavioralEntitiesFolder.exists()) {
            behavioralEntitiesFolder = ApplicationLibrary.createFolder(behavioralEntitiesFolder);
        }

        behavioralEntityPane = new LibraryContentFolders<BehavioralEntityAsset>(behavioralEntitiesFolder, "sbe");
        addTab("Behavioral Entities", behavioralEntityPane);
    }

    public static File getImageFolderLocation() {
        return imagePane.getActiveFolderLocation();
    }

    public static synchronized void scanLibrary() {
        scanFolders();
        scanAssets();
    }
    
    public static synchronized void scanFolders() {
        imagePane.scanFolders();
        staticEntityPane.scanFolders();
        behavioralEntityPane.scanFolders();
    }
    
    public static synchronized void scanAssets() {
        imagePane.scanAssets();
        staticEntityPane.scanAssets();
        behavioralEntityPane.scanAssets();
    }
}
