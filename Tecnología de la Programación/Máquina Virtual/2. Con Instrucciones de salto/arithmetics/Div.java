package arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;

public class Div extends Arithmetics {

	@Override
	protected boolean executeAux(int c, int sc, CPU cpu) {
		if(c != 0) // Si el divisor es != 0
			return cpu.div(c, sc);
		else
			return false;
	}
	@Override
	protected ByteCode parseAux(String s){
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
