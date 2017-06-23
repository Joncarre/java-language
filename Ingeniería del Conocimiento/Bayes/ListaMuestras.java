
public class ListaMuestras {
	
	private static final int TAM = 100;
	int contador;
	Muestra lista [];

	/**
	 * Constructora
	 * @param tamanio
	 */
	public ListaMuestras(int tamanio) {
		this.lista = new Muestra[tamanio];
		this.contador = 0;
	}
	
	/**
	 * Retorna el contador
	 * @return
	 */
	public int getContador() {
		return contador;
	}
	
	/**
	 * Añade un nuevo valor
	 * @param valor
	 */
	public void anadirValor (Muestra valor){
		this.lista[contador] = valor;
		contador++;
	}
	
	/**
	 * Retorna la muestra
	 * @param posicion
	 * @return
	 */
	public Muestra getMuestra(int posicion){
		return lista[posicion];
	}
}
