package bytecodes;

import cpu.CPU;

public class Halt extends ByteCode {
	
	@Override
	public boolean execute(CPU cpu) {
		cpu.halt();
		return true;
	}
	@Override
	public ByteCode parse(String[] s) {
		if(s.length == 1 && s[0].equalsIgnoreCase(("HALT")))
			return new Halt();
		else
			return null;
	}
	/**
	 * Imprime "HALT"
	 */
	public String toString(){
		return "HALT";
	}
}
