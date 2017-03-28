import java.awt.Graphics;

/**
 * Write a description of interface Displayable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface Displayable
{
    /**
     * Displays the object on the screen
     * @param  g   Graphics used to draw
     * @param  refX   x-coordinate of a point of reference in the frame(ex: drawing on 
     * @param  refY   y-coordinate of a point of reference in the frame
     */
    void display(Graphics g, int refX, int refY);
}// ends class
