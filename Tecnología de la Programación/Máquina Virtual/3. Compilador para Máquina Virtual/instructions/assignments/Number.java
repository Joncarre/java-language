package instructions.assignments;

import bytecodes.ByteCode;
import bytecodes.oneparameter.Push;

public class Number implements Term {
	private int number;
	
	/**
	 * Constructora
	 * @param n
	 */
	public Number(int n){
		this.number = n;
	}
	@Override
	public Term parse(String term){
		if(this.isNumeric(term))
			return new Number(Integer.parseInt(term));
		else
			return null;
	}
	@Override
	public ByteCode compile(cpu.Compiler compile){
		return new Push(this.number);
	}
	/**
	 * Devuelve el término numérico
	 */
	public String toString(){
		return Integer.toString(this.number);
	}
	/**
	 * Comprueba si un dato es numérico (sacada de Internet)
	 * @param cadena
	 * @return
	 */
	private boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
}
