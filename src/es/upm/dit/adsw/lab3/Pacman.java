package es.upm.dit.adsw.lab3;

/**
 * Lanza la aplicacion grafica, bien como applet o como aplicacion autocontenida.
 *
 * @author Jose A. Manas
 * @version 11/2/2012
 */

import javax.swing.*;

public class Pacman
        extends JApplet {

    /**
     * Inicia el applet.
     */
    public void init() {
        new GUI(this);
    }

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame(GUI.TITULO);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        new GUI(frame);
    }
}