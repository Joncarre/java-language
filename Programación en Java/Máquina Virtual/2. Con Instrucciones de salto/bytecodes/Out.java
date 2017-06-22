package bytecodes;

import cpu.CPU;

public class Out extends ByteCode {

	@Override
	public boolean execute(CPU cpu) {
		cpu.out();
		return true;
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
		return "HALT";
	}
}
