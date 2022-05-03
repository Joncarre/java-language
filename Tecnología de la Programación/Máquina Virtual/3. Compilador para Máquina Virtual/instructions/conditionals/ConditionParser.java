package instructions.conditionals;

import cpu.LexicalParser;

public abstract class ConditionParser {
	private final static Condition[] conditions = {
		new Less(null, null), new LessEq(null, null), new Equal(null, null), new NotEqual(null, null)
	};
	
	/**
	 * Busca un parseo para el operador booleano
	 * @param t1
	 * @param op
	 * @param t2
	 * @param parser
	 * @return
	 */
	public static Condition parse(String t1, String op, String t2, LexicalParser lexParser){
		boolean seguir = true;
		int i = 0;
		Condition cond = null;
		while(i < conditions.length && seguir){
			cond = conditions[i].parser(t1, op, t2, lexParser);
			if(cond != null)
				seguir = false;
			else
				i++;
		}
		return cond;
	}
}
