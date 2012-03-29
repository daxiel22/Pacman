package es.upm.dit.adsw.lab3;

/**
 * Una pared entre dos celdas.
 *
 * @author Jose A. Manas
 * @version 10/2/2012
 */
public class Pared {
    private final Celda celda1;
    private final Celda celda2;

    /**
     * Constructor.
     *
     * @param celda1 una celda.
     * @param celda2 otra celda.
     */
    public Pared(Celda celda1, Celda celda2) {
        this.celda1 = celda1;
        this.celda2 = celda2;
    }

    /**
     * Getter.
     *
     * @return celda1.
     */
    public Celda getCelda1() {
        return celda1;
    }

    /**
     * Getter.
     *
     * @return celda2.
     */
    public Celda getCelda2() {
        return celda2;
    }

    /**
     * Mensaje para imprimir.
     *
     * @return Mensaje para imprimir.
     */
    public String toString() {
        return String.format("[%s, %s]", celda1, celda2);
    }

    /**
     * Para comparar esta celda con otra.
     *
     * @param o otra celda.
     * @return TRUE si las celdas son equivalentes.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Pared pared = (Pared) o;

        if (celda2 == null) {
            return celda1.equals(pared.celda1) && pared.celda2 == null;
        }

        if (celda1.equals(pared.celda1) && celda2.equals(pared.celda2))
            return true;
        if (celda1.equals(pared.celda2) && celda2.equals(pared.celda1))
            return true;
        return false;
    }

    /**
     * Identficador unico.
     *
     * @return Identficador unico.
     */
    @Override
    public int hashCode() {
        int result = celda1.hashCode();
        if (celda2 != null)
            result = 31 * result + celda2.hashCode();
        return result;
    }
}
