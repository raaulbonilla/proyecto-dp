import java.util.*;

//CLASE REALIZADA POR: Raúl Bonilla

/**
 *
 * Clase desarrollada por: Raúl Bonilla Cáceres
 *
 * Modela el funcionamiento de una empresa de vehículos eléctricos (EV).
 * Gestiona una flota de {@link ElectricVehicle} y una red de
 * {@link ChargingStation}.
 *
 * @author DP classes
 * @version 2024.10.07
 */
public class EVCompany {
    private String name;
    private ArrayList<ElectricVehicle> suscribedVehicles;
    private ArrayList<ChargingStation> stations;
    private Map<ChargingStation, LinkedHashSet<ElectricVehicle>> chargingRegistry;

    /**
     * Constructor de la clase {@code EVCompany}.
     *
     * @param name Nombre de la empresa.
     */
    public EVCompany(String name) {
        this.name = name;
        suscribedVehicles = new ArrayList<ElectricVehicle>();
        stations = new ArrayList<ChargingStation>();
        chargingRegistry = new TreeMap<>(Comparator.comparing(ChargingStation::getId));

    }

    /**
     * @return El nombre de la empresa.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Lista inmodificable con todos los {@link ElectricVehicle} dados de alta.
     */
    public List<ElectricVehicle> getVehicles() {
        return Collections.unmodifiableList(suscribedVehicles);
    }

    /**
     * Añade un {@link ElectricVehicle} a la flota de la empresa.
     *
     * @param vehicle Vehículo eléctrico que se desea registrar.
     */
    public void addElectricVehicle(ElectricVehicle vehicle) {
        suscribedVehicles.add(vehicle);
    }

    /**
     * Incorpora una {@link ChargingStation} a la red de la empresa.
     *
     * @param station Estación de carga que se incorpora.
     */
    public void addChargingStation(ChargingStation station) {
        stations.add(station);
    }
    
    public void notifyCharging(ChargingStation station, ElectricVehicle vehicle) {
        chargingRegistry.computeIfAbsent(station, k -> new LinkedHashSet<>()).add(vehicle);
    }

    /**
     * Recupera una {@link ChargingStation} a partir de su identificador único.
     *
     * @param id Identificador de la estación que se desea localizar.
     * @return La {@link ChargingStation} con dicho identificador o {@code null} si no existe.
     */
    public ChargingStation getChargingStation(String id) {
        ChargingStation chs = null;
        
        for (ChargingStation station : stations) {
            if (station.getId().equals(id)) {
                chs = station;
            }
        }
        return chs;
    }

    /**
     * Recupera una {@link ChargingStation} a partir de su {@link Location}.
     *
     * @param location {@link Location} de la estación buscada.
     * @return La {@link ChargingStation} situada en esa localización o {@code null} si no existe.
     */
    public ChargingStation getChargingStation(Location location) {
        ChargingStation chs = null;
        
        for (ChargingStation station : stations) {
            if (station.getLocation().equals(location)) {
                chs = station;
            }
        }
        return chs;
    }

    /**
     * @return Lista inmodificable con todas las {@link ChargingStation} gestionadas.
     */
    public List<ChargingStation> getCityStations() {
        return Collections.unmodifiableList(stations);
    }

    /**
     * @return El número total de {@link ChargingStation} gestionadas.
     */
    public int getNumberOfStations() {
        return stations.size();
    }

    /**
     * Elimina todos los vehículos y estaciones, dejando la empresa sin registros.
     */
    public void reset() {
        suscribedVehicles.clear();
        stations.clear();
    }

}