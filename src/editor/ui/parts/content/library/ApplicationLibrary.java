package editor.ui.parts.content.library;

import editor.logic.Settings;
import editor.logic.types.assets.StaticEntityAsset;
import editor.ui.parts.content.library.buttons.LibraryControls;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;
import editor.ui.parts.menu.filemenu.ApplicationFileMenuSettingsItem;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ApplicationLibrary extends JPanel {
    private LibraryControls libraryControls;
    private LibraryContent libraryContent;

    private static Set<String> imageChecksumSet = new HashSet<String>();

    public static File projectFolder;

    public ApplicationLibrary() {
        setBackground(new Color(0xe2e2e2));

        projectFolder = new File(Settings.getProjectFolder());
        if (!projectFolder.exists()) {
            ApplicationFileMenuSettingsItem.showDialog();
            projectFolder = new File(Settings.getProjectFolder());
            projectFolder = createFolder(projectFolder);
        }

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        libraryControls = new LibraryControls();
        add(libraryControls, BorderLayout.LINE_START);

        libraryContent = new LibraryContent();
        add(libraryContent, BorderLayout.CENTER);
    }

    public static File createFolder(File folder) {
        boolean success = folder.mkdirs();
        try {
            if (!success) {
                System.err.println("Could not create folder at " + folder.getCanonicalPath());
                folder = null;
            }

            System.out.println("Created folder at: " + folder.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return folder;
    }

    public synchronized static boolean hasImageChecksum(String imageChecksum) {
        return imageChecksumSet.contains(imageChecksum);
    }

    public synchronized static void addImageChecksum(String imageChecksum) {
        imageChecksumSet.add(imageChecksum);
    }

    public synchronized static void removeImageChecksum(String imageChecksum) {
        imageChecksumSet.remove(imageChecksum);
    }
}
