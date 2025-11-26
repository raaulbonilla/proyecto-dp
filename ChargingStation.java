import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//CLASE REALIZADA POR: Raúl Bonilla

/**
 * Modela una estación de recarga para vehículos eléctricos.
 * Una estación de carga puede contener varias unidades {@link Charger}.
 *
 * @author DP classes
 * @version 2024.10.07
 */
public class ChargingStation {
    private String id;
    private String city;
    private Location location;
    private List<Charger> chargers;

    /**
     * Constructor de la clase {@code ChargingStation}.
     *
     * @param city     Ciudad en la que se encuentra la estación.
     * @param id       Identificador único de la estación.
     * @param location {@link Location} que indica su posición.
     */
    public ChargingStation(String city, String id, Location location) {
        this.city = city;
        this.id = id;
        this.location = location;
        chargers = new ArrayList<Charger>();
    }

    /**
     * @return El nombre de la ciudad correspondiente con la estacion de carga.
     */
    public String getCity() {
        return city;
    }

    /**
     * @return El identificador único de la estación de recarga.
     */
    public String getId() {
        return id;
    }

    /**
     * @return La {@link Location} en la que se sitúa la estación.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return Lista inmodificable con todos los {@link Charger} de la estación.
     */
    public List<Charger> getChargers() {
        return Collections.unmodifiableList(chargers);
    }

    /**
     * Recupera el primer {@link Charger} libre disponible en la estación.
     *
     * @return Un {@link Charger} libre o {@code null} si no hay disponibles.
     */
    public Charger getFreeCharger() {
        Charger free = null;

        int i = 0;
        boolean enc = false;

        while (i < chargers.size() && !enc) {
            Charger ch = chargers.get(i);
            if (ch.isFree()) {
                free = ch;
                enc = true;
            }
            i++;
        }
        return free;
    }

    /**
     * Establece la ubicación actual de la estación de carga.
     *
     * @param location Nueva {@link Location}. No debe ser {@code null}.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Devuelve una cadena con la información completa de la estación de carga.
     *
     * @return Cadena descriptiva con los datos principales de la estación.
     */
    public String getCompleteInfo() {
        return String.format("(ChargingStation: %s, %s, %d, %s)",
                getId(),
                getCity(),
                getNumerEVRecharged(),
                getLocation());
    }

    /**
     * @return Representación en texto de la estación de recarga.
     */
    @Override
    public String toString() {
        return String.format("(ChargingStation: %s, %s, %d, %s)",
                getId(),
                getCity(),
                getNumerEVRecharged(),
                getLocation());
    }

    /**
     * Calcula el número total de {@link ElectricVehicle} recargados.
     *
     * @return Número total de recargas realizadas en la estación.
     */
    public int getNumerEVRecharged() {
        int contador = 0;

        for (int i = 0; i < chargers.size(); i++) {
            contador = contador + chargers.get(i).getNumerEVRecharged();
        }
        return contador;
    }

    /**
     * Añade un nuevo {@link Charger} a la estación.
     *
     * @param charger Unidad de carga que se incorpora.
     */
    public void addCharger(Charger charger) {
        chargers.add(charger);
    }

    /**
     * Busca un cargador por su identificador único.
     *
     * @param chargerId Identificador del cargador buscado.
     * @return El {@link Charger} correspondiente o {@code null} si no existe.
     */
    public Charger getChargerById(String chargerId) {
        Charger c = null;

        if (chargerId != null) {
            for (Charger ch : chargers) {
                if (chargerId.equals(ch.getId())) {
                c = ch;
                }
            }
        }
        return c;
    }
}
