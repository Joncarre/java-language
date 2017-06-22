package oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;

public class Load extends OneParameter {
	
	/**
	 * Constructora
	 * @param p
	 */
	public Load(int p) {
		super(p);
	}
	public Load() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("LOAD")))
			return new Load(N);
		else
			return null;
	}
	@Override
	public boolean execute(CPU cpu) {
		return cpu.read(getParam());
	}
	@Override
	protected String toStringAux() {
		return "LOAD";
	}
}
