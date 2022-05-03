package main;

import bytecodes.ByteCode;

public class ByteCodeProgram {
	private ByteCode[] program;
	public static final int MAX_PROGRAM = 200; // Indica el tamaño del array 'this.program'
	private int numElems; // Indica el número de elementos del array 'this.program'

	/**
	 * Constructora
	 */
	public ByteCodeProgram(){
		this.program = new ByteCode[ByteCodeProgram.MAX_PROGRAM];
		this.numElems = 0;
	}
	/**
	 * Imprimir el programa almacenado
	 */
	public String toString(){
		String mensaje;
		if(this.numElems > 0){
			mensaje = "Programa almacenado:" + System.getProperty("line.separator");
			for(int i = 0; i < this.numElems; i++)
				mensaje += i + ": " + this.program[i].toString() + System.getProperty("line.separator");
		}else
			mensaje = "";
		return mensaje;
	}
	/**
	 * Devuelve la instrucción apuntada por el contador de programa
	 * @param programCounter
	 * @return
	 */
	public ByteCode getByteCode(int programCounter){
		return this.program[programCounter];
	}
	/**
	 * Inserta una nueva instrucción en el programa
	 * @param bc
	 */
	public void setInstruction(ByteCode bc){
		this.program[this.numElems] = bc; // Insertamos la intrucción 'bc'
		this.numElems++;
	}
	/**
	 * Inserta una nueva instrucción en la posición 'pos'
	 * @param bc
	 * @param pos
	 */
	public boolean setInstructionPosition(ByteCode bc, int pos){
		if(pos >= 0 && pos < this.numElems){ // Si 'pos' está en el rango adecuado
			this.program[pos] = bc;
			return true;
		}else
			return false;
	}
	/**
	 * "Borra" el programa almacenado
	 */
	public boolean reset(){
		this.numElems = 0;
		return true;
	}
	/**
	 * Devuelve el tamaño del programa (los elementos que tiene)
	 * @return
	 */
	public int getnumElems(){
		return this.numElems;
	}
}