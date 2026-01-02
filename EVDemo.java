import java.util.*;
/**
 * Provides a simple demonstration and simulation environment for the
 * Electric Vehicle (EV) and Charging Station model.
 * It sets up the environment and runs the simulation for a fixed number of steps.
 *
 * Scenarios:
 * <ul>
 * <li>Demo SIMPLE (demo=DemoType.SIMPLE): Two vehicles are created.</li>
 * <li>Demo MEDIUM (demo=DemoType.MEDIUM): Five vehicles are created.</li>
 * <li>Demo ADVANCED(demo=DemoType.ADVANCED): Eight vehicles are created.</li>
 * </ul>
 * @author DP Clasess
 * @version 2025
 */
public class EVDemo
{

    /** The maximum X coordinate for the grid (number of rows). */
    public static final int MAXX = 20;
    /** The maximum Y coordinate for the grid (number of columns). */
    public static final int MAXY = 20;

    /** The maximum number of turns. */
    public static final int MAXSTEPS = 50;

    /** Energy cost per step. */
    public static final int COSTEKM = 5;

    /** The company managing the EVs and charging stations. */
    private EVCompany company;

    /** * The simulation's vehicles; they are the electric vehicles of the company.
     * @see ElectricVehicle
     */
    private List<ElectricVehicle> vehicles;

    /** * The charging stations available in the city.
     * @see ChargingStation
     */
    private List<ChargingStation> stations;

    /** Constant for selecting the demo scenario, using the {@link DemoType} enumeration. */
    private static final DemoType DEMO=DemoType.SIMPLE;

    /**
     * Creates the {@link ElectricVehicle}s based on {@code numVehiclesCreated}, assigns them
     * starting and target {@link Location}s, and adds them to the company.
     * The vehicles list is sorted by plate using {@link ComparatorPlate}.
     */
    private void createElectricVehicles() {
        Location [] locations = {new Location(1,1), new Location(1,1), new Location(1,19), new Location(1,19),
                                new Location(19,1), new Location(19,1), new Location(10,19), new Location(19,10),
                                new Location(10,10), new Location(10,10)};

        Location [] targetLocations = {new Location(20,20), new Location(20,20), new Location(19,1), new Location(19,1),
                                       new Location(1,19), new Location(1,19), new Location(19,10), new Location(10,19),
                                       new Location(10,20), new Location(20,10)};

        for (int i=0;i < DEMO.getNumVehiclesToCreate();i++){
            ElectricVehicle ev;
            int module = i % VehicleTier.numTiers();
            if (VehicleTier.values()[module] == VehicleTier.PRIORITY) {
                ev = new PriorityEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            } else if (VehicleTier.values()[module] == VehicleTier.VTC) {
                ev = new VtcEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            } else if (VehicleTier.values()[module] == VehicleTier.PREMIUM) {
                ev = new PremiumEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            } else {
                ev = new StandardEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            }
            vehicles.add(ev);
        }
        vehicles.sort(new ComparatorPlate());
        for (ElectricVehicle ev : vehicles) {
            company.addElectricVehicle(ev);
        }
    }


