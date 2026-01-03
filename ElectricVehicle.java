import java.util.List;
import java.util.Locale;
import java.util.Iterator;

//CLASE REALIZADA POR: Alberto Fructos

/**
 * Modela los elementos comunes de un vehículo eléctrico dentro de la
 * simulación,
 * que avanza hacia un destino y puede necesitar recargar.
 *
 * @author David J. Barnes and Michael Kölling
 * @author DP classes
 * @version 2024.10.07
 */
public class ElectricVehicle {
    private String plate;
    private String name;
    private EVCompany company;
    private Location location;
    private Location targetLocation;
    private int idleCount;
    private int batteryCapacity;
    private int batteryLevel;
    private int kwhCharged;
    private int chargesCount;
    private double chargesCost;
    private Location chargingTarget;
    private boolean arrived;
    private Integer arrivalTurn;
    private VehicleTier type;

    /**
     * Constructor de la clase {@code ElectricVehicle}.
     *
     * @param company         Empresa a la que pertenece el vehículo. No debe ser
     *                        {@code null}.
     * @param location        {@link Location} inicial del vehículo. No debe ser
     *                        {@code null}.
     * @param targetLocation  {@link Location} destino final. No debe ser
     *                        {@code null}.
     * @param name            Nombre del vehículo.
     * @param plate           Matrícula identificativa.
     * @param batteryCapacity Capacidad máxima de la batería.
     * @throws NullPointerException Si {@code company}, {@code location} o
     *                              {@code targetLocation} son nulos.
     */
    public ElectricVehicle(EVCompany company, Location location, Location targetLocation, String name, String plate,
            int batteryCapacity) {
        this.company = company;
        this.location = location;
        this.targetLocation = targetLocation;
        this.name = name;
        this.plate = plate;
        this.batteryCapacity = batteryCapacity;
        this.batteryLevel = batteryCapacity;
        this.idleCount = 0;
        this.kwhCharged = 0;
        this.chargesCount = 0;
        this.chargesCost = 0.0;
        this.chargingTarget = null;
        this.arrived = false;
        this.arrivalTurn = null;
        this.type = null;
    }
    public void setType(VehicleTier type){
        this.type = type;
    }
    public VehicleTier getType (){
        return this.type;
    }
   
    public EVCompany getCompany() {
    return company;
    }

    public void setRechargingLocation(Location location) {
    this.chargingTarget = location;
    }
    
    /**
     * @return Identificador del vehículo (matrícula).
     */
    public String getId() {
        return this.plate;
    }

    /**
     * Obtiene la ubicación actual del vehículo.
     *
     * @return Localización en la que se encuentra actualmente.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @return La matrícula del vehículo.
     */
    public String getPlate() {
        return plate;
    }
    /**
     * Actualiza la ubicación actual del vehículo.
     *
     * @param location Nueva localización. No debe ser {@code null}.
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Obtiene la localización objetivo final.
     *
     * @return Destino al que se dirige el vehículo.
     */
    public Location getTargetLocation() {
        return targetLocation;
    }

    /**
     * Obtiene la localización temporal para recargar.
     *
     * @return {@link Location} de la próxima {@link ChargingStation} que visitará,
     *         o
     *         {@code null} si no hay recarga planificada.
     */
    public Location getRechargingLocation() {
        return chargingTarget;
    }

    /**
     * Obtiene el turno en el que el vehículo alcanzó su destino final.
     *
     * @return Turno de llegada o {@code -1} si aún no ha llegado.
     */
    public int getArrivingStep() {
        if (arrivalTurn != null)
            return arrivalTurn;
        else
            return -1;
    }

    /**
     * Establece la localización objetivo final.
     *
     * @param location Destino al que se desplazará. No debe ser {@code null}.
     */
    public void setTargetLocation(Location location) {
        this.targetLocation = location;
    }

