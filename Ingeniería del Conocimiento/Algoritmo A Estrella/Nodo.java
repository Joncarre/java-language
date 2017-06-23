
public class Nodo {
	
	private TipoLista tipoLista;
	private int fila;
	private int columna;
	private Ficha nodo;
	private int filaPadre;
	private int columnaPadre;
	private Ficha nodoPadre;
	private int filaMeta;
	private int columnaMeta;
	private Ficha nodoMeta;
	private double hn; // Función h(n)
	private double gn; // Función g(n)
	private double fn; // Función f(n)
	
	/**
	 * Constructora con parámetros
	 * @param fila
	 * @param columna
	 */
	public Nodo(int fila, int columna){
		this.fila = fila;
		this.columna = columna;
		this.nodo = Ficha.VACIA;
		this.nodoMeta = Ficha.VACIA;
		this.tipoLista = TipoLista.DESCONOCIDO;
		this.hn = 0;
		this.gn = 0;
		this.fn = 0;
		this.filaMeta = -1;
		this.columnaMeta = -1;
	}
	
	/**
	 * 
	 * @return
	 */
	public TipoLista getTipoLista(){
		return this.tipoLista;		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFila(){
		return this.fila;		
	}
	
	/**
	 * 
	 * @return
	 */
	public int getColumna(){
		return this.columna;
	}
	
	/**
	 * 
	 * @return
	 */
	public Ficha getFichaNodo(){
		return this.nodo;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFilaPadre(){
		return this.filaPadre;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getColumnaPadre(){
		return this.columnaPadre;
	}
	
	/**
	 * 
	 * @return
	 */
	public Ficha getFichaNodoPadre(){
		return this.nodoPadre;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getFilaMeta(){
		return this.filaMeta;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getColumnaMeta(){
		return this.columnaMeta;
	}
	
	/**
	 * 
	 * @return
	 */
	public Ficha getFichaNodoMeta(){
		return this.nodoMeta;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getHn(){
		return this.hn;		
	}
	
	/**
	 * 
	 * @return
	 */
	public double getGn(){
		return this.gn;		
	}
	
	/**
	 * 
	 * @return
	 */
	public double getFn(){
		return this.fn;		
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipoLista(TipoLista tipo){
		this.tipoLista = tipo;		
	}
	
	/**
	 * 
	 * @param fila
	 */
	public void setFila(int fila){
		this.fila = fila;		
	}
	
	/**
	 * 
	 * @param columna
	 */
	public void setColumna(int columna){
		this.columna = columna;
	}
	
	/**
	 * 
	 * @param ficha
	 */
	public void setFichaNodo(Ficha ficha){
		this.nodo = ficha;
	}
	
	/**
	 * 
	 * @param filaPadre
	 */
	public void setFilaPadre(int filaPadre){
		this.filaPadre = filaPadre;
	}
	
	/**
	 * 
	 * @param columnaPadre
	 */
	public void setColumnaPadre(int columnaPadre){
		this.columnaPadre = columnaPadre;
	}
	
	/**
	 * 
	 * @param ficha
	 */
	public void setFichaNodoPadre(Ficha ficha){
		this.nodoPadre = ficha;
	}
	
	/**
	 * 
	 * @param filaMeta
	 */
	public void setFilaMeta(int filaMeta){
		this.filaMeta = filaMeta;
	}
	
	/**
	 * 
	 * @param columnaMeta
	 */
	public void setColumnaMeta(int columnaMeta){
		this.columnaMeta = columnaMeta;
	}
	
	/**
	 * 
	 * @param ficha
	 */
	public void setFichaNodoMeta(Ficha ficha){
		this.nodoMeta = ficha;
	}
	
	/**
	 * 
	 * @param fila
	 * @param columna
	 * @param filaPadre
	 * @param columnaPadre
	 * @param hnPadre
	 */
	public void setHn(int fila, int columna, int filaPadre, int columnaPadre, double hnPadre){
		this.hn = Math.sqrt(Math.pow(filaPadre-fila, 2)+Math.pow(columnaPadre-columna, 2)) + hnPadre;		
	}
	
	/**
	 * 
	 * @param fila
	 * @param columna
	 * @param filaMeta
	 * @param columnaMeta
	 */
	public void setGn(int fila, int columna, int filaMeta, int columnaMeta){
		this.gn = Math.sqrt(Math.pow(filaMeta-fila, 2)+Math.pow(columnaMeta-columna, 2));		
	}
	
	/**
	 * 
	 */
	public void setFn(){
		this.fn = getHn() + getGn();		
	}
	
	/**
	 * 
	 * @param hn
	 */
	public void setHn(double hn){
		this.hn = hn;
	}
	
	/**
	 * 
	 * @param gn
	 */
	public void setGn(double gn){
		this.gn = gn;
	}	
}
