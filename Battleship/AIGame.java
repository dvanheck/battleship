import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Where the flow of turns is regulated 
 * 
 * @author David Van Heck
 * @version 1.0
 */
public class AIGame implements Game
{
    // instance variables
    private BattleGrid playerGrid; // the player's grid to store it's ships
    private BattleGrid aiGrid; // the computer's grid to store it's ships
    private GridTools myGridTools; // used to convert row letters to row numbers (and visa versa)
    private Random myRng; // responsible for generating random numbers for the AI
    private JFrame myFrame; // frame to display game in
    private GamePanel myGamePanel; // panel to paint on
    private ArrayList<Coordinate> myCoordinates; // ArrayList of all coordinates on grid
    
    /**
     * Constructor for objects of class Game
     */
    public AIGame()
    {
        // initialize instance variables
        playerGrid = new BattleGrid();
        aiGrid = new BattleGrid();
        myGridTools = new GridTools();
        myRng = new Random();
        myFrame = new JFrame("Battleship");
        myGamePanel = new GamePanel();
        myCoordinates = new ArrayList<Coordinate>();
        
        for(int r = 1; r <= 10; r++)
        {
            for(int c = 1; c <= 10; c++)
            {
                myCoordinates.add(new Coordinate(r, c));
            }// ends for loop
        }// ends for loop
    }// ends constructor

    /**
     * Begins the game
     *
     */
    public void startGame()
    {
        createViewport();
        placePlayerShips();
        placeRandomShips(aiGrid);

        startTurns();
        endGame();
    }// ends method

    /**
     * Declare a winner and end the game
     *
     */
    public void endGame()
    {
        System.out.println("The game is over");
    }// ends method
    
    /**
     * Generates the window to display the game in and draws two grids
     *
     */
    private void createViewport()
    {
        createFrame();
        myGamePanel.displayBoard();
    }// ends method

    /**
     * Create the frame of the window to be displayed and add a GamePanel component
     *
     */
    private void createFrame()
    {
        // set frame to exit on the press of the x in the top right
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set look and feel
        myFrame.setDefaultLookAndFeelDecorated(true);

        // correctly size frame
        myFrame.setSize(530, 900);

        // add GamePanel component to the JFrame
        myGamePanel.setOpaque(false);
        myFrame.add(myGamePanel);

        // show the frame
        myFrame.setVisible(true);
        
    }// ends method

