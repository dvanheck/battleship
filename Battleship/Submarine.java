import java.awt.Graphics;

/**
 * Write a description of class Submarine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Submarine extends Ship
{
    /**
     * Constructor for objects of class Submarine
     */
    public Submarine()
    {
        // initialise instance variables
        myLENGTH = 3;
        mySTATE = 2;
        myHealth = 3;
        myName = new String("Submarine");
    }// ends constructor

    /**
     * Displays the ship on the player's grid
     * 
     * @param  g   Graphics used by the Viewport panel
     * @param  posOfOceanGridX   x-coordinate of the grid(not the ship) in the frame
     * @param  posOfOceanGridY   y-coordinate of the grid(not the ship) in the frame
     */
    public void display(Graphics g, int posOfOceanGridX, int posOfOceanGridY)
    {
        // vars
        final int CW = 30; // for the width and/or height of each square in the grid in pixels
        final int ARCW = 60; // for the arc width/height of the curve on each ship
        
        // set the x position to the x-grid coordinate of the ship multiplied by the current column width plus the x position of the ocean grid
        int x  = (getPosX() * CW) + posOfOceanGridX; 
        // set the y position to the y-grid coordinate of the ship multiplied by the current column width plus the y position of the ocean grid
        int y = (getPosY() * CW) + posOfOceanGridY;
        int rot = getRot(); /*the clockwise rotation direction to set squares in a line (0 is 0 or 360 degrees(up), 
                                  1 is 90 degrees(right), 2 is 180(down), 3 is 270(left)*/
        int length = getLength(); // peg length of ship
        
        
        // draw the ship in the correct direction
        if(rot == 0)
        {
            g.fillRoundRect(x, y - length * CW + CW, CW, length * CW, ARCW, ARCW);
        }// ends if
        else if(rot == 1)
        {
            g.fillRoundRect(x, y, length * CW, CW, ARCW, ARCW);
        }// ends else if
        else if(rot == 2)
        {
            g.fillRoundRect(x, y, CW, length * CW, ARCW, ARCW);
        }// ends else if
        else if(rot == 3)
        {
            g.fillRoundRect(x - length * CW + CW, y, length * CW, CW, ARCW, ARCW);
        }// ends else if
    }// ends method
}// ends class
