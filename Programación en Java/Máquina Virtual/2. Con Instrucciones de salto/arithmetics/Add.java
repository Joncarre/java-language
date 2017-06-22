package arithmetics;

import bytecodes.ByteCode;
import cpu.CPU;

public class Add extends Arithmetics {
	
	@Override
	protected boolean executeAux(int c, int sc, CPU cpu) {
		return cpu.add(c, sc);
	}
	@Override
	protected ByteCode parseAux(String s){
		if(s.equalsIgnoreCase(("ADD")))
			return new Add();
		else
			return null;
	}
	/**
	 * Imprime "ADD"
	 */
	public String toStringAux(){
		return "ADD";
	}
}
