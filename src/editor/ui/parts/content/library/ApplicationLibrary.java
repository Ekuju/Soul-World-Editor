package editor.ui.parts.content.library;

import editor.ui.parts.content.library.buttons.LibraryControls;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.parts.LibraryEntry;

import javax.swing.*;
import java.awt.*;

public class ApplicationLibrary extends JPanel {
    private LibraryControls libraryControls;
    private LibraryContent libraryContent;

    public ApplicationLibrary() {
        setBackground(new Color(0xe2e2e2));

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        libraryControls = new LibraryControls();
        add(libraryControls, BorderLayout.LINE_START);

        libraryContent = new LibraryContent();
        add(libraryContent, BorderLayout.CENTER);

        LibraryContent.addLibraryEntry(new LibraryEntry());
        LibraryContent.addLibraryEntry(new LibraryEntry());

        for (int i = 0; i < 40; i++) {
            LibraryContent.addLibraryEntry(new LibraryEntry());
        }
    }
}
