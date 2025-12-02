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
public class EVCompany implements Iterable<ElectricVehicle> {
    private String name;
    private ArrayList<ElectricVehicle> suscribedVehicles;
    private ArrayList<ChargingStation> stations;
    private Map<Charger, LinkedHashSet<ElectricVehicle>> chargingRegistry;
    
    /**
     * Constructor de la clase {@code EVCompany}.
     *
     * @param name Nombre de la empresa.
     */
    public EVCompany(String name) {
        this.name = name;
        suscribedVehicles = new ArrayList<ElectricVehicle>();
        stations = new ArrayList<ChargingStation>();
        chargingRegistry = new TreeMap<>(Comparator.comparing(Charger::getId));

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
    
    public void notifyCharging(Charger charger, ElectricVehicle vehicle) {
        if (charger != null && vehicle != null) {
            chargingRegistry.computeIfAbsent(charger, k -> new LinkedHashSet<>()).add(vehicle);
        }
    }
    
    public List<Charger> getRegisteredChargers() {
        return Collections.unmodifiableList(new ArrayList<>(chargingRegistry.keySet()));
    }
    
    public List<ElectricVehicle> getRegisteredVehicles(Charger charger) {
        List<ElectricVehicle> vehicles = Collections.emptyList();
        LinkedHashSet<ElectricVehicle> registered = chargingRegistry.get(charger);

        if (registered != null) {
            vehicles = Collections.unmodifiableList(new ArrayList<>(registered));
        }

        return vehicles;
    }
    
    public Map<Charger, List<ElectricVehicle>> getChargingRegistry() {
        Map<Charger, List<ElectricVehicle>> copy = new TreeMap<>(Comparator.comparing(Charger::getId));
        
        for (Map.Entry<Charger, LinkedHashSet<ElectricVehicle>> entry : chargingRegistry.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        
        return Collections.unmodifiableMap(copy);
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
        chargingRegistry.clear();
    }
    
    @Override
    public boolean equals(Object otroObjeto) {
        boolean esIgual = false;

        if (this == otroObjeto) {
            esIgual = true;
        } else {
            if (otroObjeto != null && getClass() == otroObjeto.getClass()) {
                EVCompany otraEmpresa = (EVCompany) otroObjeto;
                
                if (name == null) {
                    esIgual = otraEmpresa.name == null;
                } else {
                    esIgual = name.equals(otraEmpresa.name);
                }
            }
        }

        return esIgual;
    }
    
    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }
    
    @Override
    public Iterator<ElectricVehicle> iterator() {
        return suscribedVehicles.iterator();
    }
}