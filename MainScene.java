import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * The MainScene class manages all the game logistics. This means that the
 * score, options, pausing, ending, and restarting the game are all managed in
 * the MainScene.
 * 
 *
 * @author jessicajiang and sarahpark
 * @version May 11, 2015
 * @author Period: TODO
 * @author Assignment: 1APCSfinalproject
 *
 * @author Sources:
 *         https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html
 *         http:
 *         //docs.oracle.com/javase/7/docs/api/java/awt/event/KeyListener.html
 *         http://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
 */
public class MainScene extends JPanel
{
    private static final Color BG_COLOR = new Color( 0x6B5A62 );

    // background color of playing screen
    private static final String FONT_NAME = "Questrial";

    // font of the numbers of the tiles
    private static final int TILE_SIZE = 64;

    // size of each tile
    private static final int TILES_MARGIN = 16;

    // margin between the tiles
    private GameGrid grid;

    // creating an object of the GameGrid class

    protected boolean myWin = false;

    protected boolean myLose = false;

    protected int myScore = 0;


    public MainScene()
    {
        setFocusable( true ); // focusable flag indicates whether a component
        // can gain the focus if it is requested to do so

        grid = new GameGrid( this );

        // deals with all the keystroke registering
        // keyadapter allows the keys pressed to work
        addKeyListener( new KeyAdapter()
        {
            // keypressing
            public void keyPressed( KeyEvent e )
            {
                // when the escape button is pressed (VK_SPACE), call reset
                // game
                if ( e.getKeyCode() == KeyEvent.VK_SPACE )
                {
                    resetGame();
                }

                if ( !grid.canMove() )
                {
                    myLose = true;
                }

                // connects the arrow keys to their movement functions in the
                // GameGrid class; respectively, left, right, down, and up
                if ( !myWin && !myLose )
                {
                    switch ( e.getKeyCode() )
                    {
                        case KeyEvent.VK_LEFT:
                            grid.left();
                            break;
                        case KeyEvent.VK_RIGHT:
                            grid.right();
                            break;
                        case KeyEvent.VK_DOWN:
                            grid.down();
                            break;
                        case KeyEvent.VK_UP:
                            grid.up();
                            break;
                    }
                }

                if ( !myWin && !grid.canMove() )
                {
                    myLose = true;
                }

                repaint();
            }
        } );

        myScore = 0;
        myWin = false;
        myLose = false;
    }


    /**
     * Resets the game back to the score being 0, and resets the grid to empty
     * tiles.
     */
    public void resetGame()
    {
        myScore = 0;
        myWin = false;
        myLose = false;
        grid.resetGrid();
    }


    /**
     * Adds the score for every move that is made.
     * 
     * @param score
     */
    public void incrementScore( int score )
    {
        myScore += score;
    }


    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics) Paints the
     * background of the scene to the background color and draws the tiles in
     * the grid
     */
    public void paint( Graphics g )
    {
        super.paint( g );
        g.setColor( BG_COLOR );
        g.fillRect( 0, 0, this.getSize().width, this.getSize().height );
        for ( int y = 0; y < 4; y++ )
        {
            for ( int x = 0; x < 4; x++ )
            {
                drawTile( g, grid.getGrid()[x + y * 4], x, y );
            }
        }
    }


    /**
     * Draws each tile into the grid.
     * 
     * @param g2
     * @param tile
     * @param x
     * @param y
     */
    private void drawTile( Graphics g2, Tile tile, int x, int y )
    {
        Graphics2D g = ( (Graphics2D)g2 );
        // here
        int value = tile.value;
        int xOffset = tileCoordinates( x );
        int yOffset = tileCoordinates( y );
        g.setColor( tile.getBackground() );
        g.fillRoundRect( xOffset, yOffset, TILE_SIZE, TILE_SIZE, 14, 14 );
        g.setColor( tile.textColor() );
        final int fsize;
        if ( value < 100 )
        {
            fsize = 36;
        }
        else if ( value < 1000 )
        {
            fsize = 32;
        }
        else
        {
            fsize = 24;
        }
        Font font = new Font( FONT_NAME, Font.BOLD, fsize );
        g.setFont( font );

        String s = String.valueOf( value );
        final FontMetrics fm = getFontMetrics( font );

        final int w = fm.stringWidth( s );
        final int h = -(int)fm.getLineMetrics( s, g ).getBaselineOffsets()[2];

        if ( value != 0 )
            g.drawString( s, xOffset + ( TILE_SIZE - w ) / 2, yOffset
                + TILE_SIZE - ( TILE_SIZE - h ) / 2 - 2 );

        // =================================================================
        // Winning/Losing Conditions
        // ===================================================================
        if ( myWin || myLose )
        {
            // TODO://edit window GUI
            g.setColor( Color.lightGray ); // sets the background color of the
                                           // last screen
            g.fillRect( 0, 0, getWidth(), getHeight() );
            g.setColor( Color.white ); // sets the color of the text
            g.setFont( new Font( FONT_NAME, Font.BOLD, 48 ) );
            // writes the game over strings
            if ( myWin )
            {
                g.drawString( "You won!", 65, 150 );
            }
            if ( myLose )
            {
                g.drawString( "You Lose!", 50, 130 );
            }
            if ( myWin || myLose )
            {
                g.setFont( new Font( FONT_NAME, Font.ITALIC, 16 ) );
                g.setColor( new Color( 128, 128, 128, 128 ) );
                g.drawString( "Press SPACE to play again", 80, getHeight() - 60 );
            }
        }
        g.setFont( new Font( FONT_NAME, Font.PLAIN, 18 ) );
        g.drawString( "Score: " + myScore, getWidth() - 150, getHeight() - 20 );

    }


    /**
     * Finds the leftmost or topmost pixel of the nth single square tile
     * 
     * @param n
     * @return integer
     */
    private static int tileCoordinates( int n )
    {
        return n * ( TILES_MARGIN + TILE_SIZE ) + TILES_MARGIN;
    }

}
