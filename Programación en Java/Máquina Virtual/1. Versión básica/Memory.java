

public class Memory {
	public static final int MAX_MEMORY = 200;
	private Integer[] memory;
	private int size;
	private boolean empty;
	
	/**
	 * Constructora
	 */
	public Memory(){
		this.memory = new Integer[Memory.MAX_MEMORY];
		this.size = 0;
		this.empty = true;
	}
	/**
	 * Imprimir Memoria
	 */
	public String toString(){
		String mensaje = "  Memoria:";
		boolean vacia = true;
		for(int i = 0; i < this.size; i++){
			if(this.memory[i] != null){
				mensaje += " [" + i + "]:" + this.memory[i];
				vacia = false;
			}
		}
		if(vacia)
			mensaje += " <vacia>";
		return mensaje;
	}
	/**
	 * Escribe en memoria
	 * @param pos
	 * @param value
	 * @return
	 */
	public boolean write(int pos, int value){
		if(pos >= 0){ // Si pos >= 0 (si estaba ocupada se sobreescribe)
			this.resize(pos); // Comprobamos si hay que redimensionar
			this.memory[pos] = value;
			return true;
		}else
			return false;
	}
	/**
	 * Lee de memoria
	 * @param pos
	 * @return
	 */
	public int read(int pos){
		this.resize(pos); // Puede que la posición a leer sea superior que el tamaño de la memoria
		if(this.memory[pos] == null) // Si 'pos' no ha sido escrita
			return -1;
		else
			return this.memory[pos];
	}
	/**
	 * Redimensiona el array 'memory'
	 * @param pos
	 */
	public void resize(int pos){
		this.empty = false;
		if(pos >= this.size){ // Si 'pos' supera el tamaño actual
			Integer[] memoryAux = new Integer[pos * 2]; // Aumentamos el tamaño al doble
			for(int i = 0; i < pos * 2; i++)
				memoryAux[i] = null; // Inicializamos las posiciones a 'null'
			for(int i = 0; i < this.size; i++)
				memoryAux[i] = this.memory[i]; // Copiamos el valor en el nuevo array
			this.memory = memoryAux;
			this.size = pos * 2;
		}
	}
}