    /**
     * Calcula la ruta óptima del vehículo.
     * Si la batería no permite llegar al destino intenta localizar una
     * {@link ChargingStation}
     * intermedia y la establece como {@code rechargingLocation}.
     */
    public void calculateRoute() {
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

                if (llegoAEstacion) {
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
                } else {
                    {
                        // no llego a la estacion, entonces no hace nada
                    }
                }
            }
        }
        
        if (mejor == null) {
            if (fallback != null) {
                mejor = fallback;
            }
        }
        
        if (mejor != null) {
            chargingTarget = mejor.getLocation();
        } else {
            chargingTarget = null;
        }
    }

    /**
     * Obtiene una representación textual de la ruta planificada, incluyendo la
     * parada de recarga si existe.
     *
     * @return Cadena con el recorrido en forma
     *         {@code origen -> [recarga ->] destino}.
     */
    public String getStringRoute() {
        String actual = location.getX() + "-" + location.getY();
        String destino = targetLocation.getX() + "-" + targetLocation.getY();

        if (chargingTarget != null && !chargingTarget.equals(location)) {
            String estacion = chargingTarget.getX() + "-" + chargingTarget.getY();
            return "(" + actual + ", " + estacion + ", " + destino + ")";
        } else {
            return "(" + actual + ", " + destino + ")";
        }
    }

    /**
     * Comprueba si el nivel de batería actual permite cubrir una distancia
     * determinada.
     *
     * @param distanceToTargetLocation Distancia que se desea verificar.
     * @return {@code true} si la batería es suficiente, {@code false} en caso
     *         contrario.
     */
    public boolean enoughBattery(int distanceToTargetLocation) {
        int energiaNecesaria = distanceToTargetLocation * EVDemo.COSTEKM;

        return batteryLevel >= energiaNecesaria;
    }

    /**
     * Determina la mejor {@link ChargingStation} intermedia en caso de no poder
     * alcanzar el destino directamente.
     * Establece {@code rechargingLocation} con la ubicación elegida.
     */
    public void calculateRechargingPosition() {
        calculateRoute();
    }

    /**
     * Comprueba si el vehículo tiene planificada una parada de recarga.
     *
     * @return {@code true} si existe parada de recarga, {@code false} en caso
     *         contrario.
     */
    public boolean hasRechargingLocation() {
        boolean has;
        has = false;

        if (chargingTarget != null) {
            has = true;
        }
        return has;
    }

    /**
     * @return Número de turnos que el vehículo ha permanecido inactivo.
     */
    public int getIdleCount() {
        return idleCount;
    }

    /**
     * @return Número total de recargas realizadas por el vehículo.
     */
    public int getChargesCount() {
        return chargesCount;
    }

    /**
     * Incrementa el número de turnos en los que el vehículo permanece inactivo.
     */
    public void incrementIdleCount() {
        idleCount = idleCount + 1;
    }

    /**
     * Calcula la distancia estilo Manhattan hasta la siguiente localización
     * objetivo.
     *
     * @return Distancia pendiente hasta el objetivo.
     */
    public int distanceToTheTargetLocation() {
        int distancia;

        if (chargingTarget != null) {
            distancia = location.distance(chargingTarget);
        } else {
            distancia = location.distance(targetLocation);
        }
        return distancia;
    }

    /**
     * Simula el proceso de recarga cuando el vehículo llega a una
     * {@code rechargingLocation}.
     * La batería se llena, se calcula el coste y se vuelve a generar la ruta.
     *
     * @param step Turno actual de la simulación.
     */
    public void recharge(int step) {
        ChargingStation estacion = company.getChargingStation(location);

        if (estacion != null) {
            // Usa el selector para permitir criterios específicos por subclase.
            Charger cargador = selectChargerForRecharge(estacion);
            if (cargador != null) {
                int energiaNecesaria = batteryCapacity - batteryLevel;
                if (energiaNecesaria > 0) {
                    double coste = cargador.recharge(this, energiaNecesaria);
                    if (coste > 0) {
                        batteryLevel = batteryCapacity;
                        incrementCharges();
                        kwhCharged = kwhCharged + energiaNecesaria;
                        incrementChargesCost(coste);
                        chargingTarget = null;
                        calculateRoute();

                        System.out.println(String.format(Locale.US,
                                "(step: %d - %s: %s recharges: %dkwh at %s: %s with cost: %.2f€ ********)",
                                step,
                                getClass().getSimpleName(),
                                plate,
                                energiaNecesaria,
                                cargador.getClass().getSimpleName(),
                                cargador.getId(),
                                coste));
                    } else {
                        incrementIdleCount();
                    }
                } else {
                    incrementIdleCount();
                    chargingTarget = null;
                    calculateRoute();
                }
            } else {
                incrementIdleCount();
            }
        }
    }
    
    /**
     * Selecciona el cargador a utilizar dentro de una estación.
     * Se puede sobrescribir en subclases para aplicar criterios específicos.
     *
     * @param estacion Estación donde recargar.
     * @return Cargador elegido o {@code null} si no hay compatible libre.
     */
    protected Charger selectChargerForRecharge(ChargingStation estacion) {
        return estacion.getFirstCompatibleCharger(this);
    }

    /**
     * Incrementa el contador de recargas realizadas por el vehículo.
     */
    public void incrementCharges() {
        chargesCount = chargesCount + 1;
    }    
    
    /**
     * Añade un importe al coste total acumulado de recargas.
     *
     * @param cost Coste de la última recarga.
     */
    public void incrementChargesCost(double cost) {
        chargesCost = chargesCost + cost;
    }

    /**
     * Gestiona la lógica cuando el vehículo alcanza su objetivo.
     * Determina si llega al destino final o a una estación de carga,
     * actualizando el estado del vehículo o iniciando la recarga según corresponda.
     *
     * @param objetivo Ubicación objetivo alcanzada.
     * @param step     Turno actual de la simulación.
     */
    private void processArrivalAt(Location objetivo, int step) {
        if (objetivo.equals(targetLocation)) {
            if (!arrived) {
                arrived = true;
                if (arrivalTurn == null) {
                    arrivalTurn = step;
                }
                System.out.println(String.format(
                        Locale.US,
                        "(step: %d - %s: %s at target destination ********)",
                        step,
                        getClass().getSimpleName(),
                        plate));
            }
        } else {
            if (chargingTarget != null) {
                if (objetivo.equals(chargingTarget)) {
                    recharge(step);
                }
            }
        }
    }

    /**
     * Ejecuta un turno de acciones del vehículo.
     * Avanza un paso hacia su objetivo (de recarga o final) o permanece inactivo.
     *
     * @param step Turno actual de la simulación.
     */
    public void act(int step) {
        if (arrived) {
            incrementIdleCount();
        } else {
            Location objetivo;
            if (chargingTarget != null) {
                objetivo = chargingTarget;
            } else {
                objetivo = targetLocation;
            }
            
            if (location.equals(objetivo)) {
                processArrivalAt(objetivo, step);
            } else {
                Location siguiente = location.nextLocation(objetivo);
                setLocation(siguiente);

                batteryLevel = batteryLevel - EVDemo.COSTEKM;
                if (batteryLevel < 0) {
                    batteryLevel = 0;
                }
                
                if (siguiente.equals(objetivo)) {
                    processArrivalAt(objetivo, step);
                }
            }
        }
    }

    /**
     * Reduce el nivel de batería aplicando el coste de un movimiento
     * ({@link EVDemo#COSTEKM}).
     * Asegura que el nivel de batería no sea negativo.
     */
    public void reduceBatteryLevel() {
        batteryLevel = batteryLevel - EVDemo.COSTEKM;

        if (batteryLevel < 0) {
            batteryLevel = 0;
        }
    }

    /**
     * Devuelve una representación detallada del vehículo eléctrico.
     *
     * @return Cadena con el nombre, matrícula, batería, recargas, costes,
     *         inactividad y ruta.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(")
                .append(getClass().getSimpleName())
                .append(": ")
                .append(name).append(", ")
                .append(plate).append(", ")
                .append(batteryCapacity).append("kwh, ")
                .append(batteryLevel).append("kwh, ")
                .append(chargesCount).append(", ")
                .append(String.format(Locale.US, "%.2f€", chargesCost)).append(", ")
                .append(idleCount).append(", ")
                .append(location);

        if (chargingTarget != null && !chargingTarget.equals(location)) {
            sb.append(", ").append(chargingTarget);
        }

        sb.append(", ").append(targetLocation).append(')');
        return sb.toString();
    }

    /**
     * Genera una cadena con la información del vehículo precedida por el número de
     * turno actual.
     *
     * @param step Turno de la simulación.
     * @return Cadena formateada para el registro del turno.
     */
    public String getStepInfo(int step) {
        String info = toString();

        if (info.startsWith("(") && info.endsWith(")")) {
            info = info.substring(1, info.length() - 1);
        }
        return "(step: " + step + " - " + info + ")";
    }

    /**
     * Devuelve la información inicial o final del vehículo para su uso en
     * resúmenes.
     *
     * @return Cadena producida por {@link #toString()}.
     */
    public String getInitialFinalInfo() {
        return toString();
    }

    /**
     * @return Capacidad máxima de la batería.
     */
    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    /**
     * @return Nivel actual de carga de la batería.
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * Incrementa el nivel de batería en la cantidad indicada.
     *
     * @param battery Energía a añadir al nivel actual.
     */
    public void addBatteryLevel(int battery) {
        batteryLevel = batteryLevel + battery;
    }
}