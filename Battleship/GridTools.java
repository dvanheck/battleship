
/**
 * Has methods to convert between the two ways of representing rows (letter or number)
 * 
 * 
 * @author David Van Heck
 * @version 1.0
 */
public class GridTools
{
    // instance variables
    private final int mySIZE; // length and width of square grid
    private int[] myColVals; // 1 - 10 going from left to right on the board
    private int[] myRowVals; // 1 - 10 going from top to bottom
    private char[] myRowLetters; // A to J going from top to bottom on the board
    
    /**
     * Constructor for objects of class Grid
     */
    public GridTools()
    {
        // initialise instance variables
        mySIZE = 10;
        myColVals = new int[mySIZE];
        myRowVals = new int[mySIZE];
        myRowLetters = new char[mySIZE];
        
        char aToJ = 'A'; // to decrement down from J to A and store each letter in myRowLetters
        
        // store row, col, and letters in arrays
        for(int i = 0; i < mySIZE; i++)
        {
            myColVals[i] = i + 1;
            myRowVals[i] = i + 1;
            myRowLetters[i] = aToJ;
            aToJ++;
        }// ends for
        
    }// ends constructor

    /**
     * Converts a given letter from A - J to the y-coordinate on the coordinate plane (ex: B would become 9 or J would become 1)
     * If the letter is invalid, return -1
     * 
     * @param  l   the letter to be converted
     * @return   the converted y-coordinate
     */
    public int letterToNum(char letter)
    {
        // check which number the letter should become and return it
        for(int i = 0; i < mySIZE; i++)
        {
            if(letter == myRowLetters[i])
            {
                return myRowVals[i];
            }// ends if
        }// ends for
        
        // if the letter is not valid, return -1
        return -1;
    }// ends method
    
    /**
     * Converts a given number on the coordinate plane to a letter from A - J to the(ex: C would become 8 or 2 would become I)
     * If the number is invalid, return z
     * 
     * @param  num   the y-coordinate to be converted to a letter
     * @return   the converted letter
     */
    public char numToLetter(int num)
    {
        // check which letter the y-coordinate should become and return it
        for(int i = 0; i <= mySIZE; i++)
        {
            if(num == myRowVals[i])
            {
                return myRowLetters[i];
            }// ends if
        }// ends for
        
        // if the number is not valid
        return 'z';
    }// ends method
}
