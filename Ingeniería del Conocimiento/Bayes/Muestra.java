
public class Muestra {
	
	private static final int TAMANIO = 4;
    private double[] arrayValores;
    private String tipo;    
    private int contador;
    
    /**
     * Constructora
     */
    public Muestra(){
        
        this.arrayValores= new double[TAMANIO];
        this.contador = 0;
    }
    
    /**
     * Retorna el valor
     * @param posicion
     * @return
     */
    public double getValor(int posicion){
        return this.arrayValores[posicion];
    }
    
    /**
     * Retorna el tipo
     * @return
     */
    public String getTipo(){
        return this.tipo;
    }
    
    /**
     * Añade un nuevo valor
     * @param valor
     */
    public void setValor(double valor){
        this.arrayValores[this.contador] = valor;
        this.contador++;
    }
    
    /**
     * Modifica el tipo
     * @param tipo
     */
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
}