    /**
     * Asks the user to place their ships and adds their ships into their myShips ArrayList
     * 
     */
    private void placePlayerShips()
    {
        // vars
        Scanner in = new Scanner(System.in);
        String square; // square entered to place ship
        char inYPosLetter; // y-letter of column
        int r = -1; // row of square
        int c = -1; // column of square
        int rot = -1; // the rotation value of the ship (0 is 0/360 degrees, 1 is 90 degrees, 2 is 180 degrees, 3 is 270 degrees)
        Ship inShip;
        String shipType = new String(""); // for each type of ship to be asked to place
        String direction; // for the direction of the ship
        boolean isValid; // checks if input is valid
        boolean isDone = false; // checks if input is done being taken

        /* for loop to ask which square to place each ship on, create a new ship, and add the squares taken up by 
        the ship to the player's BattleGrid collisionGrid */
        for(int count = 0; isDone == false; count++)
        {  
            // if the loop is on the last ship, set isDone to true to end loop when finished
            if(count == playerGrid.getMaxShips() - 1)
            {
                isDone = true;
            }

            // prompt user for where to place ships
            System.out.print('\f');
            System.out.println("Time to choose where you want to place your fleet!\n");
            System.out.println("Choose the square you want to place your ships on by \nentering the letter(A - J)" +
                "followed by the number of that square(1 - 10). \nThe ship's bottom most peg will be placed \n" +
                "at the chosen square in the direction specified.\n");

            // tell them they can also random
            System.out.println("You can also just type in the word random to get randomized ships. \n");

            // set the correct ships to ask for/add on each iteration
            if(count == 0)
            {
                shipType = new String("Destroyer(2 peg-hole)");
                inShip = new Destroyer();
            }// ends else if
            else if(count == 1)
            {
                shipType = new String("Submarine(3 peg-hole)");
                inShip = new Submarine();
            }// ends else if
            else if(count == 2)
            {
                shipType = new String("Cruiser(3 peg-hole)");
                inShip = new Cruiser();
            }// ends else if
            else if(count == 3)
            {
                shipType = new String("Battleship(4 peg-hole)");
                inShip = new Battleship();
            }// ends else if
            else if(count == 4)
            {
                shipType = new String("Carrier(5 peg-hole)");
                inShip = new Carrier();
            }// ends else if
            else
            {
                System.out.println("Error: Input loop out of bounds");
                inShip = new Destroyer();
            }// ends else

            // do-while loop to validate input
            do
            {
                // assume input is valid until proven not valid
                isValid = true;

                // validate inputted square
                do
                {
                    // assume input is valid 
                    isValid = true;

                    // ask for square
                    System.out.println("\nOn which square would you like to place your " + shipType + " ship?(ex: C7)");
                    square = in.nextLine();

                    // if the square entered is the String random, set the playerGrid to have random ships and display them
                    if(square.toLowerCase().equals("random"))
                    {
                        // set the playerGrid to have random ships and display them
                        placeRandomShips(playerGrid);
                        myGamePanel.setOceanShips(playerGrid.getShips());
                        myGamePanel.displayShips();
                        
                    }// ends if
                    // else, validate square
                    else
                    {
                        // check if sqaure is valid
                        isValid = isValidSquare(square); 
                    }// ends else

                }// ends do while 
                while(isValid == false);

                // check if square entered is not random, store square coords and check collision
                if(!(square.toLowerCase().equals("random")))
                {
                    // store row number
                    inYPosLetter = square.toUpperCase().charAt(0);
                    r = myGridTools.letterToNum(inYPosLetter);

                    // store column number
                    c = Integer.parseInt(square.substring(1));

                    // validate inputted rotation
                    do
                    {
                        // assume input is valid
                        isValid = true;

                        // ask for rotation
                        System.out.println("Which direction would you like your ship to face? (up, down, left, or right)");
                        direction = in.nextLine();

                        // find store rotation value
                        if(direction.toLowerCase().equals("up"))
                        {
                            rot = 0;
                        }// ends if
                        else if(direction.toLowerCase().equals("right"))
                        {
                            rot = 1;
                        }// ends else if
                        else if(direction.toLowerCase().equals("down"))
                        {
                            rot = 2;
                        }
                        else if(direction.toLowerCase().equals("left"))
                        {
                            rot = 3;
                        }// ends else if
                        else
                        {
                            System.out.println("Error: That is not a valid direction");
                            isValid = false;
                        } // ends else
                    }// ends do while
                    while(isValid == false);

                    // if the rotation and location are are valid, check collision
                    if(isValid)
                    {                
                        // check if the ship does not collide with any other ships, if it does, store false in isValid
                        isValid = playerGrid.checkCollision(r, c, rot, inShip.getLength(), true);
                    }// ends if
                }// ends if
            }// ends do while
            while(isValid == false); // while the input is not valid

            // if user input is not to randomize the board
            if(!(square.toLowerCase().equals("random")))
            {
                // set the ship's position and rotation
                inShip.setPos(c, r);
                inShip.setRot(rot);

                // add the new ship to the player grid ArrayList
                playerGrid.addShip(inShip);

                // add the ship to the GamePanel ArrayList of ocean ships and display it
                myGamePanel.addOceanShip(inShip);
                myGamePanel.repaint();

                // set the playerGrid's squares to be taken up by ship space
                playerGrid.setSquares(r, c, rot, inShip.getLength(), inShip.getState());
            }// ends if
            // else all the ships have been already randomly generated so end the input loop
            else
            {
                isDone = true;
            }// ends if
        }// ends for

        // clear screen and display message
        System.out.print('\f');
        System.out.println("Your ships are ready for battle!");
    }// ends method

