package oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;

public class Goto extends OneParameter {
	
	/**
	 * Constructora
	 * @param p
	 */
	public Goto(int p) {
		super(p);
	}
	public Goto() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("GOTO")))
			return new Goto(N);
		else
			return null;
	}
	@Override
	public boolean execute(CPU cpu) {
		return cpu.setProgramCounter(this.param);
	}
	@Override
	protected String toStringAux() {
		return "GOTO";
	}
}
