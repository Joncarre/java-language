package bytecodes.arithmetics;

import cpu.CPU;
import exceptions.DivisionByZeroException;
import exceptions.StackException;
import bytecodes.ByteCode;

public abstract class Arithmetics implements ByteCode {
	
	/**
	 * Ejecuta el 'parse' del ByteCode correspondiente 
	 * @throws StackException 
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
	 * @throws StackException 
	 */
	abstract protected ByteCode parseAux(String s);
	/**
	 * Ejecuta el 'execute' del ByteCode correspondiente
	 */
	public void execute(CPU cpu) throws DivisionByZeroException, StackException {
		try {
			int c = cpu.pop();
			int sc = cpu.pop();
			if(cpu.getSizeStack() >= 0){
				this.executeAux(c, sc, cpu);
			}else {
				// Si la operación falla, debemos volver a meter los elementos en la pila y lanzar excepción
				cpu.push(sc);
				cpu.push(c);
				throw new StackException("EX: elementos insuficientes en la pila");
			}
		} catch (DivisionByZeroException a) {
			throw a;
		} catch (StackException b) {
			throw b;
		}
	}
	/**
	 * Método de ejecución auxiliar para las subclases de Arithmetics
	 * @param c
	 * @param sc
	 * @param cpu
	 * @return
	 */
	abstract protected void executeAux(int c, int sc, CPU cpu) throws DivisionByZeroException, StackException;
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
