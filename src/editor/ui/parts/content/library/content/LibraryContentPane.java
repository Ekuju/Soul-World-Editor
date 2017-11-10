package editor.ui.parts.content.library.content;

import editor.ui.parts.content.library.content.parts.LibraryEntry;
import editor.ui.parts.content.library.content.parts.ScrollablePanel;

import javax.swing.*;
import java.awt.*;

public class LibraryContentPane extends JScrollPane {
    private LibraryScrollableContent flowingContentPanel;

    public LibraryContentPane() {
        setBorder(null);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getViewport().setBorder(null);
        getViewport().setBackground(new Color(0xeeeeee));

        flowingContentPanel = new LibraryScrollableContent();
        flowingContentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        add(flowingContentPanel);

        setViewportView(flowingContentPanel);
    }

    public void addLibraryEntry(LibraryEntry libraryEntry) {
        flowingContentPanel.add(libraryEntry);
        flowingContentPanel.updateUI();
        updateUI();
    }

    public void removeLibraryEntry(LibraryEntry libraryEntry) {
        flowingContentPanel.remove(libraryEntry);
        flowingContentPanel.updateUI();
    }
}
