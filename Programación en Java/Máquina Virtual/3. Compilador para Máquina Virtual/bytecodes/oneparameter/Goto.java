package bytecodes.oneparameter;

import bytecodes.ByteCode;
import cpu.CPU;
import exceptions.StackException;

public class Goto extends OneParameter {
	
	/**
	 * Constructora
	 * @param p
	 */
	public Goto(int p) {
		super(p);
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
	public void execute(CPU cpu)throws StackException{
		try {
			cpu.setProgramCounter(this.param);
		} catch (StackException e) {
			throw e;
		}
	}
	@Override
	protected String toStringAux() {
		return "GOTO";
	}
}
