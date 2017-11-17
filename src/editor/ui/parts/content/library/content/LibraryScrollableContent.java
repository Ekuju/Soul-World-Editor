package editor.ui.parts.content.library.content;

import editor.logic.types.assets.Asset;
import editor.ui.parts.content.library.content.parts.ScrollablePanel;
import editor.ui.parts.content.library.content.parts.WrapLayout;
import utils.Focusable;

import java.awt.*;

public class LibraryScrollableContent<T extends Asset> extends ScrollablePanel {
    public LibraryScrollableContent() {
        setBorder(null);
        WrapLayout wrapLayout = new WrapLayout();
        setLayout(wrapLayout);

        Focusable.enable(this);
    }
}
