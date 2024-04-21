package Presentation;

import java.io.IOException;

/**
 * <p>An Presentation.Accessor makes it possible to read and write data
 * for a presentation.</p>
 * <p>Non-abstract subclasses should implement the load and save methods.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.7 2024/01/25 Jordy Neef
 */

public abstract class Accessor
{
    public Accessor()
    {
    }

    public static Accessor getDemoAccessor()
    {
        return new DemoPresentation();
    }

    abstract public void loadFile(Presentation presentation, String fileName) throws IOException;

    abstract public void saveFile(Presentation presentation, String fileName) throws IOException;
}
