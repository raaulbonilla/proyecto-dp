import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;

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
    
    public boolean esCompatible(ElectricVehicle ev){
        return isCompatible(ev);
    }

    @Override
    public boolean isCompatible(ElectricVehicle ev) {
        return ev.getType() == VehicleTier.STANDARD || ev.getType() == VehicleTier.VTC;
    }
    
    @Override 
    public double recharge(ElectricVehicle ev, int kwsRecharging) {
        double cost = 0.0;
        if(esCompatible(ev)){
            cost = super.recharge(ev, kwsRecharging);
        }
        return cost;
    }
}
