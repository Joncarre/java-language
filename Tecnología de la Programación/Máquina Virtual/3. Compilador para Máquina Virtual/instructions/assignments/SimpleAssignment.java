package instructions.assignments;

import bytecodes.ByteCode;
import bytecodes.oneparameter.Store;
import cpu.LexicalParser;
import exceptions.ArrayException;
import instructions.Instruction;

public class SimpleAssignment implements Instruction{
	private String varName;
	private Term rhs;
	
	/**
	 * Constructora
	 * @param varName
	 * @param rhs
	 */
	public SimpleAssignment(String varName, Term rhs){
		this.varName = varName;
		this.rhs = rhs;
	}
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if(words.length != 3) // Si la cadena no tiene longitud 3
			return null;
		else{
			char name = words[0].charAt(0);
			if('a' <= name && name <= 'z'){ // Si la variable es una letra del abecedario
				if(words[1].equalsIgnoreCase("=")){ // Si el signo es "="
					Term rhs = TermParser.parse(words[2]);
					if(rhs == null)
						return null;
					else{
						return new SimpleAssignment(String.valueOf(name), rhs);
					}	
				}else{
					return null;
				}	
			}else{
				return null;
			}
		}
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			ByteCode bc1 = this.rhs.compile(compiler);
			// Añadimos el término con su variable
			compiler.addByteCode(bc1);
			compiler.addByteCode(new Store(compiler.getIndex(this.varName)));
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Devuelve la asignación simple en forma de String
	 */
	public String toString(){
		return this.varName + " = " + String.valueOf(this.rhs);
	}
}
