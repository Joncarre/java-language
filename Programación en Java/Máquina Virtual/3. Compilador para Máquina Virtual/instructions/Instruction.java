package instructions;

import cpu.LexicalParser;
import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;

public interface Instruction {
	Instruction lexParse(String[] words, LexicalParser lexParser) throws LexicalAnalysisException;
	void compile(cpu.Compiler compiler) throws ArrayException;
}
