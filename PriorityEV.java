
/**
 * Write a description of class PriorityEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityEV extends ElectricVehicle{
    
    public PriorityEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.PRIORITY);                
    }
}