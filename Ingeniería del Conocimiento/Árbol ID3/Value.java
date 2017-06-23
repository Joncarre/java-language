
public class Value {
	
	private Valor valor;
	private float a;
	private float cuentaPositivos;
	private float cuentaNegativos;
	private float p;
	private float n;
	private int total; // Nos dice cuántos elementos hay en la 'ListaValues'
	private float r;
	
	/**
	 * Constructora
	 * @param valor
	 */
	public Value(Valor valor) {
		this.valor = valor;
		this.a = 0;
		this.cuentaPositivos = 0;
		this.cuentaNegativos = 0;
		this.p = 0;
		this.n = 0;
		this.total = 0;
		this.r = 0;
	}
	
	/**
	 * Retorna valor
	 * @return
	 */
	public Valor getValor(){
		return this.valor;
	}
	
	/**
	 * Retorna la cuenta de positivos
	 * @return
	 */
	public float getPositivos(){
		return this.cuentaPositivos;
	}
	
	/**
	 * Retorna la cuenta de negativos
	 * @return
	 */
	public float getNegativos(){
		return this.cuentaNegativos;
	}
	
	/**
	 * Retorna A
	 * @return
	 */
	public float getA(){
		return this.a;
	}
	
	/**
	 * Retorna P
	 * @return
	 */
	public float getP(){
		return this.p;
	}
	
	/**
	 * Retorna N
	 * @return
	 */
	public float getN(){
		return this.n;
	}
	
	/**
	 * Retorna el total
	 * @return
	 */
	public int getTotal(){
		return this.total;
	}
	
	/**
	 * Retorna R
	 * @return
	 */
	public float getR(){
		return this.r;
	}
	
	/**
	 * Modifica la cuenta de positivos
	 * @param positivo
	 */
	public void setPositivos(float positivo){
		this.cuentaPositivos += positivo;
	}
	
	/**
	 * Modifica la cuenta de negativos
	 * @param negativo
	 */
	public void setNegativos(float negativo){
		this.cuentaNegativos += negativo;
	}
	
	/**
	 * Modifica A
	 * @param a
	 */
	public void setA(float a){
		this.a += a;
	}
	
	/**
	 * Modifica P
	 * @param p
	 */
	public void setP(float p){
		this.p = p/this.a;
	}
	
	/**
	 * Modifica N
	 * @param n
	 */
	public void setN(float n){
		this.n = n/this.a;
	}
	
	/**
	 * Modifica el total
	 * @param total
	 */
	public void setTotal(int total){
		this.total = total;
	}
	
	/**
	 * Modifica R
	 */
	public void setR(){
		this.r = this.a/this.total;
	}
}
