import java.util.*;

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Ordena vehículos eléctricos por su matrícula en orden ascendente.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparatorPlate implements Comparator<ElectricVehicle>
{
    /**
     * Compara dos vehículos empleando el identificador asignado como matrícula.
     *
     * @param ev1 Primer vehículo a comparar.
     * @param ev2 Segundo vehículo a comparar.
     * @return Valor negativo si la matrícula de {@code ev1} precede a la de {@code ev2},
     *         cero si son iguales o positivo en caso contrario.
     */
    public int compare(ElectricVehicle ev1, ElectricVehicle ev2){
        if (ev1.getId().equals(ev2.getId()))
            return 0;
        else if (ev1.getId().compareTo(ev2.getId()) < 0) 
            return -1; 
        else {
            return 1; 
        }
    }
}
