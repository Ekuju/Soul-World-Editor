package editor.ui.parts.content.library.content.parts;

import javax.swing.*;
import java.awt.*;

public class LibraryEntry extends JPanel {
    public static final int SIZE = 64;

    public LibraryEntry() {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        // setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(0xa2a2a2));

        setPreferredSize(new Dimension(64, 64));
    }
}
