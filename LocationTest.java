import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test implementation of the {@link Location} class.
 * Provides unit tests for methods like {@code distance()} and {@code nextLocation()}.
 * @author David J. Barnes and Michael Kölling
 * @version 2016.02.29
 * @version 2024.10.07 DP classes (adaptado a Java 8+)
 */
public class LocationTest
{
    /**
     * Default constructor for test class LocationTest.
     */
    public LocationTest()
    {
    }

    /**
     * Sets up the test fixture.
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        // No setup required for Location tests
    }

    /**
     * Tears down the test fixture.
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
        // No teardown required for Location tests
    }
    
    /**
     * Test the {@code distance} method of the {@link Location} class.
     * Checks distances from a central location to surrounding points.
     */
    @Test
    public void testDistance()
    {
        Location origin = new Location(5, 5);

        assertEquals("La distancia a la misma ubicación debe ser cero", 0, origin.distance(origin));
        assertEquals("La distancia horiz debe coincidir con la diferencia en x", 2, origin.distance(new Location(7, 5)));
        assertEquals("La distancia vert debe coincidir con la diferencia en y", 4, origin.distance(new Location(5, 9)));
        assertEquals("La distancia diagonal debe ser el máximo delta", 3, origin.distance(new Location(8, 8)));
        assertEquals("La distancia debe contar movimientos en ambos ejes", 5, origin.distance(new Location(10, 10)));
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is adjacent 
     * (one step away in any direction).
     */
    @Test
    public void testAdjacentLocations()
    {
        Location origin = new Location(5, 5);

        Location[] adjacentDestinations = {
            new Location(5, 6), //norte
            new Location(6, 6), //noreste
            new Location(6, 5), //este
            new Location(6, 4), //sureste
            new Location(5, 4), //sur
            new Location(4, 4), //suroeste
            new Location(4, 5), //este
            new Location(4, 6)  //noreste
        };

        for (Location destination : adjacentDestinations) {
            assertEquals("Una ubi adyacente debe devolverse directamente", destination, origin.nextLocation(destination));
        }
    }
    
    /**
     * Test the {@code nextLocation} method when the destination is not adjacent 
     * (more than one step away).
     */
    @Test
    public void testNonAdjacentLocations()
    {
        Location origin = new Location(2, 2);

        Location stepEast = origin.nextLocation(new Location(5, 2));
        assertEquals("Debe avanzar un paso al este", new Location(3, 2), stepEast);

        Location stepNorth = origin.nextLocation(new Location(2, 7));
        assertEquals("Debe avanzar un paso al norte", new Location(2, 3), stepNorth);

        Location stepNorthEast = origin.nextLocation(new Location(6, 7));
        assertEquals("Debe avanzar un paso en diagonal", new Location(3, 3), stepNorthEast);
    }
}