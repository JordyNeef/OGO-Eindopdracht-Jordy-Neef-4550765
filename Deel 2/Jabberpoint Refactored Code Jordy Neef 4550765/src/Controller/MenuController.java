package Controller;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import Presentation.XMLAccessor;
import Presentation.Accessor;
import Presentation.Presentation;

/**
 * <p>The controller for the menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar
{

    private final Frame parent; //The frame, only used as parent for the Dialogs
    public Presentation presentation; //Commands are given to the presentation
    protected static final String ABOUT = "About";
    protected static final String FILE = "File";
    protected static final String EXIT = "Exit";
    protected static final String GOTO = "Go to";
    protected static final String HELP = "Help";
    protected static final String NEW = "New";
    protected static final String NEXT = "Next";
    protected static final String OPEN = "Open";
    protected static final String PAGENR = "Page number?";
    protected static final String PREV = "Prev";
    protected static final String SAVE = "Save";
    protected static final String VIEW = "View";

    protected static final String TESTFILE = "testPresentation.xml";
    protected static final String SAVEFILE = "savedPresentation.xml";

    protected static final String IOEX = "IO Exception: ";
    protected static final String LOADERR = "Load Error";
    protected static final String SAVEERR = "Save Error";

    public MenuController(Frame frame, Presentation pres)
    {
        this.parent = frame;
        this.presentation = pres;

        this.fileMenu();
        this.viewMenu();
        this.helpMenu();
    }

    private void fileMenu()
    {
        Menu fileMenu = new Menu(FILE);

        this.addOpenFileMenuItem(fileMenu);
        this.addNewFileMenuItem(fileMenu);
        this.addSaveFileMenuItem(fileMenu);
        this.addExitFileMenuItem(fileMenu);

        add(fileMenu);
    }

    private void viewMenu()
    {
        Menu viewMenu = new Menu(VIEW);

        this.addNextViewMenuItem(viewMenu);
        this.addPreviousViewMenuItem(viewMenu);
        this.addGotoViewMenuItem(viewMenu);

        add(viewMenu);
    }

    private void helpMenu()
    {
        Menu helpMenu = new Menu(HELP);

        this.addAboutHelpMenuItem(helpMenu);

        setHelpMenu(helpMenu); //Needed for portability (Motif, etc.).
    }

    private void addOpenFileMenuItem(Menu fileMenu)
    {
        var openFileMenuItem = mkMenuItem(OPEN);
        fileMenu.add(openFileMenuItem);

        openFileMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                presentation.clear();
                loadFile(TESTFILE, LOADERR, true);
                parent.repaint();
            }
        });
    }

    private void addNewFileMenuItem(Menu fileMenu)
    {
        var newFileMenuItem = mkMenuItem(NEW);
        fileMenu.add(newFileMenuItem);

        newFileMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                presentation.clear();
                parent.repaint();
            }
        });
    }

    private void addSaveFileMenuItem(Menu fileMenu)
    {
        var saveFileMenuItem = mkMenuItem(SAVE);
        fileMenu.add(saveFileMenuItem);

        saveFileMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                loadFile(SAVEFILE, SAVEERR, false);
            }
        });
    }

    private void addExitFileMenuItem(Menu fileMenu)
    {
        var exitFileMenuItem = mkMenuItem(EXIT);
        fileMenu.addSeparator();
        fileMenu.add(exitFileMenuItem);

        exitFileMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                presentation.exit(0);
            }
        });
    }

    private void addNextViewMenuItem(Menu viewMenu)
    {
        var nextViewMenuItem = mkMenuItem(NEXT);
        viewMenu.add(nextViewMenuItem);

        nextViewMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                presentation.nextSlide();
            }
        });

    }

    private void addPreviousViewMenuItem(Menu viewMenu)
    {
        var PreviousViewMenuItem = mkMenuItem(PREV);
        viewMenu.add(PreviousViewMenuItem);

        PreviousViewMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                presentation.prevSlide();
            }
        });
    }

    private void addGotoViewMenuItem(Menu viewMenu)
    {
        var gotoViewMenuItem = mkMenuItem(GOTO);
        viewMenu.add(gotoViewMenuItem);

        gotoViewMenuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                String pageNumberStr = JOptionPane.showInputDialog(PAGENR);
                int pageNumber = setPageNumberGotoMenuItem(Integer.parseInt(pageNumberStr));

                presentation.setSlideNumber(pageNumber - 1);
            }
        });
    }

    private int setPageNumberGotoMenuItem(int pageNumber)
    {
        if (pageNumber > presentation.getSize())
        {
            pageNumber = presentation.getSize();
        }

        if (pageNumber < 1)
        {
            pageNumber = 1;
        }

        return pageNumber;
    }

    private void addAboutHelpMenuItem(Menu helpMenu)
    {
        var menuItem = mkMenuItem(ABOUT);
        helpMenu.add(menuItem);

        menuItem.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                AboutBox.show(parent);
            }
        });
    }

    private void loadFile(String file, String err, boolean load)
    {
        Accessor xmlAccessor = new XMLAccessor();

        try
        {
            if (load)
            {
                xmlAccessor.loadFile(this.presentation, file);
                this.presentation.setSlideNumber(0);
            } else
            {
                xmlAccessor.saveFile(this.presentation, file);
            }
        } catch (IOException exc)
        {
            JOptionPane.showMessageDialog(this.parent, IOEX + exc,
                    err, JOptionPane.ERROR_MESSAGE);
        }
    }

    //Creating a menu-item
    public MenuItem mkMenuItem(String name)
    {
        return new MenuItem(name, new MenuShortcut(name.charAt(0)));
    }
}
