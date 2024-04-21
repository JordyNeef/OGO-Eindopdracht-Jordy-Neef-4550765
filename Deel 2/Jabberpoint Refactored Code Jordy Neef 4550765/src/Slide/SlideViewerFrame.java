package Slide;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

import Presentation.Presentation;
import Controller.KeyController;
import Controller.MenuController;

/**
 * <p>The applicatiewindow for a slideviewcomponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame
{
    private static final String JABTITLE = "Jabberpoint 1.6 - OU";
    public static final int WIDTH = 1200;
    public static final int HEIGHT = 800;

    public SlideViewerFrame(String title, Presentation presentation)
    {
        super(title);
        SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
        presentation.setShowView(slideViewerComponent);
        setupWindow(slideViewerComponent, presentation);
    }

    //Set up the GUI
    public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation)
    {
        setTitle(JABTITLE);

		this.setupListeners();
		this.setUpMenu(slideViewerComponent, presentation);
    }

	private void setupListeners()
	{
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
	}

	private void setUpMenu(SlideViewerComponent slideViewerComponent, Presentation presentation)
	{
		getContentPane().add(slideViewerComponent);
		addKeyListener(new KeyController(presentation)); //Add a controller
		setMenuBar(new MenuController(this, presentation));    //Add another controller
		setSize(new Dimension(WIDTH, HEIGHT)); //Same sizes a slide has
		setVisible(true);
	}
}
