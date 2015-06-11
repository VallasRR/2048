import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;


/**
 * The Grid class is the entire background and playing field for the game. This
 * means that all movement and interaction between the tiles is accounted for in
 * the Grid class.
 * 
 *
 * @author jessicajiang and sarahpark
 * @version May 27, 2015
 * @author Period: 4
 * @author Assignment: 1APCSfinalproject
 *
 * @author Sources: TODO
 */
public class GameGrid
{
    private static final String FONT_NAME = "Georgia";

    // font of the numbers of the tiles

    private Tile[] myTiles;

    private MainScene scene;


    // =========================================================================
    // Grid
    // =========================================================================

    public GameGrid( MainScene myScene )
    {
        scene = myScene;

        // scene.resetGame();
        resetGrid();
    }


    /**
     * Returns location of tile
     * 
     * @param x
     * @param y
     * @return Tile
     */
    private Tile tileLocation( int x, int y )
    {
        return myTiles[x + y * 4];
    }


    /**
     * Returns the array of tiles
     * 
     * @return Tile[]
     */
    public Tile[] getGrid()
    {
        return myTiles;
    }


    /**
     * Resets the grid to have empty tiles, creates two new tiles
     */
    public void resetGrid()
    {
        myTiles = new Tile[4 * 4];
        for ( int i = 0; i < myTiles.length; i++ )
        {
            myTiles[i] = new Tile();
        }
        addTile();
        addTile();
    }


    /**
     * Adds a tile into the available spaces in the grid
     */
    private void addTile()
    {
        List<Tile> list = availableSpace();
        if ( !availableSpace().isEmpty() )
        {
            int index = (int)( Math.random() * list.size() ) % list.size();
            Tile emptyTile = list.get( index );
            if ( Math.random() < 0.9 )
            {
                emptyTile.value = 2;
            }
            else
            {
                emptyTile.value = 4;
            }
        }
    }


    /**
     * Returns a list of the available spaces left in the grid
     * 
     * @return list of spaces
     */
    private List<Tile> availableSpace()
    {
        final List<Tile> list = new ArrayList<Tile>( 16 );
        for ( Tile t : myTiles )
        {
            if ( t.isEmpty() )
            {
                list.add( t );
            }
        }
        return list;
    }


    /**
     * Boolean to check if the grid is full or not
     * 
     * @return boolean for true or false
     */
    private boolean isFull()
    {
        return availableSpace().size() == 0;
    }


