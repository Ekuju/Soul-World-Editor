package editor.ui.parts.content.library.buttons;

import editor.ui.parts.content.library.content.importer.AssetFileImporter;

import javax.swing.*;
import java.awt.*;

public class LibraryControls extends JPanel {
    public LibraryControls() {
        setBackground(new Color(0xe2e2e2));

        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        ImportSpriteButton importSpriteButton = new ImportSpriteButton();
        add(importSpriteButton);

        add(Box.createRigidArea(new Dimension(0,8)));

        DeleteButton deleteButton = new DeleteButton();
        add(deleteButton);

        add(Box.createRigidArea(new Dimension(0,8)));

        CreateStaticEntityButton createStaticEntityButton = new CreateStaticEntityButton();
        add(createStaticEntityButton);

        add(Box.createRigidArea(new Dimension(0,8)));

        CreateBehavioralEntityButton createBehavioralEntityButton = new CreateBehavioralEntityButton();
        add(createBehavioralEntityButton);
    }
}
