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
 * * @author DP Clasess
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
    private static final DemoType DEMO=DemoType.NANO;
    
    
    /**
     * Creates the {@link ElectricVehicle}s based on {@code numVehiclesCreated}, assigns them
     * starting and target {@link Location}s, and adds them to the company.
     * The vehicles list is sorted by plate using {@link ComparatorEVPlate}.
     */
    private void createElectricVehicles() {
        Location [] locations = {new Location(1,1), new Location(1,1), new Location(1,19), new Location(1,19), 
                                new Location(19,1), new Location(19,1), new Location(10,19), new Location(19,10),
                                new Location(10,10), new Location(10,10)};

        Location [] targetLocations = {new Location(20,20), new Location(20,20), new Location(19,1), new Location(19,1), 
                                       new Location(1,19), new Location(1,19), new Location(19,10), new Location(10,19),
                                       new Location(10,20), new Location(20,10)};
                                        
        //createLocations(locations,targetLocations);
        for (int i=0;i < DEMO.getNumVehiclesToCreate();i++){
            ElectricVehicle ev;
            int module = i % VehicleTier.numTiers();
            if (VehicleTier.values()[module] == VehicleTier.PRIORITY) ev = new PriorityEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.VTC) ev = new VtcEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else if (VehicleTier.values()[module] == VehicleTier.PREMIUM) ev = new PremiumEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            else ev = new StandardEV(company, locations[i], targetLocations[i], ("EV"+i), (i+"CCC"), (i+1)*(20-i));
            company.addElectricVehicle(ev);
        }
        // TODO: Complete code here if needed
    }
    

    /**
     * Creates predefined {@link ChargingStation}s and adds them to the company.
     * The stations list is sorted by ID using {@link ComparatorChargingStationId}.
     */
    private void createStations() {  
        Location [] locations = {new Location(5,5), new Location(15,15), new Location(5,15), new Location(15,5), new Location(10,10)};
                                
        for (int i=0;i<DEMO.getNumStationsToCreate();i++){
            company.addChargingStation(new ChargingStation("Cáceres","CC0" + i,locations[i]));
        }
        
        // TODO: Complete code here if needed
    }

    /**
     * Creates a fixed number of {@link Charger} units for each {@link ChargingStation}
     * and orders the chargers within each station.
     */
    private void createChargers() {  
        List<ChargingStation> stations = company.getCityStations();
        int j=0;
        for (ChargingStation station : stations){
            for (int i=0;i<DEMO.getNumChargersToCreate();i++){
                // Creates chargers with varying speed and fee based on index 'i' and 'j'.
                Charger ch;
                if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate()-1)) {
                    ch = new SolarCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())) {
                    ch = new UltraFastCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                } 
                else if (i % DEMO.getNumChargersToCreate() == (j % DEMO.getNumStationsToCreate())+1) {
                    ch = new PriorityCharger(station.getId() + "_00" + i, ((i+j+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                else {
                    ch = new StandardCharger(station.getId() + "_00" + i, ((i+1)*20), ((i+1)*0.20f));
                    // TODO: Complete code here if needed
                }    
                station.addCharger(ch);
            }
            j++;
            
        }    
    }
    
    
}
