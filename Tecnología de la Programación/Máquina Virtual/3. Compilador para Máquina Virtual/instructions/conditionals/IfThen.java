package instructions.conditionals;

import main.ParsedProgram;
import cpu.LexicalParser;
import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;
import instructions.Instruction;

public class IfThen implements Instruction{
	private Condition condition;
	private ParsedProgram ifBody;
	
	/**
	 * Constructora
	 * @param cd
	 * @param pP
	 */
	public IfThen(Condition cd, ParsedProgram pP){
		this.condition = cd;
		this.ifBody = pP;
	}	
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException {
		if(words.length == 4 && words[0].equalsIgnoreCase("IF")){
			Condition cond = ConditionParser.parse(words[1], words[2], words[3], lexParser); // Parseamos la condición del if
			ParsedProgram ifBody = new ParsedProgram(); // Creamos un nuevo ParsedProgram para el cuerpo
			try {
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(ifBody, "ENDIF"); // Parseamos el cuerpo del if
			} catch (LexicalAnalysisException e) {
				throw e;
			}
			return new IfThen(cond, ifBody);
		}else
				return null;
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			this.condition.compile(compiler);
			compiler.compile(this.ifBody);
			int jump = compiler.getSizeBcProgram(); // Número actual de bytecode al que tengo saltar
			this.condition.setJump(jump);
		} catch (ArrayException e) {
			throw e;
		}
	}
}
