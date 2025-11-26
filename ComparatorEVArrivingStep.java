import java.util.*; 

//CLASE REALIZADA POR: Alberto Fructos

/**
 * Compara vehículos eléctricos por el turno en el que alcanzan su destino final.
 * Los vehículos que ya han llegado se ordenan antes y, en caso de empate, por matrícula.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ComparatorEVArrivingStep implements Comparator<ElectricVehicle>
{
    /**
     * Establece el orden entre dos vehículos según su turno de llegada y, si es necesario, su matrícula.
     *
     * @param ev1 Primer vehículo a comparar.
     * @param ev2 Segundo vehículo a comparar.
     * @return Resultado de la comparación siguiendo el criterio descrito.
     */
    @Override
    public int compare(ElectricVehicle ev1, ElectricVehicle ev2)
    {
        int step1 = ev1.getArrivingStep();
        int step2 = ev2.getArrivingStep();

        boolean arrived1 = step1 >= 0;
        boolean arrived2 = step2 >= 0;

        if (arrived1 && arrived2) {
            if (step1 != step2) {
                return Integer.compare(step1, step2);
            }
            return ev1.getPlate().compareTo(ev2.getPlate());
        } else if (arrived1) {
            return -1;
        } else if (arrived2) {
            return 1;
        } else {
            return ev1.getPlate().compareTo(ev2.getPlate());
        }
    }
}
