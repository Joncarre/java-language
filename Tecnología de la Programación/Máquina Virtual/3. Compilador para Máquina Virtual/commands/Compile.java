package commands;

import main.Engine;
import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;

public class Compile implements Command {

	@Override
	public void execute(Engine engine) throws ArrayException, LexicalAnalysisException {
		try {
			engine.executeCompile();	
		} catch (ArrayException a) {
			throw a;
		} catch (LexicalAnalysisException b) {
			throw b;
		}
	}
	@Override
	public Command parse(String[] s) {
		if(s.length == 1 && s[0].equalsIgnoreCase(("COMPILE")))
			return new Compile();
		else
			return null;
	}
	@Override
	public String textHelp() {
		return "	COMPILE: Genera un nuevo ByteCode program tras realizar el análisis léxico " +
					System.getProperty("line.separator");
	}
	/**
	 * Imprime "COMPILE"
	 */
	public String toString(){
		return "COMPILE";
	}
}