    /**
     * Creates predefined {@link ChargingStation}s and adds them to the company.
     * The stations list is sorted by ID using {@link ComparatorChargingStationId}.
     */
    private void createStations() {
        Location [] locations = {new Location(5,5), new Location(15,15), new Location(5,15), new Location(15,5), new Location(10,10)};

        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            stations.add(new ChargingStation("Cáceres","CC0" + i,locations[i]));
        }
        stations.sort(new ComparatorChargingStationId());
        for (ChargingStation station : stations) {
            company.addChargingStation(station);
        }
    }

    /**
     * Creates a fixed number of {@link Charger} units for each {@link ChargingStation}
     * and orders the chargers within each station.
     */
    private void createChargers() {
        int j=0;
        for (ChargingStation station : stations){
            for (int i=0;i<DEMO.getNumChargersToCreate();i++){
                Charger ch;
                if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate()-1)) {
                    ch = new SolarCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                }
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())) {
                    ch = new UltraFastCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                }
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())+1) {
                    ch = new PriorityCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                }
                else {
                    ch = new StandardCharger(station.getId() + "_00" + i, ((i+1)*20), ((i+1)*0.20f));
                }
                station.addCharger(ch);
            }
            j++;

        }
    }

    /**
     * Restablece la simulación a su estado inicial y la ejecuta.
     */
    public void reset() {
        company = new EVCompany("Compañía EVCharging Cáceres");
        vehicles = new ArrayList<>();
        stations = new ArrayList<>();

        createElectricVehicles();
        createStations();
        createChargers();

        for (ElectricVehicle ev : vehicles) {
            ev.calculateRoute();
        }

        showInitialInfo();
        run();
        showFinalInfo();
    }

    /**
     * Muestra la información inicial de la simulación.
     */
    public void showInitialInfo() {
        System.out.println("( " + company.getName() + " )");
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");
        for (ElectricVehicle ev : vehicles) {
            System.out.println(ev.getInitialFinalInfo());
        }
        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");
        for (ChargingStation station : stations) {
            System.out.println(station.getCompleteInfo());
            for (Charger charger : station.getChargers()) {
                System.out.println(charger.toString());
            }
        }
    }

    /**
     * Ejecuta la simulación completa.
     */
    public void run() {
        System.out.println("(------------------)");
        System.out.println("( Simulation start )");
        System.out.println("(------------------)");
        run(MAXSTEPS);
    }

    /**
     * Ejecuta un número concreto de pasos.
     *
     * @param steps Número de pasos a simular.
     */
    public void run(int steps) {
        for (int i = 0; i < steps; i++) {
            step(i);
        }
    }

    /**
     * Ejecuta un único paso de la simulación.
     *
     * @param step Número de turno.
     */
    public void step(int step) {
        for (ElectricVehicle ev : vehicles) {
            ev.act(step);
            System.out.println(ev.getStepInfo(step));
        }
    }

    /**
     * Muestra la información final de la simulación.
     */
    public void showFinalInfo() {
        System.out.println("(-------------------)");
        System.out.println("( Final information )");
        System.out.println("(-------------------)");
        System.out.println("(-------------------)");
        System.out.println("( Electric Vehicles )");
        System.out.println("(-------------------)");

        List<ElectricVehicle> vehiclesSorted = new ArrayList<>(vehicles);
        vehiclesSorted.sort(new ComparatorEVArrivingStep());
        for (ElectricVehicle ev : vehiclesSorted) {
            System.out.println(ev.getInitialFinalInfo());
        }

        System.out.println("(-------------------)");
        System.out.println("( Charging Stations )");
        System.out.println("(-------------------)");

        List<ChargingStation> stationsSorted = new ArrayList<>(stations);
        stationsSorted.sort(new ComparatorChargingStationNumberRecharged());
        for (ChargingStation station : stationsSorted) {
            System.out.println(station.getCompleteInfo());
            for (Charger charger : station.getChargers()) {
                System.out.println(charger.toString());
                for (ElectricVehicle ev : charger.getRechargedVehicles()) {
                    System.out.println(ev.getInitialFinalInfo());
                }
            }
        }

        System.out.println("(--------------)");
        System.out.println("( Company Info )");
        System.out.println("(--------------)");
        System.out.println("(EVCompany: " + company.getName() + ")");
        for (Charger charger : company.getRegisteredChargers()) {
            System.out.println(charger.toString());
            for (ElectricVehicle ev : company.getRegisteredVehicles(charger)) {
                System.out.println(ev.getInitialFinalInfo());
            }
        }
    }

    /**
     * Punto de entrada de la simulación.
     *
     * @param args Argumentos de entrada (no se utilizan).
     */
    public static void main() {
        EVDemo demo = new EVDemo();
        demo.reset();
    }
}