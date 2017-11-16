package editor.ui.parts.content.library.content.importer;

import editor.Application;
import editor.logic.types.assets.ImageAsset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            InputStream inputStream = new FileInputStream(selectedFile);
            DigestInputStream dis = new DigestInputStream(inputStream, messageDigest);
            BufferedImage image = ImageIO.read(dis);
            if (image == null) {
                return;
            }
            byte[] digest = messageDigest.digest();
            String fileChecksum = new String(digest);

            if (ApplicationLibrary.hasImageChecksum(fileChecksum)) {
                return;
            }

            File newFileLocation = new File(LibraryContent.getImageFolderLocation(), name + ".png");
            ImageIO.write(image, "png", newFileLocation);

            LibraryContent.scanAssets();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
