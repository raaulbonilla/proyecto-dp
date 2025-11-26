import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para {@link ChargingStation}.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ChargingStationTest
{
    private ChargingStation chargingStation;

    /**
     * Constructor por defecto para la clase de pruebas {@code ChargingStationTest}.
     */
    public ChargingStationTest()
    {
    }

    /**
     * Configura el escenario de pruebas.
     *
     * Se ejecuta antes de cada método de prueba.
     */
    @BeforeEach
    public void setUp()
    {
        chargingStation = new ChargingStation("Madrid", "CS-001", new Location(10, 20));
    }

    /**
     * Limpia el escenario de pruebas.
     *
     * Se ejecuta después de cada método de prueba.
     */
    @AfterEach
    public void tearDown()
    {
        chargingStation = null;
    }

    /**
     * Comprueba que una estación de carga admite la incorporación de un nuevo cargador
     * y que queda accesible mediante sus métodos de consulta.
     */
    @Test
    public void testAddCharger()
    {
        Charger newCharger = new Charger("CH-001", 150, 0.35);

        assertEquals(0, chargingStation.getChargers().size(), "La estación no debe contener cargadores antes de añadir uno nuevo");

        chargingStation.addCharger(newCharger);

        assertEquals(1, chargingStation.getChargers().size(), "La estación debe incrementar el número de cargadores tras añadir uno");
        assertTrue(chargingStation.getChargers().contains(newCharger), "El nuevo cargador debe formar parte de la colección de la estación");
        assertSame(newCharger, chargingStation.getChargerById("CH-001"), "El cargador debe poder recuperarse por su identificador");
    }
}