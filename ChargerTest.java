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

    private EVCompany company;
    private Location start;
    private Location destination;

    private StandardEv standardEV;
    private VtcEV vtcEV;
    private PriorityEV priorityEV;
    private PremiumEV premiumEV;

    private StandardCharger standardCharger;
    private SolarCharger solarCharger;
    private PriorityCharger priorityCharger;
    private UltraFastCharger ultraFastCharger;
    
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
        company = new EVCompany("Test Company");
        start = new Location(0, 0);
        destination = new Location(5, 5);
        vehicle = new ElectricVehicle(company, start, destination, "Model S", "EV-123", 100);
        charger = new Charger("CH-01", 50, 0.35);
        
        standardEV = new StandardEv(company, start, destination, "Std", "STD-1", 100);
        vtcEV = new VtcEV(company, start, destination, "VTC", "VTC-1", 100);
        priorityEV = new PriorityEV(company, start, destination, "Pri", "PRI-1", 100);
        premiumEV = new PremiumEV(company, start, destination, "Pre", "PRE-1", 100);

        standardCharger = new StandardCharger("SC-01", 50, 0.35);
        solarCharger = new SolarCharger("SO-01", 50, 0.35);
        priorityCharger = new PriorityCharger("PC-01", 50, 0.35);
        ultraFastCharger = new UltraFastCharger("UC-01", 150, 0.35);
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
        company = null;
        start = null;
        destination = null;
        
        standardCharger = null;
        solarCharger = null;
        priorityCharger = null;
        ultraFastCharger = null;

        standardEV = null;
        vtcEV = null;
        priorityEV = null;
        premiumEV = null;
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
    
    @Test
    public void testCompatible() {
        assertTrue(standardCharger.esCompatible(standardEV), "StandardCharger debe aceptar STANDARD");
        assertTrue(standardCharger.esCompatible(vtcEV), "StandardCharger debe aceptar VTC");
        assertFalse(standardCharger.esCompatible(priorityEV), "StandardCharger NO debe aceptar PRIORITY");
        assertFalse(standardCharger.esCompatible(premiumEV), "StandardCharger NO debe aceptar PREMIUM");
        assertTrue(solarCharger.esCompatible(vtcEV), "SolarCharger debe aceptar VTC");
        assertFalse(solarCharger.esCompatible(standardEV), "SolarCharger NO debe aceptar STANDARD");
        assertFalse(solarCharger.esCompatible(priorityEV), "SolarCharger NO debe aceptar PRIORITY");
        assertFalse(solarCharger.esCompatible(premiumEV), "SolarCharger NO debe aceptar PREMIUM");
        assertTrue(priorityCharger.esCompatible(priorityEV), "PriorityCharger debe aceptar PRIORITY");
        assertFalse(priorityCharger.esCompatible(standardEV), "PriorityCharger NO debe aceptar STANDARD");
        assertFalse(priorityCharger.esCompatible(vtcEV), "PriorityCharger NO debe aceptar VTC");
        assertFalse(priorityCharger.esCompatible(premiumEV), "PriorityCharger NO debe aceptar PREMIUM");
        assertTrue(ultraFastCharger.esCompatible(premiumEV), "UltraFastCharger debe aceptar PREMIUM");
        assertFalse(ultraFastCharger.esCompatible(standardEV), "UltraFastCharger NO debe aceptar STANDARD");
        assertFalse(ultraFastCharger.esCompatible(vtcEV), "UltraFastCharger NO debe aceptar VTC");
        assertFalse(ultraFastCharger.esCompatible(priorityEV), "UltraFastCharger NO debe aceptar PRIORITY");
    }
    
    @Test
    public void testRechargeCostChargers() {
        int kwh = 20;
        double fee = 0.35;

        double expectedStandard = kwh * fee;
        double costStandard = standardCharger.recharge(standardEV, kwh);
        assertEquals(expectedStandard, costStandard, 0.0001, "StandardCharger debe cobrar coste base");
        double costStandardVtc = standardCharger.recharge(vtcEV, kwh);
        assertEquals(expectedStandard, costStandardVtc, 0.0001, "StandardCharger con VTC debe cobrar coste base");

        double expectedPriority = kwh * fee;
        double costPriority = priorityCharger.recharge(priorityEV, kwh);
        assertEquals(expectedPriority, costPriority, 0.0001, "PriorityCharger debe cobrar coste base");

        double expectedSolar = (kwh * fee) * 0.90;
        double costSolar = solarCharger.recharge(vtcEV, kwh);
        assertEquals(expectedSolar, costSolar, 0.0001, "SolarCharger debe aplicar 10% de descuento");

        double expectedUltra = (kwh * fee) * 1.10;
        double costUltra = ultraFastCharger.recharge(premiumEV, kwh);
        assertEquals(expectedUltra, costUltra, 0.0001, "UltraFastCharger debe aplicar 10% de recargo");
    }
}