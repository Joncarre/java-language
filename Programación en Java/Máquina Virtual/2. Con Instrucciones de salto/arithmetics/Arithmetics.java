package arithmetics;

import cpu.CPU;
import bytecodes.ByteCode;

public abstract class Arithmetics extends ByteCode {
	
	/**
	 * Ejecuta el 'parse' del ByteCode correspondiente 
	 */
	public ByteCode parse(String[] fragment){
		if(fragment.length != 1)
			return null;
		else
			return this.parseAux(fragment[0]);
	}
	/**
	 * Método de parseo auxiliar para las subclases de Arithmetics
	 * @param s
	 * @return
	 */
	abstract protected ByteCode parseAux(String s);
	/**
	 * Ejecuta el 'execute' del ByteCode correspondiente
	 */
	public boolean execute(CPU cpu){
		boolean correcta = false;
		if(cpu.getSizeStack() >= 2){
			int c = cpu.pop();
			int sc = cpu.pop();
			if(this.executeAux(c, sc, cpu)){
				correcta = true;
			}else {
				// Si la operación falla, debemos volver a meter los elementos en la pila
				cpu.push(sc);
				cpu.push(c);
				correcta = false;
			}
		}
		return correcta;
	}
	/**
	 * Método de ejecución auxiliar para las subclases de Arithmetics
	 * @param c
	 * @param sc
	 * @param cpu
	 * @return
	 */
	abstract protected boolean executeAux(int c, int sc, CPU cpu);
	/**
	 * Ejecuta el 'toString' del ByteCode correspondiente 
	 */
	public String toString(){
		return this.toStringAux();
	}
	/**
	 * Método auxiliar de imprimir mensajes para las subclases de Arithmetics
	 * @return
	 */
	abstract protected String toStringAux();	
}
