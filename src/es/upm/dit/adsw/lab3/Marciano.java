package es.upm.dit.adsw.lab3;

public class Marciano extends Thread{
	private final Laberinto laberinto;
    private Celda celda;
    public final int dt;
	/**
	  * Constructor.
	  *
	  * @param laberinto el laberinto en el que se mueve.
	  * @param celda     posición inicial.
	  * @param dt        delta de tiempo para irse moviendo.
	*/
	public Marciano(Laberinto laberinto, Celda celda, int dt) {
		 this.laberinto = laberinto;
	     this.celda = celda;
	     this.dt=dt;
	     
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
	  * Se intenta mover en la dirección indicada.
	  *
	  * @param direccion en la que desea moverse.
	  * @throws InterruptedException si se ve interrumpido.
	  */

	public void intentaMover(Direccion direccion)
	throws InterruptedException {
		try{Celda celda2 = laberinto.getConexion(celda, direccion);
		if (celda2 == null) {
			return;
		}
		Monitor monitor = laberinto.getMonitor();
		monitor.mueveMarciano(this, celda2);
		}
		catch (JugadorComido jugadorComido) {
			laberinto.jugadorPierde();
}
}
	public void setCelda(Celda nueva) {
		Celda anterior = this.celda;
		anterior.setEstado(Estado.VACIA);
		nueva.setEstado(Estado.BICHO);
		this.celda = nueva;
		laberinto.pinta();
}
	public void run() {
		try{
			while (true){
				intentaMover(Direccion.random());
				Thread.sleep(dt);
			}
} 			catch (InterruptedException e){
				return;
			}
		}
}
