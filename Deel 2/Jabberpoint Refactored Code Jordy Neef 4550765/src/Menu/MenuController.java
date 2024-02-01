package Menu;

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

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	
	private Frame parent; //The frame, only used as parent for the Dialogs
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

	public MenuController(Frame frame, Presentation pres) {
		this.parent = frame;
		this.presentation = pres;

		this.fileMenu();
		this.viewMenu();
		this.helpMenu();
	}

	private void fileMenu() {
		Menu fileMenu = new Menu(FILE);

		var menuItem1 = mkMenuItem(OPEN);
		fileMenu.add(menuItem1);

		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				loadFile(TESTFILE, LOADERR, true);
				parent.repaint();
			}
		});

		var menuItem2 = mkMenuItem(NEW);
		fileMenu.add(menuItem2);

		menuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.clear();
				parent.repaint();
			}
		});

		var menuItem3 = mkMenuItem(SAVE);
		fileMenu.add(menuItem3);

		menuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile(SAVEFILE, SAVEERR, false);
			}
		});

		var menuItem4 = mkMenuItem(EXIT);
		fileMenu.addSeparator();
		fileMenu.add(menuItem4);

		menuItem4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.exit(0);
			}
		});
		add(fileMenu);
	}

	private void loadFile(String file, String err, boolean load) {
		Accessor xmlAccessor = new XMLAccessor();
		try {
			if(load) {
				xmlAccessor.loadFile(this.presentation, file);
				this.presentation.setSlideNumber(0);
			}
			else {
				xmlAccessor.saveFile(this.presentation, file);
			}
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(this.parent, IOEX + exc,
					err, JOptionPane.ERROR_MESSAGE);
		}
	}

	private void viewMenu() {
		Menu viewMenu = new Menu(VIEW);

		var menuItem1 = mkMenuItem(NEXT);
		viewMenu.add(menuItem1);

		menuItem1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.nextSlide();
			}
		});

		var menuItem2 = mkMenuItem(PREV);
		viewMenu.add(menuItem2);

		menuItem2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				presentation.prevSlide();
			}
		});

		var menuItem3 = mkMenuItem(GOTO);
		viewMenu.add(menuItem3);

		menuItem3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
				int pageNumber = Integer.parseInt(pageNumberStr);
				presentation.setSlideNumber(pageNumber - 1);
			}
		});

		add(viewMenu);
	}
	private void helpMenu() {
		var menuItem = mkMenuItem(ABOUT);
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(menuItem);

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AboutBox.show(parent);
			}
		});

		setHelpMenu(helpMenu);		//Needed for portability (Motif, etc.).
	}

//Creating a menu-item
	public MenuItem mkMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}
}
