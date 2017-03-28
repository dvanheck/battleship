import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Write a description of class GamePanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GamePanel extends JPanel
{
    // booleans for if to draw ships when repaint() is called
    private boolean myShouldDrawShips;
    private boolean myShouldDrawEnemyShips;
    //private boolean myShouldDrawBoard;
    
    // store ocean grid(friendly) and target grid(enemy) ships
    private ArrayList<Ship> myOceanShips;
    private ArrayList<Ship> myTargetShips;
    
    // store if certain squares are hit or missed to draw markers on them(0 is no hitmarker, 1 is white, 2 is red)
    private int[][] myTargetHitGrid; 
    private int[][] myOceanHitGrid;
    
    private final int myGRIDSIZE = 10; // size of grids
    
    /**
     * Constructor for objects of class GamePanel
     */
    public GamePanel()
    {
        myShouldDrawShips = true;
        myShouldDrawEnemyShips = false;
        //myShouldDrawBoard = true;
        myOceanShips = new ArrayList<Ship>();
        myTargetHitGrid = new int[myGRIDSIZE][myGRIDSIZE];
        myOceanHitGrid = new int[myGRIDSIZE][myGRIDSIZE];
        //setBackground(Color.blue);
        
    }// ends constructor

    /**
     * Sets whether to show the ships on the ocean grid
     *
     * @param  drawShips   sets if the ships should be shown on the ocean grid when repaint() is called
     */
    public void setShouldDrawShips(boolean drawShips)
    {
        myShouldDrawShips = drawShips;
    }// ends method
    
    /**
     * Sets whether to show the enemy ships on the target grid
     *
     * @param  drawEnemyShips   sets if the enemy ships should be shown on the target grid repaint() is called
     */
    public void setShouldDrawEnemyShips(boolean drawEnemyShips)
    {
        myShouldDrawEnemyShips = drawEnemyShips;
    }// ends method

    /**
     * Sets myOceanShips ArrayList of ships to display on the Ocean grid
     *
     * @param  myOceanShips   ArrayList of the ocean grid ships
     */
    public void setOceanShips(ArrayList<Ship> ships)
    {
        myOceanShips = new ArrayList<Ship>(ships);
    }// ends method
    
    /**
     * Adds a ship to the myOceanShips ArrayList to be displayed on the ocean grid
     *
     * @param  inShip   new ocean Ship to be added to the myOceanShips ArrayList
     */
    public void addOceanShip(Ship inShip)
    {
        myOceanShips.add(inShip);
    }// ends method
    
    /**
     * Sets myTargetShips ArrayList of enemy ships to display on the target grid
     *
     * @param  ships   ArrayList of the enemy target grid ships
     */
    public void setTargetShips(ArrayList<Ship> ships)
    {
        myTargetShips = new ArrayList<Ship>(ships);
    }// ends method
    
    /**
     * Adds a ship to the myTargetShips ArrayList to be displayed on the target grid
     *
     * @param  inShip   new enemy Ship to be added to the myTargetShips ArrayList
     */
    public void addTargetShip(Ship inShip)
    {
        myTargetShips.add(inShip);
    }// ends method
    
    /**
     * Sets index at myTargetHitGrid array of hitmarkers to display on the target grid
     *
     * @param  r   row value to set hitmarker
     * @param  c   column value to set hitmarker
     * @param  marker   int for what type of hitmarker to place at the specified row and column(0 is no hitmarker, 1 is white, 2 is red)
     */
    public void setTargetHitGrid(int r, int c, int marker)
    {
        myTargetHitGrid[r - 1][c - 1] = marker;
    }// ends method
    
    /**
     * Sets index at myTargetHitGrid array of hitmarkers to display on the target grid
     *
     * @param  r   row value to set hitmarker
     * @param  c   column value to set hitmarker
     * @param  isHit   boolean for if there is a hit(true) or miss(false) at the square
     */
    public void setTargetHitGrid(int r, int c, boolean isHit)
    {
        int marker; // for type of marker
        
        // check if the ship is hit and store a hit(2) or miss(1) marker at the location
        if(isHit)
        {
            marker = 2;
            myTargetHitGrid[r - 1][c - 1] = marker;
        }// ends if
        else if(isHit == false)
        {
            marker = 1;
            myTargetHitGrid[r - 1][c - 1] = marker;
        }// ends else if
        else
        {
            System.out.println("Error in setTargetHitGrid!");
        }// ends else
    }// ends method
    
    /**
    * Sets index at myOceanHitGrid array of hitmarkers to display on the target grid
     *
     * @param  r   row value to set hitmarker
     * @param  c   column value to set hitmarker
     * @param  isHit   boolean for if there is a hit(true) or miss(false) at the square
     */
    public void setOceanHitGrid(int r, int c, boolean isHit)
    {
        int marker; // for type of marker
        
        // check if the ship is hit and store a hit(2) or miss(1) marker at the location
        if(isHit)
        {
            marker = 2;
            myOceanHitGrid[r - 1][c - 1] = marker;
        }// ends if
        else if(isHit == false)
        {
            marker = 1;
            myOceanHitGrid[r - 1][c - 1] = marker;
        }// ends else if
        else
        {
            System.out.println("Error in setOceanHitGrid!");
        }// ends else
    }// ends method
    
    /**
    * Sets index at myOceanHitGrid array of hitmarkers to display on the target grid
     *
     * @param  r   row value to set hitmarker
     * @param  c   column value to set hitmarker
     * @param  marker   int for what type of hitmarker to place at the specified row and column(0 is no hitmarker, 1 is white, 2 is red)
     */
    public void setOceanHitGrid(int r, int c, int marker)
    {
        myOceanHitGrid[r - 1][c - 1] = marker;
    }// ends method
        
    /**
     * Displays the ships on the player's ocean grid
     *
     */
    public void displayShips()
    {
        myShouldDrawShips = true;
        repaint();
    }// ends method
    
    /**
     * Displays the ships on the player's ocean grid
     *
     */
    public void displayBoard()
    {
        //myShouldDrawBoard = true;
        repaint();
    }// ends method

    /**
     * Displays the two boards on the JFrame
     *
     * @param  g   Graphics used by the Viewport component
     */
    @Override
    public void paintComponent(Graphics g)
    {
        // vars
        int x, y; // to store coordinates
        
        // set starting location coordinates of ocean and target grids
        int posOceanX = 90;
        int posOceanY = 480;
        int posTargetX = 90;
        int posTargetY = 90;
        
        final int CW = 30; // to store column/row width of grid
        Font currentFont; // the current font on the grid
        
        // set font
        currentFont = new Font("Stencil", 1, 36);
        g.setFont(currentFont);
        
        // draw title
        x = 145;
        y = 60;
        g.drawString("BATTLESHIP", x, y);
        
        // set font
        currentFont = new Font("Ariel", 1, 12);
        g.setFont(currentFont);
        
        // draw target grid
        drawGrid(g, posTargetX, posTargetY);
        
        // draw ocean grid to later show player ships
        drawGrid(g, posOceanX, posOceanY);
    
        // display the ships on the ocean grid if shouldDrawShips is true
        if(myShouldDrawShips)
        {
            Iterator itr = myOceanShips.iterator(); // to traverse myOceanShips
            
            // draw each ship in the ArrayList
            while(itr.hasNext())
            {
                Ship ship = (Ship)itr.next();
                ship.display(g, posOceanX, posOceanY);
            }// ends while
        }// ends if
        
        // display enemy ships on the target grid if myShouldDrawEnemyShips is true
        if(myShouldDrawEnemyShips)
        {
            Iterator itr = myTargetShips.iterator(); // to traverse myTargetShips
            
            // draw each ship in the ArrayList
            while(itr.hasNext())
            {
                Ship ship = (Ship)itr.next();
                ship.display(g, posTargetX, posTargetY);
            }// ends while
        }// ends if
        
        // display grid hitmarkers
        for(int r = 0; r < myGRIDSIZE; r++)
        {
            for(int c = 0; c < myGRIDSIZE; c++)
            {
                // display markers on the ocean grid
                if(myOceanHitGrid[r][c] == 0)
                {
                    // do nothing
                }// ends if
                else if(myOceanHitGrid[r][c] == 1)
                {
                    // draw a white miss-marker at the coordinate on the grid
                    g.setColor(new Color(255, 255, 255));
                    g.fillOval((c + 1) * CW + posOceanX, (r + 1) * CW + posOceanY, CW, CW);
                }// ends else if
                else if(myOceanHitGrid[r][c] == 2)
                {
                    // draw a red hitmarker at the coordinate
                    g.setColor(new Color(255, 0, 0));
                    g.fillOval((c + 1) * CW + posOceanX, (r + 1) * CW + posOceanY, CW, CW);
                }// ends else if
                else
                {
                    System.out.println("Error in displaying Ocean markers");
                }// ends else
                
                // display markers on the target grid
                if(myTargetHitGrid[r][c] == 0)
                {
                    // do nothing
                }// ends if
                else if(myTargetHitGrid[r][c] == 1)
                {
                    // draw a white miss-marker at the coordinate on the grid
                    g.setColor(new Color(255, 255, 255));
                    g.fillOval((c + 1) * CW + posTargetX, (r + 1) * CW + posTargetY, CW, CW);
                }// ends else if
                else if(myTargetHitGrid[r][c] == 2)
                {
                    // draw a red hitmarker at the coordinate
                    g.setColor(new Color(255, 0, 0));
                    g.fillOval((c + 1) * CW + posTargetX, (r + 1) * CW + posTargetY, CW, CW);
                }// ends else if
                else
                {
                    System.out.println("Error in displaying Target markers");
                }// ends else
            }// ends for
        }// ends for
    }// ends method

    /**
     * Draws a grid at the specified coordinate
     *
     * @param  g   Graphics for drawing
     * @param  x   x-coordinate to place the grid at
     * @param  y   y-coordinate to place the grid at
     */
    public void drawGrid(Graphics g, int x, int y)
    {
        // vars
        // numbers of rows/columns
        int NUM_COLS = 11;
        int NUM_ROWS = 11;
        
        // amount of space to put between each grid line
        int COL_SPACING = 30;
        int ROW_SPACING = 30;
        
        // coordinates to draw lines
        int x1, y1, x2, y2; 
        
        // to provide the characters A to J to print on the first column
        GridTools converter = new GridTools();
        
        /** Color background **/
        g.setColor(new Color(0, 90, 230, 80));
        g.fillRect(x, y, (COL_SPACING * (NUM_COLS)), (ROW_SPACING * (NUM_ROWS)));
        
        g.setColor(Color.black);
        /** Draw the vertical grid lines */
        // set line starting point
        x1 = x;
        y1 = y;
        
        // set line ending point
        x2 = x;
        y2 = y + 330;
        
        // draw 12 vertical lines to make 11 columns
        for(int c = 0; c <= NUM_COLS; c++)
        {
            g.drawLine(x1, y1, x2, y2);
            
            // increase the starting and ending x-coordinates(make the lines draw to the right 30 units)
            x1 += 30;
            x2 += 30;
        }// ends for
        
        /** Draw horizontal grid lines */
        // set line starting point
        x1 = x; 
        y1 = y;
        
        // set line ending point
        x2 = x + 330; 
        y2 = y;
        
        // draw 12 horizontal lines to make 11 rows
        for(int r = 0; r <= NUM_ROWS; r++)
        {
            g.drawLine(x1, y1, x2, y2);
            
            // increase the line start and end y-coordinates(make the lines draw down 30 units)
            y1 += 30;
            y2 += 30;
        }// ends for
        
        
        
        /** Draw letters A to J on the first column of the grid */
        // set starting point
        x1 = x + 10;
        y1 = y + 50;
        
        for(int r = 1; r < NUM_ROWS; r++)
        {
            // draw the letter corresponding to the current number value of r
            g.drawString(new Character(converter.numToLetter(r)).toString(), x1, y1);
            
            // move down 30 units
            y1 += 30;
        }// ends for
        
        
        /** Draw numbers 1 to 10 on the first row of the grid */
        // set starting point
        x1 = x + 40;
        y1 = y + 20;
        
        for(int c = 1; c < NUM_COLS; c++)
        {
            // draw current number
            g.drawString(new Integer(c).toString(), x1, y1);
            
            // move right 30 units
            x1 += 30;
        }// ends for
        

        //         /** Draw a small circle on each square of the 10x10 playing grid */
        //         // set starting point
        //         x1 = x + 40;
        //         y1 = y + 40;
        //         
        //         for(int r = 1; r < NUM_ROWS; r++)
        //         {
        //             for(int c = 1; c < NUM_COLS; c++)
        //             {
        //                 g.drawOval(x1, y1, 10, 10); // draw an oval in the middle of the square
        //                 x1 += 30; // move right 30
        //             }
        //             
        //             x1 = x + 40; // reset x
        //             y1 += 30; // move down 30
        //         }// ends for
    }// ends method

}// ends class
