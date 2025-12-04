
/**
 * Write a description of class VtcEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VtcEV extends ElectricVehicle{
    
    public VtcEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.VTC);                 
    }
}