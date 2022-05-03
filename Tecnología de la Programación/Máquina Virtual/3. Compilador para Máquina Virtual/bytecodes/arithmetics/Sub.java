package bytecodes.arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Sub extends Arithmetics {

	@Override
	protected void executeAux(int c, int sc, CPU cpu) throws StackException{
		try {
			cpu.push(sc - c);
		} catch (StackException e) {
			throw new StackException("EX: en SUB, tamaño insuficiente de pila");
		}
	}
	@Override
	protected ByteCode parseAux(String s) {
		if(s.equalsIgnoreCase(("SUB")))
			return new Sub();
		else
			return null;
	}
	/**
	 * Imprime "SUB"
	 */
	public String toStringAux(){
		return "SUB";
	}
}
