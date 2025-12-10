
/**
 * Write a description of class StandardEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardEv extends ElectricVehicle{
    
    public StandardEv(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.STANDARD); 
         
        }
   
    
    @Override
    public void calculateRoute(){
        EVCompany comp;
        Charger ch;
        super.calculateRoute();
        if (getType() == VehicleTier.STANDARD) {
                //comp.notifyCharging(ch, this);
        }
    }
    
}