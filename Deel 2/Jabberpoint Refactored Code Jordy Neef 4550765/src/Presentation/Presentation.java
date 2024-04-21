package Presentation;

import java.util.ArrayList;

import Slide.Slide;
import Slide.SlideViewerComponent;


/**
 * <p>Presentations keeps track of the slides in a presentation.</p>
 * <p>Only one instance of this class is available.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation
{
    private String showTitle; //The title of the presentation
    private ArrayList<Slide> showList; //An ArrayList with slides
    private int currentSlideNumber; //The number of the current slide
    private SlideViewerComponent slideViewComponent; //The view component of the slides

    public Presentation()
    {
        this.showList = null;
        this.currentSlideNumber = 0;
        this.slideViewComponent = null;
        clear();
    }

    public Presentation(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
        clear();
    }

    public int getSize()
    {
        return this.showList.size();
    }

    public String getTitle()
    {
        return this.showTitle;
    }

    public void setTitle(String title)
    {
        this.showTitle = title;
    }

    public void setShowView(SlideViewerComponent slideViewerComponent)
    {
        this.slideViewComponent = slideViewerComponent;
    }

    //Returns the number of the current slide
    public int getSlideNumber()
    {
        return this.currentSlideNumber;
    }

    //Change the current slide number and report it the window
    public void setSlideNumber(int number)
    {
        if (this.slideViewComponent == null)
        {
			return;
        }

		this.currentSlideNumber = number;
		this.slideViewComponent.update(this, getCurrentSlide());
    }

    //Navigate to the previous slide unless we are at the first slide
    public void prevSlide()
    {
        if (this.currentSlideNumber <= 0)
        {
			return;
        }

		this.setSlideNumber(this.currentSlideNumber - 1);
    }

    //Navigate to the next slide unless we are at the last slide
    public void nextSlide()
    {
        if (this.currentSlideNumber >= (showList.size() - 1))
        {
            return;
        }

		this.setSlideNumber(this.currentSlideNumber + 1);
    }

    //Remove the presentation
    public void clear()
    {
        this.showList = new ArrayList<>();
        this.setSlideNumber(-1);
    }

    //Add a slide to the presentation
    public void append(Slide slide)
    {
        this.showList.add(slide);
    }

    //Return a slide with a specific number
    public Slide getSlide(int number)
    {
        if (number < 0 || number >= getSize())
        {
            return null;
        }

        return this.showList.get(number);
    }

    //Return the current slide
    public Slide getCurrentSlide()
    {
        return this.getSlide(this.currentSlideNumber);
    }

    public void exit(int n)
    {
        System.exit(n);
    }
}
