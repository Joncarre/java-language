package arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;

public class Mul extends Arithmetics {

	@Override
	protected boolean executeAux(int c, int sc, CPU cpu) {
		return cpu.mul(c, sc);
	}
	@Override
	protected ByteCode parseAux(String s){
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
