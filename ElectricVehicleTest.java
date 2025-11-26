import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para {@link ElectricVehicle}.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ElectricVehicleTest
{
    private EVCompany company;
    private Location startLocation;
    private Location targetLocation;
    private ElectricVehicle vehicle;
    
    /**
     * Constructor por defecto para la clase de pruebas {@code ElectricVehicleTest}.
     */
    public ElectricVehicleTest()
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
        company = new EVCompany("EcoMove");
        startLocation = new Location(0, 0);
        targetLocation = new Location(10, 0);
        vehicle = new ElectricVehicle(company, startLocation, targetLocation, "EcoMotion", "EV-001", 100);
    }

    /**
     * Limpia el escenario de pruebas.
     *
     * Se ejecuta después de cada método de prueba.
     */
    @AfterEach
    public void tearDown()
    {
        company = null;
        startLocation = null;
        targetLocation = null;
        vehicle = null;
    }
    
    /**
     * Comprueba que el constructor inicializa correctamente los atributos principales.
     */
    @Test
    public void testCreation()
    {
        assertSame(startLocation, vehicle.getLocation(), "La localizacion inicial debe coincidir con la proporcionada");
        assertEquals(targetLocation, vehicle.getTargetLocation(), "El destino final debe coincidir con el indicado");
        assertEquals("EV-001", vehicle.getId(), "La matrícula debe conserbarse");
        assertEquals(100, vehicle.getBatteryCapacity(), "La capacidad de la batería debe corresponder al valor indicado");
        assertEquals(100, vehicle.getBatteryLevel(), "El nivel de batería debe iniciarse al máximo");
        assertEquals(-1, vehicle.getArrivingStep(), "El turno de llegada debe inicializarse sin valor");
        assertEquals(0, vehicle.getIdleCount(), "El contador deinactividad debe comenzar a cero");
        assertEquals(0, vehicle.getChargesCount(), "El contador de recargas debe comenzar a cero");
        assertFalse(vehicle.hasRechargingLocation(), "No debe haber un destino intermedio planificado al crear el vehículo");
        assertEquals("(0-0, 10-0)", vehicle.getStringRoute(), "La ruta inicial debe ser directa al destino");
    }
    
    /**
     * Verifica que la comprobación de batería considere el nivel de carga disponible.
     */
    @Test
    public void testEnoughBattery()
    {
        assertTrue(vehicle.enoughBattery(10), "Con bateria completa debe poder recorrer distancias moderadas");
        assertTrue(vehicle.enoughBattery(20), "Debe poder recorrer una distancia equivalente a la capacidad ttal");

        for (int i= 0; i < 16; i++) {
            vehicle.reduceBatteryLevel();
        }

        assertTrue(vehicle.enoughBattery(4), "La comprobaciion debe considerar el nivel de batería restante");
        assertFalse(vehicle.enoughBattery(6), "No debe indicar batería suficiente para distancias superiores a la disponible");
    }
    
    /**
     * Valida que el cálculo de la ruta seleccione paradas intermedias óptimas cuando es necesario.
     */
    @Test
    public void testCalculateRoute()
    {
        ChargingStation stationPriority = new ChargingStation("Madrid", "CS-A", new Location(2, 0));
        ChargingStation stationSecondary = new ChargingStation("Madrid", "CS-B", new Location(1, 4));

        company.addChargingStation(stationSecondary);
        company.addChargingStation(stationPriority);

        vehicle.calculateRoute();

        assertNull(vehicle.getRechargingLocation(), "Con suficiente batería no debe planificar paradas intermedias");
        assertEquals("(0-0, 10-0)", vehicle.getStringRoute(), "La ruta debe permanecer directa cuando no se necesita recargar");

        ElectricVehicle lowBatteryVehicle = new ElectricVehicle(company, new Location(0, 0), new Location(8, 0), "EcoMini", "EV-002", 20);

        lowBatteryVehicle.calculateRoute();

        assertEquals(stationPriority.getLocation(), lowBatteryVehicle.getRechargingLocation(), "Debe seleccionar la estación que minimiza el recorrido total");
        assertEquals("(0-0, 2-0, 8-0)", lowBatteryVehicle.getStringRoute(), "La ruta debe incluir la parada de recarga planificada");
    }
    
    /**
     * Comprueba que el proceso de recarga restaure el estado adecuado del vehículo.
     */
    @Test
    public void testRecharge()
    {
        ChargingStation homeStation = new ChargingStation("Madrid", "CS-HOME", new Location(0, 0));
        Charger freeCharger = new Charger("CH-001", 150, 0.40) {
            @Override
            public boolean isFree() {
                return true;
            }
        };

        homeStation.addCharger(freeCharger);
        company.addChargingStation(homeStation);

        for (int i = 0; i< 8; i++) {
            vehicle.reduceBatteryLevel();
        }

        vehicle.recharge(5);

        assertEquals(vehicle.getBatteryCapacity(), vehicle.getBatteryLevel(), "La batería debe quedar completamente cargada");
        assertEquals(1, vehicle.getChargesCount(), "Debe registrarse una única recarga");
        assertEquals(0, vehicle.getIdleCount(), "No debe incrementarse el tiempo de inactividad cuando hay cargadores libres");
        assertNull(vehicle.getRechargingLocation(), "Tras la recarga debe reevaluar la ruta y eliminar paradas innecesarias");
        assertEquals("(0-0, 10-0)", vehicle.getStringRoute(), "La ruta debe volver a apuntar directamente al destino");
    }
}