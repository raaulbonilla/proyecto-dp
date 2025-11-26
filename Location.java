import java.util.*;

//CLASE REALIZADA POR: Aaron Becerro

/**
 * Modela una ubicación en una ciudad bidimensional mediante coordenadas (x, y).
 *
 * @author DP classes
 * @version 2024.10.07
 */
public class Location {
    private int x;
    private int y;

    /**
     * Modela una localización dentro de la ciudad.
     *
     * @param x Coordenada X. Debe ser no negativa.
     * @param y Coordenada Y. Debe ser no negativa.
     * @throws IllegalArgumentException Si alguna coordenada es negativa.
     */
    public Location(int x, int y) {
        if (x < 0) {
            throw new IllegalArgumentException(
                    "Negative x-coordinate: " + x);
        }

        if (y < 0) {
            throw new IllegalArgumentException(
                    "Negative y-coordinate: " + y);
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Genera la siguiente localización en línea recta (movimiento tipo Manhattan)
     * desde esta posición hasta el destino.
     *
     * @param destination {@link Location} objetivo.
     * @return La siguiente {@link Location} un paso más cerca del destino, o el propio destino si ya se alcanzó.
     */
    public Location nextLocation(Location destination) {
        int nextX = this.getX();
        int nextY = this.getY();

        if (destination.getX() > this.getX()) {
            nextX++;
        } else if (destination.getX() < this.getX()) {
            nextX--;
        }

        if (destination.getY() > this.getY()) {
            nextY++;
        } else if (destination.getY() < this.getY()) {
            nextY--;
        }
        Location proxima = new Location(nextX, nextY);
        return proxima;
    }

    /**
     * Calcula los movimientos necesarios para llegar al destino empleando la distancia de Chebyshev
     * (máximo de las diferencias absolutas de las coordenadas).
     *
     * @param destination {@link Location} destino.
     * @return Número de pasos requeridos.
     */
    public int distance(Location destination) {
        int posActualX = this.x;
        int posActualY = this.y;

        int posSigX = destination.x;
        int posSigY = destination.y;

        int distanciaX;
        int distanciaY;

        if (posActualX < posSigX) {
            distanciaX = posSigX - posActualX;
        } else {
            distanciaX = posActualX - posSigX;
        }

        if (posActualY < posSigY) {
            distanciaY = posSigY - posActualY;
        } else {
            distanciaY = posActualY - posSigY;
        }

        if (distanciaX > distanciaY) {
            return distanciaX;
        } else {
            return distanciaY;
        }
    }

    /**
     * Implementa la igualdad de contenido entre ubicaciones.
     *
     * @param other Objeto con el que se compara.
     * @return {@code true} si es una {@link Location} con las mismas coordenadas, {@code false} en caso contrario.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX() &&
                    y == otherLocation.getY();
        } else {
            return false;
        }
    }

    /**
     * @return Representación textual de la ubicación en el formato "x-y".
     */
    @Override
    public String toString() {
        return x + "-" + y;
    }

    /**
     * Genera un código hash para la ubicación.
     * Usa los 16 bits superiores para la Y y los 16 inferiores para la X
     * con el fin de obtener valores únicos en la mayoría de cuadrículas.
     *
     * @return Código hash de la ubicación.
     */
    @Override
    public int hashCode() {
        return (y << 16) + x;
    }

    /**
     * @return La coordenada X.
     */
    public int getX() {
        return x;
    }

    /**
     * @return La coordenada Y.
     */
    public int getY() {
        return y;
    }
}