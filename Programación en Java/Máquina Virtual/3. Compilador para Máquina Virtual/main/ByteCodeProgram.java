package main;

import exceptions.ArrayException;
import bytecodes.ByteCode;

public class ByteCodeProgram {
	private ByteCode[] program;
	public static final int MAX_PROGRAM = 80; // Indica el tamaño del array 'this.program'
	private int numElems; // Indica el número de elementos del array 'this.program'

	/**
	 * Constructora
	 */
	public ByteCodeProgram(){
		this.program = new ByteCode[ByteCodeProgram.MAX_PROGRAM];
		this.numElems = 0;
	}
	/**
	 * Inserta un nuevo bytecode al programa en la posición 'pos'
	 * @param bc
	 * @param pos
	 * @return
	 * @throws ArrayException
	 */
	public boolean addByteCode(ByteCode bc, int pos) throws ArrayException{
		if(pos >= 0 && pos < this.numElems){ // Si 'pos' está en el rango adecuado
			this.program[pos] = bc;
			return true;
		}else
			throw new ArrayException("EX: 'pos' fuera de rango al añadir un bytecode");
	}
	/**
	 * Inserta una nueva instrucción en el programa
	 * @param bc
	 * @throws ArrayException
	 */
	public void addNextByteCode(ByteCode bc) throws ArrayException{
		if(this.numElems == ByteCodeProgram.MAX_PROGRAM)
			throw new ArrayException("EX: tamaño máximo (" + ByteCodeProgram.MAX_PROGRAM + ") del 'bcProgram' alcanzado");
		else{
			this.program[this.numElems] = bc;
			this.numElems++;	
		}
	}
	/**
	 * Devuelve la instrucción apuntada por el contador de programa
	 * @param programCounter
	 * @return
	 */
	public ByteCode getInst(int programCounter) throws ArrayException{
		if(programCounter >= 0 && programCounter < this.numElems)
			return this.program[programCounter];
		else
			throw new ArrayException("EX: se pretende devolver una instrucción fuera de rango en 'bcProgram'");
	}
	/**
	 * "Borra" el programa almacenado
	 */
	public void reset(){
		this.numElems = 0;
	}
	/**
	 * Devuelve el tamaño del programa (los elementos que tiene)
	 * @return
	 */
	public int getSize(){
		return this.numElems;
	}
	/**
	 * Imprimir el programa bytecode almacenado
	 */
	public String toString(){
		String mensaje;
		if(this.numElems > 0){
			mensaje = "Programa bytecode almacenado:" + System.getProperty("line.separator");
			for(int i = 0; i < this.numElems; i++)
				mensaje += i + ": " + this.program[i].toString() + System.getProperty("line.separator");
		}else
			mensaje = "";
		return mensaje;
	}
}