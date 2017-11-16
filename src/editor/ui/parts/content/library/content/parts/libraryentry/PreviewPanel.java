package editor.ui.parts.content.library.content.parts.libraryentry;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PreviewPanel extends JComponent {
    private BufferedImage image;
    private BufferedImage previewImage;

    public PreviewPanel() {
        this(new BufferedImage(LibraryEntry.SIZE, LibraryEntry.SIZE / 4 * 3, BufferedImage.TYPE_INT_ARGB));
    }

    public PreviewPanel(BufferedImage previewImage) {
        this.image = new BufferedImage(LibraryEntry.SIZE, LibraryEntry.SIZE / 4 * 3, BufferedImage.TYPE_INT_ARGB);

        if (previewImage == null) {
            previewImage = new BufferedImage(LibraryEntry.SIZE, LibraryEntry.SIZE / 4 * 3, BufferedImage.TYPE_INT_ARGB);
        }

        int width = previewImage.getWidth();
        int height = previewImage.getHeight();

        double percentWidth = (double) width / LibraryEntry.SIZE;
        double percentHeight = (double) height / (LibraryEntry.SIZE / 4 * 3);

        double scale = 1.0;
        if (percentWidth > percentHeight) {
            scale = 1.0 / percentWidth;
        } else {
            scale = 1.0 / percentHeight;
        }

        int newWidth = (int) Math.round(width * scale);
        int newHeight = (int) Math.round(height * scale);
        Image scaledImage = previewImage.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        this.previewImage = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = this.previewImage.getGraphics();
        g.drawImage(scaledImage, 0, 0, null);

        setPreferredSize(new Dimension(LibraryEntry.SIZE, LibraryEntry.SIZE / 4 * 3));
    }

    @Override
    public void paintComponent(Graphics g) {
        int width = previewImage.getWidth();
        int height = previewImage.getHeight();

        int paddingX = (LibraryEntry.SIZE - width) / 2;
        int paddingY = (LibraryEntry.SIZE / 4 * 3 - height) / 2;

        g.drawImage(
                previewImage,
                paddingX,
                paddingY,
                LibraryEntry.SIZE - paddingX,
                LibraryEntry.SIZE / 4 * 3 - paddingY,
                0,
                0,
                width,
                height,
                Color.DARK_GRAY,
                null
        );

        g.setColor(Color.BLACK);
    }
}
