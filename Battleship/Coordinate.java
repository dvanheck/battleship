
/**
 * A coordinate with a row value and a column value
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Coordinate
{
    // instance variables for storing row and column
    private int myR;
    private int myC;

    /**
     * Constructor for objects of class Coordinate
     */
    public Coordinate(int r, int c)
    {
        // initialise instance variables
        myR = r;
        myC = c;
    }// ends constructor
    
    /**
     * Copy constructor for objects of class Coordinate
     */
    public Coordinate(Coordinate coord)
    {
        // initialise instance variables
        myR = coord.getRow();
        myC = coord.getColumn();
    }// ends constructor
    
    /**
     * Returns the row value of the coordinate
     *
     * @return   r  the row value of the coordinate
     */
    public int getRow()
    {
        return myR;
    }// ends method
    
    /**
     * Returns the column value of the coordinate
     * 
     * @return   c  the column value of the coordinate
     */
    public int getColumn()
    {
        return myC;
    }// ends method
    
    /**
     * Sets the row value to the specified value
     *
     * @param  r  the row value
     */
    public void setRow(int r)
    {
        myR = r;
    }// ends method
    
    /**
     * Sets the column value to the specified value
     *
     * @param  c  the column value
     */
    public void setColumn(int c)
    {
        myC = c;
    }// ends method
}// ends class
