import java.awt.Graphics;

/**
 * Write a description of class Ship here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public abstract class Ship implements Displayable
{
    // instance variables
    private int myPosX; // the x-axis position of the ship
    private int myPosY; // the numerical y-axis position of the ship
    private char myPosYLetter; // the letter y-axis position of the ship on
    protected int myRot; // for the rotation of each ship
    protected int myHealth; // for the health of the ship to determine when the ship is sunken
    protected int myLENGTH; // for peg length of ship
    protected int mySTATE; // number that represents the type of ship on the grid
    protected String myName; // string name of ship
    
    /**
     * Returns the x-coordinate of the ship
     * 
     * @return  myPosX   the x-coordinate of the ship
     */
    public int getPosX()
    {
        return myPosX;
    }// ends the getPosX method
    
    /**
     * Returns the integer y-coordinate of the ship
     * 
     * @return  myPosY   the y-coordinate of the ship
     */
    public int getPosY()
    {
        return myPosY;
    }// ends the getPosY method
    
    /**
     * Returns the character y-coordinate of the ship
     * 
     * @return  myPosYLetter   the char y-coordinate of the ship
     */
    public int getPosYLetter()
    {
        return myPosYLetter;
    }// ends the getPosYLetter method
    
    /**
     * Returns rotation value of ship
     *
     * @return  myRot
     */
    public int getRot()
    {
        // put your code here
        return myRot;
    }
    
    /**
     * Amount of health the ship currently has
     *
     * @return  myHealth   the health of the ship to determine when the ship is sunken
     */
    public int getHealth()
    {
        return myHealth;
    }// ends ghe getLength method
    
    /**
     * Amount of health the ship currently
     *
     * @return  double for percentage of ship health
     */
    public double getHealthPercentage()
    {
        Double maxHealth = new Double((double)myLENGTH);
        Double currentHealth = new Double((double)myHealth);
        
        return currentHealth.doubleValue()/maxHealth.doubleValue();
    }// ends ghe getLength method
    
    /**
     * Gets the number of pegs on the ship
     *
     * @return  myLENGTH   the peg length of the ship
     */
    public int getLength()
    {
        return myLENGTH;
    }// ends ghe getLength method
    
    /**
     * Gets the number to represent the ship on the grid(1 is a destroyer, 
     * 2 is a submarine, 3 is a cruiser, 4 is a battlship, 5 is a carrier)
     *
     * @return  mySTATE   the peg length of the ship
     */
    public int getState()
    {
        return mySTATE;
    }// ends the getState method
    
    /**
     * Returns the name of the ship
     *
     * @return  myName   the String name of the ship
     */
    public String getName()
    {
        return myName;
    }// ends the getName method

    /**
     * Sets the position of the ship
     * 
     * @param  posX   the integer x-coordinate of the ship
     * @param  posY   the integer y-coordinate of the ship
     */
    public void setPos(int posX, int posY)
    {
        myPosX = posX;
        myPosY = posY;
    }// ends the setPos method
    
    /**
     * Sets the rotation of the ship 
     *
     * @param  rot   the clockwise rotation value of the ship
     */
    public void setRot(int rot)
    {
        myRot = rot;
    }// ends the setRot method
    
    /**
     * Sets the health of the ship 
     *
     * @param  health   the health of the ship to determine when the ship is sunken
     */
    public void setHealth(int health)
    {
        myHealth = health;
    }// ends the setRot method
    
    /**
     * Lowers the current health by 1
     *
     */
    public void decrementHealth()
    {
        myHealth--;
    }

    /**
     * Displays the ship
     * @param  g   Graphics used by the Viewport panel
     * @param  x   x-coordinate of the grid(not the ship) in the frame
     * @param  y   y-coordinate of the grid(not the ship) in the frame
     */
    public abstract void display(Graphics g, int x, int y);
    
}// ends the Ship class
