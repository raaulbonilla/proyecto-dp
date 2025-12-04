import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para {@link Charger}.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ChargerTest
{
    private Charger charger;
    private ElectricVehicle vehicle;
    
    /**
     * Constructor por defecto para la clase de pruebas {@code ChargerTest}.
     */
    public ChargerTest()
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
        EVCompany company = new EVCompany("Test Company");
        Location start = new Location(0, 0);
        Location destination = new Location(5, 5);
        vehicle = new ElectricVehicle(company, start, destination, "Model S", "EV-123", 100);
        charger = new Charger("CH-01", 50, 0.35);
    }

    /**
     * Limpia el escenario de pruebas.
     *
     * Se ejecuta después de cada método de prueba.
     */
    @AfterEach
    public void tearDown()
    {
        charger = null;
        vehicle = null;
    }

    /**
     * Verifica que el método {@link Charger#recharge(ElectricVehicle, int)} calcule correctamente el coste
     * y registre al vehículo recargado.
     */
    @Test
    public void testRecharge()
    {
        int energyToCharge = 20;
        float expectedCost = energyToCharge * 0.35f;

        double cost = charger.recharge(vehicle, energyToCharge);

        assertEquals(expectedCost, cost, 0.0001f, "Recharge should return the calculated cost");
        assertEquals(1, charger.getNumerEVRecharged(), "Vehicle must be registered as recharged");
    }
}