package es.upm.dit.adsw.lab3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Un laberinto.
 *
 * @author Jose A. Manas
 * @version 21/3/2012
 */
public class Laberinto {
    private final int lado;
    private final GUI gui;

    private final Monitor monitor;

    private final Celda[][] celdas;
    private final List<Marciano> marcianos = new ArrayList<Marciano>();
    private final Jugador jugador;

    /**
     * Constructor.
     *
     * @param lado numero de celdas en vertical y en horizontal.
     * @param gui  para pintar en la ventana.
     */
    public Laberinto(int lado, GUI gui) {
        this.lado = lado;
        this.gui = gui;

        monitor = new Monitor();

        celdas = new Celda[lado][lado];
        for (int x = 0; x < lado; x++) {
            for (int y = 0; y < lado; y++) {
                celdas[x][y] = new Celda(x, y);
            }
        }
        for (int x = 0; x < lado; x++) {
            for (int y = 0; y < lado; y++) {
                Celda celda = celdas[x][y];
                if (0 < x)
                    celda.conecta(Direccion.OESTE, celdas[x - 1][y]);
                if (x + 1 < lado)
                    celda.conecta(Direccion.ESTE, celdas[x + 1][y]);
                if (0 < y)
                    celda.conecta(Direccion.SUR, celdas[x][y - 1]);
                if (y + 1 < lado)
                    celda.conecta(Direccion.NORTE, celdas[x][y + 1]);
            }
        }

        Celda salida = getCelda(0, 0);
        Celda meta = getCelda(lado - 1, lado - 1);
        jugador = new Jugador(this, salida, meta);

        generaMinimo();

        // tira algunas paredes mas ...
        int nmas = lado;
        while (nmas > 0) {
            Celda celda1 = getCelda(PuntoXY.random(lado));
            Direccion direccion = Direccion.random();
            Celda celda2 = celda1.getCelda(direccion);
            if (celda2 != null && celda1.hayPared(celda2)) {
//                System.out.printf("quitaPared(%s, %s)%n", celda1, celda2);
                Celda.quitaPared(celda1, celda2);
                nmas--;
            }
        }
    }

    /**
     * Algoritmo de PRIM.
     * Quita el numero minimo de paredes para que todas las celdas esten conectadas.
     */
    private void generaMinimo() {
        Celda origen = getCelda(PuntoXY.random(lado));
//        origen.setEstado(Color.CYAN);

        Set<Celda> enganchadas = new HashSet<Celda>();
        enganchadas.add(origen);

        java.util.List<Pared> paredes = new ArrayList<Pared>();
        paredes.addAll(origen.getParedes());

        while (paredes.size() > 0) {
            int random = (int) (paredes.size() * Math.random());
            Pared pared = paredes.remove(random);
            Celda celda1 = pared.getCelda1();
            Celda celda2 = pared.getCelda2();
            if (celda2 == null)
                continue;
            if (!enganchadas.contains(celda2)) {
                Celda.quitaPared(celda1, celda2);
                enganchadas.add(celda2);
                for (Pared pared1 : celda2.getParedes()) {
                    if (!paredes.contains(pared))
                        paredes.add(pared1);
                }
            }
        }
    }

    /**
     * Getter.
     *
     * @return numero de celdas en horizontal o en vertical.
     */
    public int getLado() {
        return lado;
    }

    /**
     * Getter.
     *
     * @return el jugador.
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * Getter.
     *
     * @return el monitor que controla movimientos.
     */
    public Monitor getMonitor() {
        return monitor;
    }

    /**
     * Mete un marciano en la lista.
     *
     * @param marciano nuevo marciano.
     */
    public void add(Marciano marciano) {
        marcianos.add(marciano);
    }

    /**
     * Getter.
     *
     * @return lista de marcianos vivos.
     */
    public List<Marciano> getMarcianos() {
        return marcianos;
    }

    /**
     * Getter.
     *
     * @param punto un punto (fila, columna).
     * @return celda en ese punto.
     */
    public Celda getCelda(PuntoXY punto) {
        return celdas[punto.getX()][punto.getY()];
    }

    /**
     * Getter.
     *
     * @param x abscisa (fila).
     * @param y ordenada (columna).
     * @return celda en ese punto.
     */
    public Celda getCelda(int x, int y) {
        return celdas[x][y];
    }

    /**
     * Busca una conexion desde la celda1 en la direccion indicada.
     *
     * @param celda1    celda desde la que queremos movernos.
     * @param direccion direccion en la que queremos movernos.
     * @return NULL si nos salimos del laberinto o si hay una pared.<br>
     *         Si podemos movernos, devuelve la celda a la que vamos.
     */
    public Celda getConexion(Celda celda1, Direccion direccion) {
        if (celda1.hayPared(direccion))
            return null;
        Celda celda2 = celda1.getCelda(direccion);
        if (celda2 == null)
            return null;
        return celda2;
    }

    /**
     * Le dice a la interfaz de usuario que seria conveniente repintar.
     */
    public void pinta() {
        gui.pintame();
    }

    /**
     * Se acabo el juego: el jugador ha llegado a la meta fijada.
     */
    public void jugadorGana() {
        gui.fin("jugador gana");
    }

    /**
     * Se acabo el juego: el jugador ha tropezado con un bicho.
     */
    public void jugadorPierde() {
        gui.fin("jugador pierde");
    }

}
