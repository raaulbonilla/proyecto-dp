import java.util.List;
import java.util.Iterator;

/**
 * Write a description of class VtcEV here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class VtcEV extends ElectricVehicle{
    
    public VtcEV(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity){
            
         super(company, location, targetLocation, name, plate, batteryCapacity);                 
         setType(VehicleTier.VTC);                 
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
        double mejorTarifa = 0.0; 
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
                    boolean tieneValido = false;
                    double mejorTarifaEnEstacion = 0.0;

                    Iterator<Charger> itCh = st.getChargers().iterator();
                    while (itCh.hasNext()) {
                        Charger ch = itCh.next();
                        if (ch instanceof StandardCharger || ch instanceof SolarCharger) {
                            double tarifa = ch.getChargingFee();

                            if (!tieneValido || tarifa < mejorTarifaEnEstacion) {
                                mejorTarifaEnEstacion = tarifa;
                                tieneValido = true;
                            }
                        }
                    }

                    if (tieneValido) {
                        if (esFallback) {
                            if (fallback == null) {
                                fallback = st;
                            }
                        } else {
                            if (primera || mejorTarifaEnEstacion < mejorTarifa) {
                                mejor = st;
                                mejorTarifa = mejorTarifaEnEstacion;
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
                    if (ch instanceof StandardCharger || ch instanceof SolarCharger) {
                        List<ElectricVehicle> recargados = ch.getRechargedVehicles();

                        if (!recargados.isEmpty()) {
                            ElectricVehicle ultimo = recargados.get(recargados.size() - 1);
                            if (ultimo == this && cargadorSeleccionado == null) {
                                cargadorSeleccionado = ch;
                            }
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