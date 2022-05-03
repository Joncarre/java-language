package instructions.conditionals;

import bytecodes.oneparameter.conditionaljumps.ConditionalJumps;
import bytecodes.oneparameter.conditionaljumps.Ifeq;
import cpu.LexicalParser;
import instructions.assignments.Term;

public class Equal extends Condition{
	
	/**
	 * Constructora
	 * @param t1
	 * @param t2
	 */
	public Equal(Term t1, Term t2){
		super(t1, t2);
	}
	@Override
	protected Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser) {
		if(op.equalsIgnoreCase("=")){
			return new Equal(t1, t2);
		}else
			return null;
	}
	@Override
	protected ConditionalJumps compileOp() {
		return new Ifeq(0);
	}
}
