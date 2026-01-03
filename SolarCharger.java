/**
 * Write a description of class SolarCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SolarCharger extends Charger{
    public SolarCharger(String id, int speed, double fee) {
    super(id, speed, fee);
    }
    
    @Override
    public boolean isCompatible(ElectricVehicle ev){
        return ev.getType() == VehicleTier.VTC;
    }
    
    @Override
    protected double calculateCost(int kwsRecharging) {
        return super.calculateCost(kwsRecharging) * 0.9;
    }
    
    @Override
    public boolean esCompatible(ElectricVehicle ev) {
        return isCompatible(ev);
    }
}