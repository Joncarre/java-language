package instructions.assignments;

import bytecodes.ByteCode;
import bytecodes.arithmetics.Add;
import bytecodes.arithmetics.Div;
import bytecodes.arithmetics.Mul;
import bytecodes.arithmetics.Sub;
import bytecodes.oneparameter.Store;
import cpu.LexicalParser;
import exceptions.ArrayException;
import instructions.Instruction;

public class CompoundAssignment implements Instruction{
	private String varName;
	private String operator;
	private Term t1;
	private Term t2;
	
	/**
	 * Constructora
	 * @param varName
	 * @param operator
	 * @param t1
	 * @param t2
	 */
	public CompoundAssignment(String varName, String operator, Term t1, Term t2) {
		this.varName = varName;
		this.operator = operator;
		this.t1 = t1;
		this.t2 = t2;
	}
	@Override
	public Instruction lexParse(String[] words, LexicalParser lexParser) {
		if(words.length != 5){ // Si la cadena no tiene longitud 5
			return null;
		}else{
			char name = words[0].charAt(0);
			if('a' <= name && name <= 'z'){ // Si la variable es una letra del abecedario
				if(words[1].equalsIgnoreCase("=")){ // Si el signo es "="
					char operator  = words[3].charAt(0);
					if(operator == '+' || operator == '-' || operator == '*' || operator == '/'){ // Si el símbolo de 'operator' es válido
						Term t1 = TermParser.parse(words[2]);
						Term t2 = TermParser.parse(words[4]);
						if(t1 == null || t2 == null){ // Si algún término no es correcto
							return null;
						}else{
							return new CompoundAssignment(String.valueOf(name), String.valueOf(operator), t1, t2);
						}
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}
		return null;
	}
	@Override
	public void compile(cpu.Compiler compiler) throws ArrayException {
		try {
			ByteCode bc1 = t1.compile(compiler);
			ByteCode bc2 = t2.compile(compiler);
			// Añadimos los dos términos
			compiler.addByteCode(bc1);
			compiler.addByteCode(bc2);
			// Añadimos el tipo de operación
			if(this.operator.equalsIgnoreCase("+")){
				compiler.addByteCode(new Add());
			}else if(this.operator.equalsIgnoreCase("-")){
				compiler.addByteCode(new Sub());
			}else if(this.operator.equalsIgnoreCase("*")){
				compiler.addByteCode(new Mul());
			}else if(this.operator.equalsIgnoreCase("/")){
				compiler.addByteCode(new Div());
			}
			// Añadimos la variable
			compiler.addByteCode(new Store(compiler.getIndex(this.varName)));
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Devuelve la asignación compuesta en forma de String
	 */
	public String toString(){
		return this.varName + " = " + String.valueOf(this.t1) + " " + this.operator + " " + String.valueOf(this.t2);
	}
}
