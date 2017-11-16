package editor.ui.parts.content.library.content;

import editor.logic.types.assets.Asset;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class LibraryContentFolders<T extends Asset> extends JTabbedPane {
    private static final String REGEX = "^[a-zA-Z0-9 ]+$";

    private ArrayList<LibraryContentPane<T>> folderList;
    private ArrayList<String> folderNameList;
    private int currentSelectedTabIndex;

    private Set<String> currentFolderNames;

    private boolean alreadyCreatedFolder = false;

    private File contentFolder;
    private String acceptedExtension;

    public LibraryContentFolders(File contentFolder, String acceptedExtension) {
        setBorder(null);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        this.contentFolder = contentFolder;
        this.acceptedExtension = acceptedExtension;

        LibraryContentPane<T> root = new LibraryContentPane<T>(contentFolder, acceptedExtension);
        addTab("/", root);

        addTab("+", null);

        currentFolderNames = new HashSet<String>();
        currentFolderNames.add("/");

        folderList = new ArrayList<LibraryContentPane<T>>();
        folderNameList = new ArrayList<String>();

        folderList.add(root);
        folderNameList.add("/");

        addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (alreadyCreatedFolder) {
                    return;
                }

                int currentIndex = getSelectedIndex();
                int tabCount = getTabCount();

                if (currentIndex == tabCount - 1) {
                    String newFolderName = JOptionPane.showInputDialog("Enter the name of the new folder:");
                    if (newFolderName == null || newFolderName.length() == 0) {
                        setSelectedIndex(currentSelectedTabIndex);
                        return;
                    }

                    if (!newFolderName.matches(REGEX)) {
                        setSelectedIndex(currentSelectedTabIndex);
                        return;
                    }

                    if (currentFolderNames.contains(newFolderName)) {
                        setSelectedIndex(currentSelectedTabIndex);
                        return;
                    }

                    alreadyCreatedFolder = true;
                    addFolder(newFolderName);
                    scanFolder();
                    alreadyCreatedFolder = false;

                    return;
                }

                currentSelectedTabIndex = currentIndex;
            }
        });

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    scanFolder();

                    try {
                        Thread.sleep(2000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public File getActiveFolderLocation() {
        return folderList.get(currentSelectedTabIndex).getFolderLocation();
    }

    public void scanAssets() {
        for (LibraryContentPane<T> contentPane : folderList) {
            contentPane.scanAssets();
        }
    }

    private void addFolder(String name) {
        File newFolder = new File(contentFolder, name);
        boolean success = newFolder.mkdir();
        if (!success) {
            System.err.println("Could not create new folder " + name);
        }
    }

    private synchronized void scanFolder() {
        File[] folders = contentFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if (folders == null) {
            System.err.println("The content folder returned null folders.");
            return;
        }

        // save the current selected component in case it finds new folders
        Component currentSelectedComponent = getComponentAt(currentSelectedTabIndex);

        // get the current folder names as a set
        Set<String> currentExistingFolderNames = new HashSet<String>();
        for (File folder : folders) {
            String name = folder.getName();
            currentExistingFolderNames.add(name);
        }

        // remove any folders that no longer exist
        for (String folderName : new HashSet<String>(currentFolderNames)) {
            if (folderName.equals("/")) {
                continue;
            }

            if (!currentExistingFolderNames.contains(folderName)) {
                int index = folderNameList.indexOf(folderName);
                if (index == currentSelectedTabIndex) {
                    currentSelectedTabIndex = 0;
                    setSelectedIndex(currentSelectedTabIndex);
                }

                currentFolderNames.remove(folderName);
                folderList.remove(index);
                folderNameList.remove(index);

                removeTabAt(index);
            }
        }

        // get the new folder names
        Set<String> newFolderNames = new HashSet<String>();
        for (File folder : folders) {
            String name = folder.getName();
            if (currentFolderNames.contains(name)) {
                continue;
            }

            newFolderNames.add(name);
        }

        for (String newFolderName : newFolderNames) {
            int index = 1;
            for (; index < folderNameList.size(); index++) {
                if (newFolderName.compareTo(folderNameList.get(index)) < 0) {
                    break;
                }
            }

            File newFolderPath = new File(contentFolder, newFolderName);

            LibraryContentPane<T> libraryContentPane = new LibraryContentPane<T>(newFolderPath, acceptedExtension);
            insertTab(newFolderName, null, libraryContentPane, null, index);

            currentFolderNames.add(newFolderName);
            folderList.add(index, libraryContentPane);
            folderNameList.add(index, newFolderName);
        }

        // get the current tab index again
        boolean found = false;
        for (int i = 0; i < getTabCount() - 1; i++) {
            if (getComponentAt(i) == currentSelectedComponent) {
                currentSelectedTabIndex = i;
                found = true;
                break;
            }
        }

        if (!found) {
            currentSelectedTabIndex = 0;
        }

        setSelectedIndex(currentSelectedTabIndex);
    }
}
