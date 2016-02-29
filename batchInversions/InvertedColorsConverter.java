
/**
 * Write a description of InvertedColorsConverter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class InvertedColorsConverter {
    public ImageResource makeInverted(ImageResource inImage) {
        ImageResource outImage = new ImageResource(inImage.getWidth(), inImage.getHeight());
        for(Pixel pixel : outImage.pixels()) {
            Pixel inPixel = inImage.getPixel(pixel.getX(), pixel.getY());
            int newRed = 255 - inPixel.getRed();
            int newGreen = 255 - inPixel.getGreen();
            int newBlue = 255 - inPixel.getBlue();
            pixel.setRed(newRed);
            pixel.setGreen(newGreen);
            pixel.setBlue(newBlue);
        }
        return outImage;
    }
    public void invertImage() {
        ImageResource image = new ImageResource();
        ImageResource invertedImage = makeInverted(image);
        invertedImage.draw();
    }
}
