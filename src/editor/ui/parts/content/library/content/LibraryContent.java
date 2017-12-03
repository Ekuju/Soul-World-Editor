package editor.ui.parts.content.library.content;

import editor.logic.stage.parts.scenes.StaticEntityScene;
import editor.logic.types.assets.BehavioralEntityAsset;
import editor.logic.types.assets.ImageAsset;
import editor.logic.types.assets.StaticEntityAsset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;
import utils.Focusable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class LibraryContent extends JTabbedPane {
    private static LibraryContentFolders<ImageAsset> imagePane;
    private static LibraryContentFolders<StaticEntityAsset> staticEntityPane;
    private static LibraryContentFolders<BehavioralEntityAsset> behavioralEntityPane;

    private static File imagesFolder;
    private static File staticEntitiesFolder;
    private static File behavioralEntitiesFolder;
    
    private static LibraryContentFolders selectedLibraryContentFolder = null;

    public LibraryContent() {
        setBorder(null);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        Focusable.enable(this);

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

        staticEntityPane = new LibraryContentFolders<StaticEntityAsset>(staticEntitiesFolder, StaticEntityScene.EXTENSION);
        addTab("Static Entities", staticEntityPane);

        behavioralEntitiesFolder = new File(ApplicationLibrary.projectFolder, "behavioral_entities");
        if (!behavioralEntitiesFolder.exists()) {
            behavioralEntitiesFolder = ApplicationLibrary.createFolder(behavioralEntitiesFolder);
        }

        behavioralEntityPane = new LibraryContentFolders<BehavioralEntityAsset>(behavioralEntitiesFolder, "sbe");
        addTab("Behavioral Entities", behavioralEntityPane);
        
        selectedLibraryContentFolder = imagePane;
        setSelectedIndex(0);
        
        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentIndex = getSelectedIndex();
                Component selectedComponent = getSelectedComponent();
                if (!(selectedComponent instanceof LibraryContentFolders)) {
                    System.err.println("Selected component is not an instance of LibraryContentFolder. " + selectedComponent);
                    return;
                }

                selectedLibraryContentFolder = (LibraryContentFolders) getSelectedComponent();
            }
        });
    }

    public static File getImageFolderLocation() {
        return imagePane.getActiveFolderLocation();
    }

    public static File getStaticEntityFolderLocation() {
        return staticEntityPane.getActiveFolderLocation();
    }

    public static File getBehavioralEntityFolderLocation() {
        return behavioralEntityPane.getActiveFolderLocation();
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
    
    public static synchronized LibraryEntry getSelectedAsset() {
        return selectedLibraryContentFolder.getSelectedAsset();
    }
}
