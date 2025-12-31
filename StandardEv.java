import java.util.Iterator;
import java.util.List;
/**
 * Write a description of class StandardEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardEv extends ElectricVehicle {
    
    public StandardEv(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.STANDARD); 
         
    }
   @Override
    public void recharge(int step) {
        int recargasAntes = getChargesCount();
        super.recharge(step);
        if (getChargesCount() > recargasAntes) {
            EVCompany company = getCompany();
            ChargingStation station = company.getChargingStation(getLocation());
            
            if (station != null) {
                Charger cargadorSeleccionado = null;
                Iterator<Charger> it = station.iterator();
               
                while (it.hasNext()) {
                    Charger ch = it.next();
                    List<ElectricVehicle> recargados = ch.getRechargedVehicles();

                    if (!recargados.isEmpty()) {
                        ElectricVehicle ultimo = recargados.get(recargados.size() - 1);

                        if (ultimo == this && cargadorSeleccionado == null) {
                            cargadorSeleccionado = ch;
                        }
                    }
                }
                if (cargadorSeleccionado != null) {
                    company.notifyCharging(cargadorSeleccionado, this);
                }
            }
        }
    }
}