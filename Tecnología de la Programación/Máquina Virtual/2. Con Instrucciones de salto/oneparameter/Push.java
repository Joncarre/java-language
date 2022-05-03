package oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;

public class Push extends OneParameter {
	
	/**
	 * Constructora
	 * @param p
	 */
	public Push(int p) {
		super(p);
	}
	public Push() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("PUSH")))
			return new Push(N);
		else
			return null;
	}
	@Override
	public boolean execute(CPU cpu) {
		return cpu.push(getParam());
	}
	@Override
	protected String toStringAux() {
		return "PUSH";
	}
}
