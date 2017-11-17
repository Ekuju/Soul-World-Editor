package editor.ui.parts.content.library.buttons;

import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.buttons.parts.CustomButton;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class DeleteButton extends CustomButton {
    public DeleteButton() {
        try {
            Image rotateToolImage = ImageIO.read(new File("assets/delete.png"))
                    .getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(rotateToolImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setToolTipText("Delete");

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LibraryEntry selectedLibraryEntry = LibraryContent.getSelectedAsset();
                if (selectedLibraryEntry == null) {
                    System.out.println("Could not get selected asset.");
                    return;
                }
                
                File assetFile = selectedLibraryEntry.getFile();
                boolean success = assetFile.delete();
                if (!success) {
                    System.err.println("Could not delete asset file " + assetFile.getName() + ". Will try again on exit.");
                    assetFile.deleteOnExit();
                    
                    return;
                }

                LibraryContent.scanAssets();
            }
        });
    }
}
