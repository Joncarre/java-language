package bytecodes.arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.DivisionByZeroException;
import exceptions.StackException;

public class Div extends Arithmetics {

	@Override
	protected void executeAux(int c, int sc, CPU cpu) throws DivisionByZeroException, StackException {
		if(c != 0) // Si el divisor es != 0
			try {
				cpu.push(sc / c);
			} catch (StackException e) {
				throw new StackException("EX: en DIV, tamaño insuficiente de pila");
			}
		else
			throw new DivisionByZeroException("EX: el divisor no puede ser cero");
	}
	@Override
	protected ByteCode parseAux(String s) {
		if(s.equalsIgnoreCase(("DIV")))
			return new Div();
		else
			return null;
	}
	/**
	 * Imprime "DIV"
	 */
	public String toStringAux(){
		return "DIV";
	}
}
