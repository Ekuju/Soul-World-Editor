package editor.ui.parts.content.library.content.parts.libraryentry;

import editor.logic.types.assets.Asset;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewPanel extends JComponent {
    public static final int MAX_WIDTH = LibraryEntry.SIZE;
    public static final int MAX_HEIGHT = LibraryEntry.SIZE / 4 * 3;
    
    private Asset asset;

    public PreviewPanel(Asset asset) {
        this.asset = asset;

        setPreferredSize(new Dimension(MAX_WIDTH, MAX_HEIGHT));
    }

    @Override
    public void paintComponent(Graphics g) {
        BufferedImage previewImage = asset.getPreviewImage(MAX_WIDTH, MAX_HEIGHT);
        
        int width = previewImage.getWidth();
        int height = previewImage.getHeight();

        int paddingX = (MAX_WIDTH - width) / 2;
        int paddingY = (MAX_HEIGHT - height) / 2;

        g.drawImage(
                previewImage,
                paddingX,
                paddingY,
                MAX_WIDTH - paddingX,
                MAX_HEIGHT - paddingY,
                0,
                0,
                width,
                height,
                null,
                null
        );

        g.setColor(Color.BLACK);
    }
}
