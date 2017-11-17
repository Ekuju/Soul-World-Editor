package editor.ui.parts.content.library.content.parts.libraryentry;

import editor.Application;
import editor.logic.types.assets.Asset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.LibraryContentPane;
import editor.ui.parts.content.library.content.importer.AssetFileImporter;
import utils.Focusable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LibraryEntry<T extends Asset> extends JPanel {
    public static final int SIZE = 64;
    private static final Color BACKGROUND = new Color(0xc2c2c2);
    private static final Color BACKGROUND_SELECTED = new Color(0xe2e2e2);

    private T entry;
    
    private boolean focused;

    public LibraryEntry(T entry) {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        setBackground(BACKGROUND);

        setPreferredSize(new Dimension(64, 64));

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        setFocusable(true);

        this.entry = entry;

        PreviewPanel previewPanel = new PreviewPanel(entry.getPreviewImage());
        add(previewPanel, BorderLayout.PAGE_START);

        JTextField nameField = new JTextField();
        add(nameField, BorderLayout.PAGE_END);

        nameField.setPreferredSize(new Dimension(SIZE, SIZE / 4 - 2));
        nameField.setMaximumSize(new Dimension(SIZE, SIZE / 4 - 2));
        nameField.setFont(new Font("Arial", Font.PLAIN, 10));

        nameField.setBorder(null);
        nameField.setBackground(BACKGROUND);

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

                    Application.applicationFrame.requestFocus();
                    return;
                }
                
                if (entry.getFile().getName().equals(newFileName)) {
                    return;
                }

                String extension = extensionParts[extensionParts.length - 1].toLowerCase();

                File newFile = new File(entry.getFile().getParentFile(), newFileName + "." + extension);

                boolean success = entry.getFile().renameTo(newFile);
                if (!success) {
                    System.err.println("Could not rename file " + entry.getFile().getName() + " to " + newFile.getName());
                    nameField.setText(entry.getAssetName());

                    Application.applicationFrame.requestFocus();
                    return;
                }

                entry.setFile(newFile);
                entry.setAssetName(newFileName);

                Application.applicationFrame.requestFocus();

                LibraryContent.scanAssets();
            }
        });
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                nameField.setBackground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                nameField.setBackground(BACKGROUND);
            }
        });
        
        Focusable.enable(this);
        
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBackground(Color.DARK_GRAY);
                nameField.setBackground(Color.DARK_GRAY);

                focused = true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBackground(BACKGROUND);
                nameField.setBackground(BACKGROUND);

                focused = false;
            }
        });
    }

    public String getFileChecksum() {
        return entry.getChecksum();
    }
    
    public File getFile() {
        return entry.getFile();
    }
    
    public boolean isSelected() {
        return focused;
    }
}
