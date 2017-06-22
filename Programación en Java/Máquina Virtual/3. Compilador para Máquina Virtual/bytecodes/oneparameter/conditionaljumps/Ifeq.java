package bytecodes.oneparameter.conditionaljumps;

import bytecodes.ByteCode;

public class Ifeq extends ConditionalJumps {
	/**
	 * Constructora
	 * @param p
	 */
	public Ifeq(int p) {
		super(p);
	}
	@Override
	protected boolean compare(int c, int sc) {
		return sc == c;
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("IFEQ")))
			return new Ifeq(N);
		else
			return null;
	}
	@Override
	protected String toStringAux() {
		return "IFEQ";
	}
}
