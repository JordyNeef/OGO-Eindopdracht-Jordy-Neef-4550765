package Item;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.imageio.ImageIO;

import java.io.IOException;

import Style.Style;


/**
 * <p>The class for a Bitmap item</p>
 * <p>Bitmap items are responsible for drawing themselves.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem extends SlideItem
{
    private BufferedImage bufferedImage;
    private String imageName;
    protected static final String FILE = "File ";
    protected static final String NOTFOUND = " not found";

    //level indicates the item-level; name indicates the name of the file with the image
    public BitmapItem(int level, String name)
    {
        super(level);
        this.readImage(name);
    }

    //read the image with the given name
    private void readImage(String name)
    {
        this.imageName = name;
        try
        {
            this.bufferedImage = ImageIO.read(new File(this.imageName));
        } catch (IOException e)
        {
            System.err.println(FILE + this.imageName + NOTFOUND);
        }
    }

    //Returns the filename of the image
    public String getName()
    {
        return this.imageName;
    }

    //Returns the bounding box of the image
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle)
    {
        return new Rectangle((int) (myStyle.indent * scale), 0,
                (int) (this.bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.leading * scale)) +
                        (int) (this.bufferedImage.getHeight(observer) * scale));
    }

    //Draws the image
    public void draw(int x, int y, float scale, Graphics graphics, Style myStyle, ImageObserver observer)
    {
        int width = x + (int) (myStyle.indent * scale);
        int height = y + (int) (myStyle.leading * scale);

        graphics.drawImage(this.bufferedImage, width, height,
                (int) (this.bufferedImage.getWidth(observer) * scale),
                (int) (this.bufferedImage.getHeight(observer) * scale), observer);
    }

    public String toString()
    {
        return "Item.BitmapItem[" + getLevel() + "," + this.imageName + "]";
    }
}
