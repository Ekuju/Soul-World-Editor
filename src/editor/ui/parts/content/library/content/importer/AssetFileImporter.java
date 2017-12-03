package editor.ui.parts.content.library.content.importer;

import editor.Application;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.LibraryContent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class AssetFileImporter extends JFileChooser {
    public static final String VALID_FILE_NAME_REGEX = "[a-zA-Z0-9\\-_\\. ]+";
    private static final String[] ACCEPTED_FILE_TYPES = new String[]{
            "jpg",
            "jpeg",
            "gif",
            "png"
    };

    public AssetFileImporter() {
        FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter(
                "Images",
                ACCEPTED_FILE_TYPES
        );
        setFileFilter(extensionFilter);
    }

    public void open() {
        int value = showOpenDialog(Application.applicationFrame);
        if (value != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = getSelectedFile();
        String[] extensionParts = selectedFile.getName().split("\\.");
        if (extensionParts.length <= 1) {
            return;
        }

        String extension = extensionParts[extensionParts.length - 1].toLowerCase();
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < extensionParts.length - 1; i++) {
            nameBuilder.append(extensionParts[i]);
        }
        String name = nameBuilder.toString();

        boolean found = false;
        for (String acceptedExtension : ACCEPTED_FILE_TYPES) {
            if (acceptedExtension.toLowerCase().equals(extension)) {
                found = true;
                break;
            }
        }

        if (!found) {
            return;
        }

        // at this point we know the file we're loading is an acceptable image
        try {
            // validate the checksums so we know what files we can or can't add
            ApplicationLibrary.scanForFileChecksums();
            
            String checksum = ApplicationLibrary.getChecksum(selectedFile);
            if (ApplicationLibrary.hasFileChecksum(checksum)) {
                System.err.println("Image checksum already exists.");

                return;
            }
            
            BufferedImage image = ImageIO.read(new FileInputStream(selectedFile));
            if (image == null) {
                return;
            }
            
            File newFileLockLocation = new File(LibraryContent.getImageFolderLocation(), "." + name + ".png.lock");
            FileOutputStream fileOutputStream = new FileOutputStream(newFileLockLocation);
            fileOutputStream.flush();
            fileOutputStream.close();

            File newFileLocation = new File(LibraryContent.getImageFolderLocation(), name + ".png");
            ImageIO.write(image, "png", newFileLocation);

            // add the checksum because we successfully wrote the image
            ApplicationLibrary.scanForFileChecksums();
            
            boolean success = newFileLockLocation.delete();
            if (!success) {
                System.err.println("Could not delete lock file " + newFileLockLocation.getName() + ". Will try to delete it on save.");
                newFileLockLocation.deleteOnExit();
            }

            ApplicationLibrary.scanLibrary();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
