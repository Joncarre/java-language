package instructions.conditionals;

import bytecodes.oneparameter.conditionaljumps.ConditionalJumps;
import bytecodes.oneparameter.conditionaljumps.Ifle;
import cpu.LexicalParser;
import instructions.assignments.Term;

public class Less extends Condition{
	
	/**
	 * Constructora
	 * @param t1
	 * @param t2
	 */
	public Less(Term t1, Term t2) {
		super(t1, t2);
	}
	@Override
	protected Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser) {
		if(op.equalsIgnoreCase("<")){
			return new Less(t1, t2);
		}else
			return null;
	}
	@Override
	protected ConditionalJumps compileOp() {
		return new Ifle(0);
	}
}
