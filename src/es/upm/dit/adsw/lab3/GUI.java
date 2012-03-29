package es.upm.dit.adsw.lab3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Interfaz gráfica.
 *
 * @author Jose A. Manas
 * @version 21.3.2012
 */
public class GUI
        extends JPanel {
    /**
     * Nombre del juego.
     */
    public static final String TITULO = "Laberintos";

    /**
     * Espacio entre la zona de juego y el borde de la ventana.
     */
    private static final int MARGEN = 10;
    /**
     * Ancho de la zona de juego.
     */
    private static final int ANCHO = 500;
    /**
     * Tamano de una celda: pixels.
     */
    private int celda;

    /**
     * Para que el usuario indique el tamano del laberinto.
     */
    private final JTextField campoLado;

    /**
     * El laberinto.
     */
    private Laberinto laberinto;

    private void nuevoJuego(int lado) {
        celda = (ANCHO - 2 * MARGEN) / lado;
        laberinto = new Laberinto(lado, this);
        pintame();
    }

    private GUI(Container container) {
        nuevoJuego(15);

        setPreferredSize(new Dimension(ANCHO, ANCHO));
        container.add(this, BorderLayout.CENTER);
        setFocusable(true);
        requestFocusInWindow();

        campoLado = new JTextField(5);
        campoLado.setAlignmentX(JTextField.RIGHT_ALIGNMENT);
        campoLado.setText(String.valueOf(laberinto.getLado()));
        campoLado.setMaximumSize(campoLado.getPreferredSize());

        JToolBar toolBarS = new JToolBar();
        toolBarS.setFloatable(false);
        toolBarS.add(campoLado);
        toolBarS.add(new CreaAction());
        toolBarS.add(new MarcianoAction());
        toolBarS.add(Box.createHorizontalGlue());
        container.add(toolBarS, BorderLayout.SOUTH);

        addKeyListener(new MyKeyListener());

        repaint();
    }

    /**
     * Constructor.
     *
     * @param applet Applet.
     */
    public GUI(JApplet applet) {
        this(applet.getContentPane());
    }

    /**
     * Constructor.
     *
     * @param frame Pantalla en consola.
     */
    public GUI(JFrame frame) {
        this(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Le dice al thread de swing que deberia refrescar la pantalla.
     * Swing lo hara cuando le parezca bien.
     */
    public void pintame() {
        repaint();
    }

    /**
     * Llamada por java para pintarse en la pantalla.
     *
     * @param g sistema grafico 2D para dibujarse.
     */
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.LIGHT_GRAY);
        int nwx = MARGEN;
        int nwy = MARGEN;
        int lado = laberinto.getLado();

        // pinta la celda destino
        pintaMeta(g);

        // pinta las demas celdas
        for (int x = 0; x < lado; x++) {
            for (int y = 0; y < lado; y++) {
                pintaCelda(g, x, y);
            }
        }

        // pinta el marco
        g.setColor(Color.BLACK);
        g.drawLine(nwx - 1, nwy - 1, nwx - 1, nwy + lado * celda + 1);
        g.drawLine(nwx + lado * celda + 1, nwy - 1, nwx + lado * celda + 1, nwy + lado * celda + 1);
        g.drawLine(nwx - 1, nwy - 1, nwx + lado * celda + 1, nwy - 1);
        g.drawLine(nwx - 1, nwy + lado * celda + 1, nwx + lado * celda + 1, nwy + lado * celda + 1);
    }

    private void pintaMeta(Graphics g) {
        PuntoXY objetivo = laberinto.getJugador().getMeta().getPunto();
        g.setColor(Color.YELLOW);
        int swx = sw_x(objetivo.getX());
        int nwy = sw_y(objetivo.getY() + 1);
        g.fillRect(swx, nwy, celda, celda);
    }

    /**
     * Pinta una celda.
     *
     * @param g sistema grafico 2D para dibujarse.
     * @param x columna.
     * @param y fila.
     */
    private void pintaCelda(Graphics g, int x, int y) {
        Celda celda = laberinto.getCelda(x, y);

        // pinta las paredes de la celda
        g.setColor(Color.RED);
        if (celda.hayPared(Direccion.NORTE))
            g.drawLine(sw_x(x), sw_y(y + 1), sw_x(x + 1), sw_y(y + 1));
        if (celda.hayPared(Direccion.SUR))
            g.drawLine(sw_x(x), sw_y(y), sw_x(x + 1), sw_y(y));
        if (celda.hayPared(Direccion.ESTE))
            g.drawLine(sw_x(x + 1), sw_y(y), sw_x(x + 1), sw_y(y + 1));
        if (celda.hayPared(Direccion.OESTE))
            g.drawLine(sw_x(x), sw_y(y), sw_x(x), sw_y(y + 1));

        Estado estado = celda.getEstado();
        if (estado != null && estado != Estado.VACIA)
            rellenaCelda(g, x, y, estado.getColor());
    }

    /**
     * Pinta el contenido de una celda.
     *
     * @param g     sistema grafico 2D para dibujarse.
     * @param x     columna.
     * @param y     fila.
     * @param color para rellenar.
     */
    private void rellenaCelda(Graphics g, int x, int y, Color color) {
        int nwx = sw_x(x) + 3;
        int nwy = sw_y(y + 1) + 3;
        int dx = this.celda - 6;
        int dy = this.celda - 6;
        g.setColor(color);
        g.fillOval(nwx, nwy, dx, dy);
    }

    /**
     * Dada una columna, calcula el vertice inferior izquierdo.
     *
     * @param columna columna.
     * @return abscisa del vertice inferior izquierdo.
     */
    private int sw_x(int columna) {
        return MARGEN + columna * celda;
    }

    /**
     * Dada una fila, calcula el vertice inferior izquierdo.
     *
     * @param fila fila.
     * @return vertice inferior izquierdo.
     */
    private int sw_y(int fila) {
        int lado = laberinto.getLado();
        return MARGEN + (lado - fila) * celda;
    }

    /**
     * Pone fin a un juego: imprime un mensaje y genera un juego nuevo.
     *
     * @param msg mensaje explicativo.
     */
    public void fin(String msg) {
        JOptionPane.showMessageDialog(this,
                msg, "Laberinto",
                JOptionPane.INFORMATION_MESSAGE);
        for (Marciano marciano : laberinto.getMarcianos())
            marciano.interrupt();
        nuevoJuego(laberinto.getLado());
    }

    /**
     * Interprete del boton NUEVO.
     */
    private class CreaAction
            extends AbstractAction {
        /**
         * Constructor.
         */
        public CreaAction() {
            super("Nuevo");
        }

        /**
         * Se hace cargo de la pulsacion.
         *
         * @param event evento que dispara la accion.
         */
        public void actionPerformed(ActionEvent event) {
            for (Marciano marciano : laberinto.getMarcianos())
                marciano.interrupt();
            int lado = Integer.parseInt(campoLado.getText());
            nuevoJuego(lado);
            requestFocus();
        }
    }

    /**
     * Interprete del boton MARCIANO.
     */
    private class MarcianoAction
            extends AbstractAction {
        /**
         * Constructor.
         */
        public MarcianoAction() {
            super("marciano");
        }

        /**
         * Se hace cargo de la pulsacion.
         *
         * @param event evento que dispara la accion.
         */
        public void actionPerformed(ActionEvent event) {
            int lado = laberinto.getLado();
            PuntoXY punto = PuntoXY.random(lado);
            Celda celda = laberinto.getCelda(punto);
            Marciano marciano = new Marciano(laberinto, celda, 100);
            laberinto.add(marciano);
            marciano.start();
            requestFocus();
        }
    }

    /**
     * Captura el teclado.
     */
    private class MyKeyListener
            extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            Direccion direccion = getDireccion(event);
            if (direccion != null)
                laberinto.getJugador().intentaMover(direccion);
        }

        private Direccion getDireccion(KeyEvent ke) {
            if (ke.getKeyCode() == KeyEvent.VK_UP)
                return Direccion.NORTE;
            if (ke.getKeyCode() == KeyEvent.VK_DOWN)
                return Direccion.SUR;
            if (ke.getKeyCode() == KeyEvent.VK_RIGHT)
                return Direccion.ESTE;
            if (ke.getKeyCode() == KeyEvent.VK_LEFT)
                return Direccion.OESTE;
            return null;
        }
    }
}
