package instructions.conditionals;

import cpu.LexicalParser;
import exceptions.ArrayException;
import bytecodes.ByteCode;
import bytecodes.oneparameter.conditionaljumps.ConditionalJumps;
import instructions.assignments.Term;
import instructions.assignments.TermParser;

public abstract class Condition {
	private Term term1;
	private Term term2;
	protected ConditionalJumps cj; // Para la compilación
	
	/**
	 * Constructora
	 * @param t1
	 * @param t2
	 */
	public Condition(Term t1, Term t2){
		this.term1 = t1;
		this.term2 = t2;
	}
	/**
	 * Parsea la condición completa (términos y operando)
	 * @param t1
	 * @param op
	 * @param t2
	 * @param parser
	 * @return
	 */
	public Condition parser(String t1, String op, String t2, LexicalParser lexParser){
		this.term1 = TermParser.parse(t1);
		this.term2 = TermParser.parse(t2);
		
		if(this.term1 != null && this.term2 != null){ // Si el parseo de los términos es correcto
			return this.parseOp(term1, op, term2, lexParser);
		}else {
			return null;
		}
	}
	/**
	 * Método de parseo auxiliar para las subclases de Condition
	 * @param t1
	 * @param op
	 * @param t2
	 * @param lexParser
	 * @return
	 */
	protected abstract Condition parseOp(Term t1, String op, Term t2, LexicalParser lexParser);
	/**
	 * Lleva a cabo la compilación de la condición completa (términos y operando)
	 * @param compiler
	 * @throws ArrayException
	 */
	public void compile(cpu.Compiler compiler) throws ArrayException{
		try {
			ByteCode bc1 = this.term1.compile(compiler);
			ByteCode bc2 = this.term2.compile(compiler);
			// Añadimos los términos de la condición
			compiler.addByteCode(bc1);
			compiler.addByteCode(bc2);
			// Creamos y añadimos el ConditionalJump (cuyo salto será modificado al terminar el cuerpo)
			this.cj = compileOp();
			compiler.addByteCode(this.cj);
		} catch (ArrayException e) {
			throw e;
		}
	}
	/**
	 * Método auxiliar para la compilación de los 'ConditionalJumps'
	 * @return
	 */
	protected abstract ConditionalJumps compileOp();
	/**
	 * Modifica el salto de un objeto de la clase 'ConditionalJumps'
	 * @param jump
	 */
	public void setJump(int jump){
		this.cj.setJump(jump);
	}
}
