package main;

import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;
import instructions.Instruction;

public class ParsedProgram {
	private Instruction[] pProgram;
	public static final int MAX_PROGRAM = 25;
	private int numElems;
	
	/**
	 * Constructora
	 */
	public ParsedProgram(){
		this.pProgram = new Instruction[ParsedProgram.MAX_PROGRAM];
		this.numElems = 0;
	}
	/**
	 * Añade una instrucción al programa parseado
	 * @param inst
	 * @throws ArrayException
	 */
	public void addInst(Instruction inst) throws LexicalAnalysisException{
		if(this.numElems == ParsedProgram.MAX_PROGRAM)
			throw new LexicalAnalysisException("EX: tamaño máximo (" + ParsedProgram.MAX_PROGRAM + ") del 'pProgram' alcanzado");
		else{
			this.pProgram[this.numElems] = inst;
			this.numElems++;
		}
	}
	/**
	 * Devuelve la instrucción k-ésima del programa parseado
	 * @param k
	 * @return
	 * @throws ArrayException 
	 */
	public Instruction getInst(int k) throws ArrayException{
		if(k >= 0 && k < this.numElems)
			return this.pProgram[k];
		else
			throw new ArrayException("EX: se pretende devolver una instrucción fuera de rango en 'pProgram'");
	}
	/**
	 * Devuelve el tamaño actual del programa parseado
	 * @return
	 */
	public int getSize(){
		return this.numElems;
	}
	/**
	 * "Borra" el programa parseado almacenado
	 */
	public void reset(){
		this.numElems = 0;
	}
	/**
	 * Indica si no hay programa parseado
	 * @return
	 */
	public boolean isEmpty(){
		return this.numElems == 0;
	}
	/**
	 * Imprime el programa parseado almacenado
	 */
	public String toString(){
		return "";
	}
}
