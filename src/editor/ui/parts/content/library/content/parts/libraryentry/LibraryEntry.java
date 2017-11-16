package editor.ui.parts.content.library.content.parts.libraryentry;

import editor.Application;
import editor.logic.types.assets.Asset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.importer.AssetFileImporter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LibraryEntry<T extends Asset> extends JPanel {
    public static final int SIZE = 64;

    private T entry;

    public LibraryEntry(T entry) {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        // setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(new Color(0xa2a2a2));

        setPreferredSize(new Dimension(64, 64));

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        this.entry = entry;

        PreviewPanel previewPanel = new PreviewPanel(entry.getPreviewImage());
        add(previewPanel, BorderLayout.PAGE_START);

        JTextField nameField = new JTextField();
        add(nameField, BorderLayout.PAGE_END);

        nameField.setPreferredSize(new Dimension(SIZE, SIZE / 4 - 2));
        nameField.setMaximumSize(new Dimension(SIZE, SIZE / 4 - 2));
        nameField.setFont(new Font("Arial", Font.PLAIN, 8));

        nameField.setBorder(null);

        nameField.setText(entry.getAssetName());
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newFileName = nameField.getText();
                if (!newFileName.matches(AssetFileImporter.VALID_FILE_NAME_REGEX)) {
                    System.err.println("File name is not valid: " + newFileName);
                    return;
                }

                String[] extensionParts = entry.getFile().getName().split("\\.");
                if (extensionParts.length <= 1) {
                    System.out.println("Existing file does not have a file type");
                    nameField.setText(entry.getAssetName());

                    nameField.transferFocus();
                    return;
                }

                String extension = extensionParts[extensionParts.length - 1].toLowerCase();

                File newFile = new File(entry.getFile().getParentFile(), newFileName + "." + extension);

                boolean success = entry.getFile().renameTo(newFile);
                if (!success) {
                    System.err.println("Could not rename file " + entry.getFile().getName() + " to " + newFile.getName());
                    nameField.setText(entry.getAssetName());

                    nameField.transferFocus();
                    return;
                }

                entry.setFile(newFile);
                entry.setAssetName(newFileName);

                nameField.transferFocus();

                LibraryContent.scanAssets();
            }
        });
    }

    public String getFileChecksum() {
        return entry.getChecksum();
    }
}
