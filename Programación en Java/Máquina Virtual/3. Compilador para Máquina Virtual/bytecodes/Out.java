package bytecodes;

import cpu.CPU;
import exceptions.StackException;

public class Out implements ByteCode {

	@Override
	public void execute(CPU cpu) throws StackException {
		try {
			cpu.out();
		} catch (StackException e) {
			throw e;
		}
	}
	@Override
	public ByteCode parse(String[] s) {
		if(s.length == 1 && s[0].equalsIgnoreCase(("OUT")))
			return new Out();
		else
			return null;
	}
	/**
	 * Imprime "OUT"
	 */
	public String toString(){
		return "OUT";
	}
}
