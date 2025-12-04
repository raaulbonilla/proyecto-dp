
/**
 * Write a description of class PremiumEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PremiumEV extends ElectricVehicle{
    
    public PremiumEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.PREMIUM);                 
    }
}