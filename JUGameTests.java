import static org.junit.Assert.*;

import java.awt.Color;

import junit.framework.JUnit4TestAdapter;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.regex.*;

import org.junit.Test;


// assertTrue, assertEquals
public class JUGameTests
{

    // Test -- Tile Class
    @Test
    public void TileConstructorTest()
    {
        Tile t = new Tile();
        assertEquals( "<<Invalid Default Tile Constructor>>", t.value, 0 );
        t = new Tile( 1 );
        assertEquals( "<<Invalid Tile Constructor>>", t.value, 1 );
    }


    @Test
    public void TileIsEmpty()
    {
        Tile t = new Tile();
        assertTrue( "<<Invalid isEmpty>>", t.isEmpty() );
        t = new Tile( 1 );
        assertFalse( "Invalid isEmpty>>", t.isEmpty() );
    }


    @Test
    public void TileGetForeground()
    {
        Tile t = new Tile();
        assertEquals( "<<Invalid Foreground Color>>",
            t.textColor(),
            Color.BLACK );
    }


    // Test -- MainScene Class
    @Test
    public void MainSceneConstructorTest()
    {
        MainScene ms = new MainScene();
        assertFalse( "<<Invalid MainScene Constructor>>", ms.myLose );
        assertFalse( "<<Invalid MainScene Constructor >>", ms.myWin );
        assertEquals( "<<Invalid MainScene Constructor >>", ms.myScore, 0 );
    }


    // Test -- GameGrid Class
    @Test
    public void GameGridConstructorTest()
    {
        MainScene ms = new MainScene();
        GameGrid gg = new GameGrid( ms );
        assertNotNull( "<<Invalid GameGrid Constructor>>" );
        for ( Tile t : gg.getGrid() )
        {
            assertNotNull( "<<Invalid Grid Reset>>", t );
        }
    }


    public static junit.framework.Test suite()
    {
        return new JUnit4TestAdapter( JUGameTests.class );
    }


    public static void main( String args[] )
    {
        org.junit.runner.JUnitCore.main( "GameTests" );
    }

}
