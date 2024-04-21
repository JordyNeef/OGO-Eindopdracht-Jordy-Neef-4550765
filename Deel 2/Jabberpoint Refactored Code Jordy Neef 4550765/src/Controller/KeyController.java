package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import Presentation.Presentation;

/**
 * <p>This is the Menu.KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter
{
    private final Presentation presentation; //Commands are given to the presentation

    public KeyController(Presentation p)
    {
        this.presentation = p;
    }

    public void keyPressed(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_ENTER:
            case '+':
                this.presentation.nextSlide();
                break;
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_UP:
            case '-':
                this.presentation.prevSlide();
                break;
            case 'q':
            case 'Q':
                System.exit(0);
            default:
                break;
        }
    }
}
