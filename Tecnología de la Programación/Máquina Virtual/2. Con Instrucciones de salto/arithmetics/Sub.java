package arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;

public class Sub extends Arithmetics {

	@Override
	protected boolean executeAux(int c, int sc, CPU cpu) {
		return cpu.sub(c, sc);
	}
	@Override
	protected ByteCode parseAux(String s){
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
