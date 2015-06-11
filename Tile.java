import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * Object would be instantiated multiple times throughout the Grid. Represents
 * each playing piece that is essential to the game; changes color and label
 * with each collision.
 *
 * @author jessicajiang and sarahpark
 * @version May 13, 2015
 * @author Period: 4
 * @author Assignment: 1APCSfinalproject
 *
 * @author Sources: TODO
 */
public class Tile
{
    public int value;


    /**
     * Creates the tile
     */
    public Tile()
    {
        this( 0 );
    }


    /**
     * Creates a tile with a number inside it
     * 
     * @param num
     */
    public Tile( int num )
    {
        value = num;
    }


    /**
     * Specifies whether or not the tile is "empty" - that is, not having a
     * value at all
     * 
     * @return true or false for if the tile is empty
     */
    public boolean isEmpty()
    {
        return value == 0;
    }


    /**
     * Returns the color for the text of the numbers on the tiles
     * 
     * @return Color
     */
    public Color textColor()
    {
        return Color.BLACK;
    }


    /**
     * Returns the game for the background of the game
     * 
     * @return Color
     */
    public Color getBackground()
    {
        // BufferedImage img = null;
        switch ( value )
        {
            case 2:
                return new Color( 0xeee4da );
            case 4:
                return new Color( 0xede0c8 );
            case 8:
                return new Color( 0xf2b179 );
            case 16:
                return new Color( 0xf59563 );
            case 32:
                return new Color( 0xf67c5f );
            case 64:
                return new Color( 0xf65e3b );
            case 128:
                return new Color( 0xedcf72 );
            case 256:
                return new Color( 0xedcc61 );
            case 512:
                return new Color( 0xedc850 );
            case 1024:
                return new Color( 0xedc53f );
            case 2048:
                return new Color( 0xedc22e );
        }

        return new Color( 0xcdc1b4 );
    }
}
