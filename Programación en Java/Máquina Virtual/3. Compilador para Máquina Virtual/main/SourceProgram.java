package main;

import exceptions.ArrayException;
import exceptions.LexicalAnalysisException;

public class SourceProgram {
	private String[] sProgram;
	public static final int MAX_PROGRAM = 30;
	private int numElems;
	
	/**
	 * Constructora
	 */
	public SourceProgram(){
		this.sProgram = new String[SourceProgram.MAX_PROGRAM];
		this.numElems = 0;
	}
	/**
	 * Añade una instrucción al programa fuente
	 * @param inst
	 * @throws ArrayException
	 */
	public void addInst(String inst) throws ArrayException{
		if(this.numElems == SourceProgram.MAX_PROGRAM)
			throw new ArrayException("EX: tamaño máximo (" + SourceProgram.MAX_PROGRAM + ") del 'sProgram' alcanzado");
		else{
			this.sProgram[this.numElems] = inst;
			this.numElems++;
		}
	}
	/**
	 * Devuelve la instrucción k-ésima del programa fuente
	 * @param k
	 * @return
	 * @throws ArrayException 
	 */
	public String getInst(int k) throws LexicalAnalysisException{
		if(k >= 0 && k < this.numElems)
			return this.sProgram[k];
		else
			throw new LexicalAnalysisException("EX: se pretende devolver una instrucción fuera de rango en 'sProgram'");
	}
	/**
	 * Devuelve el tamaño actual del programa fuente
	 * @return
	 */
	public int getSize(){
		return this.numElems;
	}
	/**
	 * "Borra" el programa fuente almacenado
	 */
	public void reset(){
		this.numElems = 0;
	}
	/**
	 * Indica si no hay programa fuente
	 * @return
	 */
	public boolean isEmpty(){
		return this.numElems == 0;
	}
	/**
	 * Imprime el programa fuente almacenado
	 */
	public String toString(){
		String mensaje;
		if(this.numElems > 0){
			mensaje = "Programa fuente almacenado:" + System.getProperty("line.separator");
			for(int i = 0; i < this.numElems; i++){
				mensaje += i + ": " + this.sProgram[i] + System.getProperty("line.separator");
			}
		}else
			mensaje = "";
		return mensaje;
	}
}
