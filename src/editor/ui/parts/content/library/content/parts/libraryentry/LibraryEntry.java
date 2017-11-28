package editor.ui.parts.content.library.content.parts.libraryentry;

import editor.Application;
import editor.logic.types.assets.Asset;
import editor.ui.parts.content.library.ApplicationLibrary;
import editor.ui.parts.content.library.content.LibraryContent;
import editor.ui.parts.content.library.content.LibraryContentPane;
import editor.ui.parts.content.library.content.importer.AssetFileImporter;
import editor.ui.parts.content.stageproperties.StageCombinedPanel;
import editor.ui.parts.content.stageproperties.stage.DraggingManager;
import utils.Focusable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;

public class LibraryEntry<T extends Asset> extends JPanel {
    public static final int SIZE = 64;
    public static final Color BACKGROUND = new Color(0xc2c2c2);
    private static final double MINIMUM_DRAG_DISTANCE = 4;

    private T entry;
    private boolean focused;
    
    private boolean mouseDown;
    private Point mouseDownPosition;
    private Point2D.Double mousePosition;
    private boolean dragging;

    public LibraryEntry(T entry) {
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        setBackground(BACKGROUND);

        setPreferredSize(new Dimension(64, 64));

        BorderLayout borderLayout = new BorderLayout();
        setLayout(borderLayout);
        
        setFocusable(true);

        this.entry = entry;

        PreviewPanel previewPanel = new PreviewPanel(entry);
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
        
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                double x = (double) e.getPoint().x / PreviewPanel.MAX_WIDTH;
                double y = (double) e.getPoint().y / PreviewPanel.MAX_HEIGHT;
                mouseDownPosition = new Point(e.getPoint());
                mousePosition = new Point2D.Double(x, y);
                
                mouseDown = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endDrag(e.getLocationOnScreen());
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (mouseDown && !dragging) {
                    beginDrag();
                }
            }
        });
        
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point point = e.getPoint();

                if (mouseDown && !dragging) {
                    double dx = mouseDownPosition.x - point.x;
                    double dy = mouseDownPosition.y - point.y;

                    if (dx * dx + dy * dy >= MINIMUM_DRAG_DISTANCE * MINIMUM_DRAG_DISTANCE) {
                        beginDrag();
                    }
                }
                
                DraggingManager.updateDrag(e.getLocationOnScreen());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                
                if (mouseDown && !dragging) {
                    double dx = mouseDownPosition.x - point.x;
                    double dy = mouseDownPosition.y - point.y;
                    
                    if (dx * dx + dy * dy >= MINIMUM_DRAG_DISTANCE * MINIMUM_DRAG_DISTANCE) {
                        beginDrag();
                    }
                }
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
    
    private void beginDrag() {
        dragging = true;
        DraggingManager.beginDrag(entry.getAssetInstance(), mousePosition);
    }
    
    private void endDrag(Point screenPosition) {
        mouseDown = false;
        dragging = false;
        DraggingManager.endDrag(screenPosition);
    }
}
