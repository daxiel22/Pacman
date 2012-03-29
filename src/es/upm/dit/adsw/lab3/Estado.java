package es.upm.dit.adsw.lab3;

import java.awt.*;

/**
 * Para marcar el contenido de cada celda.
 *
 * @author Jose A. Manas
 * @version 19/3/2012
 */
public enum Estado {
    VACIA(Color.WHITE), AZUL(Color.BLUE), BICHO(Color.RED);
    private final Color color;

    /**
     * Constructor.
     *
     * @param color para pintar la celda.
     */
    private Estado(Color color) {
        this.color = color;
    }

    /**
     * Getter.
     *
     * @return color para pintar.
     */
    public Color getColor() {
        return color;
    }
}
