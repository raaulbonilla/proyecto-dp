import java.util.*;

//CLASE REALIZADA POR: Raúl Bonilla, Aaron Becerro y Alberto Fructos

/**
 * Proporciona un entorno de demostración y simulación para el modelo de
 * vehículos eléctricos y estaciones de recarga.
 * Configura todos los elementos y ejecuta la simulación durante un número fijo de turnos.
 *
 * Escenarios:
 * <ul>
 * <li>Demo SIMPLE (demo=DemoType.SIMPLE): se crean dos vehículos.</li>
 * <li>Demo MEDIUM (demo=DemoType.MEDIUM): se crean cinco vehículos.</li>
 * <li>Demo ADVANCED (demo=DemoType.ADVANCED): se crean ocho vehículos.</li>
 * </ul>
 *
 * @author DP Clasess
 * @version 2025
 */
public class EVDemo
{

    /** Coordenada X máxima del mapa (número de filas). */
    public static final int MAXX = 20;
    /** Coordenada Y máxima del mapa (número de columnas). */
    public static final int MAXY = 20;

    /** Número máximo de turnos de la simulación. */
    public static final int MAXSTEPS = 50;

    /** Empresa que gestiona los vehículos y las estaciones de recarga. */
    private EVCompany company;

    /** Vehículos eléctricos implicados en la simulación.
     * @see ElectricVehicle
     */
    private List<ElectricVehicle> vehicles;

    /** Estaciones de recarga disponibles en la ciudad.
     * @see ChargingStation
     */
    private List<ChargingStation> stations;

    /** Constante que selecciona el escenario de demostración, usando la enumeración {@link DemoType}. */
    private static final DemoType DEMO=DemoType.ADVANCED;

    /** Coste energético de cada desplazamiento expresado en kWh. */
    public static final int COSTEKM = 5;

    /**
     * Constructor de la clase {@code EVDemo}.
     * Inicializa la empresa, la lista de vehículos y la de estaciones.
     */
    public EVDemo()
    {
        this.company = new EVCompany("EVCharging Cáceres");
        this.vehicles = new ArrayList<>();
        this.stations = new ArrayList<>();
    }

    /**
     * Ejecuta la demostración durante un número fijo de turnos.
     * En cada turno todos los vehículos realizan su acción.
     */
    public void run()
    {
        for(int step = 0; step < MAXSTEPS; step++) {
            step(step);
        }
        showFinalInfo();
    }

    /**
     * Ejecuta la simulación para un único turno solicitando que todos los vehículos actúen.
     *
     * @param step Turno actual de la simulación.
     */
    public void step(int step)
    {
        for (ElectricVehicle ev : vehicles) {
            ev.act(step);
            System.out.println(ev.getStepInfo(step));
        }
    }

    /**
     * Restablece la demostración a su punto inicial.
     * Elimina vehículos y estaciones existentes, reinicia la empresa y vuelve a crear el escenario inicial.
     */
    public void reset()
    {
        vehicles.clear();
        stations.clear();

        this.vehicles = new ArrayList<>();
        this.stations = new ArrayList<>();        
        this.company = new EVCompany("EVCharging Cáceres");

        createElectricVehicles();
        createStations(); 
        createChargers();
        configureRoutes();
        
        showInitialInfo();
    }
    
    /**
     * Crea los {@link ElectricVehicle} definidos por el escenario, asigna posiciones de origen y destino
     * y los registra en la empresa.
     * Finalmente ordena la lista por matrícula usando {@link ComparatorPlate}.
     */
    private void createElectricVehicles() {
        Location [] locations = {new Location(10,13), new Location(8,4), new Location(8,4), new Location(15,10),
                                new Location(1,1), new Location(2,2), new Location(11,13), new Location(14,16)};
        Location [] targetLocations = {new Location(1,1), new Location(19,19), new Location(12,17), new Location(4,4),
                                        new Location(1,10), new Location(5,5), new Location(8,7), new Location(19,19)};
                                        
        for (int i=0;i < DEMO.getNumVehiclesToCreate();i++){
            ElectricVehicle ev = new ElectricVehicle(company, locations[i],targetLocations[i],("EV"+i),(i+"CCC"),(i+1)*15);
            vehicles.add(ev);
            company.addElectricVehicle(ev);
        }
        vehicles.sort(new ComparatorPlate());
    }
    
