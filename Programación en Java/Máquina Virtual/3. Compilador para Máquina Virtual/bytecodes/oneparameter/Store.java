package bytecodes.oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Store extends OneParameter {

	/**
	 * Constructora
	 * @param p
	 */
	public Store(int p) {
		super(p);
	}
	@Override
	protected ByteCode parseAux(String s1, String s2){
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("STORE")))
			return new Store(N);
		else
			return null;
	}
	@Override
	public void execute(CPU cpu) throws StackException {
		try {
			cpu.store(this.param);
		} catch (StackException e) {
			throw e;
		}
	}
	@Override
	protected String toStringAux() {
		return "STORE";
	}
}
