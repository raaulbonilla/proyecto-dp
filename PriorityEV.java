import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class PriorityEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PriorityEV extends ElectricVehicle{
    
    public PriorityEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.PRIORITY);                
    }
    
    @Override
    public void act(int step) {
        int recargasAntes = getChargesCount();
        super.act(step);

        boolean llegoDestino = getArrivingStep() != -1;
        boolean recargo = getChargesCount() > recargasAntes;
        boolean enEstacion = getRechargingLocation() != null
                && getLocation().equals(getRechargingLocation());

        if (!llegoDestino && !recargo && !enEstacion) {
            super.act(step);
        }
    }
    
    @Override
    public void calculateRoute() {
        super.calculateRoute();
        EVCompany company = getCompany();
        Location location = getLocation();
        Location targetLocation = getTargetLocation();
        int batteryLevel = getBatteryLevel();
        int batteryCapacity = getBatteryCapacity();

        int distanciaDestino = location.distance(targetLocation);
        int energiaNecesaria = distanciaDestino * EVDemo.COSTEKM;

        ChargingStation mejor = null;
        ChargingStation fallback = null;
        int mejorDistDestino = 0;
        boolean primera = true;

        if (batteryLevel < energiaNecesaria) {
            List<ChargingStation> stations = company.getCityStations();
            Iterator<ChargingStation> it = stations.iterator();

            while (it.hasNext()) {
                ChargingStation st = it.next();
                Location locSt = st.getLocation();
                int d1 = location.distance(locSt);
                int energiaHastaEstacion = d1 * EVDemo.COSTEKM;

                boolean llegoAEstacion = batteryLevel >= energiaHastaEstacion;
                boolean esFallback = (d1 == 0 && batteryLevel == batteryCapacity);

                if (llegoAEstacion) {
                    boolean tienePriority = st.hasCompatibleCharger(this);

                    if (tienePriority) {
                        if (esFallback) {
                            if (fallback == null) {
                                fallback = st;
                            }
                        } else {
                            int d2 = locSt.distance(targetLocation);
                            if (primera || d2 < mejorDistDestino) {
                                mejor = st;
                                mejorDistDestino = d2;
                                primera = false;
                            }
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
}