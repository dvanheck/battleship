import java.util.ArrayList;

/**
 * The battlefield that holds all the ships and has methods to check collision
 * 
 * @author David Van Heck 
 * @version 1.0
 */
public class BattleGrid
{
    // instance variables 
    private ArrayList<Ship> myShips;
    private int[][] mySquares;
    private final int myMAX_SHIPS;
    private final int mySIZE;
    

    /**
     * Constructor for objects of class BattleGrid
     */
    public BattleGrid()
    {
        // initialise instance variables
        myMAX_SHIPS = 5;
        mySIZE = 10;
        myShips = new ArrayList<Ship>(myMAX_SHIPS);
        mySquares = new int[mySIZE][mySIZE];
    }// ends constructor
    
    
    /**
     * Returns the number of ships that can be placed on the BattleGrid
     *
     * @return   number of unsunken ships currently on the BattleGrid
     */
    public int getNumShips()
    {
        return myShips.size();
    }// ends method
    
    /**
     * Returns the maximum number of ships that can be placed on the battlefield
     * (the number of ships that are placed at the beginning of the game)
     *
     * @return  myNUM_SHIPS   the number of ships that can be on the grid
     */
    public int getMaxShips()
    {
        return myMAX_SHIPS;
    }
    
    /**
     * Get's the ship at the specified index from ArrayList of ships
     *
     * @param  index   the index of the ship in the ArrayList
     * @return     the specified ship
     */
    public Ship getShip(int index)
    {
        return myShips.get(index);
    }// ends method
    
    /**
     * Get's the myShips ArrayList
     *
     * @return  myShips   the ArrayList of ships on the grid
     */
    public ArrayList getShips()
    {
        return myShips;
    }

    /**
     * Returns the state of a specified square
     *
     * @param  r  row number
     * @param  c  column number
     * @return  state   the state of the square (0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
     *                 4 is a battlship, 5 is a carrier, 6 is a hit, 7 is a missed square, 8 is a sunken ship)
     */
    public int getSquareState(int r, int c)
    {
        // put your code here
        return mySquares[r - 1][c - 1];
        
    }// ends method
    
    /**
     * Takes in a Ship and adds it to the current ArrayList of ships
     * 
     * @param  newShip   the ship to be added to the myShips ArrayList
     * @return     the sum of x and y 
     */
    public void addShip(Ship newShip)
    {
        myShips.add(newShip);
    }// ends method    
    
    /**
     * Removes all ships from the myShips ArrayList
     *
     */
    public void clearShips()
    {
        myShips.clear();
    }

    /**
     * Sets the given square to a given state
     *
     * @param  r   the int row value(y-coordinate) of the square
     * @param  c   the int column value(x-coordinate) of the square
     * @param  state   state to set the square (0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
     *                 4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship)
     */
    public void setSquare(int r, int c, int state)
    {
        mySquares[r - 1][c - 1] = state;
    }// ends method
    
    /**
     * Sets the squares following the starting square to the specified state in the specified rotation direction
     *
     * @param  r   the row value(y coordinate) of the square to start on
     * @param  c   the column value(x coordinate) of the square to start on
     * @param  rot   the clockwise rotation direction to set squares in a line (0 is 0 or 360 degrees(up), 
     *               1 is 90 degrees(right), 2 is 180(down), 3 is 270(left)
     * @param  length   the amount of squares to change in the direction specified
     * @param  state   the state to change the squares to (0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
     *                 4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship)         
     */
    public void setSquares(int r, int c, int rot, int length, int state)
    {
        try
        {
            /* check direction, then fill squares in a line 
            if there is an intersection with another ship or the squares go out of bounds, throw an exception*/
            if(rot == 0)
            {
                for(int i = 0; i < length; i++)
                {
                    mySquares[r - 1 - i][c - 1] = state;
                }// ends for loop
            }// ends if
            
            else if(rot == 1)
            {
                for(int i = 0; i < length; i++)
                {
                    mySquares[r - 1][c - 1 + i] = state;
                }// ends for
            }// ends else if
            
            else if(rot == 2)
            {
                for(int i = 0; i < length; i++)
                {
                    mySquares[r - 1 + i][c - 1] = state;
                }// ends for
            }// ends if
            
            else if(rot == 3)
            {
                for(int i = 0; i < length; i++)
                {
                    mySquares[r - 1][c - 1 - i] = state;
                }// ends for
            }// ends else if
        } 
        // catches if out of grid bounds or if intersects with another ship
        catch (Exception e) 
        {
            System.out.println("Error: This shouldn't happen " + e.getMessage());
        }// ends catch
    }// ends method
    