    /**
     * Places the ai's ships on the player board in random locations
     *
     */
    private void placeRandomShips(BattleGrid grid)
    {
        // vars
        int r; // row to place ship
        int c; // column to place ship
        int rot; // rotation of the ship to place
        boolean isColliding = false; // ships are collding

        // clear ships from grid
        grid.clearShips();

        // fill grid's myShips ArrayList with ships
        grid.addShip(new Destroyer());
        grid.addShip(new Submarine());
        grid.addShip(new Cruiser());
        grid.addShip(new Battleship());
        grid.addShip(new Carrier());

        // add each ship to the grid
        for(int i = 0; i < grid.getNumShips(); i++)
        {
            // do while the ship collides or goes off the grid
            do
            {
                // assume ships are not colliding
                isColliding = false;

                // generate random rotation and coordinates
                rot = myRng.nextInt(4);
                r = myRng.nextInt(10) + 1;
                c = myRng.nextInt(10) + 1;

                // check if ships collide and if they do try again
                if(grid.checkCollision(r, c, rot, grid.getShip(i).getLength(), false) == false)
                {
                    isColliding = true;
                }// ends if
            }// ends do while
            while(isColliding); 

            // set rotation and location of ship in grid
            grid.getShip(i).setRot(rot);
            grid.getShip(i).setPos(c, r);

            // place ship on grid
            grid.setSquares(r, c, rot, grid.getShip(i).getLength(), grid.getShip(i).getState());
        }// ends for

        // display message and add ships to GamePanel
        myGamePanel.setTargetShips(grid.getShips());
        System.out.println("\nThe Computer's ships are ready for battle!\n");
    }// ends method

    /**
     * Goes through the turns of the game until the game ends
     *
     */
    private void startTurns()
    {
        //vars
        boolean gameIsPlaying = true; // set to false to end the game
        boolean playerWins; // true if the player wins, false if the Computer wins
        
        // cycle turns until game ends
        while(gameIsPlaying)
        {
            // ask the user to fire on a AI square
            playerFire();
            
            // if, there are no more ships on the aiGrid, the player wins and the game ends
            if(aiGrid.getNumShips() == 0)
            {
                System.out.println("You win!!!!");
                playerWins = true;
                gameIsPlaying = false;
            }// ends if
            
            // if the game is still playing
            if(gameIsPlaying)
            {
                // have the AI fire on a player square
                aiFire();
                
                // if there are no more ships on the playerGrid, the AI wins and the game ends
                if(playerGrid.getNumShips() == 0)
                {
                    System.out.println("The Computer wins!!!!");
                    playerWins = false;
                    gameIsPlaying = false;
                }// ends if
            }// ends if
        }// ends while 
    }// ends method

    /**
     * Asks the player for the square they would like to fire on and fires a shot on the ai's grid
     *
     */
    private void playerFire()
    {
        // vars
        boolean isValid = true; // for checking if input is valid
        boolean isHit; // set to true if a ship was hit by the shot and false if the shot was a miss
        Scanner in = new Scanner(System.in); // for taking input
        String square; // for the inputted square
        int r = -1; // for the number of the row
        int c = -1; // for the number of the column
        char rowLetter; // for the letter of the row on the grid
        
        // cheat names
        String enemyShipsCheat = new String("showenemyships"); 
        String hideEnemyShips = new String("hideenemyships");

        String squareState; // for the state of square
        String sunkenShip; // for the name of a sunken ship

        // display that it is time to fire on the opponent's ships
        System.out.println("Your turn:");

        // do while to validate square
        do
        {
            // ask user where he wants to shoot
            System.out.println("Where would you like to shoot?");
            square = new String(in.nextLine());

            // if the showEnemyShips cheat code was entered, show enemy ships and set isValid to false
            if(square.toLowerCase().equals(enemyShipsCheat))
            {
                myGamePanel.setShouldDrawEnemyShips(true);
                myGamePanel.displayShips();
                isValid = false;
            }// ends if
            // if the hideEnemyShips cheat was entered, hide enemy ships and set isValid to false
            else if(square.toLowerCase().equals(hideEnemyShips))
            {
                myGamePanel.setShouldDrawEnemyShips(false);
                myGamePanel.displayShips();
                isValid = false;
            }// ends else if
            // else check if square is valid
            else
            {
                // check if square is valid and set isValid to true or false
                isValid = isValidSquare(square);

                // if the square is valid
                if(isValid)
                {
                    // store row and column
                    r = myGridTools.letterToNum(square.toUpperCase().charAt(0));
                    c = Integer.parseInt(square.substring(1));
                    
                    // check if the square is already hit(6) or already missed(7), and display message and set isValid to false
                    if(aiGrid.getSquareState(r, c) == 6)
                    {
                        System.out.println("You already missed on that square! Try again!\n");
                        isValid = false;
                    }// ends if
                    else if(aiGrid.getSquareState(r, c) == 7)
                    {
                        System.out.println("You have already hit that square! Try again!\n");
                        isValid = false;
                    }// ends else if
                    else if(aiGrid.getSquareState(r, c) == 8)
                    {
                        System.out.println("You have already sunken a ship on that square! Try again!\n");
                        isValid = false;
                    }// ends else if
                }// ends if
            }// ends else
        }// ends do while
        while(isValid == false);
        
        // store the letter of the row for use in displaying messages
        rowLetter = myGridTools.numToLetter(r);
        
        // store current state of the square
        squareState = aiGrid.squareStateToString(r, c);

        /* fire on the opponent's square(set the ai battlegrid to a hit or missed square and store if the square was hit
        or not in isHit) */
        isHit = aiGrid.fireOnSquare(r, c);

        // the GamePanel targetHitGrid to the hit or miss at the inputted square
        myGamePanel.setTargetHitGrid(r, c, isHit);
        // draw the marker
        myGamePanel.repaint();
        
        // print a line for spacing
        System.out.println();
        
        // display a message for if the ship was hit or not
        if(isHit)
        {
            System.out.println("You have hit the enemy " + squareState + " on " + rowLetter + "" + c + "!\n"); 

            // if a ship is sunken
            sunkenShip = aiGrid.findSunkenShip();
            if(!(sunkenShip == ""))
            {
                // display which ship is sunken
                System.out.println("You have sunk the enemy " + sunkenShip + "!\n");
            }// ends if
        }
        else
        {
            System.out.println("You have missed on " + rowLetter + "" + c + "!\n");
        }// ends else if
    }// ends method

