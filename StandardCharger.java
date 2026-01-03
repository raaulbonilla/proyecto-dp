/**
 * Write a description of class StandardCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardCharger extends Charger {
    public StandardCharger(String id, int speed, double fee) {
        super(id, speed, fee);
        
    }
    
    @Override
    public boolean isCompatible(ElectricVehicle ev){
        return ev.getType() == VehicleTier.STANDARD || ev.getType() == VehicleTier.VTC;
    }
    
    @Override 
    public boolean esCompatible(ElectricVehicle ev) {
        return isCompatible(ev);
    }
}