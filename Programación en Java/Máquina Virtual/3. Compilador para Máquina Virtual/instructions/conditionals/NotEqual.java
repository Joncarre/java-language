package instructions.conditionals;

import bytecodes.oneparameter.conditionaljumps.ConditionalJumps;
import bytecodes.oneparameter.conditionaljumps.Ifneq;
import cpu.LexicalParser;
import instructions.assignments.Term;

public class NotEqual extends Condition{
	
	/**
	 * Constructora
	 * @param t1
	 * @param t2
	 */
	public NotEqual(Term t1, Term t2) {
		super(t1, t2);
	}
	@Override
	protected Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser) {
		if(op.equalsIgnoreCase("!=")){
			return new NotEqual(t1, t2);
		}else
			return null;
	}
	@Override
	protected ConditionalJumps compileOp() {
		return new Ifneq(0);
	}
}
