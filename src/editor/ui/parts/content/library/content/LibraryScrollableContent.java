package editor.ui.parts.content.library.content;

import editor.ui.parts.content.library.content.parts.LibraryEntry;
import editor.ui.parts.content.library.content.parts.ScrollablePanel;
import editor.ui.parts.content.library.content.parts.WrapLayout;

import javax.swing.*;
import java.awt.*;

public class LibraryScrollableContent extends ScrollablePanel {
    public LibraryScrollableContent() {
        setBorder(null);
        WrapLayout wrapLayout = new WrapLayout();
        setLayout(wrapLayout);
    }
}
