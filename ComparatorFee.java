import java.util.*;

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Ordena cargadores según la tarifa aplicada, priorizando las tarifas más altas.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparatorFee implements Comparator<Charger>
{
    /**
     * Compara dos cargadores a partir de su tarifa por kWh.
     *
     * @param c1 Primer cargador a comparar.
     * @param c2 Segundo cargador a comparar.
     * @return Valor negativo si {@code c1} tiene una tarifa mayor, cero si son iguales
     *         o positivo si su tarifa es inferior.
     */
    public int compare(Charger c1 , Charger c2){
        if (c1.getChargingFee() > c2.getChargingFee())
            return -1;
        else if (c1.getChargingFee() < c2.getChargingFee())
            return 1;
        else {
            return 0;
        }
    }
}