    /**
     * Sets the squares following the starting square to the specified state in the specified rotation direction
     * if the squares hit a ship, the method does not do anything
     *
     * @param  r   the row value(y coordinate) of the square to start on
     * @param  c   the column value(x coordinate) of the square to start on
     * @param  rot   the clockwise rotation direction to set squares in a line (0 is 0 or 360 degrees(up), 
     *               1 is 90 degrees(right), 2 is 180(down), 3 is 270(left)
     * @param  length   the amount of squares to change in the direction specified
     * @param  state   the state to change the squares to (0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
     *                 4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship)         
     */
    public void setSquaresToShipspace(int r, int c, int rot, int length, int state)
    {
        try
        {
            /* check direction, then fill squares in a line 
            if there is an intersection with another ship or the squares go out of bounds, throw an exception*/
            if(rot == 0)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1 - i][c - 1] == 0)
                    {
                        mySquares[r - 1 - i][c - 1] = state;
                    }// ends if
                    else
                    {
                        throw new Exception("Error: The ship intersects with another ship");
                    }// ends else
                }// ends for
            }// ends if
            
            else if(rot == 1)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1][c - 1 + i] == 0)
                    {
                        mySquares[r - 1][c - 1 + i] = state;
                    }// ends if
                    else
                    {
                        throw new Exception("Error: The ship intersects with another ship");
                    }// ends else
                }// ends for
            }// ends else if
            
            else if(rot == 2)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1 + i][c - 1] == 0)
                    {
                        mySquares[r - 1 + i][c - 1] = state;
                    }// ends if
                    else
                    {
                        throw new Exception("Error: The ship intersects with another ship");
                    }// ends else
                }// ends for
            }// ends if
            
            else if(rot == 3)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1][c - 1 - i] == 0)
                    {
                        mySquares[r - 1][c - 1 - i] = state;
                    }// ends if
                    else
                    {
                        throw new Exception("Error: The ship intersects with another ship");
                    }// ends else
                }// ends for
            }// ends else if
        } 
        // catches if out of grid bounds or if intersects with another ship
        catch (Exception e) 
        {
            System.out.println("Error: This shouldn't happen " + e.getMessage());
        }// ends catch
    }// ends method
    
    /**
     * Checks if the squares from a given coordinate across a given length in a given 
     * direction are all empty (have a value of 0)
     * Returns true if ship does not collide/go out of bounds and false if it does
     *
     * @param  r   the row value(y coordinate) of the square to start on
     * @param  c   the column value(x coordinate) of the square to start on
     * @param  rot   the clockwise rotation direction to set squares in a line (0 is 0 or 360 degrees(up), 
     *               1 is 90 degrees(right), 2 is 180(down), 3 is 270(left)
     * @param  length   the amount of squares to change in the direction specified
     * @return  isValid   set to true if the placed ship does not collide or go off the board, and false if it does go off the board
     */
    public boolean checkCollision(int r, int c, int rot, int length, boolean isPlayerTurn)
    {
        boolean isValid = true;
        // check if the ship to place will collide with any other ships or be outside of the level
        try // to catch out of bounds errors
        {
            /* check direction, if there is an intersection with another ship or the squares go out of bounds, set isValid to false*/
            if(rot == 0)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1  - i][c - 1] != 0)
                    {
                        isValid = false;
                    }// ends if                   
                }// ends for
            }// ends if
            
            else if(rot == 1)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1][c - 1 + i] != 0)
                    {
                        isValid = false;
                    }// ends if                    
                }// ends for 
            }// ends else if
            
            else if(rot == 2)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1 + i][c - 1] != 0)
                    {
                        isValid = false;
                    }// ends if                    
                }// ends for
            }// else if
            
            else if(rot == 3)
            {
                for(int i = 0; i < length; i++)
                {
                    if(mySquares[r - 1][c - 1 - i] != 0)
                    {
                        isValid = false;
                    }// ends if
                }// ends for
            }// else if
        }// ends try
        // catches if out of grid bounds
        catch (Exception e) 
        {
            // if it is the player's turn, display an error message
            if(isPlayerTurn == true)
            {
                System.out.println("Error: Ship goes out of bounds");
            }
            
            isValid = false;
            return isValid;
        }// ends catch
        
        // if the input is invalid and it is the player's turn, display an error message
        if(isValid == false && isPlayerTurn == true)
        {
            System.out.println("Error: Ship collides with another ship");
        }// ends if
        
        return isValid;
    }// ends method
    
    /**
     * Checks if a there are any hitMarkers on the playerGrid
     *
     * @return  true if there is a hit on the grid, false if no hits on grid (obviously excluding sunken ships)
     */
    public boolean hasHitMarkers()
    {
        // check every square and if one is a hit, return true
        for(int i = 0; i < mySIZE; i++)
        {
            for(int j = 0; j < mySIZE; j++)
            {
                if(mySquares[i][j] == 7)
                {
                    return true;
                }// ends if
            }// ends for
        }// ends for
        
        return false;
    }// ends method
    
    /**
     * Checks if a there are any hitMarkers on the playerGrid
     *
     * @return  the number of hits on the grid, excluding sunken ships
     */
    public int getNumHits()
    {
        int count = 0;
        
        // count number of hits
        for(int i = 0; i < mySIZE; i++)
        {
            for(int j = 0; j < mySIZE; j++)
            {
                if(mySquares[i][j] == 7)
                {
                    count++;
                }// ends if
            }// ends for
        }// ends for
        
        return count;
    }// ends method
    
    /**
     * Checks if there is a row of horizontal hits and returns true if there are and false if there are not any horizontal hit rows
     *
     * @return  true or false for if there is a row of horizontal hits or not
     */
    public boolean hitsAreHorizontal()
    {
        // vars
        boolean hitFound = false;
        for(int r = 0; r < mySIZE; r++)
        {
            for(int c = 0; c < mySIZE; c++)
            {
                if(mySquares[r][c] == 7)
                {
                    hitFound = true;
                }// ends if
                // if a hit has been found, check if there is a hit to the right or down of it
                if(hitFound == true)
                {
                    // if there is a hit to the right, return true
                    if(c != 9 && mySquares[r][c + 1] == 7)
                    {
                        return true;
                    }// ends if
                    // if there is a hit below, return false
                    else if(r != 9 && mySquares[r + 1][c] == 7)
                    {
                        return false;
                    }// ends else if
                }// ends if
            }// ends for
        }// ends for
        
        System.out.println("Error in hitsAreHorizontal method of BattleGrid");
        return false;
        
    }

    /**
     * Returns the Coordinate of the first hit found
     *
     * @return  Coordinate of the first hit found
     */
    public Coordinate findFirstHit()
    {
        // find first hit
        for(int r = 0; r < mySIZE; r++)
        {
            for(int c = 0; c < mySIZE; c++)
            {
                if(mySquares[r][c] == 7)
                {
                    return (new Coordinate(r + 1, c + 1));
                }// ends if
            }// ends for
        }// ends for
        
        // no hits so return (-1, -1)
        return (new Coordinate(-1, -1));
    }// ends method
    
    /**
     * Returns the Coordinate of the last hit found after a line of hits with a parameter for if the hits are horizontal or vertical
     *
     * @param  areHorizontal   true if the hits are horizontal, false if hits are vertical
     * @return  Coordinate of the last hit found
     */
    public Coordinate findLastHit(boolean areHorizontal)
    {
        // 
        boolean hasFoundHit = false; // for if hit has been found or not
        // find last hit
        if(areHorizontal == true)
        {
            for(int r = 0; r < mySIZE; r++)
            {
                for(int c = 0; c < mySIZE; c++)
                {
                    if(mySquares[r][c] == 7)
                    {
                        hasFoundHit = true;
                    }// ends if
                    // if a hit has been found
                    if(hasFoundHit == true)
                    {
                        // if the next square is not a hit, and the next square does not go out of bounds, return the current square
                        if(c + 1 == 10)  
                        {
                            return new Coordinate(r + 1, c + 1);
                        }// ends if
                        else if(mySquares[r][c + 1] != 7)
                        {
                            return new Coordinate(r + 1, c + 1);
                        }// ends else if
                    }// ends if
                }// ends for
            }// ends for
        }// ends if
        else if(areHorizontal == false)
        {
            for(int c = 0; c < mySIZE; c++)
            {
                for(int r = 0; r < mySIZE; r++)
                {
                    if(mySquares[r][c] == 7)
                    {
                        hasFoundHit = true;
                    }// ends if
                    // if a hit has been found
                    if(hasFoundHit == true)
                    {
                        // if the next square is not a hit and does not go out of bounds, return the current square
                        if(r + 1 == 10)   
                        {
                            return new Coordinate(r + 1, c + 1);
                        }// ends if
                        else if(mySquares[r + 1][c] != 7)
                        {
                            return new Coordinate(r + 1, c + 1);
                        }// ends else if
                    }// ends if
                }// ends for
            }// ends for
        }// ends if
        
        // no hits so return (-1, -1)
        return (new Coordinate(-1, -1));
    }// ends method

    /**
     * Checks if a ship is located at a specified coordinate and returns true if a ship is there and false if a ship is not there
     *
     * @param  r   the row to check
     * @param  c   the column to check
     * @return  hasShip   if the square has a ship on it   
     */
    public boolean hasShip(int r, int c)
    {
        // get the state at the specified square 
        int state = getSquareState(r, c); // (0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser, 
                                          // 4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship)   
        
        // check if there is a ship at the specified coordinate
        if(state >= 1 && state <= 5)
        {
            return true;
        }// ends if
        else
        {
            return false;
        }// ends else
    }
    
    /**
     * Takes in a square and if there is a ship on that square, the square state is set to 7(hit ship) and true is returned
     * If there is not a ship on the given square, the square is set to 6(missed ship) and false is returned
     *
     * @param  r   the row to fire on
     * @param  c   the column to fire on
     * @return  true or false   if a ship was hit, return true, if the shot was a miss, return false
     */
    public boolean fireOnSquare(int r, int c)
    {
        // if there is a ship on the square, set the square to a hit ship
        if(hasShip(r, c))
        {
            for(int i = 0; i < myShips.size(); i++)
            {
                // if ship is hit, remove health
                if(getSquareState(r, c) == myShips.get(i).getState())
                {
                    myShips.get(i).decrementHealth();
                }// ends if
            }// ends for
            
            setSquare(r, c, 7);
            return true;
        }// ends if
        // if there is not a ship on the square, set the square to a missed ship
        else if(hasShip(r, c) == false)
        {
            setSquare(r, c, 6);
            return false;
        }// ends if
        else
        {
            System.out.println("There is no way this can ever happen, but if it does there's something wrong");
            return false;
        }// ends else
    }// ends method
    
    /**
     * Checks if the square has been hit or missed yet and returns a true or false
     *
     * @return  true if the square has been hit or missed, false if the square has not been hit or missed
     */
    public boolean hasBeenFiredOn(int r, int c)
    {
        boolean hasBeenFiredOn = false; // for if the square has been fired on yet or not
        
        // check if the square is already hit(6) or already missed(7), or has a sunken ship(8), and set hasBeenFired on to true
        if(getSquareState(r, c) == 6 || getSquareState(r, c) == 7 || getSquareState(r, c) == 8)
        {
            hasBeenFiredOn = true;
        }// ends if
        
        return hasBeenFiredOn;
    }// ends method

    /**
     * Finds a sunken ship and returns the String of the ship found, 
     * if no ship is found, return ""
     *
     * @return  sunkenShip   the String name of the sunken ship
     */
    public String findSunkenShip()
    {
        final int SUNK_STATE = 8;
        Ship sunkenShip;
        String sunkenShipName;
        
        // find if a ship is sunken and return the first string found, and remove that ship from the ArrayList
        for(int i = 0; i < myShips.size(); i++)
        {
            // one of myShips has no health left
            if(myShips.get(i).getHealthPercentage() == 0.0)
            {
                // store the memory address of the sunken ship
                sunkenShip = myShips.get(i);
                
                // set it's squares to sunken
                setSquares(sunkenShip.getPosY(), sunkenShip.getPosX(), sunkenShip.getRot(), sunkenShip.getLength(), SUNK_STATE);
                
                // get name
                sunkenShipName = myShips.get(i).getName();
                
                // remove from ArrayList of ships
                myShips.remove(i);
                
                // return name
                return sunkenShipName;
            }// ends if
        }// ends for
        
        // if no ship found return ""
        return "";
    }// ends method

    /**
     * Returns the String state of the square entered
     *
     * @param  y   a sample parameter for a method
     * @return   state  the String state of the square(0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
     *                 4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship)      
     */
    public String squareStateToString(int r, int c)
    {
        String state; /* for state(0 means nothing is there, 1 a destroyer is there, 2 is a submarine, 3 is a cruiser,
                      4 is a battlship, 5 is a carrier, 6 is a hit(a fire), 7 is a missed square, 8 is a sunken ship) */
                      
        // check state of square and return appropriate state
        if(getSquareState(r, c) == 0)
        {
            state = new String("Empty");
            return state;
        }// ends if
        else if(getSquareState(r, c) == 1)
        {
            state = new String("Destroyer");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 2)
        {
            state = new String("Submarine");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 3)
        {
            state = new String("Cruiser");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 4)
        {
            state = new String("Battleship");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 5)
        {
            state = new String("Carrier");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 6)
        {
            state = new String("already hit");
            return state;
        }// ends else if
        else if(getSquareState(r, c) == 7)
        {
            state = new String("already missed");
            return state;
        }// ends else if
        else
        {
            return "Error";
        }// ends else
    }// ends method
}// ends class
