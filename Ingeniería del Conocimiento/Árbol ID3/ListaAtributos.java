
public class ListaAtributos {

	private static final int TAMANIO = 4;
	private Attribute lista[];
	private int contador;
	
	/**
	 * Constructora
	 */
	public ListaAtributos(){
		this.lista = new Attribute[TAMANIO];
		this.contador = 0;
	}
	
	/**
	 * Retorna la lista
	 * @param contador
	 * @return
	 */
	public Attribute getLista(int contador){
		return this.lista[contador];
	}
	
	/**
	 * Retorna el contador
	 * @return
	 */
	public int getContador(){
		return this.contador;
	}
	
	/**
	 * Inserta un atributo
	 * @param atributo
	 */
	public void insertar(String atributo){
		Attribute attrib = new Attribute(atributo);
		this.lista[contador] = attrib;
		this.contador++;
	}
}
