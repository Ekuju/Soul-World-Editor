package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.file.Files;

/**
 * Created by Trent on 11/17/2017.
 */
public class SoulIO {
    public static byte[] convert(File file) {
        String[] extensionParts = file.getName().split("\\.");
        if (extensionParts.length <= 1) {
            System.err.println("File does not have a type.");

            return null;
        }

        String extension = extensionParts[extensionParts.length - 1].toLowerCase();
        
        switch (extension) {
            case "png": {
                try {
                    Image image = ImageIO.read(file);
                    WritableRaster raster = ((BufferedImage) image).getRaster();
                    DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
                    return data.getData();
                } catch (IOException e) {
                    System.err.println("Could not read file " + file.getPath());
                    e.printStackTrace();
                    
                    return null;
                }
            }
            
            default: {
                try {
                    return Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    System.err.println("Could not read file " + file.getPath());
                    e.printStackTrace();
                    
                    return null;
                }
            }
        }
    }
}
