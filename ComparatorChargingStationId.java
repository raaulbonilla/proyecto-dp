import java.util.*; 

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Compara dos {@link ChargingStation} por su identificador en orden ascendente.
 * Resulta útil para ordenar colecciones de estaciones de recarga.
 *
 * @author DP Clasess
 * @version 2023
 */
public class ComparatorChargingStationId implements Comparator<ChargingStation>
{
    /**
     * Compara dos {@link ChargingStation} basándose en el orden lexicográfico de sus identificadores.
     *
     * @param st1 Primera estación a comparar.
     * @param st2 Segunda estación a comparar.
     * @return Número negativo, cero o positivo según el identificador de {@code st1}
     *         sea menor, igual o mayor que el de {@code st2}.
     */
    public int compare(ChargingStation st1, ChargingStation st2){
         String id1 = st1.getId();
         String id2 = st2.getId();

         if (id1.equals(id2)){
             return 0;
         } else return id1.compareTo(id2);
    } 
}
