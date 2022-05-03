package oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;

public class Store extends OneParameter {

	/**
	 * Constructora
	 * @param p
	 */
	public Store(int p) {
		super(p);
	}
	public Store() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("STORE")))
			return new Store(N);
		else
			return null;
	}
	@Override
	public boolean execute(CPU cpu) {
		return cpu.store(getParam());
	}
	@Override
	protected String toStringAux() {
		return "STORE";
	}
}