    /**
     * Boolean to see if there are any available moves left
     * 
     * @return true or false if there are any moves
     */
    public boolean canMove()
    {
        if ( !isFull() )
        {
            return true;
        }
        // iterates through the entire grid to check the free space
        for ( int x = 0; x < 4; x++ )
        {
            for ( int y = 0; y < 4; y++ )
            {
                Tile t = tileLocation( x, y );
                if ( ( x < 3 && t.value == tileLocation( x + 1, y ).value )
                    || ( ( y < 3 ) && t.value == tileLocation( x, y + 1 ).value ) )
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Compares two lines in the grid to each other to see if they are equal
     * (basically if they are ready to be added)
     * 
     * @param line1
     * @param line2
     * @return
     */
    private boolean compare( Tile[] line1, Tile[] line2 )
    {
        if ( line1 == line2 )
        {
            return true;
        }
        else if ( line1.length != line2.length )
        {
            return false;
        }

        for ( int i = 0; i < line1.length; i++ )
        {
            if ( line1[i].value != line2[i].value )
            {
                return false;
            }
        }
        return true;
    }


    /**
     * Rotates the tiles so that the "left" method can be reused by right, up,
     * and down
     * 
     * @param angle
     * @return
     */
    private Tile[] rotate( int angle )
    {
        Tile[] newTiles = new Tile[4 * 4];
        int xoff = 3;
        int yoff = 3;
        if ( angle == 90 )
        {
            yoff = 0;
        }
        else if ( angle == 270 )
        {
            xoff = 0;
        }

        double rad = Math.toRadians( angle );
        int cos = (int)Math.cos( rad );
        int sin = (int)Math.sin( rad );
        for ( int x = 0; x < 4; x++ )
        {
            for ( int y = 0; y < 4; y++ )
            {
                int newX = ( x * cos ) - ( y * sin ) + xoff;
                int newY = ( x * sin ) + ( y * cos ) + yoff;
                newTiles[( newX ) + ( newY ) * 4] = tileLocation( x, y );
            }
        }
        return newTiles;
    }


    /**
     * Moves one line to the other lines to prepare numbers for adding
     * 
     * @param oldLine
     * @return
     */
    private Tile[] moveLine( Tile[] oldLine )
    {
        LinkedList<Tile> l = new LinkedList<Tile>();
        for ( int i = 0; i < 4; i++ )
        {
            if ( !oldLine[i].isEmpty() )
                l.addLast( oldLine[i] );
        }
        if ( l.size() == 0 )
        {
            return oldLine;
        }
        else
        {
            Tile[] newLine = new Tile[4];
            ensureSizes( l, 4 );
            for ( int i = 0; i < 4; i++ )
            {
                newLine[i] = l.removeFirst();
            }
            return newLine;
        }
    }


    /**
     * Merges two lines together, whether they are the same line or not
     * 
     * @param oldLine
     * @return
     */
    private Tile[] mergeLine( Tile[] oldLine )
    {
        LinkedList<Tile> list = new LinkedList<Tile>();
        for ( int i = 0; i < 4 && !oldLine[i].isEmpty(); i++ )
        {
            int num = oldLine[i].value;
            if ( i < 3 && oldLine[i].value == oldLine[i + 1].value )
            {
                num *= 2;
                scene.incrementScore( num );
                //WINNING SCORE HERE
                int target = 2048;
                if ( num == target )
                {
                    scene.myWin = true;
                }
                i++;
            }
            list.add( new Tile( num ) );
        }
        if ( list.size() == 0 )
        {
            return oldLine;
        }
        else
        {
            ensureSizes( list, 4 );
            return list.toArray( new Tile[4] );
        }
    }


    /**
     * Adds new tiles until the grid the grid equals the size b
     * 
     * @param l
     * @param s
     */
    private static void ensureSizes( java.util.List<Tile> a, int b )
    {
        while ( a.size() != b )
        {
            a.add( new Tile() );
        }
    }


    /**
     * Locates the line of the array
     * 
     * @param index
     * @return
     */
    private Tile[] getLine( int index )
    {
        Tile[] result = new Tile[4];
        for ( int i = 0; i < 4; i++ )
        {
            result[i] = tileLocation( i, index );
        }
        return result;
    }


    /**
     * Copies the result array into the indexed row
     * 
     * @param index
     * @param result
     */
    private void setLine( int index, Tile[] result )
    {
        for ( int i = 0; i < 4; i++ )
        {
            myTiles[index * 4 + i] = result[i];
        }

    }


    // ==========================================================================
    // Movement
    // ==========================================================================
    /**
     * Moves the tiles left. The following methods below use this left method as
     * well.
     */
    public void left()
    {
        boolean needAddTile = false;
        for ( int i = 0; i < 4; i++ )
        {
            Tile[] line = getLine( i );
            Tile[] merged = mergeLine( moveLine( line ) );
            setLine( i, merged );
            if ( !needAddTile && !compare( line, merged ) )
            {
                needAddTile = true;

            }
        }

        if ( needAddTile )
        {
            addTile();
        }
    }


    public void right()
    {
        myTiles = rotate( 180 );
        left();
        myTiles = rotate( 180 );
    }


    public void up()
    {
        myTiles = rotate( 270 );
        left();
        myTiles = rotate( 90 );
    }


    public void down()
    {
        myTiles = rotate( 90 );
        left();
        myTiles = rotate( 270 );
    }
}
