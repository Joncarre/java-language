package bytecodes.oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Push extends OneParameter {
	
	/**
	 * Constructora
	 * @param p
	 */
	public Push(int p) {
		super(p);
	}
	@Override
	protected ByteCode parseAux(String s1, String s2){
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("PUSH")))
			return new Push(N);
		else
			return null;
	}
	@Override
	public void execute(CPU cpu) throws StackException {
		try {
			cpu.push(this.param);
		} catch (StackException e) {
			throw e;
		}
	}
	@Override
	protected String toStringAux() {
		return "PUSH";
	}
}
