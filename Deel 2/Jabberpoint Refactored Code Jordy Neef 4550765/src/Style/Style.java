package Style;

import java.awt.Color;
import java.awt.Font;

/**
 * <p>Style.Style stands for Indent, Color, Font and Leading.</p>
 * <p>The link between a style number and a item level is hard-linked:
 * in Slide.Slide the style is grabbed for an item
 * with a style number the same as the item level.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style
{
    private static Style[] styles; // de styles
    private static final String FONTNAME = "Helvetica";
    public int indent;
    private final Font font;
    private final int fontSize;
    public Color color;
    public int leading;

    public Style(int indent, Color color, int points, int leading)
    {
        this.indent = indent;
        this.color = color;
        this.font = new Font(FONTNAME, Font.BOLD, this.fontSize = points);
        this.leading = leading;
    }

    public static void createStyles()
    {
        styles = new Style[5];

        // De styles zijn vast ingecodeerd.
        styles[0] = new Style(0, Color.red, 48, 20);    // style voor item-level 0
        styles[1] = new Style(20, Color.blue, 40, 10);    // style voor item-level 1
        styles[2] = new Style(50, Color.black, 36, 10);    // style voor item-level 2
        styles[3] = new Style(70, Color.black, 30, 10);    // style voor item-level 3
        styles[4] = new Style(90, Color.black, 24, 10);    // style voor item-level 4
    }

    public static Style getStyle(int level)
    {
        if (level >= styles.length)
        {
            level = styles.length - 1;
        }

        return styles[level];
    }

    public String toString()
    {
        return "[" + this.indent + "," + this.color + "; " + this.fontSize + " on " + this.leading + "]";
    }

    public Font getFont(float scale)
    {
        return font.deriveFont(this.fontSize * scale);
    }
}