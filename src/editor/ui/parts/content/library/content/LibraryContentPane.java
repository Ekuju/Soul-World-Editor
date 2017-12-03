package editor.ui.parts.content.library.content;

import editor.logic.stage.parts.scenes.StaticEntityScene;
import editor.logic.types.assets.Asset;
import editor.logic.types.assets.ImageAsset;
import editor.logic.types.assets.StaticEntityAsset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.parts.libraryentry.LibraryEntry;
import editor.ui.parts.content.library.content.parts.ScrollablePanel;
import utils.Focusable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LibraryContentPane<T extends Asset> extends JScrollPane {
    private LibraryScrollableContent<T> flowingContentPanel;

    private File directory;
    private String acceptedExtension;

    private Set<String> knownFileNameSet;
    private Map<String, LibraryEntry<T>> fileNameToLibraryEntryMap;

    public LibraryContentPane(File directory, String acceptedExtension) {
        this.directory = directory;
        this.acceptedExtension = acceptedExtension.toLowerCase();

        setBorder(null);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        getViewport().setBorder(null);
        getViewport().setBackground(new Color(0xeeeeee));

        Focusable.enable(this);

        flowingContentPanel = new LibraryScrollableContent<T>();
        flowingContentPanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);
        add(flowingContentPanel);

        setViewportView(flowingContentPanel);

        knownFileNameSet = new HashSet<String>();
        fileNameToLibraryEntryMap = new HashMap<String, LibraryEntry<T>>();
    }

    public File getFolderLocation() {
        return directory;
    }

    public synchronized void scanAssets() {
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
        
        Set<String> existingFileNameSet = new HashSet<String>();
        for (File file : files) {
            existingFileNameSet.add(file.getName());
        }

        for (File file : files) {
            String fileName = file.getName();
            File lockFile = new File(file.getParentFile(), "." + fileName + ".lock");
            if (lockFile.exists()) {
                continue;
            }

            if (!knownFileNameSet.contains(fileName)) {
                knownFileNameSet.add(fileName);
                addLibraryEntry(getLibraryEntryFromFile(file));
            }
        }
        
        for (String fileName : new HashSet<String>(knownFileNameSet)) {
            if (!existingFileNameSet.contains(fileName)) {
                removeLibraryEntry(fileName);
            }
        }
    }
    
    public synchronized LibraryEntry<T> getSelectedAsset() {
        for (String fileName : knownFileNameSet) {
            LibraryEntry<T> libraryEntry = fileNameToLibraryEntryMap.get(fileName);
            
            if (libraryEntry.isSelected()) {
                return libraryEntry;
            }
        }
        
        return null;
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

        switch (extension) {
            case "png": {
                String checksum = ApplicationLibrary.getChecksum(file);
                
                T asset = (T) new ImageAsset(name, checksum, file);
                LibraryEntry<T> libraryEntry = new LibraryEntry<T>(asset);
                
                fileNameToLibraryEntryMap.put(file.getName(), libraryEntry);
                
                return libraryEntry;
            }

            case StaticEntityScene.EXTENSION: {
                String checksum = ApplicationLibrary.getChecksum(file);

                T asset = (T) new StaticEntityAsset(name, checksum, file);
                LibraryEntry<T> libraryEntry = new LibraryEntry<T>(asset);

                fileNameToLibraryEntryMap.put(file.getName(), libraryEntry);

                return libraryEntry;
            }

            default: {
                System.err.println("Tried to read a file type that was not supported. " + name + "." + extension);
            }
        }

        return null;
    }

    private void addLibraryEntry(LibraryEntry<T> libraryEntry) {
        if (libraryEntry == null) {
            return;
        }

        flowingContentPanel.add(libraryEntry);
        flowingContentPanel.updateUI();
    }

    private void removeLibraryEntry(String name) {
        knownFileNameSet.remove(name);
        LibraryEntry<T> libraryEntry = fileNameToLibraryEntryMap.remove(name);
        
        flowingContentPanel.remove(libraryEntry);
        flowingContentPanel.updateUI();
    }
}
