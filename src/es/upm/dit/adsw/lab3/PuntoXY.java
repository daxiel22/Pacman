package es.upm.dit.adsw.lab3;

/**
 * Un punto en 2 dimensiones.
 *
 * @author Jose A. Manas
 * @version 10/2/2012
 */
public class PuntoXY {
    private final int x;
    private final int y;

    /**
     * Constructor.
     *
     * @param x abscisa (columna).
     * @param y ordenada (fila).
     */
    public PuntoXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter.
     *
     * @return abscisa (columna).
     */
    public int getX() {
        return x;
    }

    /**
     * Setter.
     *
     * @return ordenada (fila).
     */
    public int getY() {
        return y;
    }

    /**
     * Mensaje para imprimir.
     *
     * @return Mensaje para imprimit.
     */
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    /**
     * Genera un punto aleatorio dentro de un cuadrado.
     *
     * @param lado lado del cuadrado.
     * @return un punto aleatorio.
     */
    public static PuntoXY random(int lado) {
        int x = (int) (lado * Math.random());
        int y = (int) (lado * Math.random());
        return new PuntoXY(x, y);
    }

    /**
     * Compara THIS con otro objeto.
     *
     * @param o otro objeto.
     * @return TRUE si somos equivalentes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PuntoXY q = (PuntoXY) o;

        return x == q.x && y == q.y;

    }

    /**
     * Identificador unico.
     *
     * @return identificador unico.
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
