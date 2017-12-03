package editor.ui.parts.content.library.buttons;

import editor.logic.stage.parts.scenes.StaticEntityScene;
import editor.ui.parts.content.library.buttons.parts.CustomButton;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.stageproperties.stage.RenderingStage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class CreateStaticEntityButton extends CustomButton {
    public CreateStaticEntityButton() {
        try {
            Image rotateToolImage = ImageIO.read(new File("assets/entity.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(rotateToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Create Static Entity");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String staticEntityName = JOptionPane.showInputDialog("Enter the unique name of the new static entity:");
                if (staticEntityName == null || staticEntityName.length() == 0) {
                    System.err.println("Tried to create a static entity with no name.");
                    return;
                }

                StaticEntityScene staticEntityScene = new StaticEntityScene(staticEntityName);

                File file = staticEntityScene.getFile();
                if (file.exists()) {
                    System.err.println("Static entity with the unique name " + staticEntityName + " already exists in this location.");
                    return;
                }

                RenderingStage.pushScene(new StaticEntityScene(staticEntityName));
            }
        });
    }
}
