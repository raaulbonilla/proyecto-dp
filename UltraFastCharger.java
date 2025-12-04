
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
    
    boolean esCompatible (ElectricVehicle ev){
        boolean es = false;
        if(ev.getType() == VehicleTier.PREMIUM){
            es = true;
        }
        return es;
    }
}