    /**
     * Fires a random shot on the player's grid 
     *
     */
    private void aiFire()
    {
        // vars
        int randIndex; // random index of coordinates ArrayList
        Coordinate randCoord; // random coordinate taken from the random index of the myCoordinates ArrayList
        int r; // the row value of the square to fire on
        int c; // the column value of the square to fire on
        char rowLetter; // for the letter value of the row to display to the user
        boolean isHit; // if the square is a hit or not
        String squareState; // for the state of square
        String sunkenShip; // for sunkenShip to display
        Scanner in = new Scanner(System.in);
        
        // display that it is the ai's turn to fire
        System.out.println("\nComputer's turn:\n");
        
        // if there are no current hits on playerGrid, fire a random shot
        if(playerGrid.hasHitMarkers() == false)
        {
            // generate random index based of size of ArrayList
            randIndex = myRng.nextInt(myCoordinates.size() - 1);
            
            // get coordinate from ArrayList and remove element
            randCoord = myCoordinates.get(randIndex);
            myCoordinates.remove(randIndex);
            
            // store coordinates in r and c
            r = randCoord.getRow();
            c = randCoord.getColumn();
            rowLetter = myGridTools.numToLetter(r);
            
            // store current state of the square
            squareState = playerGrid.squareStateToString(r, c);
        
            // fire on the player's Grid at those random coordinates and set if a ship was hit or not
            isHit = playerGrid.fireOnSquare(r, c);
            
            // the GamePanel targetHitGrid to the hit or miss at the inputted square
            myGamePanel.setOceanHitGrid(r, c, isHit);
            // draw the marker
            myGamePanel.repaint();
            
            // display a message for if the ship was hit or not
            if(isHit)
            {
                System.out.println("The Computer has hit your " + squareState + " on " + rowLetter + "" + c + "!\n"); 
    
                // if a ship is sunken, decrement health and display message
                sunkenShip = playerGrid.findSunkenShip();
                if(!(sunkenShip == ""))
                {
                    // display which ship is sunken
                    System.out.println("The Computer has sunk your " + sunkenShip + "!\n");
                }// ends if
            }// ends if
            else
            {
                System.out.println("The Computer has missed on " + rowLetter + "" + c + "!\n");
            }// ends else if
            
            // ask user to press enter to continue
            System.out.print("Press enter to continue...");
            in.nextLine();
            
            // clear terminal window
            System.out.print('\f');
        }// ends if
        // if there are hits on playerGrid
        else if(playerGrid.hasHitMarkers())
        {
            // if there is only 1 hitmarker
            if(playerGrid.getNumHits() == 1)
            {
                // find location of hit
                r = playerGrid.findFirstHit().getRow();
                c = playerGrid.findFirstHit().getColumn();
                
                // fire a random shot at the square that is directly above, below, to the left, or to the right of the hit square
                // make ArrayList of possible coordinates to hit
                ArrayList<Coordinate> possibleCoords = new ArrayList<Coordinate>();
                
                // add the coordinates if they have not been fired on yet
                if(isValidSquare(r - 1, c) == true && playerGrid.hasBeenFiredOn(r - 1, c) == false)
                {
                    possibleCoords.add(new Coordinate(r - 1, c));
                }// ends if
                
                if(isValidSquare(r + 1, c) == true && playerGrid.hasBeenFiredOn(r + 1, c) == false)
                {
                    possibleCoords.add(new Coordinate(r + 1, c));
                }// ends if
                
                if(isValidSquare(r, c - 1) == true && playerGrid.hasBeenFiredOn(r, c - 1) == false)
                {
                    possibleCoords.add(new Coordinate(r, c - 1));
                }// ends if
                
                if(isValidSquare(r, c + 1) == true && playerGrid.hasBeenFiredOn(r, c + 1) == false)
                {
                    possibleCoords.add(new Coordinate(r, c + 1));
                }// ends if
                
                // if there is only one coordinate, pick that coordinate
                if(possibleCoords.size() == 1)
                {
                    randCoord = possibleCoords.get(0);
                    randIndex = 0;
                }// ends if
                else
                {
                    // pick random coordinate from ArrayList of possible coords
                    randIndex = myRng.nextInt(possibleCoords.size() - 1);
                    randCoord = possibleCoords.get(randIndex);
                }// ends if
                
                // store row and column
                r = randCoord.getRow();
                c = randCoord.getColumn();
                rowLetter = myGridTools.numToLetter(r);
                
                // remove coordinate from possCoords and from the total ArrayList of Coordinates
                removeFromMyCoordinates(randCoord);
                possibleCoords.remove(randIndex);
                
                // store current state of the square
                squareState = playerGrid.squareStateToString(r, c);
            
                // fire on the player's Grid at those random coordinates and set if a ship was hit or not
                isHit = playerGrid.fireOnSquare(r, c);
                
                // the GamePanel targetHitGrid to the hit or miss at the inputted square
                myGamePanel.setOceanHitGrid(r, c, isHit);
                // draw the marker
                myGamePanel.repaint();
                
                // display a message for if the ship was hit or not
                if(isHit)
                {
                    System.out.println("The Computer has hit your " + squareState + " on " + rowLetter + "" + c + "!\n"); 
        
                    // if a ship is sunken, decrement health and display message
                    sunkenShip = playerGrid.findSunkenShip();
                    if(!(sunkenShip == ""))
                    {
                        // display which ship is sunken
                        System.out.println("The Computer has sunk your " + sunkenShip + "!\n");
                    }// ends if
                }// ends if
                else
                {
                    System.out.println("The Computer has missed on " + rowLetter + "" + c + "!\n");
                }// ends else if
                
                // ask user to press enter to continue
                System.out.print("Press enter to continue...");
                in.nextLine();
                
                // clear terminal window
                System.out.print('\f');
            
            }// ends if
            // if there is more than 1 hitmarker
            else if(playerGrid.getNumHits() > 1)
            {
                // determine the direction of the hits
                if(playerGrid.hitsAreHorizontal() == true)
                {
                    // find first and last hit in the line of hits
                    Coordinate firstHit = new Coordinate(playerGrid.findFirstHit());
                    Coordinate lastHit = new Coordinate(playerGrid.findLastHit(true));
                    
                    // ArrayList of possible coordinates
                    ArrayList<Coordinate> possCoords = new ArrayList<Coordinate>();
                    
                    // check if coordinates are already hit or missed
                    // then, add possible coordinates at the first and last point of the line of hits
                    if(firstHit.getColumn() - 1 > 0 && playerGrid.hasBeenFiredOn(firstHit.getRow(), firstHit.getColumn() - 1) == false)     
                    {
                        possCoords.add(new Coordinate(firstHit.getRow(), firstHit.getColumn() - 1));
                    }// ends if
                    
                    if(lastHit.getColumn() + 1 <= 10 && playerGrid.hasBeenFiredOn(lastHit.getRow(), lastHit.getColumn() + 1) == false)
                    {
                        possCoords.add(new Coordinate(lastHit.getRow(), lastHit.getColumn() + 1));
                    }// ends if
                    
                    /* if there are no possible coordinates in the ArrayList, add possible coordinates
                     * that are above/below
                     */
                    
                    // if there is only one coordinate, pick that coordinate
                    if(possCoords.size() == 1)
                    {
                        randCoord = possCoords.get(0);
                        randIndex = 0;
                    }// ends if
                    else
                    {
                        // pick random coordinate from ArrayList of possible coords
                        randIndex = myRng.nextInt(possCoords.size() - 1);
                        randCoord = possCoords.get(randIndex);
                    }// ends if
                    
                    // store row and column
                    r = randCoord.getRow();
                    c = randCoord.getColumn();
                    rowLetter = myGridTools.numToLetter(r);
                    
                    // remove coordinate from possCoords and from the total ArrayList of Coordinates
                    removeFromMyCoordinates(randCoord);
                    possCoords.remove(randIndex);
                    
                    // store current state of the square
                    squareState = playerGrid.squareStateToString(r, c);
                
                    // fire on the player's Grid at those random coordinates and set if a ship was hit or not
                    isHit = playerGrid.fireOnSquare(r, c);
                    
                    // the GamePanel targetHitGrid to the hit or miss at the inputted square
                    myGamePanel.setOceanHitGrid(r, c, isHit);
                    // draw the marker
                    myGamePanel.repaint();
                    
                    // display a message for if the ship was hit or not
                    if(isHit)
                    {
                        System.out.println("The Computer has hit your " + squareState + " on " + rowLetter + "" + c + "!\n"); 
            
                        // if a ship is sunken, decrement health and display message
                        sunkenShip = playerGrid.findSunkenShip();
                        if(!(sunkenShip == ""))
                        {
                            // display which ship is sunken
                            System.out.println("The Computer has sunk your " + sunkenShip + "!\n");
                        }// ends if
                    }// ends if
                    else
                    {
                        System.out.println("The Computer has missed on " + rowLetter + "" + c + "!\n");
                    }// ends else if
                    
                    // ask user to press enter to continue
                    System.out.print("Press enter to continue...");
                    in.nextLine();
                    
                    // clear terminal window
                    System.out.print('\f');
            
                }// ends if
                else if(playerGrid.hitsAreHorizontal() == false)
                {
                    // find first and last hit in the line of hits
                    Coordinate firstHit = new Coordinate(playerGrid.findFirstHit());
                    Coordinate lastHit = new Coordinate(playerGrid.findLastHit(false));
                    
                    // ArrayList of possible coordinates
                    ArrayList<Coordinate> possCoords = new ArrayList<Coordinate>();
                    
                    // check if coordinates are already hit or missed
                    // then, add possible coordinates at the first and last point of the line of hits
                    if(firstHit.getRow() - 1 > 0 && playerGrid.hasBeenFiredOn(firstHit.getRow() - 1, firstHit.getColumn()) == false)
                    {
                        possCoords.add(new Coordinate(firstHit.getRow() - 1, firstHit.getColumn()));
                    }// ends if
                    
                    if(lastHit.getRow() + 1 <= 10 && playerGrid.hasBeenFiredOn(lastHit.getRow() + 1, lastHit.getColumn()) == false)
                    {
                        possCoords.add(new Coordinate(lastHit.getRow() + 1, lastHit.getColumn()));
                    }// ends if
                    
                    // if there is only one coordinate, pick that coordinate
                    if(possCoords.size() == 1)
                    {
                        randCoord = possCoords.get(0);
                        randIndex = 0;
                    }// ends if
                    else
                    {
                        // pick random coordinate from ArrayList of possible coords
                        randIndex = myRng.nextInt(possCoords.size() - 1);
                        randCoord = possCoords.get(randIndex);
                    }// ends if
                    
                    // store row and column
                    r = randCoord.getRow();
                    c = randCoord.getColumn();
                    rowLetter = myGridTools.numToLetter(r);
                    
                    // remove coordinate from possCoords and from the total ArrayList of Coordinates
                    removeFromMyCoordinates(randCoord);
                    possCoords.remove(randIndex);
                    
                    // store current state of the square
                    squareState = playerGrid.squareStateToString(r, c);
                
                    // fire on the player's Grid at those random coordinates and set if a ship was hit or not
                    isHit = playerGrid.fireOnSquare(r, c);
                    
                    // the GamePanel targetHitGrid to the hit or miss at the inputted square
                    myGamePanel.setOceanHitGrid(r, c, isHit);
                    // draw the marker
                    myGamePanel.repaint();
                    
                    // display a message for if the ship was hit or not
                    if(isHit)
                    {
                        System.out.println("The Computer has hit your " + squareState + " on " + rowLetter + "" + c + "!\n"); 
            
                        // if a ship is sunken, decrement health and display message
                        sunkenShip = playerGrid.findSunkenShip();
                        if(!(sunkenShip == ""))
                        {
                            // display which ship is sunken
                            System.out.println("The Computer has sunk your " + sunkenShip + "!\n");
                        }// ends if
                    }// ends if
                    else
                    {
                        System.out.println("The Computer has missed on " + rowLetter + "" + c + "!\n");
                    }// ends else if
                    
                    // ask user to press enter to continue
                    System.out.print("Press enter to continue...");
                    in.nextLine();
                    
                    // clear terminal window
                    System.out.print('\f');
            
                }
                 
            }// ends else if
        }// ends else if
    }// ends method
    
