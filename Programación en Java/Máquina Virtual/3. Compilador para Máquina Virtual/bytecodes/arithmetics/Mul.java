package bytecodes.arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Mul extends Arithmetics {

	@Override
	protected void executeAux(int c, int sc, CPU cpu) throws StackException{
		try {
			cpu.push(sc * c);
		} catch (StackException e) {
			throw new StackException("EX: en MUL, tamaño insuficiente de pila");
		}
	}
	@Override
	protected ByteCode parseAux(String s) {
		if(s.equalsIgnoreCase(("MUL")))
			return new Mul();
		else
			return null;
	}
	/**
	 * Imprime "MUL"
	 */
	public String toStringAux(){
		return "MUL";
	}
}
