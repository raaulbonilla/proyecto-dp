import java.util.*; 

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Compara dos objetos {@link Charger} según su identificador en orden ascendente.
 * Suele emplearse como último criterio de desempate en ordenaciones compuestas.
 *
 * @author DP Clasess
 * @version 2024.10.07
 */
public class ComparatorChargersId implements Comparator<Charger>
{
    /**
     * Compara dos {@link Charger} en función del orden lexicográfico de sus identificadores.
     *
     * @param c1 Primer cargador a comparar.
     * @param c2 Segundo cargador a comparar.
     * @return Número negativo, cero o positivo según el identificador de {@code c1}
     *         sea menor, igual o mayor que el de {@code c2}.
     */
    public int compare(Charger c1, Charger c2){
         String id1 = c1.getId();
         String id2 = c2.getId();

         if (id1.equals(id2)){
             return 0;
         } else return id1.compareTo(id2);
    } 
}
