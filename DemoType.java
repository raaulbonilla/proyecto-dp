/**
 * Enumeración que define los escenarios de demostración preconfigurados de la simulación.
 * Cada tipo establece cuántos vehículos, estaciones y cargadores se crearán.
 *
 * @author DP classes
 * @version 2025.20.10
 */
public enum DemoType
{
    /** Escenario sencillo: 2 vehículos, 4 estaciones y 4 cargadores por estación. */
    SIMPLE ("SIMPLE DEMO", 2, 4, 4),
    /** Escenario medio: 5 vehículos, 4 estaciones y 4 cargadores por estación. */
    MEDIUM ("MEDIUM DEMO", 5, 4, 4),
    /** Escenario avanzado: 8 vehículos, 4 estaciones y 4 cargadores por estación. */
    ADVANCED ("ADVANCED DEMO", 8, 4, 4);

    /** Nombre descriptivo del escenario. */
    private String name;
    /** Número de {@link ElectricVehicle} que se generarán. */
    private int numVehiclesToCreate;
    /** Número de {@link ChargingStation} que se generarán. */
    private int numStationsToCreate;
    /** Número de {@link Charger} que se crearán por estación. */
    private int numChargersToCreate;

    /**
     * Constructor de los tipos de demostración.
     *
     * @param name Nombre descriptivo.
     * @param numVehiclesToCreate Número de vehículos.
     * @param numStationsToCreate Número de estaciones.
     * @param numChargersToCreate Número de cargadores por estación.
     */
    DemoType(String name, int numVehiclesToCreate, int numStationsToCreate, int numChargersToCreate){
        this.name = name;
        this.numVehiclesToCreate = numVehiclesToCreate;
        this.numStationsToCreate = numStationsToCreate;
        this.numChargersToCreate = numChargersToCreate;
    }

    /**
     * @return El nombre descriptivo de la demostración.
     */
    public String getName(){
        return name;
    }

    /**
     * @return La cantidad de vehículos que se crearán en este escenario.
     */
    public int getNumVehiclesToCreate(){
        return numVehiclesToCreate;
    }

    /**
     * @return La cantidad de estaciones de recarga previstas en este escenario.
     */
    public int getNumStationsToCreate(){
        return numStationsToCreate;
    }

    /**
     * @return El número de cargadores que tendrá cada estación.
     */
    public int getNumChargersToCreate(){
        return numChargersToCreate;
    }
}