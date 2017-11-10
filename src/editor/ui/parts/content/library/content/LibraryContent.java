package editor.ui.parts.content.library.content;

import editor.ui.parts.content.library.content.parts.LibraryEntry;

import javax.swing.*;
import java.awt.*;

public class LibraryContent extends JTabbedPane {
    private static LibraryContentPane contentPane;

    public LibraryContent() {
        setBorder(null);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        contentPane = new LibraryContentPane();
        addTab("Pane", contentPane);

        LibraryContentPane test2Pane = new LibraryContentPane();
        addTab("Test 2", test2Pane);
    }

    public static void addLibraryEntry(LibraryEntry libraryEntry) {
        contentPane.addLibraryEntry(libraryEntry);
    }

    public static void removeLibraryEntry(LibraryEntry libraryEntry) {
        contentPane.removeLibraryEntry(libraryEntry);
    }
}
