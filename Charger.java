import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Modela una unidad de carga dentro de una {@link ChargingStation}.
 * Registra sus capacidades de carga, la tarifa y los vehículos eléctricos que
 * ha recargado.
 *
 * @author DP classes
 * @version 2024.10.07
 */
public class Charger {
    private String id;
    private double chargingSpeed;
    private double chargingFee;
    private List<ElectricVehicle> eVsRecharged;
    private double amountCollected;
    private boolean free;

    /**
     * Constructor de la clase {@code Charger}.
     *
     * @param id    Identificador único del cargador.
     * @param speed Velocidad máxima de carga en kWh.
     * @param fee   Coste por kWh de la recarga.
     */
    public Charger(String id, int speed, double fee) {
        this.id = id;
        this.chargingSpeed = speed;
        this.chargingFee = fee;
        this.eVsRecharged = new ArrayList<>();
        this.amountCollected = 0.0;
        this.free = true;
    }

    /**
     * @return La velocidad máxima de carga en kWh.
     */
    public double getChargingSpeed() {
        return chargingSpeed;
    }

    /**
     * @return La tarifa aplicada por cada kWh cargado.
     */
    public double getChargingFee(){
        return chargingFee;
    }

    /**
     * Añade un {@link ElectricVehicle} a la lista de vehículos recargados por
     * este cargador.
     *
     * @param vehicle Vehículo eléctrico que ha sido recargado.
     */
    public void addEvRecharged(ElectricVehicle vehicle) {
        eVsRecharged.add(vehicle);
    }

    /**
     * Indica si este cargador es compatible con el vehículo recibido.
     *
     * @param vehicle Vehículo a comprobar.
     * @return {@code true} si el cargador acepta el vehículo.
     */
    public boolean isCompatible(ElectricVehicle vehicle) {
        return true;
    }

    /**
     * Alias mantenido para las pruebas existentes.
     *
     * @param ev Vehículo eléctrico.
     * @return {@code true} si es compatible.
     */
    public boolean esCompatible(ElectricVehicle ev) {
        return isCompatible(ev);
    }

    /**
     * Calcula el coste base de la recarga según los kWh suministrados.
     *
     * @param kwsRecharging kWh recargados.
     * @return Coste de la operación.
     */
    protected double calculateCost(int kwsRecharging) {
        return kwsRecharging * chargingFee;
    }

    /**
     * Simula el proceso de recarga de un {@link ElectricVehicle}.
     * Aumenta la cantidad recaudada y registra el vehículo como recargado.
     *
     * @param vehicle       Vehículo que se va a recargar.
     * @param kwsRecharging Cantidad de kWh que se recargan.
     * @return El coste de la operación de recarga.
     */
    public double recharge(ElectricVehicle vehicle, int kwsRecharging) {
        if (!isCompatible(vehicle)) {
            return 0.0;
        }
        free = false;
        double cost = calculateCost(kwsRecharging);
        amountCollected += cost;
        addEvRecharged(vehicle);
        free = true;
        return cost;
    }

    /**
     * Devuelve una representación en texto del cargador con su identificador,
     * velocidad, tarifa y número de vehículos recargados.
     *
     * @return Cadena con la información principal del cargador.
     */
    @Override
    public String toString() {
        return String.format(Locale.US,
            "(%s: %s, %skwh, %.1f€, %d, %.2f€)",
            getClass().getSimpleName(),
            id,
            formatSpeed(),
            chargingFee,
            eVsRecharged.size(),
            amountCollected);
    }

    /**
     * Formatea la velocidad de carga para eliminar decimales innecesarios.
     *
     * @return Cadena con la velocidad de carga formateada.
     */
    private String formatSpeed() {
        if (chargingSpeed == (long) chargingSpeed) {
            return String.format(Locale.US, "%d", (long) chargingSpeed);
        }
        return String.format(Locale.US, "%.1f", chargingSpeed);
    }

    /**
     * Devuelve una representación completa del cargador, incluyendo los detalles
     * de todos los {@link ElectricVehicle} que ha recargado.
     *
     * @return Cadena con la información completa del cargador y su historial de
     *         uso.
     */
    public String getCompleteInfo() {
        String info = "Charger ID: " + id + "\n" +
            "Speed: " + chargingSpeed + " kWh" + "\n" +
            "Fee: " + chargingFee + " €/kWh" + "\n" +
            "Amount collected: " + amountCollected + " €" + "\n" +
            "Status: " + (free ? "Free" : "Occupied") + "\n"+
            "Number of EVs recharged: " + eVsRecharged.size() + "\n";
        return info;
    }

    /**
     * @return El número total de {@link ElectricVehicle} recargados por este cargador.
     */
    public int getNumerEVRecharged() {
        return eVsRecharged.size();
    }

    /**
     * @return {@code true} si el cargador está libre, {@code false} en caso contrario.
     */
    public boolean isFree() {
        return free;
    }

    /**
     * Actualiza el estado de disponibilidad del cargador.
     *
     * @param free {@code true} si queda libre, {@code false} si está ocupado.
     */
    public void setFree(boolean free) {
        this.free = free;
    }

    /**
     * @return El identificador único del cargador.
     */
    public String getId() {
        return id;
    }

    /**
     * @return El importe total recaudado por este cargador.
     */
    public double getAmountCollected() {
        return amountCollected;
    }
    
    public void setAmountCollected( double amount){
        this.amountCollected = amount;
    }

    /**
     * @return Lista inmodificable con los vehículos que han sido recargados.
     */
    public List<ElectricVehicle> getRechargedVehicles() {
        return Collections.unmodifiableList(eVsRecharged);
    }
    
    @Override
    public boolean equals(Object otroObjeto) {
        boolean esIgual = false;

        if (this == otroObjeto) {
            esIgual = true;
        } else {
            if (otroObjeto != null && getClass() == otroObjeto.getClass()) {
                Charger otroCharger = (Charger) otroObjeto;
    
                if (id == null) {
                    esIgual = otroCharger.id == null;
                } else {
                    esIgual = id.equals(otroCharger.id);
                }
            }
        }

        return esIgual;
    }
}
