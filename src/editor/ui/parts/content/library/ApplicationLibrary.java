package editor.ui.parts.content.library;

import com.sun.deploy.util.StringUtils;
import editor.logic.Settings;
import editor.ui.parts.content.library.buttons.LibraryControls;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.importer.AssetFileImporter;
import editor.ui.parts.menu.filemenu.ApplicationFileMenuSettingsItem;
import utils.SoulIO;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ApplicationLibrary extends JPanel {
    public static final String[] ASSET_EXTENSIONS = new String[]{
            "png",
            "sse",
            "sbe"
    };
    
    private LibraryControls libraryControls;
    private LibraryContent libraryContent;

    private static Set<String> checksumSet = new HashSet<String>();
    private static Map<String, File> checksumToFileMap = new HashMap<String, File>();
    private static Set<File> knownFileSet = new HashSet<File>();

    public static File projectFolder;

    public ApplicationLibrary() {
        setBackground(new Color(0xe2e2e2));

        projectFolder = new File(Settings.getProjectFolder());
        if (!projectFolder.exists()) {
            ApplicationFileMenuSettingsItem.showDialog();
            projectFolder = new File(Settings.getProjectFolder());
            projectFolder = createFolder(projectFolder);
        }

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);

        libraryControls = new LibraryControls();
        add(libraryControls, BorderLayout.LINE_START);

        libraryContent = new LibraryContent();
        add(libraryContent, BorderLayout.CENTER);
        
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    scanLibrary();

                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static File createFolder(File folder) {
        boolean success = folder.mkdirs();
        try {
            if (!success) {
                System.err.println("Could not create folder at " + folder.getCanonicalPath());
                folder = null;
            }

            System.out.println("Created folder at: " + folder.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return folder;
    }
    
    public static synchronized void scanLibrary() {
        scanForFileChecksums();
        
        LibraryContent.scanLibrary();
    }

    public static synchronized boolean hasFileChecksum(String checksum) {
        System.out.println("||| Checking: " + checksum);
        return checksumSet.contains(checksum);
    }

    private static synchronized void addFileChecksum(String checksum, File file) {
        System.out.println("||| Adding: " + checksum);
        checksumSet.add(checksum);
        checksumToFileMap.put(checksum, file);
    }

    private static synchronized void removeFileChecksum(String checksum) {
        checksumSet.remove(checksum);
        checksumToFileMap.remove(checksum);
    }

    public synchronized static void scanForFileChecksums() {
        // remove checksums for files that don't exist anymore
        for (String checksum : new HashSet<String>(checksumSet)) {
            File file = checksumToFileMap.get(checksum);
            
            if (!file.exists()) {
                checksumSet.remove(checksum);
                checksumToFileMap.remove(checksum);
            }
        }
        
        // find all files
        Set<File> existingFiles = getAllValidFiles();
        System.out.println(existingFiles);
        
        // determine which files are new
        for (File existingFile : existingFiles) {
            if (!knownFileSet.contains(existingFile)) {
                String checksum = getChecksum(existingFile);
                if (hasFileChecksum(checksum)) {
                    System.err.println("Found duplicate file in the project folder. Attempting to delete.");
                    boolean success = existingFile.delete();
                    if (!success) {
                        System.err.println("Could not delete duplicate file " + existingFile.getName() + ". Will attempt to delete on exit.");
                        existingFile.deleteOnExit();
                    } else {
                        System.err.println("Deleted.");
                    }
                    
                    continue;
                }

                checksumSet.add(checksum);
                knownFileSet.add(existingFile);
                checksumToFileMap.put(checksum, existingFile);
            }
        }
        
        for (String checksum : checksumSet) {
            System.out.println(checksum);
        }
        System.out.println();
    }
    
    private static Set<File> getAllValidFiles() {
        Set<File> fileSet = new HashSet<File>();
        getAllValidFiles(projectFolder, fileSet);
        
        return fileSet;
    }
    
    private static void getAllValidFiles(File currentFolder, Set<File> validFileSet) {
        Set<String> acceptedExtensionSet = new HashSet<String>();
        Collections.addAll(acceptedExtensionSet, ASSET_EXTENSIONS);
        
        File[] files = currentFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                File lockFile = new File(pathname.getParentFile(), pathname.getName() + ".lock");
                if (lockFile.exists()) {
                    return false;
                }
                
                String[] extensionParts = pathname.getName().split("\\.");
                if (extensionParts.length <= 1) {
                    return false;
                }

                String extension = extensionParts[extensionParts.length - 1].toLowerCase();
                return acceptedExtensionSet.contains(extension);
            }
        });
        if (files != null) {
            Collections.addAll(validFileSet, files);
        }
        
        File[] folders = currentFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (folders != null) {
            for (File folder : folders) {
                getAllValidFiles(folder, validFileSet);
            }
        }
    }
    
    public static String getChecksum(File file) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = SoulIO.convert(file);
            if (bytes == null) {
                return null;
            }
            
            InputStream inputStream = new ByteArrayInputStream(bytes);
            DigestInputStream dis = new DigestInputStream(inputStream, messageDigest);
            DataInputStream dataInputStream = new DataInputStream(dis);
            
            byte[] byteBuffer = new byte[dataInputStream.available()];
            dataInputStream.readFully(byteBuffer);
            
            byte[] digest = messageDigest.digest();
            
            return new String(digest);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
