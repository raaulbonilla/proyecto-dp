import java.util.*;

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Ordena los cargadores priorizando aquellos con mayor velocidad de carga.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparatorSpeed implements Comparator<Charger>
{
    /**
     * Compara dos cargadores a partir de su velocidad máxima de carga.
     *
     * @param c1 Primer cargador a comparar.
     * @param c2 Segundo cargador a comparar.
     * @return Valor negativo si {@code c1} es más rápido, cero si son iguales o positivo en caso contrario.
     */
    public int compare(Charger c1 , Charger c2){
        if (c1.getChargingSpeed() > c2.getChargingSpeed())
            return -1;
        else if (c1.getChargingSpeed() < c2.getChargingSpeed())
            return 1;
        else {
            return 0;
        }
    }
}
