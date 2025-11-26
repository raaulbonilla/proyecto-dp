import java.util.*; 

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Compara dos {@link ChargingStation} principalmente por la cantidad de
 * {@link ElectricVehicle} recargados en orden descendente.
 * Si el número de recargas coincide, utiliza {@link ComparatorChargingStationId}
 * para desempatar por identificador en orden ascendente.
 *
 * @author DP Clasess
 * @version 2023
 */
public class ComparatorChargingStationNumberRecharged implements Comparator<ChargingStation>
{
    /**
     * Compara dos estaciones según su número de recargas y, en caso de empate, por identificador.
     *
     * @param st1 Primera estación a comparar.
     * @param st2 Segunda estación a comparar.
     * @return Valor negativo si {@code st1} tiene más recargas o un mismo número pero identificador menor;
     *         cero si son equivalentes; valor positivo en caso contrario.
     */
    public int compare(ChargingStation st1, ChargingStation st2){
         if (st1.getNumerEVRecharged() > st2.getNumerEVRecharged())
            return -1;
        else if (st1.getNumerEVRecharged() < st2.getNumerEVRecharged())
            return 1;
        else return (new ComparatorChargingStationId().compare(st1,st2));
    }
}
