import java.util.Iterator;
import java.util.List;
/**
 * Write a description of class StandardEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class StandardEV extends ElectricVehicle {
    
    public StandardEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity) {
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.STANDARD); 
         
    }

    

    @Override
    public void calculateRoute() {
        EVCompany company = getCompany();
        Location location = getLocation();
        Location targetLocation = getTargetLocation();
        int batteryLevel = getBatteryLevel();
        int batteryCapacity = getBatteryCapacity();

        int distanciaDestino = location.distance(targetLocation);
        int energiaNecesaria = distanciaDestino * EVDemo.COSTEKM;

        ChargingStation mejor = null;
        ChargingStation fallback = null;
        int mejorScore = 0;
        boolean primera = true;

        if (batteryLevel < energiaNecesaria) {
            List<ChargingStation> stations = company.getCityStations();
            Iterator<ChargingStation> it = stations.iterator();
            while (it.hasNext()) {
                ChargingStation st = it.next();
                int d1 = location.distance(st.getLocation());
                int energiaHastaEstacion = d1 * EVDemo.COSTEKM;

                boolean llegoAEstacion = batteryLevel >= energiaHastaEstacion;
                boolean esFallback = (d1 == 0 && batteryLevel == batteryCapacity);

                if (llegoAEstacion && st.hasCompatibleCharger(this)) {
                    if (esFallback) {
                        if (fallback == null) {
                            fallback = st;
                        }
                    } else {
                        int d2 = st.getLocation().distance(targetLocation);
                        int score = d1 + d2;

                        if (primera || score < mejorScore) {
                            mejor = st;
                            mejorScore = score;
                            primera = false;
                        }
                    }
                }
            }
        }

        if (mejor == null && fallback != null) {
            mejor = fallback;
        }

        if (mejor != null) {
            setRechargingLocation(mejor.getLocation());
        } else {
            setRechargingLocation(null);
        }
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
