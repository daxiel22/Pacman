package es.upm.dit.adsw.lab3;

/**
 * Excepcion que salta cuando un bicho y un jugador tropiezan.
 *
 * @author Jose A. Manas
 * @version 21/3/2012
 */
public class JugadorComido
        extends Exception {
    /**
     * Constructor. Sin mensaje.
     */
    public JugadorComido() {
    }

    /**
     * Constructor.
     *
     * @param s mensaje explicativo.
     */
    public JugadorComido(String s) {
        super(s);
    }
}
