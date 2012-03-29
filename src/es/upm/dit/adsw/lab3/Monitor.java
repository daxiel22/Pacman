package es.upm.dit.adsw.lab3;

public class Monitor{
	
	/**
	 * Intenta mover el jugador a la celda2.
	 * Si no puede moverse, no pasa nada.
	 * Si puede moverse, pone la celda con setCelda().
	 *
	 * @param jugador referencia al jugador.
	 * @param celda2  celda a la que quiere moverse el jugador.
	 * @throws JugadorComido si tropieza con un marciano.
	 */
	 public synchronized void mueveJugador(Jugador jugador, Celda celda2)
	   throws JugadorComido{
		 if (celda2 == null) {
				return;
		 }
		 if (jugador == null) {
				throw new IllegalArgumentException();
		 }
		 if (celda2.getEstado() == Estado.BICHO) {
				throw new JugadorComido();
			}
			jugador.setCelda(celda2);
		}
	 public synchronized void mueveMarciano(Marciano marciano, Celda celda2)
	   throws JugadorComido, InterruptedException {
		 if (celda2 == null) {
				return;
		 }
		 if (celda2.getEstado() == Estado.AZUL) {
				throw new JugadorComido();
		}
			marciano.setCelda(celda2);
		}

	}

