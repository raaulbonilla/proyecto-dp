import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;
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
    
    boolean esCompatible(ElectricVehicle ev){
        boolean es = false;
        if(ev.getType() == VehicleTier.PRIORITY){
            es = true;
        }
        return es;
    }
    
}