    /**
     * Method that removes the specified Coordinate from the myCoordinates array
     *
     * @param  coord   The Coordinate to be removed from myCoordinates
     */
    public void removeFromMyCoordinates(Coordinate coord)
    {
        // go through array and remove Coordinate once found
        for(int i = 0; i < myCoordinates.size(); i++)
        {
            if(myCoordinates.get(i).getRow() == coord.getRow() && myCoordinates.get(i).getColumn() == coord.getColumn())
            {
                myCoordinates.remove(i);
            }// ends if
        }// ends for
    }// ends method
    
    /**
     * Checks if the inputted String of a square coordinate(ex: c8) is a valid square
     *
     * @param  square   String of square coordinate (ex: a5) 
     * @return  isValid   true if square is valid and false if square is invalid
     */
    private boolean isValidSquare(String square)
    {
        boolean isValid; // set to true if square is valid and false if square is invalid
        char inYPosLetter; // letter for the y coordinate
        int r; // number of the row
        int c; // number of the column
    
        // if the text entered is 2 characters, then separate and check the individual coordinates
        if(square.length() == 2 || square.length() == 3)
        {
            // store the row
            inYPosLetter = square.toUpperCase().charAt(0);
            r = myGridTools.letterToNum(inYPosLetter); // stores row
    
            // if row is in bounds, set isValid to true and check column
            if(r >= 1 && r <= 10)
            {
                isValid = true;
    
                try{
                    c = Integer.parseInt(square.substring(1));
                }// ends try 
                catch (NumberFormatException e)
                {
                    System.out.println("Error: Please enter a valid square");
                    isValid = false;
                    return isValid;
                }// ends catch
    
                Integer column = new Integer(c); // stores column to make to string
    
                // if the string of the number in column has a 0 as it's first character, set isValid to false
                if(column.toString().charAt(0) == 48)
                {
                    isValid = false;
                    System.out.println("Error: Enter a valid square");
                }// ends if
                // if column is valid(within bounds of grid), the input is valid
                else if(c >= 1 && c <= 10)
                {
                    isValid = true;
                }// ends if
                // if not, set isValid to false
                else
                {
                    System.out.println("Error: That is not a valid square");
                    isValid = false;
                }// ends else
            }// ends if
            // if valid, store and check column
            else
            {
                System.out.println("Error: That is not a valid square");
                isValid = false;
            }// ends else
        }
        // if the text is not 2 characters, display an error message and set isValid to false
        else
        {
            System.out.println("Error: Please enter a valid square by typing the square letter then the number. (ex: A4)");
            isValid = false;
        }// ends else
    
        return isValid;
    }// ends method
    
    /**
     * Checks if the inputted String of a square coordinate(ex: c8) is a valid square
     *
     * @param  r  the row value of the square to check
     * @param  c  the column value of the row to check
     * @return  isValid   true if square is valid and false if square is invalid
     */
    private boolean isValidSquare(int rParam, int cParam)
    {
        boolean isValid; // set to true if square is valid and false if square is invalid
        int r = rParam; // number of the row
        int c = cParam; // number of the column
        
        // if row is in bounds, set isValid to true 
        if(r >= 1 && r <= 10 && c >= 1 && c <= 10)
        {
            isValid = true;
        }// ends if
        // else, set isValid to false
        else
        {
            isValid = false;
        }// ends else
    
        return isValid;
    }// ends method
    
}// ends class
