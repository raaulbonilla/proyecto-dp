import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
import java.util.*;
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
    
    public boolean esCompatible(ElectricVehicle ev){
        boolean es = false;
        if(ev.getType() == VehicleTier.VTC){
            es = true;
        }
        return es;
    }
    
    @Override
    public double recharge(ElectricVehicle ev, int kwsRecharging) {
        double cost = 0.0;
        double costBase = 0.0;
        if(esCompatible(ev)){
            costBase = super.recharge(ev,kwsRecharging);
            cost = costBase * 0.9;
            setAmountCollected(getAmountCollected() - (costBase * 0.1));
        }
        return cost;
    }
}