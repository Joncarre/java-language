package instructions.conditionals;

import bytecodes.oneparameter.Goto;
import main.ParsedProgram;
import cpu.LexicalParser;
import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;
import instructions.Instruction;

public class While implements Instruction{
	private Condition condition;
	private ParsedProgram whileBody;
	
	/**
	 * Constructora
	 * @param cd
	 * @param pP
	 */
	public While(Condition cd, ParsedProgram pP){
		this.condition = cd;
		this.whileBody = pP;
	}
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException {
		if(words.length == 4 && words[0].equalsIgnoreCase("WHILE")){
			Condition cond = ConditionParser.parse(words[1], words[2], words[3], lexParser); // Parseamos la condición del while
			ParsedProgram whileBody = new ParsedProgram(); // Creamos un nuevo ParsedProgram para el cuerpo
			try {
				lexParser.increaseProgramCounter();
				lexParser.lexicalParser(whileBody, "ENDWHILE"); // Parseamos el cuerpo del while
			} catch (LexicalAnalysisException e) {
				throw e;
			}
			return new While(cond, whileBody);
		}else
				return null;
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			int comeback = compiler.getSizeBcProgram(); // Guardamos la dirección a la que hay que volver tras cada iteración
			this.condition.compile(compiler);
			compiler.compile(this.whileBody);
			int jump = compiler.getSizeBcProgram(); // Número actual de bytecode al que tengo saltar
			this.condition.setJump(jump + 1); // +1 porque hay que saltar a la siguiente después de salir del while
			compiler.addByteCode(new Goto(comeback));
		} catch (ArrayException e) {
			throw e;
		}

	}
}
