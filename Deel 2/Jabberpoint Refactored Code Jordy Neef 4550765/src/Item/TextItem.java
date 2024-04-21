package Item;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

import Slide.Slide;
import Style.Style;

/**
 * <p>A text item.</p>
 * <p>A text item has drawing capabilities.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem
{
    private final String text;

    //A textItem of int level with text string
    public TextItem(int level, String string)
    {
        super(level);
        this.text = string;
    }

    //Returns the text
    public String getText()
    {
        return this.text == null ? "" : this.text;
    }

    //Returns the AttributedString for the Item
    public AttributedString getAttributedString(Style style, float scale)
    {
        AttributedString attrStr = new AttributedString(getText());
        attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, this.text.length());

        return attrStr;
    }

    //Returns the bounding box of an Item
    public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle)
    {
        List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
        int xsize = 0, ysize = (int) (myStyle.leading * scale);

        for (TextLayout layout : layouts)
        {
            Rectangle2D bounds = layout.getBounds();

            if (bounds.getWidth() > xsize)
            {
                xsize = (int) bounds.getWidth();
            }

            if (bounds.getHeight() > 0)
            {
                ysize += (int) bounds.getHeight();
            }

            ysize += (int) (layout.getLeading() + layout.getDescent());
        }

        return new Rectangle((int) (myStyle.indent * scale), 0, xsize, ysize);
    }

    //Draws the item
    public void draw(int x, int y, float scale, Graphics graphics, Style myStyle, ImageObserver observer)
    {
        if (text == null || text.isEmpty())
        {
            return;
        }

        List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
        Point point = new Point(x + (int) (myStyle.indent * scale), y + (int) (myStyle.leading * scale));
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setColor(myStyle.color);

        for (TextLayout layout : layouts)
        {
            point.y += (int) layout.getAscent();
            layout.draw(graphics2D, point.x, point.y);
            point.y += (int) layout.getDescent();
        }
    }

    private List<TextLayout> getLayouts(Graphics graphics, Style style, float scale)
    {
        List<TextLayout> layouts = new ArrayList<TextLayout>();
        AttributedString attrStr = getAttributedString(style, scale);
        Graphics2D graphics2D = (Graphics2D) graphics;
        FontRenderContext fontRenderContext = graphics2D.getFontRenderContext();
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), fontRenderContext);
        float wrappingWidth = (Slide.WIDTH - style.indent) * scale;

        while (measurer.getPosition() < getText().length())
        {
            TextLayout layout = measurer.nextLayout(wrappingWidth);
            layouts.add(layout);
        }

        return layouts;
    }

    public String toString()
    {
        return "Item.TextItem[" + this.getLevel() + "," + this.getText() + "]";
    }
}
