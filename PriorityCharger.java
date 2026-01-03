/**
 * Write a description of class PriorityCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityCharger extends Charger{
    public PriorityCharger(String id, int speed, double fee) {
    super(id, speed, fee);
    }
    
    @Override
    public boolean isCompatible(ElectricVehicle ev){
        return ev.getType() == VehicleTier.PRIORITY;
    }

    @Override
    public boolean esCompatible(ElectricVehicle ev) {
        return isCompatible(ev);
    }
    
}