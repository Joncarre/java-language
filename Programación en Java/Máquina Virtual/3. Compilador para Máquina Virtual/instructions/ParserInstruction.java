package instructions;

import cpu.LexicalParser;
import exceptions.LexicalAnalysisException;
import instructions.assignments.CompoundAssignment;
import instructions.assignments.SimpleAssignment;
import instructions.conditionals.IfThen;
import instructions.conditionals.While;

public class ParserInstruction {
	private final static Instruction[] instructions = {
		new SimpleAssignment(null, null), new CompoundAssignment(null, null, null, null),
		new Write(""), new Return(), new While(null, null), new IfThen(null, null)
	};
	
	/**
	 * Busca un posible parseo entre las subclases de Instruction
	 * @param line
	 * @param lexParser
	 * @return
	 * @throws LexicalAnalysisException 
	 */
	public static Instruction parse(String line, LexicalParser lexParser) throws LexicalAnalysisException{
		line = line.trim(); // Elimina caracteres en blanco
		String[] fragment = line.split(" +"); // Divide 'line' y lo mete en el array 'fragment'
		if(fragment.length == 0)
			return null;
		else{
			int i = 0;
			boolean seguir = true;
			Instruction inst = null;
			while(i < instructions.length && seguir){
				inst = instructions[i].lexParse(fragment, lexParser);
				if(inst != null)
					seguir = false;
				else
					i++;
			}
			return inst;
		}
	}
}
