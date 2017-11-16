package editor.ui.parts.content.library.content;

import editor.logic.types.assets.Asset;
import editor.logic.types.assets.ImageAsset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;
import editor.ui.parts.content.library.content.parts.ScrollablePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

public class LibraryContentPane<T extends Asset> extends JScrollPane {
    private LibraryScrollableContent<T> flowingContentPanel;

    private File directory;
    private String acceptedExtension;

    private Set<String> knownFileNameSet;

    public LibraryContentPane(File directory, String acceptedExtension) {
        this.directory = directory;
        this.acceptedExtension = acceptedExtension.toLowerCase();

        setBorder(null);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getViewport().setBorder(null);
        getViewport().setBackground(new Color(0xeeeeee));

        flowingContentPanel = new LibraryScrollableContent<T>();
        flowingContentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        add(flowingContentPanel);

        setViewportView(flowingContentPanel);

        knownFileNameSet = new HashSet<String>();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    scanAssets();

                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public File getFolderLocation() {
        return directory;
    }

    public void scanAssets() {
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (!pathname.isFile()) {
                    return false;
                }

                String[] extensionParts = pathname.getName().split("\\.");
                if (extensionParts.length <= 1) {
                    return false;
                }

                String extension = extensionParts[extensionParts.length - 1].toLowerCase();
                return extension.equals(acceptedExtension);
            }
        });

        if (files == null) {
            System.err.println("Scanned for files and received null.");
            return;
        }

        for (File file : files) {
            String fileName = file.getName();

            if (!knownFileNameSet.contains(fileName)) {
                addLibraryEntry(getLibraryEntryFromFile(file));
            }
        }
    }

    private LibraryEntry<T> getLibraryEntryFromFile(File file) {
        String[] extensionParts = file.getName().split("\\.");
        if (extensionParts.length <= 1) {
            return null;
        }

        String extension = extensionParts[extensionParts.length - 1].toLowerCase();
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < extensionParts.length - 1; i++) {
            nameBuilder.append(extensionParts[i]);
        }
        String name = nameBuilder.toString();

        try {
            switch (extension) {
                case "png": {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    InputStream inputStream = new FileInputStream(file);
                    DigestInputStream dis = new DigestInputStream(inputStream, messageDigest);
                    BufferedImage image = ImageIO.read(dis);
                    inputStream.close();
                    dis.close();
                    if (image == null) {
                        return null;
                    }

                    byte[] digest = messageDigest.digest();
                    String checksum = new String(digest);

                    T asset = (T) new ImageAsset(name, image, checksum, file);
                    return new LibraryEntry<T>(asset);
                }

                default: {
                    System.err.println("Tried to read a file type that was not supported." + extension);
                }
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addLibraryEntry(LibraryEntry<T> libraryEntry) {
        if (ApplicationLibrary.hasImageChecksum(libraryEntry.getFileChecksum())) {
            return;
        }
        ApplicationLibrary.addImageChecksum(libraryEntry.getFileChecksum());

        flowingContentPanel.add(libraryEntry);
    }

    private void removeLibraryEntry(LibraryEntry<T> libraryEntry) {
        if (!ApplicationLibrary.hasImageChecksum(libraryEntry.getFileChecksum())) {
            return;
        }
        ApplicationLibrary.removeImageChecksum(libraryEntry.getFileChecksum());

        flowingContentPanel.remove(libraryEntry);
    }
}
