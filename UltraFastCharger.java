
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
    
    public boolean esCompatible (ElectricVehicle ev){
        boolean es = false;
        if(ev.getType() == VehicleTier.PREMIUM){
            es = true;
        }
        return es;
    }
    
    @Override 
    public double recharge(ElectricVehicle ev, int kwsRecharging) {
        double cost = 0.0;
        double costBase = 0.0;
        if(esCompatible(ev)){
            costBase = super.recharge(ev, kwsRecharging);
            cost = costBase * 1.1;
            setAmountCollected(getAmountCollected() + (costBase * 0.1));
        }
        return cost;
    }

}