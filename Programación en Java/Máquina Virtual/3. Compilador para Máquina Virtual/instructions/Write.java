package instructions;

import bytecodes.Out;
import bytecodes.oneparameter.Load;
import cpu.LexicalParser;
import exceptions.ArrayException;

public class Write implements Instruction{
	private String varName;
	
	/**
	 * Constructora
	 * @param varName
	 */
	public Write(String varName){
		this.varName = varName;
	}
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if(words.length == 2 && words[0].equalsIgnoreCase("WRITE")){
			char name = words[1].charAt(0);
			if('a' <= name && name <= 'z'){ // Si la variable es una letra del abecedario	
				return new Write(String.valueOf(name));
			}else
				return null;
		}
		else
			return null;
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			int index = compiler.getIndex(this.varName);
			compiler.addByteCode(new Load(index));
			compiler.addByteCode(new Out());
		} catch (ArrayException e) {
			throw e;
		}
	}
}