    /**
     * Crea un conjunto predefinido de {@link ChargingStation} y las añade a la empresa.
     * Posteriormente ordena la colección según el identificador usando {@link ComparatorChargingStationId}.
     */
    private void createStations() {
        Location [] locations = {new Location(10,5), new Location(10,11), new Location(14,16), new Location(8,4)};

        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            ChargingStation station = new ChargingStation("Cáceres","CC0" + i,locations[i]);
            stations.add(station);
            company.addChargingStation(station);
        }
        stations.sort(new ComparatorChargingStationId());
    }

    /**
     * Crea un número fijo de {@link Charger} para cada {@link ChargingStation}
     * y ordena los cargadores internamente por velocidad, tarifa e identificador.
     */
    private void createChargers() {
        for (ChargingStation station : stations){
            List<Charger> chargers = new ArrayList<>();

            for (int i=0;i<DEMO.getNumChargersToCreate();i++){
                chargers.add(new Charger(station.getId() + "_00" + i,((i+1)*20),((i+1)*0.20f)));
            }
            chargers.sort(new ComparatorSpeed().thenComparing(new ComparatorFee()).thenComparing(new ComparatorChargersId()));
            for (Charger charger : chargers) {
                station.addCharger(charger);
            } 
        }
    }
    
    /**
     * Indica a cada {@link ElectricVehicle} que calcule su ruta inicial,
     * determinando si necesita una parada intermedia para recargar.
    */
    private void configureRoutes() {
        for (ElectricVehicle ev : vehicles) {
            ev.calculateRechargingPosition();
        }
    }

    /**
     * Muestra la información inicial de la simulación con el detalle de los {@link ElectricVehicle}
     * y las {@link ChargingStation} configuradas.
     */
    private void showInitialInfo() {
        System.out.println("( Compañía " + company.getName() + " )");
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        for (ElectricVehicle ev : vehicles) {
            System.out.println(ev.getInitialFinalInfo());
        }
        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
       
        for (ChargingStation st : stations) {
            System.out.println(st.toString());
            for (Charger charger : st.getChargers()) {
                System.out.println(charger.toString());
            }
        }
        
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
    }

    /**
     * Muestra la información final tras completar la simulación.
     * Los vehículos se ordenan por turno de llegada mediante {@link ComparatorEVArrivingStep}
     * y las estaciones por número de recargas con {@link ComparatorChargingStationNumberRecharged}.
     */
    private void showFinalInfo() {

        System.out.println("(-------------------)");
        System.out.println("( Final information )");
        System.out.println("(-------------------)");

        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        List<ElectricVehicle> orderedVehicles = new ArrayList<>(vehicles);
        orderedVehicles.sort(new ComparatorEVArrivingStep());
        for (ElectricVehicle ev : orderedVehicles) {
            System.out.println(ev.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");

        List<ChargingStation> orderedStations = new ArrayList<>(stations);
        orderedStations.sort(new ComparatorChargingStationNumberRecharged());

        for (ChargingStation st : orderedStations) {
            System.out.println(st.toString());
            for (Charger charger : st.getChargers()) {
                System.out.println(charger.toString());
                for (ElectricVehicle ev : charger.getRechargedVehicles()) {
                    System.out.println(ev.getInitialFinalInfo());
                }
            }
        }
    }

    /**
     * Punto de entrada principal para ejecutar la simulación {@code EVDemo}.
     * Crea una instancia de la clase y lanza la simulación.
     */
    public static void main() {
        EVDemo demo = new EVDemo();
        demo.reset();
        demo.run();
    }
}
