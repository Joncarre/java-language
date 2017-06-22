package instructions.assignments;

public class TermParser {
	private final static Term[] terms = {
		new Variable(""), new Number(0)
	};
	
	/**
	 * Parsea para ver si encuentra algún término de tipo Variable o Number
	 * @param st
	 * @return
	 */
	public static Term parse(String st){
		Term tm;
		for(Term t:terms){
			tm = t.parse(st);
			if(tm != null)
				return tm;
		}
		return null;
	}
}
