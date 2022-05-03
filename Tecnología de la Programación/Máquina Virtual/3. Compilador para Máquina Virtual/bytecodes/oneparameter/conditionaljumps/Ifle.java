package bytecodes.oneparameter.conditionaljumps;

import bytecodes.ByteCode;

public class Ifle extends ConditionalJumps {
	/**
	 * Constructora
	 * @param p
	 */
	public Ifle(int p) {
		super(p);
	}
	@Override
	protected boolean compare(int c, int sc) {
		return sc < c;
	}
	@Override
	protected ByteCode parseAux(String s1, String s2) {
		int N = Integer.parseInt(s2);
		if(s1.equalsIgnoreCase(("IFLE")))
			return new Ifle(N);
		else
			return null;
	}
	@Override
	protected String toStringAux() {
		return "IFLE";
	}
}
