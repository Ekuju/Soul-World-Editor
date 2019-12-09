package editor.logic.types.assets;

import editor.logic.interfaces.FinishedLoadingListener;
import editor.logic.stage.parts.instances.AssetInstance;
import editor.ui.parts.content.library.ApplicationLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public abstract class Asset {
    private String name;
    private String checksum;
    private File file;

    private ArrayList<FinishedLoadingListener> finishedLoadingListeners;

    public Asset(String name, String checksum, File file) {
        this.name = name;
        this.checksum = checksum;
        this.file = file;

        this.finishedLoadingListeners = new ArrayList<FinishedLoadingListener>();

        generateBufferedImage();
    }

    public String getAssetName() {
        return name;
    }

    public void setAssetName(String name) {
        this.name = name;
    }

    public BufferedImage getPreviewImage(int maxWidth, int maxHeight) {
        BufferedImage image = getBufferedImage();
        int width = image.getWidth();
        int height = image.getHeight();

        double scaleX = (double) width / maxWidth;
        double scaleY = (double) height / maxHeight;
        double scale = 1.0 / Math.max(scaleX, scaleY);

        int scaledWidth = (int) Math.round(width * scale);
        int scaledHeight = (int) Math.round(height * scale);

        BufferedImage returnImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Image scaledInstance = image.getScaledInstance(returnImage.getWidth(), returnImage.getHeight(), BufferedImage.SCALE_SMOOTH);

        Graphics g = returnImage.getGraphics();
        g.drawImage(scaledInstance, 0, 0, null);

        return returnImage;
    }

    public String getChecksum() {
        return checksum;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void onFinishedLoading(FinishedLoadingListener listener) {
        listener.finishedLoading(this);
    }

    /**
     * Returns the buffered image that is generated from the file when the asset is created.
     */
    public abstract void render(Graphics2D g);

    public abstract AssetInstance getAssetInstance();

    protected abstract void generateBufferedImage();

    protected abstract BufferedImage getBufferedImage();
}
