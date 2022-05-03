package instructions;

import bytecodes.Halt;
import cpu.LexicalParser;
import exceptions.ArrayException;

public class Return implements Instruction{

	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if(words.length == 1 && words[0].equalsIgnoreCase("RETURN"))
			return new Return();
		else
			return null;
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			compiler.addByteCode(new Halt());
		} catch (ArrayException e) {
			throw e;
		}
	}
}
