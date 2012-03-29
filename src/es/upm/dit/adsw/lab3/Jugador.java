package es.upm.dit.adsw.lab3;

/**
 * La ficha azul: el jugador.
 *
 * @author Jose A. Manas
 * @version 21/3/2012
 */
public class Jugador extends Thread {
    private final Laberinto laberinto;
    private Celda celda;
    private final Celda meta;

    /**
     * Constructor.
     *
     * @param laberinto el laberinto en el que se mueve.
     * @param salida    posicion inicial.
     * @param meta      posicion objetivo.
     */
    public Jugador(Laberinto laberinto, Celda salida, Celda meta) {
        this.laberinto = laberinto;
        this.celda = salida;
        salida.setEstado(Estado.AZUL);
        this.meta = meta;
    }

    /**
     * Getter.
     *
     * @return donde esta ahora.
     */
    public Celda getCelda() {
        return celda;
    }

    /**
     * Getter.
     *
     * @return posicion objetivo.
     */
    public Celda getMeta() {
        return meta;
    }

    /**
     * Se intenta mover en la direccion indicada.
     *
     * @param direccion en la que desea moverse.
     */
    public void intentaMover(Direccion direccion) {
        try {
            // calculamos la celda a la que iriamos.
            Celda celda2 = laberinto.getConexion(celda, direccion);
            if (celda2 == null) {
                // no hay una celda alcanzable en esa dirección
                return;
            }

            // vamos a ver que hay en el tablero:
            // esto debe estar sincronizado
            // para que las fichas se esten quietas mientras miro
            Monitor monitor = laberinto.getMonitor();
            monitor.mueveJugador(this, celda2);
            // Si llegamos a la meta, se acaba el juego.
            if (celda.equals(meta))
                laberinto.jugadorGana();
        } catch (JugadorComido jugadorComido) {
            //se acabo, hemos tropezado con un bicho
            laberinto.jugadorPierde();
        }
    }

    /**
     * Setter.
     * Ademas, vacia la celda actual y marca un jugador en la nueva.
     *
     * @param nueva a donde va.
     */
    public void setCelda(Celda nueva) {
        Celda anterior = this.celda;
        anterior.setEstado(Estado.VACIA);
        nueva.setEstado(Estado.AZUL);
        this.celda = nueva;
        laberinto.pinta();
    }
}
