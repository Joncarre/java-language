package bytecodes.oneparameter;

import exceptions.StackException;
import bytecodes.ByteCode;

public abstract class OneParameter implements ByteCode {
	protected int param;
	
	/**
	 * Constructora
	 * @param p
	 */
	public OneParameter(int p){
		this.param = p;
	}
	/**
	 * Devuelve el parámetro de la instrucción correspondiente
	 * @return
	 */
	public int getParam(){
		return this.param;
	}
	/**
	 * Ejecuta el 'parse' del ByteCode correspondiente 
	 */
	public ByteCode parse(String[] fragment){
		if(fragment.length != 2)
			return null;
		else
			return this.parseAux(fragment[0], fragment[1]);
	}
	/**
	 * Método de parseo auxiliar para las subclases de OneParameter
	 * @param s1
	 * @param s2
	 * @return
	 * @throws StackException 
	 */
	abstract protected ByteCode parseAux(String s1, String s2);
	/**
	 * Ejecuta el 'toString' del ByteCode correspondiente 
	 */
	public String toString(){
		return this.toStringAux() + " " + this.param;
	}
	/**
	 * Método auxiliar de imprimir mensajes para las subclases de OneParameter
	 * @return
	 */
	abstract protected String toStringAux();	
}
