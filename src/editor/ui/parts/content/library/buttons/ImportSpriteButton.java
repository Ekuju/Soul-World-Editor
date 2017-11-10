package editor.ui.parts.content.library.buttons;

import editor.ui.parts.content.library.buttons.parts.CustomButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ImportSpriteButton extends CustomButton {
    public ImportSpriteButton() {
        try {
            Image rotateToolImage = ImageIO.read(new File("assets/add.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(rotateToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Import Sprite");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
