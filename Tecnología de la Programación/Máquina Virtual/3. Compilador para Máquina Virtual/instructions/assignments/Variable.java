package instructions.assignments;

import bytecodes.ByteCode;
import bytecodes.oneparameter.Load;


public class Variable implements Term {
	private String varName;
	
	/**
	 * Constructora
	 * @param st
	 */
	public Variable(String st) {
		this.varName = st;
	}
	@Override
	public Term parse(String term) {
		if(term.length() != 1)
			return null;
		else{
			char name = term.charAt(0);
			if('a' <= name && name <= 'z')
				return new Variable(term);
			else
				return null;
		}
	}
	@Override
	public ByteCode compile(cpu.Compiler compile) {
		int indice = compile.getIndex(this.varName); // Buscamos la variable en la tabla
		return new Load(indice); // Y creamos un bytecode load con su índice
	}
	/**
	 * Devuelve el término variable
	 */
	public String toString(){
		return this.varName;
	}
}
