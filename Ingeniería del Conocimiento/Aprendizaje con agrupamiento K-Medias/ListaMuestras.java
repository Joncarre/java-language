
public class ListaMuestras {
	
	private static final int TAM = 100;
	Muestra lista [];
	int contador;
	
	/**
	 * Inicializa la lista de muestras
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
	 * Añade un valor a la lista
	 * @param valor
	 */
	public void anadirValor (Muestra valor){
		this.lista[contador] = valor;
		contador++;
	}
	
	/**
	 * Retorna el valor de la lista en la posición
	 * @param posicion
	 * @return
	 */
	public Muestra getMuestra(int posicion){
		return lista[posicion];
	}
}
