/**
 * Write a description of class UltraFastCharger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class UltraFastCharger extends Charger{
    public UltraFastCharger(String id, int speed, double fee) {
    super(id, speed, fee);
    }
    
    @Override
    public boolean isCompatible (ElectricVehicle ev){
        return ev.getType() == VehicleTier.PREMIUM;
    }
    
    @Override 
    protected double calculateCost(int kwsRecharging) {
        return super.calculateCost(kwsRecharging) * 1.1;
    }

    @Override
    public boolean esCompatible(ElectricVehicle ev) {
        return isCompatible(ev);
    }

}
